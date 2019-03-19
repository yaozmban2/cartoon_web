package cn.yu.cartoon.cartoon_web.MapperTest;

import cn.yu.cartoon.cartoon_web.mapper.CountryMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Yu
 * @version 1.0
 * @date 2019/3/15 16:05
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class CountryMapperTest {

    @Autowired
    CountryMapper countryMapper;

    @Test
    public void selectAllTest() {
        System.out.println(countryMapper.selectAll());
    }

    @Test
    public void selectByCountryIdTest() {
        System.out.println(countryMapper.selectByCountryId(4));
    }

}
