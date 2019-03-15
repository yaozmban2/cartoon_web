package cn.yu.cartoon.cartoon_web.serviceimpl;

import cn.yu.cartoon.cartoon_web.mapper.CountryMapper;
import cn.yu.cartoon.cartoon_web.pojo.dto.Country;
import cn.yu.cartoon.cartoon_web.redis.CountryRedisDao;
import cn.yu.cartoon.cartoon_web.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

/**
 * @author Yu
 * @version 1.0
 * @date 2019/3/15 16:48
 **/
@Service("countryService")
public class CountryServiceImpl implements CountryService {

    private final CountryRedisDao countryRedisDao;
    private final CountryMapper countryMapper;

    @Autowired
    public CountryServiceImpl(CountryRedisDao countryRedisDao, CountryMapper countryMapper) {
        this.countryRedisDao = countryRedisDao;
        this.countryMapper = countryMapper;
    }

    @Override
    public LinkedHashMap getAllCountryNameAndUri() {

        LinkedHashMap countries = countryRedisDao.selectAllCountryMameAndUri();

        if (null == countries || 0 == countries.size()) {
            List<Country> countryList = countryMapper.selectAll();
            for (Country country:countryList) {
                countries.put(country.getCountryName(), country.getImgUri());
            }
            countryRedisDao.insertAllCountryNameAndUri(countries);
            return countries;
        } else {
            return countries;
        }
    }

    @Override
    public List<Country> getAllCountryName() {

        return countryMapper.selectAll();
    }
}
