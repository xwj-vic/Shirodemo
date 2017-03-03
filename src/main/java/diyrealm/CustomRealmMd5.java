package diyrealm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

/**
 * Created by xuweijie on 2017/3/2.
 * reaml支持散列算法
 * 模仿数据库中的用户名和加密密文
 *
 */
public class CustomRealmMd5 extends AuthorizingRealm{

    //设置名称
    @Override
    public void setName(String name) {
        super.setName("customRealmMd5");
    }

    //认证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //authenticationToken是用户输入的
        //第一步:从authenticationToken中取出身份信息
        String userCode= (String) authenticationToken.getPrincipal();

        //第二步:根据用户输入的userCode丛数据库查询
        //实际情况这里是写查询数据库的代码（.......）


        //如果查不到返回null，

        // 模拟根据用户名从数据库查询到的密码,散列值
        String password = "f3694f162729b7d0254c6e40260bf15c";
        // 从数据库获取salt
        String salt = "qwerty";
        //上边散列值和盐对应的明文：111111

        //如果查询到，返回认证信息AuthenticationInfo
        SimpleAuthenticationInfo simpleAuthenticationInfo=
                new SimpleAuthenticationInfo(userCode,password,ByteSource.Util.bytes(salt),this.getName());

        return simpleAuthenticationInfo;
    }

    //授权
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }


}
