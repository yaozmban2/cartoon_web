package cn.yu.cartoon.cartoon_web.controller;

import cn.yu.cartoon.cartoon_web.pojo.dto.Cartoon;
import cn.yu.cartoon.cartoon_web.pojo.dto.CartoonType;
import cn.yu.cartoon.cartoon_web.pojo.dto.Country;
import cn.yu.cartoon.cartoon_web.service.CartoonTypeService;
import cn.yu.cartoon.cartoon_web.service.CountryService;
import cn.yu.cartoon.cartoon_web.util.FilesUtils;
import cn.yu.cartoon.cartoon_web.util.ZipUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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

    @Autowired
    public CartoonController(CountryService countryService, CartoonTypeService cartoonTypeService) {
        this.countryService = countryService;
        this.cartoonTypeService = cartoonTypeService;
    }

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

    @ResponseBody
    @PostMapping("/upload")
    public String uploadCartoon(@RequestParam("zip") MultipartFile zip, HttpServletRequest request){

        //如果传入的数据是空
        if (null == zip || zip.isEmpty()) {
            return "传入的数据为空";
        }

        //将MultipartFile转为File
        String fileURI = null;
        try {
            fileURI = FilesUtils.multifile2file(zip, "sdasda");
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return "服务器出了点小问题";
        }
        if (null == fileURI) {
            return "服务器出了点小问题";
        }
        File zipFile = new File(fileURI);
        try {
            if (!ZipUtils.isArchiveFile(zipFile)) {
                return "不是zip文件";
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return "服务器出了点小问题";
        }

        return "是zip文件";
    }
}
