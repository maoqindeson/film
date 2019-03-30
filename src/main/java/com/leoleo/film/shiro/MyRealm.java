package com.leoleo.film.shiro;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.leoleo.film.entity.Order;
import com.leoleo.film.entity.User;
import com.leoleo.film.service.OrderService;
import com.leoleo.film.service.UserService;
import com.leoleo.film.utils.JWTUtil;
import com.leoleo.film.utils.StringTools;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class MyRealm extends AuthorizingRealm {


    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
//    @Autowired
//    private SysRoleService sysRoleService;
//    @Autowired
//    private SysPermissionService sysPermissionService;



    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = JWTUtil.getUsername(principals.toString());
        if (null==userService.getUserByName(username)){
            return null;
        }
        User userInfo = userService.getUserByName(username);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        if (userInfo.getRole()==null){
            return null;
        }else {
            //查询该用户角色，和权限
//            RolePrice rolePrice = rolePriceService.getRolePriceByRole(userInfo.getRole());
//            if (null==rolePrice){
//                return null;
//            }
            List<String> roleList = new ArrayList<>();
            List<String> priceList = new ArrayList<>();
            roleList.add(userInfo.getRole());
            priceList.add("rolePrice.getPrice()");
            //将角色和权限放进SimpleAuthorizationInfo中，shiro自动会去匹配角色权限
            authorizationInfo.addRoles(roleList);
            authorizationInfo.addStringPermissions(priceList);
            return authorizationInfo;
        }
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String username = JWTUtil.getCurrentUsernameByToken(token);
        if (StringTools.isNullOrEmpty(username)) {
            throw new AuthenticationException("token invalid");
        }

        User userBean = userService.selectOne(new EntityWrapper<User>().eq("name",username));
        if (userBean == null) {
            throw new AuthenticationException("User didn't existed!");
        }
//
//        if (! JWTUtil.verify(token, username)) {
//            throw new AuthenticationException("Username or password error");
//        }

        return new SimpleAuthenticationInfo(token, token, "my_realm");
    }
}
