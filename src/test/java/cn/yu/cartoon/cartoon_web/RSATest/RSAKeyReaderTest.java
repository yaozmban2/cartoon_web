/*
package cn.yu.cartoon.cartoon_web.RSATest;

import cn.yu.cartoon.cartoon_web.config.shiro.RSAKeyReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

*/
/**
 * @author Yu
 * @version 1.0
 * @date 2019/3/14 13:57
 **//*

@RunWith(SpringRunner.class)
@SpringBootTest
public class RSAKeyReaderTest {

    @Test
    public void test(){
       RSAPrivateKey privateKey =  RSAKeyReader.getPrivateKey();
       RSAPublicKey publicKey = RSAKeyReader.getPublicKey();
       System.out.println(privateKey);
       System.out.println(publicKey);
    }
}
*/
