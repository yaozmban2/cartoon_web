package cn.yu.cartoon.cartoon_web.MapperTest;

import cn.yu.cartoon.cartoon_web.mapper.CartoonMapper;
import cn.yu.cartoon.cartoon_web.pojo.dto.Cartoon;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Yu
 * @version 1.0
 * @date 2019/3/15 23:47
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class CartoonMapperTest {

    @Autowired
    CartoonMapper cartoonMapper;

    @Test
    public void insertAndGetIdTest() {

        Cartoon cartoon = new Cartoon();
        cartoon.setCartoonName("海盗湾");

        System.out.println(cartoonMapper.insertAndGetId(cartoon));
        System.out.println(cartoon.getCartoonId());
    }
}
