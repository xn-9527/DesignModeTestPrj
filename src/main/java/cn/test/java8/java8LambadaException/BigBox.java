package cn.test.java8.java8LambadaException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by xiaoni on 2019/8/22.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BigBox {
    List<Box> boxes;
}
