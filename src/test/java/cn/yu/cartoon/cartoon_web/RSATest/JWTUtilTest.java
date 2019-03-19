/*
package cn.yu.cartoon.cartoon_web.RSATest;

import cn.yu.cartoon.cartoon_web.config.shiro.JWTUtil;
import cn.yu.cartoon.cartoon_web.config.shiro.RSAKeyReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

*/
/**
 * @author Yu
 * @version 1.0
 * @date 2019/3/14 17:26
 **//*

@RunWith(SpringRunner.class)
@SpringBootTest
public class JWTUtilTest {

    @Test
    public void test() {

        String token = JWTUtil.sign("yaozmban2", RSAKeyReader.getPublicKey(), RSAKeyReader.getPrivateKey());
        System.out.println(token);
        String username = JWTUtil.getUsername(token);
        System.out.println(username);
        boolean verify = JWTUtil.verify(token, "yaozmban2", RSAKeyReader.getPublicKey(), RSAKeyReader.getPrivateKey());
        System.out.println(verify);
    }
}
*/
