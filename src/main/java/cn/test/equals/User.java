package cn.test.equals;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by xiaoni on 2019/2/22.
 */
@Data
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class User {
    String name;
    String sex;
    String idCard;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            log.info("引用不同");
            return true;
        }
        if (!(o instanceof User)) {
            log.info("类型不同");
            return false;
        }

        User user = (User) o;


        if (name != null ? !name.equals(user.name) : user.name != null) {
            log.info("name不同");
            return false;
        }

        if (sex != null ? !sex.equals(user.sex) : user.sex != null) {
            log.info("sex不同");
            return false;
        }
        return sex == null ? sex.equals(user.sex) : user.sex == null;
//        if (idCard != null ? !idCard.equals(user.idCard) : user.idCard != null) {
//            log.info("idCard不同");
//        }
//
//        return idCard != null ? idCard.equals(user.idCard) : user.idCard == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
//        result = 31 * result + (idCard != null ? idCard.hashCode() : 0);
        return result;
    }
}
