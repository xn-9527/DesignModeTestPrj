package cn.test.markword;

import cn.test.equals.User;
import org.openjdk.jol.info.ClassLayout;

/**
 * Java openjdk 提供jol 工具，可以查看class的头信息
 *
 *  
 *
 * 下载 jol 工具包
 *
 * https://repo.maven.apache.org/maven2/org/openjdk/jol/jol-cli/
 *
 * 选择一个版本，进去后下载 jol-cli-*.*-full.jar  一定要下载full 的jar
 *
 * 导入包到maven。
 * mvn install:install-file -Dfile=你的本地依赖包的地址/jol-cli-0.9-full.jar -DgroupId=org.openjdk.jol -DartifactId=jol-core -Dversion=0.9 -Dpackaging=jar -DgeneratePom=true
 *
 * pom引用
 * <dependency>
 *   <groupId>org.openjdk.jol</groupId>
 *   <artifactId>jol-core</artifactId>
 *   <version>0.9</version>
 * </dependency>
 *
 * 打印：
 *
 * System.out.println(ClassLayout.parseInstance(byte.class).toPrintable());
 * ————————————————
 * 版权声明：本文为CSDN博主「dandanforgetlove」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/dandanforgetlove/article/details/106025238
 *
 * @author Chay
 * @date 2020/5/11 10:43
 */
public class ObjectMarkwordTest {
    public static void main(String[] args) {
        User user = new User("a", "mail", "fffd2341ff");
        System.out.println(ClassLayout.parseInstance(user).toPrintable());
    }
}
