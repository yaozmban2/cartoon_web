package cn.yu.cartoon.cartoon_web.serviceimpl;

import cn.yu.cartoon.cartoon_web.service.CartoonService;
import cn.yu.cartoon.cartoon_web.util.RandomUtils;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * @author Yu
 * @version 1.0
 * @date 2019/2/14 20:57
 **/
@Service("cartoonService")
class CartoonServiceImpl implements CartoonService {

    @Override
    public boolean uploadCartoonByZipFile(File zipFile) {

        String dirName = RandomUtils.randomString(8);


        return false;
    }
}
