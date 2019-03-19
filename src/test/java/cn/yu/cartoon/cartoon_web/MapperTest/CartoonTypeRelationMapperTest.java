package cn.yu.cartoon.cartoon_web.MapperTest;

import cn.yu.cartoon.cartoon_web.mapper.CartoonTypeRelationMapper;
import cn.yu.cartoon.cartoon_web.pojo.dto.CartoonTypeRelation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Yu
 * @version 1.0
 * @date 2019/3/16 0:14
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class CartoonTypeRelationMapperTest {

    @Autowired
    CartoonTypeRelationMapper cartoonTypeRelationMapper;

    @Test
    public void insertTest() {
        CartoonTypeRelation cartoonTypeRelation = new CartoonTypeRelation();
        cartoonTypeRelation.setCartoonId(1);
        cartoonTypeRelation.setTypeId(1);
        cartoonTypeRelationMapper.insert(cartoonTypeRelation);
    }

}
