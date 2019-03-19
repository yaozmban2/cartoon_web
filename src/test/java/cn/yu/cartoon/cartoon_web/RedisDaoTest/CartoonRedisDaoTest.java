package cn.yu.cartoon.cartoon_web.RedisDaoTest;

import cn.yu.cartoon.cartoon_web.mapper.CartoonMapper;
import cn.yu.cartoon.cartoon_web.pojo.dto.Cartoon;
import cn.yu.cartoon.cartoon_web.pojo.vo.cartoonvo.CartoonInfoVo;
import cn.yu.cartoon.cartoon_web.redis.CartoonRedisDao;
import cn.yu.cartoon.cartoon_web.util.FatherToChildUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Yu
 * @version 1.0
 * @date 2019/3/16 13:40
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class CartoonRedisDaoTest {

    @Autowired
    CartoonRedisDao cartoonRedisDao;

    @Autowired
    CartoonMapper cartoonMapper;

    @Test
    public void insertCartoonInfoTest() {
        CartoonInfoVo cartoonInfoVo = new CartoonInfoVo();
        Cartoon cartoon = cartoonMapper.selectById(6);

        try {
            FatherToChildUtil.fatherToChild(cartoon, cartoonInfoVo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        cartoonInfoVo.setCountryName("日本");
        cartoonRedisDao.insertCartoonInfo(cartoonInfoVo);
    }

    @Test
    public void selectByCartoonIdTest() {
        System.out.println(cartoonRedisDao.selectByCartoonId(6));
    }

}
