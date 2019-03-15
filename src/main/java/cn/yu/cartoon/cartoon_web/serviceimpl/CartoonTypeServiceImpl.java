package cn.yu.cartoon.cartoon_web.serviceimpl;

import cn.yu.cartoon.cartoon_web.mapper.CartoonTypeMapper;
import cn.yu.cartoon.cartoon_web.pojo.dto.CartoonType;
import cn.yu.cartoon.cartoon_web.service.CartoonTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Yu
 * @version 1.0
 * @date 2019/3/15 20:49
 **/
@Service("cartoonTypeService")
public class CartoonTypeServiceImpl implements CartoonTypeService {

    private final CartoonTypeMapper cartoonTypeMapper;

    @Autowired
    public CartoonTypeServiceImpl(CartoonTypeMapper cartoonTypeMapper) {
        this.cartoonTypeMapper = cartoonTypeMapper;
    }

    @Override
    public List<CartoonType> getAllCartoonType() {

        return cartoonTypeMapper.selectAll();
    }
}
