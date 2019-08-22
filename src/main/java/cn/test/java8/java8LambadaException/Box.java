package cn.test.java8.java8LambadaException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by xiaoni on 2019/8/22.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Box {
    /**
     * 柜子编号
     */
    private String index;
    /**
     * 物品大类Id
     */
    private Long goodsTypeId;
}
