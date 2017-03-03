package diyrealm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuweijie on 2017/3/2.
 * 自定义realm访问数据库（在这是模拟数据库的ini文件，为的是符合实际开发流程）
 * 实际开发中我们通过realm从数据库中查询用户信息，所以realm的作用可想而知:根据token中的身份信息去查询数据库
 * 基于资源的方式进行授权
 */
public class CustomRealm extends AuthorizingRealm {

    //设置realm的名称
    @Override
    public void setName(String name) {
        super.setName("customRealm");
    }

    //用于认证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //authenticationToken是用户输入的
        //第一步:从authenticationToken中取出身份信息
        String userCode= (String) authenticationToken.getPrincipal();

        //第二步:根据用户输入的userCode丛数据库查询
            //实际情况这里是写查询数据库的代码（.......）
        //模拟从数据库查询到的密码
        String password="111111";

        //如果查不到返回null，
        //如果查询到，返回认证信息AuthenticationInfo
        SimpleAuthenticationInfo simpleAuthenticationInfo=new SimpleAuthenticationInfo(userCode,password,this.getName());

        return simpleAuthenticationInfo;
    }

   //用于授权
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //从principalCollection获取主身份信息
        //将getPrimaryPrincipal方法返回值转为真实身份类型
        // (在上边的doGetAuthenticationInfo认证通过填充到SimpleAuthenticationInfo)
        String userCode= (String) principalCollection.getPrimaryPrincipal();

        //根据身份信息获取权限信息,
        //模拟从数据库中获取到的动态权限数据
        List<String> permissions=new ArrayList<String>();
        permissions.add("user:create");//模拟user的创建权限
        permissions.add("items:add");//模拟商品的添加权限

        //查到权限数据，返回授权信息(包括上边的permissions)
        SimpleAuthorizationInfo authorizationInfo=new SimpleAuthorizationInfo();

        //将上边查询到授权信息填充到simpleAuthorizationInfo对象中
        authorizationInfo.addStringPermissions(permissions);

        return authorizationInfo;
    }

}
