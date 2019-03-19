package cn.yu.cartoon.cartoon_web.UtilTest;

import cn.yu.cartoon.cartoon_web.mapper.ChapterMapper;
import cn.yu.cartoon.cartoon_web.pojo.dto.Chapter;
import cn.yu.cartoon.cartoon_web.util.JacksonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author Yu
 * @version 1.0
 * @date 2019/3/16 17:04
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class JacksonUtilTest {

    @Autowired
    ChapterMapper chapterMapper;

    @Test
    public void toJSonTest() {

        List<Chapter> chapters = chapterMapper.selectChaptersByCartoonId(6);
        System.out.println(chapters.get(0));
        String jsonStr = JacksonUtil.toJSon(chapters);
        List<Chapter> chapters1 = JacksonUtil.readValue(jsonStr, new TypeReference<List<Chapter>>() { });
        if (chapters1 != null) {
            System.out.println(chapters1);
        }
    }

}
