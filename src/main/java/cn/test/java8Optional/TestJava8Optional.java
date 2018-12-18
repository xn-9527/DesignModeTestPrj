package cn.test.java8Optional;

import java.util.Optional;

/**
 * Created by xiaoni on 2018/12/17.
 */
public class TestJava8Optional {
    public static void main(String[] args) {
        System.out.println("校验null名字===================");
        System.out.println(getName(null));

        System.out.println("校验nullAddressCity名字===================");
        try {
            System.out.println(getUserAddressCityName(null));
        } catch (IllegalArgumentException e) {
            System.out.println("校验nullAddressCity名字error:" + e.getMessage());
            e.printStackTrace();
        }

        User user = new User();
        user.setName("abc");
        System.out.println("校验有值的名字===================");
        System.out.println(getName(user));

        System.out.println("校验null地址===================");
        try {
            System.out.println(getUserAddressCityName(user));
        } catch (IllegalArgumentException e) {
            System.out.println("校验null地址error:" + e.getMessage());
            e.printStackTrace();
        }
        Address address = new Address();
        user.setAdress(address);
        System.out.println("校验null城市===================");
        try {
            System.out.println(getUserAddressCityName(user));
        } catch (IllegalArgumentException e) {
            System.out.println("校验null城市error:" + e.getMessage());
            e.printStackTrace();
        }
        City city = new City();
        city.setName("上海");
        address.setCity(city);
        System.out.println("校验有值的城市===================");
        try {
            System.out.println(getUserAddressCityName(user));
        } catch (IllegalArgumentException e) {
            System.out.println("校验有值的城市error:" + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("校验ifPresent,代替为空则不打印===================");
        Optional optional = Optional.ofNullable(getUserAddressCityName(user));
        optional.ifPresent(System.out::println);
        try {
            optional = Optional.ofNullable(getNickName(user));
            optional.ifPresent(System.out::println);//null确实没打印
        } catch (Exception e) {
            System.out.println("校验ifPresent cityName error:" + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("校验UserName的合法性===================");
        try {
            user.setName("bcd");
        } catch (Exception e) {
            System.out.println("校验UserName的合法性error:" + e.getMessage());
            e.printStackTrace();
        }
    }

    public static String getName(User u) {
        return Optional.ofNullable(u)
                .map(user->user.getName())
                .orElse("Unknown");
    }

    public static String getNickName(User u) {
        return Optional.ofNullable(u)
                .map(user->user.getNickName())
                .orElse(null);
    }

    /**
     * .get级联一起校验，防止nullException
     * @param u
     * @return
     * @throws IllegalArgumentException
     */
    public static String getUserAddressCityName(User u) throws IllegalArgumentException {
        return Optional.ofNullable(u)
                .map(b->b.getAdress())
                .map(a->a.getCity())
                .map(n->n.getName())
                .orElseThrow(()->new IllegalArgumentException("The value of param comp isn't available."));
    }


}
