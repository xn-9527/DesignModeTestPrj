package cn.test.equals;

import lombok.Data;

/**
 * Created by xiaoni on 2019/2/22.
 */
@Data
public class User {
    String name;
    String sex;
    String idCard;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (sex != null ? !sex.equals(user.sex) : user.sex != null) return false;
        return idCard != null ? idCard.equals(user.idCard) : user.idCard == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (idCard != null ? idCard.hashCode() : 0);
        return result;
    }
}
