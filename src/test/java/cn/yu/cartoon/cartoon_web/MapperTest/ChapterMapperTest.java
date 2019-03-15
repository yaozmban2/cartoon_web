package cn.yu.cartoon.cartoon_web.MapperTest;

import cn.yu.cartoon.cartoon_web.mapper.ChapterMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Yu
 * @version 1.0
 * @date 2019/3/13 19:57
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class ChapterMapperTest {

    @Autowired
    ChapterMapper chapterMapper;

    @Test
    public void selectCountByUriTest() {
        System.out.println(chapterMapper.selectCountByUri("sdsadfw"));
    }
}
