package com.zx.ace.demo.mystudy.mybatisdemo.dao;

import com.zx.ace.demo.mystudy.mybatisdemo.entity.City;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2017/8/27 0027.
 */
public interface CityDao {
    City getCityByName(String cityName);
}
