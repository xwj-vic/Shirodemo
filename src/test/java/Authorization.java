import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by xuweijie on 2017/3/2.
 * 测试授权(先认证后授权),推荐用基于资源的授权方式
 */
public class Authorization {
    //角色授权测试和资源授权测试
    @Test
    public void testAuthorization(){
        //第一步，创建SecurityManager工厂
        Factory<SecurityManager> factory=new IniSecurityManagerFactory("classpath:shiro-permission.ini");
        //第二步:创建SecurityManager
        SecurityManager securityManager=factory.getInstance();
        //第三步，将SecurityManager设置到系统运行环境，和spring整合后会将SecurityManager配置到spring容器中，一般单例管理
        SecurityUtils.setSecurityManager(securityManager);
        //第四步，创建subject
        Subject subject=SecurityUtils.getSubject();
        //创建token令牌,这里的用户名和密码以后由用户输入
        UsernamePasswordToken token=new UsernamePasswordToken("zhangsan","123");
        try {
            //执行认证，将用户输入的信息同数据库(即.ini配置文件)中信息进行对比
            subject.login(token);
        }catch (AuthenticationException e)
        {
            e.printStackTrace();
        }
        System.out.println("认证状态:"+subject.isAuthenticated());

        //认证通过后才能执行授权
        //第一种授权方式是基于角色的授权,hasRole传入角色的标识
        boolean ishasRole=subject.hasRole("role1");//该用户是否有role1这个角色
        System.out.println("单个角色判断"+ishasRole);

        //hasAllRoles是否拥有多个角色
        boolean hasAllRoles=subject.hasAllRoles(Arrays.asList("role1","role2"));
        System.out.println("多个角色判断"+hasAllRoles);

        //使用check方法进行授权，如果授权不通过会抛出异常
        try{
            subject.checkRole("role3");
        }catch (UnauthorizedException e){
            System.out.println("授权未通过");
        }


        //第二种授权方式是基于资源的授权,isPermitted传入权限标识符
        boolean isPermitted=subject.isPermitted("user:create");//该用户是否有对user资源进行创建的权限
        System.out.println("单个权限判断"+isPermitted);

        //多个权限判断
        boolean isPermittedAll=subject.isPermittedAll("user:create:1","user:update");
        System.out.println("多个权限判断:"+isPermittedAll);

    }
}
