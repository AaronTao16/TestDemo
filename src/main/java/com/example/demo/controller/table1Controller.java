package com.example.demo.controller;

import com.example.demo.dto.AirQuality;
import com.example.demo.dto.CONO2Mean;
import com.example.demo.provider.DBConnecter;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Controller
public class table1Controller {

    @GetMapping("/table1")
    public String table1(Model model){

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
                float f1 = Math.round(Float.parseFloat(field3)*100)/100;
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

                String field10 = value.get(6) == null ? null : value.get(6).toString();
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
                float f3 = Float.parseFloat(field13);
                airQuality.setT(f3);

                String field14 = value.get(13) == null ? null : value.get(13).toString();
                //cono2Mean.setCO_mean(field2);
                float f4 = Float.parseFloat(field14);
                airQuality.setRH(f4);

                String field15 = value.get(1) == null ? null : value.get(1).toString();
                //cono2Mean.setCO_mean(field2);
                double f5 = Double.parseDouble(field15);
                airQuality.setAH(f5);

                airQuality.setId(i+1);
                oneRes.add(airQuality);
                i++;

            }
            //oneRes.put(field1, list);

        }

        influxDB.close();

        model.addAttribute("results", oneRes);

        return "table1";
    }
}
