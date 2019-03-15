package cn.yu.cartoon.cartoon_web.mapper;

import cn.yu.cartoon.cartoon_web.pojo.dto.Country;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 对country 国家表的相关操作
 *
 * @author Yu
 * @version 1.0
 * @date 2019/3/15 16:00
 **/
@Mapper
public interface CountryMapper {

    /**
     * 查询到所有的国家信息并返回
     *
     * @author Yu
     * @date 16:04 2019/3/15
     * @return List<country> 包含所有国家信息的类
     **/
    List<Country> selectAll();

}
