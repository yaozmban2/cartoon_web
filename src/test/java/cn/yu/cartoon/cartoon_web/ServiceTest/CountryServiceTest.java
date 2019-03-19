package cn.yu.cartoon.cartoon_web.ServiceTest;

import cn.yu.cartoon.cartoon_web.service.CountryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Yu
 * @version 1.0
 * @date 2019/3/15 17:03
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class CountryServiceTest {

    @Autowired
    CountryService countryService;

    @Test
    public void getAllCountry() {
        System.out.println(countryService.getAllCountryNameAndUri());
    }

    @Test
    public void getAllCountryName() {
        System.out.println(countryService.getAllCountryName());
    }
}
