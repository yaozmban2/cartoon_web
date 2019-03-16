package cn.yu.cartoon.cartoon_web.controller;

import cn.yu.cartoon.cartoon_web.pojo.dto.Cartoon;
import cn.yu.cartoon.cartoon_web.pojo.dto.CartoonType;
import cn.yu.cartoon.cartoon_web.pojo.dto.Chapter;
import cn.yu.cartoon.cartoon_web.pojo.dto.Country;
import cn.yu.cartoon.cartoon_web.pojo.vo.cartoonvo.CartoonInfoVo;
import cn.yu.cartoon.cartoon_web.service.CartoonService;
import cn.yu.cartoon.cartoon_web.service.CartoonTypeService;
import cn.yu.cartoon.cartoon_web.service.ChapterService;
import cn.yu.cartoon.cartoon_web.service.CountryService;
import cn.yu.cartoon.cartoon_web.util.FilesUtils;
import cn.yu.cartoon.cartoon_web.util.ZipUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 漫画相关操作
 *
 * @author Yu
 * @version 1.0
 * @date 2019/2/14 11:01
 **/

@Controller
public class CartoonController {

    private static Logger logger = LoggerFactory.getLogger(CartoonController.class);

    private final CountryService countryService;
    private final CartoonTypeService cartoonTypeService;
    private final CartoonService cartoonService;
    private final ChapterService chapterService;

    @Autowired
    public CartoonController(CountryService countryService, CartoonTypeService cartoonTypeService, CartoonService cartoonService, ChapterService chapterService) {
        this.countryService = countryService;
        this.cartoonTypeService = cartoonTypeService;
        this.cartoonService = cartoonService;
        this.chapterService = chapterService;
    }

    /**
     * 跳转到漫画信息修改页面（若是没有带上漫画ID 则为添加漫画）
     *
     * @author Yu
     * @date 21:00 2019/3/15
     * @param cartoonId 漫画id
     * @return 转到漫画修改页面
     **/
    @GetMapping("/manager/upload")
    public String forwardUpload(Integer cartoonId, Model model) {

        if (null == cartoonId || cartoonId < 0) {
            Cartoon cartoon = new Cartoon();
            model.addAttribute("cartoon", cartoon);
        }
        List<Country> countryList = countryService.getAllCountryName();
        model.addAttribute("countryList", countryList);

        List<CartoonType> typeList = cartoonTypeService.getAllCartoonType();
        model.addAttribute("typeList", typeList);

        return "/item/cartoonEdit";
    }

    /**
     * 上传新建漫画
     *
     * @author Yu
     * @date 10:44 2019/3/16
     **/
    @ResponseBody
    @PostMapping("/manager/upload")
    public String uploadCartoon(Cartoon cartoon, @RequestParam(value = "type[]") Integer[] types,
                                @RequestParam(value = "image") MultipartFile multipartFile){

        try {
            cartoonService.createCartoon(cartoon, types, multipartFile);
            return "上传成功";
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return "服务器出错";
        }
    }

    /**
     *  跳转到漫画相关信息页面
     *
     * @author Yu
     * @date 10:46 2019/3/16
     **/
    @GetMapping("/cartoon/infoPage/{cartoonId}_{page}")
    public String getCartoonInfoPage(@PathVariable Integer cartoonId, @PathVariable Integer page, Model model) {

        Integer pageSize = 10;

        if (null == cartoonId) {
            return "404";
        }

        CartoonInfoVo cartoonInfoVo = cartoonService.selectCartoonById(cartoonId);
        if (null == cartoonInfoVo) {
            return "参数不对";
        }
        Integer pageCount = ( cartoonInfoVo.getChapterCount() / pageSize ) + 1;
        List<Chapter> chapterList = chapterService.getChaptersByCartoonIdByPage(cartoonId, page, pageSize);

        model.addAttribute("cartoon", cartoonInfoVo);
        model.addAttribute("pageCount", pageCount);
        model.addAttribute("chapterList", chapterList);

        return "/cartoonPage/cartoonInfoPage";
    }
}
