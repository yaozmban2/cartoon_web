package cn.yu.cartoon.cartoon_web.RedisDaoTest;

import cn.yu.cartoon.cartoon_web.redis.CountryRedisDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedHashMap;

/**
 * @author Yu
 * @version 1.0
 * @date 2019/3/15 17:18
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class CountryRedisTest {

    @Autowired
    CountryRedisDao countryRedisDao;

    @Test
    public void insertAllCountryAndUriTest() {
        LinkedHashMap map = new LinkedHashMap();
        map.put("中国", "sahdhaldj");
        map.put("中国香港", "sahdhaldj");
        map.put("中国台湾", "sahdhaldj");
        map.put("日本", "sahdhaldj");
        countryRedisDao.insertAllCountryNameAndUri(map);
    }

    @Test
    public void selectAllCountryTest() {
        System.out.println(countryRedisDao.selectAllCountryMameAndUri());
    }
}
