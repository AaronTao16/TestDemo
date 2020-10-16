package com.example.demo;

import com.example.demo.dto.AirQuality;
import com.example.demo.dto.CONO2Mean;
import com.example.demo.provider.CSVReader;
import com.example.demo.provider.DBConnecter;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class DBTest {

    @Test
    public void dbTest(){
        List<String> title = new ArrayList<>();
        List<AirQuality> res = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("E:\\2020 fall\\INFSCI 2710 DB\\Brain flux test\\influxdb-java-master\\demo\\src\\main\\resources\\AirQualityUCI.csv"));
            CSVReader cvsReader = new CSVReader();
            title = cvsReader.readCSVTitle(reader);
            res = cvsReader.readCSV(reader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        DBConnecter dbConnecter = new DBConnecter();
        InfluxDB influxDB = dbConnecter.createDB();

        for (int i = 0; i < 10; i++) {
            AirQuality airQuality = (AirQuality) res.get(i);

            influxDB.write(Point.measurement("t1")
                    .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                    .tag(title.get(0), String.valueOf(airQuality.getDate()))
                    .tag(title.get(1), String.valueOf(airQuality.getTime()))
                    .addField(title.get(2), airQuality.getCO_GT())
                    .addField(title.get(3), airQuality.getPT08_S1_CO())
                    .addField(title.get(4), airQuality.getNMHC_GT())
                    .addField(title.get(5), airQuality.getC6H6_GT())
                    .addField(title.get(6), airQuality.getPT08_S2_NMHC())
                    .addField(title.get(7), airQuality.getNOx_GT())
                    .addField(title.get(8), airQuality.getPT08_S3_NOx())
                    .addField(title.get(9), airQuality.getNO2_GT())
                    .addField(title.get(10), airQuality.getPT08_S4_NO2())
                    .addField(title.get(11), airQuality.getPT08_S5_O3())
                    .addField(title.get(12), airQuality.getT())
                    .addField(title.get(13), airQuality.getRH())
                    .addField(title.get(14), airQuality.getAH())
                    .build());
        }

        QueryResult queryResult = influxDB.query(new Query("select MEAN(\"CO(GT)\"), MEAN(\"NO2(GT)\") from t1 where Date =~ /2004/ group by Date"));
        //List<QueryResult.Result> resultList = queryResult.getResults();

        for (int i = 0; i < res.size(); i++) {
            System.out.println(res.get(i).toString());
        }

        influxDB.close();
    }

    @Test
    public void query(){
        DBConnecter dbConnecter = new DBConnecter();
        InfluxDB influxDB = dbConnecter.createDB();
        QueryResult queryResult = influxDB.query(new Query("select MEAN(\"CO(GT)\"), MEAN(\"NO2(GT)\") from t1 where Date =~ /2004/ group by Date"));
        List<QueryResult.Series> series = queryResult.getResults().get(0).getSeries();
//        List<List<String>> oneRes = new ArrayList<>();
//        int i = 0;
//        while (i < series.size()) {
//            List<List<Object>> valueList = series.get(i).getValues();
//            String field1 = series.get(i).getTags().get("Date");
//            List<String> list = new ArrayList<>();
//            list.add(field1);
////            oneRes.put("Date", field1);
//            for (List<Object> value : valueList) {
//                // 查询结果字段2取值
//                String field2 = value.get(1) == null ? null : value.get(1).toString();
////                oneRes.put("Date", field2);
//                list.add(field2);
//                // 查询结果字段3取值
//                String field3 = value.get(2) == null ? null : value.get(2).toString();
////                oneRes.put("MEAN(NO2(GT))", field3);
//                list.add(field3);
//            }
//            //oneRes.put(field1, list);
//            oneRes.add(list);
//            i++;
//        }
//        for (List<String> res:oneRes) {
//            System.out.println(res.get(0)+":"+res.get(1)+", "+res.get(2));
//
//        }

        List<CONO2Mean> oneRes = new ArrayList<>();
        int i = 0;
        while (i < series.size()) {
            List<List<Object>> valueList = series.get(i).getValues();

            for (List<Object> value : valueList) {
                String field1 = series.get(i).getTags().get("Date");
                CONO2Mean cono2Mean = new CONO2Mean();
                cono2Mean.setDate(field1);

                String field2 = value.get(1) == null ? null : value.get(1).toString();
                float f2 = Math.round(Float.parseFloat(field2)*10000)/10000;
                cono2Mean.setCO_mean(f2+"");

                String field3 = value.get(2) == null ? null : value.get(2).toString();
                cono2Mean.setNO2_mean(field3);

                cono2Mean.setId(i+1);
                oneRes.add(cono2Mean);
                i++;
            }
            //oneRes.put(field1, list);

        }

        for (int j = 0; j < oneRes.size(); j++) {
            System.out.println(oneRes.get(j).toString());
        }
        influxDB.close();
    }

    @Test
    public void testShow(){
        DBConnecter dbConnecter = new DBConnecter();
        InfluxDB influxDB = dbConnecter.getInfluxDB("mydb1");

        QueryResult queryResult = influxDB.query(new Query("select * from t1"));
        List<QueryResult.Series> series = queryResult.getResults().get(0).getSeries();
        List<AirQuality> oneRes = new ArrayList<>();
        int i = 0;

        if(series.isEmpty())
            oneRes.add(new AirQuality());

        while (i < series.size()) {
            List<List<Object>> valueList = series.get(i).getValues();


            for (List<Object> value : valueList) {
                AirQuality airQuality = new AirQuality();

                String field1 = value.get(4) == null ? null : value.get(4).toString();
                String field2 = value.get(15) == null ? null : value.get(15).toString();
                airQuality.setDate(field1+" "+field2);

                String field3 = value.get(3) == null ? null : value.get(3).toString();
                //cono2Mean.setCO_mean(field2);
                float f1 = Float.parseFloat(field3);
                airQuality.setCO_GT(f1);

                String field4 = value.get(8) == null ? null : value.get(8).toString();
                double d1 = Double.parseDouble(field4);
                airQuality.setPT08_S1_CO((int) d1);

                String field5 = value.get(5) == null ? null : value.get(5).toString();
                double d2 = Double.parseDouble(field5);
                airQuality.setNMHC_GT((int)d2);

                String field6 = value.get(2) == null ? null : value.get(2).toString();
                //cono2Mean.setCO_mean(field2);
                float f2 = Float.parseFloat(field6);
                airQuality.setC6H6_GT(f2);

                String field7 = value.get(9) == null ? null : value.get(9).toString();
                double d3 = Double.parseDouble(field7);
                airQuality.setPT08_S2_NMHC((int)d3);

                String field8 = value.get(7) == null ? null : value.get(7).toString();
                double d4 = Double.parseDouble(field8);
                airQuality.setNOx_GT((int)d4);

                String field9 = value.get(10) == null ? null : value.get(10).toString();
                double d5 = Double.parseDouble(field9);
                airQuality.setPT08_S3_NOx((int)d5);

                String field10 = value.get(10) == null ? null : value.get(10).toString();
                double d6 = Double.parseDouble(field10);
                airQuality.setNO2_GT((int)d6);

                String field11 = value.get(11) == null ? null : value.get(11).toString();
                double d7 = Double.parseDouble(field11);
                airQuality.setPT08_S4_NO2((int)d7);

                String field12 = value.get(12) == null ? null : value.get(12).toString();
                double d8 = Double.parseDouble(field12);
                airQuality.setPT08_S5_O3((int)d8);

                String field13 = value.get(14) == null ? null : value.get(14).toString();
                //cono2Mean.setCO_mean(field2);
                float f3 = Math.round(Float.parseFloat(field13)*100)/100;
                airQuality.setT(f3);

                String field14 = value.get(13) == null ? null : value.get(13).toString();
                //cono2Mean.setCO_mean(field2);
                float f4 = Math.round(Float.parseFloat(field14)*100)/100;
                airQuality.setRH(f4);

                String field15 = value.get(1) == null ? null : value.get(1).toString();
                //cono2Mean.setCO_mean(field2);
                double f5 = Math.round(Double.parseDouble(field15)*10000)/10000;
                airQuality.setAH(f5);

                airQuality.setId(i+1);
                oneRes.add(airQuality);
                i++;

            }
            //oneRes.put(field1, list);

        }

        influxDB.close();

        for (int j = 0; j < oneRes.size(); j++) {
            System.out.println(oneRes.get(j).toString());
        }
    }
}
