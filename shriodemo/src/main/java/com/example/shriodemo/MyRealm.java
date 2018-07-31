package com.example.shriodemo;

import com.example.shriodemo.domain.RoleInfo;
import com.example.shriodemo.domain.SysPermission;
import com.example.shriodemo.domain.UserInfo;
import org.apache.catalina.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MyRealm extends AuthorizingRealm {

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authenticationInfo=new SimpleAuthorizationInfo ();
        UserInfo userInfo=(UserInfo) principalCollection.getPrimaryPrincipal();
        List<RoleInfo> roleInfos=userInfo.getRoles();
        for (RoleInfo r :roleInfos){
           authenticationInfo.addRole(r.getName());
           for (SysPermission p :r.getPermissions()){
               authenticationInfo.addStringPermission(p.getPermission());
           }
        }
        return authenticationInfo;
    }
    @Autowired
    private  UserJPA userJPA;
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken=(UsernamePasswordToken)authenticationToken;
        UserInfo u=userJPA.findUserByUsername(usernamePasswordToken.getUsername());
        if(u==null){
            return  null;
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo=new SimpleAuthenticationInfo(
                u,u.getPassword(),ByteSource.Util.bytes(u.getCredentialsSalt()),getName());
        return simpleAuthenticationInfo;
    }
}
