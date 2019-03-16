package cn.yu.cartoon.cartoon_web.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 实现json字符串和pojo对象的互转
 *
 * @author Yu
 * @version 1.0
 * @date 2019/3/16 16:46
 **/
public class JacksonUtil {

    private static ObjectMapper objectMapper;

    /**
     *  将json字符串转成pojo对象
     *
     * @author Yu
     * @date 16:48 2019/3/16
     * @param jsonStr json串
     * @param valueType pojo类
     * @return pojo类
     **/
    public static <T> T readValue(String jsonStr, Class<T> valueType) {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }

        try {
            return objectMapper.readValue(jsonStr, valueType);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     *  json数组转List
     *
     * @author Yu
     * @date 16:51 2019/3/16
     * @param jsonStr json串
     * @param valueTypeRef pojo类
     **/
    public static <T> T readValue(String jsonStr, TypeReference<T> valueTypeRef){
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }

        try {
            return objectMapper.readValue(jsonStr, valueTypeRef);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     *  pojo类转成json串
     *
     * @author Yu
     * @date 16:52 2019/3/16
     * @param object 需要转换的对象
     **/
    public static String toJSon(Object object) {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }

        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
