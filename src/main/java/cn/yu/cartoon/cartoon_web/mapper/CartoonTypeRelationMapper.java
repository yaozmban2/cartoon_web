package cn.yu.cartoon.cartoon_web.mapper;

import cn.yu.cartoon.cartoon_web.pojo.dto.CartoonTypeRelation;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Yu
 * @version 1.0
 * @date 2019/3/16 0:07
 **/
@Mapper
public interface CartoonTypeRelationMapper {

    /**
     * 将漫画与漫画类型的关系存入数据库
     *
     * @author Yu
     * @date 0:12 2019/3/16
     * @param cartoonTypeRelation 相应的关系
     * @return 成功返回1  失败返回0
     **/
    Integer insert(CartoonTypeRelation cartoonTypeRelation);
}
