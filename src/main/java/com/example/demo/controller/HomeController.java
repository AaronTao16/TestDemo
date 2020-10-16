package com.example.demo.controller;

import com.example.demo.dto.AirQuality;
import com.example.demo.dto.CONO2Mean;
import com.example.demo.provider.CSVReader;
import com.example.demo.provider.DBConnecter;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(){

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

        for (int i = 0; i < res.size(); i++) {
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

        influxDB.close();

        return "home"; }

    @GetMapping("/result")
    public String result(Model model){

        DBConnecter dbConnecter = new DBConnecter();
        InfluxDB influxDB = dbConnecter.getInfluxDB("mydb1");

        QueryResult queryResult = influxDB.query(new Query("select MEAN(\"CO(GT)\"), MEAN(\"NO2(GT)\") from t1 where Date =~ /2004/ group by Date"));
        List<QueryResult.Series> series = queryResult.getResults().get(0).getSeries();
        List<CONO2Mean> oneRes = new ArrayList<>();
        int i = 0;

        if(series.isEmpty())
            oneRes.add(new CONO2Mean());

        while (i < series.size()) {
            List<List<Object>> valueList = series.get(i).getValues();

            for (List<Object> value : valueList) {
                String field1 = series.get(i).getTags().get("Date");
                CONO2Mean cono2Mean = new CONO2Mean();
                cono2Mean.setDate(field1);

                String field2 = value.get(1) == null ? null : value.get(1).toString();
                cono2Mean.setCO_mean(field2);
//                float f2 = Math.round(Float.parseFloat(field2)*100)/100;
//                cono2Mean.setCO_mean(f2+"");

                String field3 = value.get(2) == null ? null : value.get(2).toString();
                cono2Mean.setNO2_mean(field3);
                cono2Mean.setId(i+1);
                oneRes.add(cono2Mean);
                i++;
            }
            //oneRes.put(field1, list);

        }

        influxDB.close();


//        List<AirQuality> res = new ArrayList<>();
//        AirQuality airQuality = new AirQuality();
//        airQuality.setDate("2020-10-15");
//        airQuality.setCO_GT(1.2f);
//        airQuality.setNO2_GT(10);
//        res.add(airQuality);

//        List<CONO2Mean> res = new ArrayList<>();
//        CONO2Mean cono2Mean = new CONO2Mean();
//        cono2Mean.setDate("2020-10-15");
//        cono2Mean.setCO_mean("1.34");
//        cono2Mean.setNO2_mean("10");
//        res.add(cono2Mean);


        model.addAttribute("results", oneRes);

        return "result";
    }
}
