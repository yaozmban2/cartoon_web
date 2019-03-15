package cn.yu.cartoon.cartoon_web.mapper;

import cn.yu.cartoon.cartoon_web.pojo.dto.CartoonType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Yu
 * @version 1.0
 * @date 2019/3/15 20:45
 **/
@Mapper
public interface CartoonTypeMapper {

    List<CartoonType> selectAll();
}
