package cn.yu.cartoon.cartoon_web.ServiceTest;

import cn.yu.cartoon.cartoon_web.pojo.dto.Cartoon;
import cn.yu.cartoon.cartoon_web.service.CartoonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author Yu
 * @version 1.0
 * @date 2019/3/15 22:37
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class CartoonServiceTest {

    @Autowired
    CartoonService cartoonService;

    @Test
    public void uploadFrontCoverToImgServerTest() throws IOException {
        Cartoon cartoon = new Cartoon();
        FileInputStream fis = new FileInputStream("C:\\Users\\Yu\\Desktop\\css\\20170908114352_880x385_128.png");
        MultipartFile multipartFile = new MockMultipartFile("C:\\Users\\Yu\\Desktop\\css\\20170908114352_880x385_128.png", "20170908114352_880x385_128.png", "application/png", fis);
        cartoonService.uploadFrontCoverToImgServer(cartoon, multipartFile);
    }

}
