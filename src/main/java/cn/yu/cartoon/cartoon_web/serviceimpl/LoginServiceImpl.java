package cn.yu.cartoon.cartoon_web.serviceimpl;

import cn.yu.cartoon.cartoon_web.mapper.UserMapper;
import cn.yu.cartoon.cartoon_web.pojo.dto.User;
import cn.yu.cartoon.cartoon_web.service.LoginService;
import cn.yu.cartoon.cartoon_web.util.Encryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

/**
 * @author Yu
 * @version 1.0
 * @date 2019/2/8 23:02
 **/
@Service(value = "loginService")
public class LoginServiceImpl implements LoginService {

    @Autowired
    UserMapper userMapper;

    @Override
    public boolean login(String userName, String password) throws NoSuchAlgorithmException {
        User user = userMapper.selectUserByUserName(userName);
        return Encryption.md5(password).equals(user.getUserPassword());
    }
}
