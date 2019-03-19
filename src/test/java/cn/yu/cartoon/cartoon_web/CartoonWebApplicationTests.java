package cn.yu.cartoon.cartoon_web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.text.ParseException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartoonWebApplicationTests {

    @Test
    public void contextLoads() {

    }

    @Test
    public void test() throws ParseException, IOException {
        /*// 0 名字  7生日 8地址
        FileInputStream fileInputStream = new FileInputStream("C:\\Users\\Yu\\Desktop\\shenfengzheng.txt");
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "utf16");
        BufferedReader reader = new BufferedReader(inputStreamReader);
        Long count = 1L;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = simpleDateFormat.parse("19710210");
        User user = new User();

        String str = null;
        while (null != (str = reader.readLine())) {
            String[] strs = str.split(",");
            if (strs.length > 8) {
                if (!"".equals(strs[1])) {
                    continue;
                } else {
                    //if (strs[6].length() == 8 && Character.isDigit(strs[6].charAt(0)) && Character.isDigit(strs[6].charAt(1)) && Character.isDigit(strs[6].charAt(2)) && Character.isDigit(strs[6].charAt(3))) {
                    if (strs[6].length() == 8 && Character.isDigit(strs[6].charAt(0)) && Character.isDigit(strs[6].charAt(4)) && Character.isDigit(strs[6].charAt(5)) && Character.isDigit(strs[6].charAt(7))) {
                        user.setId(count);
                        user.setName(strs[0]);
                        user.setBirthday(simpleDateFormat.parse(strs[6]));
                        user.setAddress(strs[7]);
                        userRepository.save(user);
                        count++;
                    }
                }
            }
        }

        //close
        fileInputStream.close();
        inputStreamReader.close();
        reader.close();

       *//* // 创建三个User，并验证User总数
        userRepository.save(new User(1L, "didi", simpleDateFormat.parse("19890206"), "浙江省丽水XXX"));
        userRepository.save(new User(2L, "mama", simpleDateFormat.parse("19875123"), "丽水市缙云XXX"));*//*
    }*/
    }

}
