package cn.yu.cartoon.cartoon_web.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Yu
 * @version 1.0
 * @date 2019/3/16 16:55
 **/
public class JsonDateFormatFull extends JsonSerializer<Date> {

    /**
     *  设置json对Date的序列化方法
     *      Jackson支持日期字符串格式
     * 	   "yyyy-MM-dd'T'HH:mm:ss.SSSZ" "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
     * 	   "EEE, dd MMM yyyy HH:mm:ss zzz" "yyyy-MM-dd"
     *
     * @author Yu
     * @date 17:31 2019/3/16
     **/
    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String formattedDate = formatter.format(date);
        jsonGenerator.writeString(formattedDate);

    }
}
