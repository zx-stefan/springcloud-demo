package com.zx.ace.demo.mystudy.mybatisdemo.service;

import com.zx.ace.demo.mystudy.mybatisdemo.dao.CityDao;
import com.zx.ace.demo.mystudy.mybatisdemo.entity.City;
import com.zx.ace.demo.sys.config.datasource.DBContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/8/27 0027.
 */
@Service
public class CityServiceImpl implements CityService {

    @Autowired
    CityDao cityDao;

    @Override
    public City getCityByName(String cityName) {
        //设置数据源
        DBContextHolder.setDBType(DBContextHolder.DATA_SOURCE_A);
        return cityDao.getCityByName(cityName);
    }
}
