package cn.yu.cartoon.cartoon_web.redis;

import cn.yu.cartoon.cartoon_web.config.StaticResource;
import cn.yu.cartoon.cartoon_web.pojo.vo.cartoonvo.CartoonInfoVo;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.text.MessageFormat;
import java.util.Date;

/**
 * 漫画信息缓存操作
 *
 * @author Yu
 * @version 1.0
 * @date 2019/3/16 11:36
 **/
@Repository
public class CartoonRedisDao {

    private final StringRedisTemplate redisTemplate;

    @Autowired
    public CartoonRedisDao(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     *  将漫画信息存储到redis缓存中
     *      其结构为hash  key:cartoon:#{cartoonId / 512}
     *                    hk:#{cartoonId / 512}
     *                    v: #{cartoonId|cartoonName|cartoonUri|cartoonAuthor|countryName|cartoonDescription|cartoonUpdateTime|isDeleted|collectCount|readCount|isEnd|isAdult}
     * @author Yu
     * @date 14:06 2019/3/16
     * @param cartoonInfoVo 继承自cartoon类 增加了一个countryName的属性
     **/
    public void insertCartoonInfo(CartoonInfoVo cartoonInfoVo) {
        String key = "cartoon:{0}";
        key = MessageFormat.format(key, String.valueOf(cartoonInfoVo.getCartoonId() / 512));
        String hkey = String.valueOf(cartoonInfoVo.getCartoonId() % 512);

        redisTemplate.opsForHash().put(key, hkey, encodeCartoonInfoToString(cartoonInfoVo));
    }

    /**
     *  根据cartoonId 构造key从redis缓存中找到漫画信息
     *      将漫画信息字符串还原成对象
     *      缓存中存放的数据结构为hash  key:cartoon:#{cartoonId / 512}
     *                                  hk:#{cartoonId / 512}
     *                                  v: #{cartoonId|cartoonName|cartoonUri|cartoonAuthor|countryName|cartoonDescription|cartoonUpdateTime|isDeleted|collectCount|readCount|isEnd|isAdult}
     * @author Yu
     * @date 14:06 2019/3/16
     * @param cartoonId 漫画id
     * @return 如果没有数据返回null
     **/
    public CartoonInfoVo selectByCartoonId(Integer cartoonId) {

        String key = "cartoon:{0}";
        key = MessageFormat.format(key, String.valueOf(cartoonId / 512));
        String hkey = String.valueOf(cartoonId % 512);
        Object o = redisTemplate.opsForHash().get(key, hkey);
        if (null == o) {
            return null;
        }
        return restoreCartoonFromHashStore((String) o);

    }

    /**
     *  将字符串还原成漫画信息对象
     *
     * @author Yu
     * @date 14:16 2019/3/16
     * @param cartoonInfo 存有漫画信息的字符串
     * @return CartoonInfoVo 继承自cartoon类 增加了一个countryName的属性
     **/
    private CartoonInfoVo restoreCartoonFromHashStore(String cartoonInfo) {

        CartoonInfoVo cartoonInfoVo = new CartoonInfoVo();
        String[] cartoonInfoList = cartoonInfo.split("\\|");

        cartoonInfoVo.setCartoonId(Integer.valueOf(cartoonInfoList[0]));
        cartoonInfoVo.setCartoonName(cartoonInfoList[1]);
        cartoonInfoVo.setCartoonUri(cartoonInfoList[2]);
        cartoonInfoVo.setCartoonAuthor(cartoonInfoList[3]);
        cartoonInfoVo.setCountryName(cartoonInfoList[4]);
        cartoonInfoVo.setCartoonDescription(cartoonInfoList[5]);

        DateTimeFormatter format = StaticResource.getFormat();
        DateTime dateTime = format.parseDateTime(cartoonInfoList[6]);
        cartoonInfoVo.setCartoonUpdateTime(dateTime.toDate());

        cartoonInfoVo.setIsDeleted(Byte.valueOf(cartoonInfoList[7]));
        cartoonInfoVo.setCollectCount(Integer.valueOf(cartoonInfoList[8]));
        cartoonInfoVo.setReadCount(Integer.valueOf(cartoonInfoList[9]));
        cartoonInfoVo.setIsEnd(Byte.valueOf(cartoonInfoList[10]));
        cartoonInfoVo.setIsAdult(Byte.valueOf(cartoonInfoList[11]));
        cartoonInfoVo.setChapterCount(Integer.valueOf(cartoonInfoList[12]));

        return cartoonInfoVo;

    }

    /**
     * 将漫画信息对象转成字符串
     *
     * @author Yu
     * @date 14:18 2019/3/16
     * @param cartoonInfoVo 漫画信息对象
     * @return 漫画信息字符串
     **/
    private String encodeCartoonInfoToString(CartoonInfoVo cartoonInfoVo) {

        Date cartoonUpdateTime = cartoonInfoVo.getCartoonUpdateTime();
        DateTime dateTime = new DateTime(cartoonUpdateTime);
        String formatStr = StaticResource.getFormatStr();
        String dateTimeStr = dateTime.toString(formatStr);

        String cartoonInfoStr = cartoonInfoVo.getCartoonId().toString() + "|" +
                cartoonInfoVo.getCartoonName() + "|" +
                cartoonInfoVo.getCartoonUri() + "|" +
                cartoonInfoVo.getCartoonAuthor() + "|" +
                cartoonInfoVo.getCountryName() + "|" +
                cartoonInfoVo.getCartoonDescription() + "|" +
                dateTimeStr + "|" +
                cartoonInfoVo.getIsDeleted().toString() + "|" +
                cartoonInfoVo.getCollectCount().toString() + "|" +
                cartoonInfoVo.getReadCount().toString() + "|" +
                cartoonInfoVo.getIsEnd().toString() + "|" +
                cartoonInfoVo.getIsAdult().toString() + "|" +
                cartoonInfoVo.getChapterCount().toString();
        return cartoonInfoStr;
    }
}
