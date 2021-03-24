package cn.test.listAdd;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: chay
 * @date: 2021/3/24 15:29 
 * @description:
 */
public class TestListModify {


    public static void main(String[] args) {
        List<User> userList = new ArrayList<>();
        userList.add(new User("a3", 2));
        userList.add(new User("b4", 21));
        System.out.println(userList);
        User topUser = userList.get(0);
        topUser.setName("a4");
        System.out.println(userList);
        topUser = new User("c5", 22);
        System.out.println(userList);
    }
}
