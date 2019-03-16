package cn.yu.cartoon.cartoon_web.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 父类转成子类的方法
 *
 * @author Yu
 * @version 1.0
 * @date 2019/3/16 13:52
 **/
public class FatherToChildUtil {

    /**
     *  通过反射的方法 将父类对象的成员变量值 赋给子类对象的成员变量值
     *
     * @author Yu
     * @date 13:55 2019/3/16
     * @param father 父类对象
     * @param child 子类对象
     **/
    public static <T>void fatherToChild(T father,T child) throws Exception {
        if (child.getClass().getSuperclass() != father.getClass()) {
            throw new Exception("child 不是 father 的子类");
        }
        Class<?> fatherClass = father.getClass();
        Field[] declaredFields = fatherClass.getDeclaredFields();
        for (Field field : declaredFields) {
            Method method = fatherClass.getDeclaredMethod("get" + upperHeadChar(field.getName()));
            Object obj = method.invoke(father);
            field.setAccessible(true);
            field.set(child, obj);
        }
    }

    /**
     *  将首字母转为大写
     *
     * @author Yu
     * @date 13:56 2019/3/16
     * @param in 需要转变的字符串
     * @return 首字母大写后的字符串
     **/
    private static String upperHeadChar(String in) {
        String head = in.substring(0, 1);
        String out = head.toUpperCase() + in.substring(1, in.length());
        return out;
    }
}
