import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Test;

/**
 * Created by xuweijie on 2017/3/2.
 */
public class MD5Test {

    @Test
    public void md5slatTest(){
        //模拟用户输入的密码
        String source="111111";
        //加入我们的盐salt
        String salt="qwerty";

        //密码11111经过散列1次得到的密码:f3694f162729b7d0254c6e40260bf15c
        int hashIterations=1;

        //构造方法中：
        //第一个参数：明文，原始密码
        //第二个参数：盐，通过使用随机数
        //第三个参数：散列的次数，比如散列两次，相当 于md5(md5(''))
        Md5Hash md5Hash=new Md5Hash(source,salt,hashIterations);

        String password_md5=md5Hash.toString();
        System.out.println(password_md5);
    }
}
