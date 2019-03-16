package cn.yu.cartoon.cartoon_web.serviceimpl;

import cn.yu.cartoon.cartoon_web.config.CartoonFtpConfig;
import cn.yu.cartoon.cartoon_web.config.TempDirConfig;
import cn.yu.cartoon.cartoon_web.mapper.CartoonMapper;
import cn.yu.cartoon.cartoon_web.mapper.CartoonTypeRelationMapper;
import cn.yu.cartoon.cartoon_web.pojo.dto.Cartoon;
import cn.yu.cartoon.cartoon_web.pojo.dto.CartoonTypeRelation;
import cn.yu.cartoon.cartoon_web.service.CartoonService;
import cn.yu.cartoon.cartoon_web.util.FilesUtils;
import cn.yu.cartoon.cartoon_web.util.FtpUtil;
import cn.yu.cartoon.cartoon_web.util.RandomUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

/**
 * @author Yu
 * @version 1.0
 * @date 2019/2/14 20:57
 **/
@Service("cartoonService")
class CartoonServiceImpl implements CartoonService {

    private final CartoonMapper cartoonMapper;
    private final CartoonTypeRelationMapper cartoonTypeRelationMapper;

    public CartoonServiceImpl(CartoonMapper cartoonMapper, CartoonTypeRelationMapper cartoonTypeRelationMapper) {
        this.cartoonMapper = cartoonMapper;
        this.cartoonTypeRelationMapper = cartoonTypeRelationMapper;
    }

    @Override
    public void createCartoon(Cartoon cartoon, Integer[] types, MultipartFile multipartFile)  throws IOException{

        uploadFrontCoverToImgServer(cartoon, multipartFile);

        cartoon.setCartoonUpdateTime(new Date());
        cartoon.setIsDeleted((byte)0);
        cartoon.setReadCount(0);
        cartoon.setCollectCount(0);
        cartoonMapper.insertAndGetId(cartoon);

        CartoonTypeRelation cartoonTypeRelation = new CartoonTypeRelation();
        for (Integer typeId: types) {
            cartoonTypeRelation.setCartoonId(cartoon.getCartoonId());
            cartoonTypeRelation.setTypeId(typeId);
            cartoonTypeRelationMapper.insert(cartoonTypeRelation);
        }
    }

    @Override
    public void uploadFrontCoverToImgServer(Cartoon cartoon, MultipartFile multipartFile) throws IOException{

        String cartoonUri = RandomUtils.randomString(8);
        cartoon.setCartoonUri(cartoonUri);


        //所有的漫画封面都是同一个名字
        String imgFileName = CartoonFtpConfig.getCartoonFrontCoverImaName();
        String tempImagFileName = FilesUtils.multifile2file(multipartFile, TempDirConfig.getTempZipDirPath(), imgFileName);
        FtpUtil.upload(CartoonFtpConfig.getHost(), CartoonFtpConfig.getPort(),
                CartoonFtpConfig.getUserName(), CartoonFtpConfig.getPassword(),
                tempImagFileName, CartoonFtpConfig.getBasePath(), cartoonUri, true);
    }

}
