package cn.yu.cartoon.cartoon_web.MapperTest;

import cn.yu.cartoon.cartoon_web.mapper.ChapterMapper;
import org.apache.ibatis.annotations.Mapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Test
    public void selectChaptersByCartoonIdByPageTest() {
        Map map = new HashMap();
        map.put("cartoonId", 6);
        map.put("index", 0);
        map.put("size", 4);
        List list = chapterMapper.selectChaptersByCartoonIdByPage(map);
        System.out.println(chapterMapper.selectChaptersByCartoonIdByPage(map));
    }
}
