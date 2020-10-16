package com.example.demo.provider;

import com.example.demo.dto.AirQuality;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVReader {

    public List<String> readCSVTitle(BufferedReader reader){
        List<String> list = new ArrayList<>();

        try {
            String line = reader.readLine();
            String[] schema = line.split(";");
            list.addAll(Arrays.asList(schema));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<AirQuality> readCSV(BufferedReader reader){
        ArrayList<AirQuality> list = new ArrayList<>();
        try {
            String line = null;
//            line = reader.readLine();

            while(true) {
                line = reader.readLine();

                if(line == null)
                    break;

                String[] data = line.split(";");
                AirQuality quality = new AirQuality();

                if(data.length == 0)
                    break;

                //                SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy/MM/dd");
//                String[] date = data[0].split("/");
//                try {
//                    quality.setDate(dateFormat.parse(date[2]+"/"+date[0]+"/"+date[1]));
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//
//                SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
//                String[] time = data[1].split("\\.");
//                try {
//                    quality.setTime(timeFormat.parse(time[0]+":"+time[1]+":"+time[2]));
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }

                String[] date = data[0].split("/");
                quality.setDate(date[2]+"/"+date[1]+"/"+date[0]);


                String[] time = data[1].split("\\.");
                quality.setTime(time[0]+":"+time[1]+"."+time[2]);

                String[] s1 = data[2].split(",");
                float f1;
                if(s1.length > 1)
                    f1 = Float.parseFloat(s1[0] + "." + s1[1]);
                else
                    f1 = Float.parseFloat(String.valueOf(s1[0]));
                quality.setCO_GT(f1);

                quality.setPT08_S1_CO(Integer.parseInt(data[3]));
                quality.setNMHC_GT(Integer.parseInt(data[4]));

                String[] s2 = data[5].split(",");
                float f2;
                if(s2.length > 1)
                    f2 = Float.parseFloat(s2[0] + "." + s2[1]);
                else
                    f2 = Float.parseFloat(String.valueOf(s2[0]));
                quality.setC6H6_GT(f2);

                quality.setPT08_S2_NMHC(Integer.parseInt(data[6]));
                quality.setNOx_GT(Integer.parseInt(data[7]));
                quality.setPT08_S3_NOx(Integer.parseInt(data[8]));
                quality.setNO2_GT(Integer.parseInt(data[9]));
                quality.setPT08_S4_NO2(Integer.parseInt(data[10]));
                quality.setPT08_S5_O3(Integer.parseInt(data[11]));

                String[] s3 = data[12].split(",");
                float f3;
                if(s3.length > 1)
                    f3 = Float.parseFloat(s3[0] + "." + s3[1]);
                else
                    f3 = Float.parseFloat(String.valueOf(s3[0]));
                quality.setT(f3);

                String[] s4 = data[13].split(",");
                float f4;
                if(s4.length > 1)
                    f4 = Float.parseFloat(s4[0] + "." + s4[1]);
                else
                    f4 = Float.parseFloat(String.valueOf(s4[0]));
                quality.setRH(f4);

                String[] s5 = data[14].split(",");
                double f5;
                if(s5.length > 1)
                    f5 = Double.parseDouble(s5[0] + "." + s5[1]);
                else
                    f5 = Double.parseDouble((String.valueOf(s5[0])));
                quality.setAH(f5);

                list.add(quality);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }
}
