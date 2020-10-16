package com.example.demo.provider;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Query;

public class DBConnecter {
    final String serverURL = "http://127.0.0.1:8086", username = "root", password = "root";
    final InfluxDB influxDB = InfluxDBFactory.connect(serverURL, username, password);

    public InfluxDB createDB(){
        String databaseName = "mydb1";
        influxDB.query(new Query("CREATE DATABASE " + databaseName));
        influxDB.setDatabase(databaseName);

        return influxDB;
    }

    public InfluxDB getInfluxDB(String name) {
        influxDB.setDatabase(name);
        return influxDB;
    }

    public void closeDB(){
        influxDB.close();
    }

}
