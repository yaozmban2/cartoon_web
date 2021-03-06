package cn.yu.cartoon.cartoon_web.mapper;

import cn.yu.cartoon.cartoon_web.pojo.dto.QRCode;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Yu
 * @version 1.0
 * @date 2019/3/1 12:54
 **/
@Mapper
public interface QRCodeMapper {

    /**
     *  从数据库中查找未被使用的二维码
     *  将相关信息存入redis缓存中
     *  返回二维码地址
     *
     * @author Yu
     * @date 16:30 2019/3/4
     * @param qrPrice 充值的金额
     * @return String 返回二维码的地址
     **/
    QRCode selectQRCodeByPrice(int qrPrice);
}
