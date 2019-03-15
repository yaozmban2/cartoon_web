package cn.yu.cartoon.cartoon_web.service;

import cn.yu.cartoon.cartoon_web.pojo.dto.Country;

import java.util.List;
import java.util.Map;

/**
 * @author Yu
 * @version 1.0
 * @date 2019/3/15 16:48
 **/
public interface CountryService {

    /**
     *  获得所有的国家信息（名称和uri）
     *      先去redis缓存中查找
     *      如果返回Null 则去mysql中查找
     *      将查询到的信息存入到redis缓存中并返回
     *
     * @author Yu
     * @date 16:50 2019/3/15
     * @return Map 返回所有国家的信息
     **/
    Map getAllCountryNameAndUri();

    /**
     *  获得所有的国家的名称
     *
     * @author Yu
     * @date 17:07 2019/3/15
     * @return List<String> 所有国家的名称
     **/
    List<Country> getAllCountryName();

}
