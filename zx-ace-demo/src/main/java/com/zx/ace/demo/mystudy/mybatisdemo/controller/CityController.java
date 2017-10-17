package com.zx.ace.demo.mystudy.mybatisdemo.controller;

import com.zx.ace.demo.mystudy.mybatisdemo.entity.City;
import com.zx.ace.demo.mystudy.mybatisdemo.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Administrator on 2017/8/27 0027.
 */
@RestController
@RequestMapping("/city")
public class CityController {

    @Autowired
    CityService cityService;

    @RequestMapping(value = "/getCity/{cityName}", method = RequestMethod.GET)
    @ResponseBody
    public City getCityByName(@PathVariable(value = "cityName") String cityName){
        return cityService.getCityByName(cityName);
    }


}
