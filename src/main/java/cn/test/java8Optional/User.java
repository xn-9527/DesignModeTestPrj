package cn.test.java8Optional;

import lombok.Data;

import java.util.Optional;

/**
 * Created by xiaoni on 2018/12/17.
 */
@Data
public class User {
    private String name;
    private Address adress;
    private String nickName;

    /**
     * 校验名称的合法性
     *
     * @param name
     * @throws IllegalArgumentException
     */
    public void setName(String name) throws IllegalArgumentException {
        this.name = Optional.ofNullable(name)
                .filter(User::isNameValid)
                .orElseThrow(()->new IllegalArgumentException("Invalid username."));
    }

    private static boolean isNameValid(String name) {
        return "abc".equals(name);
    }
}
