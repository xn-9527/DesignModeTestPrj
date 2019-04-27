package cn.test.enumTest.EnumMap;


import lombok.extern.slf4j.Slf4j;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by xiaoni on 2019/4/27.
 */
@Slf4j
public class Herb {
    //烹饪用的香草
    public enum Type {ANNUAL, PERENNIAL, BIENNIAL}

    private final String name;
    private final Type type;

    Herb(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        return name;
    }

    public static void main(String[] args) {
        //假设一个香草的数组，表示一座花园中的植物，你想要按照类型(一年生，多年生或者两年生植物)进行组织之后将这些植物列出来。
        Herb[] garden = {new Herb("a", Type.ANNUAL), new Herb("b", Type.BIENNIAL), new Herb("c", Type.PERENNIAL), new Herb("d", Type.ANNUAL)};
        Map<Type, Set<Herb>> herbsByType = new EnumMap<Herb.Type, Set<Herb>>(Herb.Type.class);
        for (Herb.Type t : Herb.Type.values()) {
            herbsByType.put(t, new HashSet<Herb>());
        }
        for (Herb h : garden) {
            herbsByType.get(h.type).add(h);
        }
        log.info(herbsByType.toString());
    }
}
