package cn.yu.cartoon.cartoon_web.redis;

import cn.yu.cartoon.cartoon_web.pojo.dto.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 对国家表里面的数据的redis操作
 *
 * @author Yu
 * @version 1.0
 * @date 2019/3/15 16:07
 **/
@Repository("countryRedisDao")
public class CountryRedisDao {

    private StringRedisTemplate redisTemplate;

    @Autowired
    public CountryRedisDao(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     *  从redis缓存中查找出所有国家的信息
     *      如果缓存中没有相应的Keys 说明数据还没读到缓存中
     *         缓存中数据使用hash保存  key:countryUri
     *              	                Hk:#{countryname}
     *                                  Hv:#{img_uri}
     * @author Yu
     * @date 16:42 2019/3/15
     * @return Map 所有国家名字和封面uri   如果返回null 还没将数据放到redis缓存中
     **/
    public LinkedHashMap selectAllCountryMameAndUri() {
        String key = "countryUri";

        return (LinkedHashMap) redisTemplate.opsForHash().entries(key);
    }

    /**
     *  将所有国家的名字和封面uri保存到缓存中
     *      缓存中数据使用hash保存  key:countryUri
     *             	                Hk:#{countryname}
     *                              Hv:#{img_uri}
     * @author Yu
     * @date 17:01 2019/3/15
     * @param
     * @return
     **/
    public void insertAllCountryNameAndUri(LinkedHashMap countryMap) {
        String key = "countryUri";
        redisTemplate.opsForHash().putAll(key, countryMap);
    }

}
