
import com.sun.org.apache.regexp.internal.REUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.crypto.hash.HashService;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.SimpleByteSource;
import org.assertj.core.internal.Bytes;
import org.junit.Assert;
import org.junit.Test;

import javax.print.attribute.HashAttributeSet;
class  PasswordModel{
    private  String password;
    private  String salt;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
public class Test1 {

    private  PasswordModel passwordHelper(String username,String password){
        String salt=new SecureRandomNumberGenerator().nextBytes(20).toHex();
        SimpleHash simpleHash=new SimpleHash("md5",password,username+salt,2);
        PasswordModel model=new PasswordModel();
        model.setPassword(simpleHash.toHex());
        model.setSalt(salt);
        return  model;
    }

    @Test
    public  void  demo1(){
        Factory<SecurityManager> factory =
                new IniSecurityManagerFactory("classpath:shiro.ini");
        SecurityManager securityManager=((IniSecurityManagerFactory) factory).createInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject  subject=SecurityUtils.getSubject();
        UsernamePasswordToken token=new UsernamePasswordToken("zhang","123");
        subject.login(token);
        Assert.assertEquals(true,subject.isAuthenticated());
    }
    @Test
    public  void  Endecrypt(){
       DefaultHashService hashService=new DefaultHashService();
       hashService.setPrivateSalt(ByteSource.Util.bytes("hello"));
       hashService.setHashAlgorithmName("SHA-512");
       hashService.setGeneratePublicSalt(true);
       hashService.setHashIterations(1);
       hashService.setRandomNumberGenerator(new SecureRandomNumberGenerator());
       HashRequest hashRequest=new HashRequest.Builder()
               .setAlgorithmName("MD5")
               .setIterations(2)
               .setSalt(ByteSource.Util.bytes("hello"))
               .setSource(ByteSource.Util.bytes("123456")).build();
       String hex=hashService.computeHash(hashRequest).toHex();
       System.out.print(hex);
    }
    @Test
    public void  passwordDemo(){
        PasswordService passwordService=new DefaultPasswordService();
        ((DefaultPasswordService) passwordService).setHashService(new DefaultHashService());
        String hex=passwordService.encryptPassword("123456");
        System.out.print(hex);
    }
    @Test
    public  void  hashDemo(){
        java.lang.String algorithmName="MD5";
        java.lang.Object source="123456";
        String username="james";
        java.lang.Object salt=new SecureRandomNumberGenerator().nextBytes(30).toHex();
        int hashIterations=2;
        SimpleHash hash=new SimpleHash(algorithmName,source,username+salt,hashIterations);
        System.out.print(hash.toHex());
    }
    @Test
    public  void  passhelperDemo(){
        PasswordModel model= passwordHelper("james","123456");
        System.out.println(model.getPassword());
        System.out.println(model.getSalt());
    }

}
