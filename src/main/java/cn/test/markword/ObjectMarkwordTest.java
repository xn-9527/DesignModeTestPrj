package cn.test.markword;

import cn.test.equals.User;
import org.openjdk.jol.info.ClassLayout;

/**
 * @author Chay
 * @date 2020/5/11 10:43
 */
public class ObjectMarkwordTest {
    public static void main(String[] args) {
        User user = new User("a", "mail", "fffd2341ff");
        System.out.println(ClassLayout.parseInstance(user).toPrintable());
    }
}
