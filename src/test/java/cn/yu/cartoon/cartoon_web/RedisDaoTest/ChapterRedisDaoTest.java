package cn.yu.cartoon.cartoon_web.RedisDaoTest;

import cn.yu.cartoon.cartoon_web.mapper.ChapterMapper;
import cn.yu.cartoon.cartoon_web.redis.ChapterRedisDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Yu
 * @version 1.0
 * @date 2019/3/16 18:12
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class ChapterRedisDaoTest {

    @Autowired
    ChapterMapper chapterMapper;

    @Autowired
    ChapterRedisDao chapterRedisDao;

    @Test
    public void selectChaptersByCartoonIdTest() {
        if (chapterRedisDao.selectChaptersByCartoonId(7, 1, 10) == null) {
            System.out.println("空的");
        } else {
            System.out.println(chapterRedisDao.selectChaptersByCartoonId(7, 1, 10));
        }
    }

    @Test
    public void insertChaptersByCartoonIdTest() {
        System.out.println(chapterMapper.selectChaptersByCartoonId(6));
        chapterRedisDao.insertChaptersByCartoonId(6, chapterMapper.selectChaptersByCartoonId(6), 1, 10);
    }

}
