package cn.yu.cartoon.cartoon_web.mapper;

import cn.yu.cartoon.cartoon_web.pojo.dto.Cartoon;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Yu
 * @version 1.0
 * @date 2019/2/17 16:34
 **/
@Mapper
public interface CartoonMapper {

    /**
     *  将漫画信息插入到数据库中
     *      并且将自增的ID注入cartoon对象
     *
     * @author Yu
     * @date 23:44 2019/3/15
     * @param cartoon 漫画对象
     * @return 插入成功返回1  失败返回false
     **/
    Integer insertAndGetId(Cartoon cartoon);

    Cartoon selectById(Integer cartoonId);

}
