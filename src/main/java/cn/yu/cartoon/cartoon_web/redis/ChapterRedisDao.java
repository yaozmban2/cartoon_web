package cn.yu.cartoon.cartoon_web.redis;

import cn.yu.cartoon.cartoon_web.pojo.dto.Chapter;
import cn.yu.cartoon.cartoon_web.util.JacksonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Array;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 漫画章节的redis数据操作
 *
 * @author Yu
 * @version 1.0
 * @date 2019/2/18 15:55
 **/
@Repository
public class ChapterRedisDao {

    private final StringRedisTemplate redisTemplate;

    /**
     * 默认过期时长，单位：秒
     */
    private static final long DEFAULT_EXPIRE = 60 * 5;

    /**
     * 不设置过期时长
     */
    public static final long NOT_EXPIRE = -1;

    @Autowired
    public ChapterRedisDao(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     *  设置章节id和章节名字的映射
     *
     * @author Yu
     * @date 21:50 2019/2/18
     * @param chapter 章节数据
     **/
    public void setId2ChapterName(Chapter chapter) {

        Integer chapterId = chapter.getChapterId();
        int superiorId = chapterId / 500;
        int subId = chapterId % 500;

        String id2Name = "chapter:chapterName:{0}";
        String h = MessageFormat.format(id2Name, String.valueOf(superiorId));
        redisTemplate.opsForHash().put(h, String.valueOf(subId), chapter.getChapterName());
    }

    /**
     *  根据章节id获得章节名字
     *
     * @author Yu
     * @date 22:51 2019/2/18
     * @param id 章节id
     * @return String 章节名字
     **/
    public String getChapterNameById(int id) {
        int superiorId = id / 500;
        int subId = id % 500;

        String id2Name = "chapter:chapterName:{0}";
        String h = MessageFormat.format(id2Name, String.valueOf(superiorId));
        return (String) redisTemplate.opsForHash().get(h, String.valueOf(subId));
    }


    /**
     * 将章节信息存入redis中
     * 如果传入的chapter只有一个chapterId则表示是为了解决缓存穿透问题传入的数据
     * 设置缓存时间为5分钟
     *
     * @author Yu
     * @date 23:17 2019/2/18
     * @param chapter 章节信息
     **/
    public void setChapterRecord(Chapter chapter) {

        //设置Hkey  recordH
        String recordName = "chapter:{0}";
        String recordH = MessageFormat.format(recordName, String.valueOf(chapter.getChapterId()));
        //如果传入的chapter只有一个chapterId，则传入一个non的key，设置过期时间为5分钟
        if (null == chapter.getChapterName()) {
            redisTemplate.opsForHash().put(recordH, "non", "");
            redisTemplate.expire(recordH, DEFAULT_EXPIRE, TimeUnit.SECONDS);
            return;
        }
        //设置记录各字段的值
        Map<String, String> tempMap = new HashMap<>(6);
        tempMap.put("chapterName", chapter.getChapterName());
        tempMap.put("chapterUploadTime", String.valueOf(chapter.getChapterUpdateTime()));
        tempMap.put("cartoonId", String.valueOf(chapter.getCartoonId()));
        tempMap.put("chapterPrice", String.valueOf(chapter.getChapterPrice()));
        tempMap.put("chapterUri", chapter.getChapterUri());
        tempMap.put("chapterNum", String.valueOf(chapter.getChapterPageCount()));
        redisTemplate.opsForHash().putAll(recordH, tempMap);

    }

    /**
     *  根据章节id获得章节数据
     *
     * @author Yu
     * @date 23:43 2019/2/18
     * @param id 章节id
     * @return Chapter 章节相关数据
     * @throws ParseException 字符串日期转换成Date数据时出错
     **/
    public Chapter getChapterRecordById(int id) throws ParseException {

        //设置Hkey  recordH
        String recordName = "chapter:{0}";
        String recordH = MessageFormat.format(recordName, String.valueOf(id));
        //获取记录数据
        Map tempMap = redisTemplate.opsForHash().entries(recordH);
        //如果redis中没数据则返回
        if (0 == tempMap.size()) {
            return null;
        }
        Chapter chapter = new Chapter();
        //如果返回的hash只有一个键值对，则表明mysql中没有这个数据
        if (1 == tempMap.size()) {
            chapter.setChapterId(id);
            return chapter;
        }
        chapter.setChapterId(id);
        chapter.setChapterName((String) tempMap.get("chapterName"));
        chapter.setChapterUpdateTime(new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK).parse((String) tempMap.get("chapterUploadTime")));
        chapter.setCartoonId(Integer.valueOf((String) tempMap.get("cartoonId")));
        chapter.setChapterPrice(Integer.valueOf((String) tempMap.get("chapterPrice")));
        chapter.setChapterUri((String) tempMap.get("chapterUri"));
        chapter.setChapterPageCount(Integer.valueOf((String) tempMap.get("chapterNum")));
        return chapter;
    }

    /**
     *  将漫画的章节信息存入redis缓存
     *      1.使用jackson将章节信息序列化成json字符串
     *      2.将章节依此插入到redis的List中
     *      key: chaptersByCartoonId:#{cartoonId}:#{page}:#{size}
     *
     * @author Yu
     * @date 18:03 2019/3/16
     * @param cartoonId 漫画的id
     * @param chapterList 漫画的所有章节信息
     **/
    public void insertChaptersByCartoonId(Integer cartoonId, List<Chapter> chapterList, Integer page, Integer size) {

        String key = "chaptersByCartoonId:{0}:{1}:{2}";
        key = MessageFormat.format(key, String.valueOf(cartoonId), String.valueOf(page), String.valueOf(size));

        for (Chapter chapter: chapterList) {
            String chapterString = JacksonUtil.toJSon(chapter);
            redisTemplate.opsForList().rightPush(key, chapterString);
        }
    }

    /**
     *  根据漫画id分页查找缓存中存储的章节数据
     *      1.生成key去缓存中找到List数据
     *      2.使用jackson将存储的章节信息json串反序列化为对象
     *      key: chaptersByCartoonId:#{cartoonId}:#{page}:#{size}
     *
     * @author Yu
     * @date 18:06 2019/3/16
     * @param cartoonId 漫画章节
     * @param page 第几分页
     * @param size 分页大小
     * @return 如果找不到数据 则返回null
     **/
    public List<Chapter> selectChaptersByCartoonId(Integer cartoonId,Integer page, Integer size) {

        String key = "chaptersByCartoonId:{0}:{1}:{2}";
        key = MessageFormat.format(key, String.valueOf(cartoonId), String.valueOf(page), String.valueOf(size));

        List<String> chapterStrList = redisTemplate.opsForList().range(key, 0, -1);
        if (0 == chapterStrList.size()) {
            return null;
        }
        List<Chapter> chapterList = new ArrayList<>();
        for (String chapterStr:chapterStrList) {
            chapterList.add(JacksonUtil.readValue(chapterStr, Chapter.class));
        }

        return chapterList;
    }

    /**
     *  根据漫画id从分页的缓存数据中找到最新的quantity个章节<br/>
     *      1.生成key去缓存中找到List数据<br/>
     *      2.从后往前遍历出quantity个数据<br/>
     *      3.使用jackson将存储的章节信息json串反序列化为对象<br/>
     *      key: chaptersByCartoonId:#{cartoonId}:#{page}:#{size}
     *
     * @author Yu
     * @date 18:06 2019/3/16
     * @param cartoonId 漫画章节
     * @param page 第几分页
     * @param size 分页大小
     * @param quantity 要查询的章节数量
     * @return 如果找不到数据 则返回null
     **/
    public List<Chapter> selectNewestChapterByCartoonId(Integer cartoonId,Integer page, Integer size, Integer quantity) {

        String key = "chaptersByCartoonId:{0}:{1}:{2}";
        key = MessageFormat.format(key, String.valueOf(cartoonId), String.valueOf(page), String.valueOf(size));

        List<String> chapterStrList = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            chapterStrList.add(redisTemplate.opsForList().index(key, -1 - i));
        }
        if (0 == chapterStrList.size()) {
            return null;
        }
        List<Chapter> chapterList = new ArrayList<>();
        for (String chapterStr:chapterStrList) {
            chapterList.add(JacksonUtil.readValue(chapterStr, Chapter.class));
        }

        return chapterList;
    }
}
