
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;


/**
 * Created by xuweijie on 2017/3/2.
 * 使用realm方式访问得到数据认证
 */
public class CustomRealmTest {

    @Test
    public void testCustomRealm(){
        //创建SecurityManager工厂
        Factory<SecurityManager> factory=new IniSecurityManagerFactory("classpath:shiro-realm.ini");

        //创建SecurityManager
        SecurityManager securityManager=factory.getInstance();

        //将securityManager设置到当前的运行环境中
        SecurityUtils.setSecurityManager(securityManager);

        //从SecurityUtils里边的到一个subject
        Subject subject=SecurityUtils.getSubject();

        // 在认证提交前准备token（令牌）
        // 模拟用户输入的账号和密码，将来是由用户输入进去从页面传送过来
        UsernamePasswordToken token=new UsernamePasswordToken("zhangsan","111111");

        // 执行认证提交
        try {
            subject.login(token);
        }catch (AuthenticationException e){
            //认证失败
            e.printStackTrace();
        }

        //是否认证通过
        boolean isAuthenticated=subject.isAuthenticated();
        System.out.println("是否认证通过：" + isAuthenticated);

        //退出操作
        subject.logout();
    }

}