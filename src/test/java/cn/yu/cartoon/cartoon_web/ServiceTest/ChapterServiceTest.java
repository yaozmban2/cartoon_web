package cn.yu.cartoon.cartoon_web.ServiceTest;

import cn.yu.cartoon.cartoon_web.service.ChapterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author Yu
 * @version 1.0
 * @date 2019/3/16 20:40
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class ChapterServiceTest {

    @Autowired
    ChapterService chapterService;

    @Test
    public void getChaptersByCartoonId() {
        List list = chapterService.getChaptersByCartoonIdByPage(30, 2, 1);
        if (null == list) {
            System.out.println("空的");
        } else {
            System.out.println(list);
        }
    }

}
