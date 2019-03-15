/*
package cn.yu.cartoon.cartoon_web.config.shiro;

import cn.yu.cartoon.cartoon_web.service.LoginService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

*/
/**
 * @author Yu
 * @version 1.0
 * @date 2019/3/13 21:14
 **//*


public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    LoginService loginService;

    private SimpleAuthenticationInfo info = null;

*/
/**
     *  执行认证逻辑
     *
     * @author Yu
     * @date 19:32 2019/3/14
     **//*


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        String name = "admin";
        String password = "123456";

        // 将token装换成UsernamePasswordToken
        UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
        // 获取用户名即可
        String username = upToken.getUsername();
        if (!username.equals(name)) {
            return null;
        }
        // 查询数据库，是否查询到用户名和密码的用户
        return new SimpleAuthenticationInfo("", password, "");
    }


*/
/**
     *  执行授权逻辑
     *
     * @author Yu
     * @date 19:32 2019/3/14
     **//*


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }
}
*/
