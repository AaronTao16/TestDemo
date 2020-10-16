package com.example.demo.mapper;

import com.example.demo.dto.AirQuality;
import org.springframework.stereotype.Repository;

@Repository
public interface DataMapper {

    AirQuality showAll();
}
