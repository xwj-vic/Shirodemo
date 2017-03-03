import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * Created by xuweijie on 2017/3/3.
 */
public class AuthorizationRealmTest {
    //自定义realm进行资源授权测试
    @Test
    public void testAuthorizationCustomRealm()
    {
        //第一步，创建SecurityManager工厂
        IniSecurityManagerFactory factory=new IniSecurityManagerFactory("classpath:shiro-realm.ini");

        //第二步:创建SecurityManager
        SecurityManager securityManager=factory.getInstance();

        //第三步，将SecurityManager设置到系统运行环境，和spring整合后会将SecurityManager配置到spring容器中，一般单例管理
        SecurityUtils.setSecurityManager(securityManager);

        //第四步，创建subject
        Subject subject=SecurityUtils.getSubject();

        //创建token令牌,这里的用户名和密码以后由用户输入
        UsernamePasswordToken token=new UsernamePasswordToken("zhangsan","111111");

        try {
            //执行认证，将用户输入的信息同数据库(即.ini配置文件)中信息进行对比
            subject.login(token);
        }catch (AuthenticationException e)
        {
            e.printStackTrace();
        }

        System.out.println("认证状态:"+subject.isAuthenticated());



        //基于资源的授权,调用isPermitted方法会调用CustomRealm从数据库查询正确的权限数据
        // isPermitted传入权限标识符，判断user:create:1是否在CustomRealm查询到的权限数据之内
        boolean isPermitted=subject.isPermitted("user:create:1");//该用户是否有对user的1资源进行创建的权限
        System.out.println("单个权限判断"+isPermitted);

        //多个权限判断
        boolean isPermittedAll=subject.isPermittedAll("user:create:1","user:create");
        System.out.println("多个权限判断:"+isPermittedAll);
    }
}
