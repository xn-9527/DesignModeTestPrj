package cn.test.enumTest.EnumMap;

import lombok.extern.slf4j.Slf4j;

import java.util.EnumMap;
import java.util.Map;

/**
 * Created by xiaoni on 2019/4/28.
 *
 * 这个写法特别适合状态机
 */
@Slf4j
public enum Phase {
    SOLID, LIQUID, GAS;

    public enum Transition {
        //融化               凝固
        MELT(SOLID, LIQUID), FREEZE(LIQUID, SOLID),
        //气化              液化
        BOIL(LIQUID, GAS), CONDENSE(GAS, LIQUID),
        //升华，崇高的         凝华
        SUBLIME(SOLID, GAS), DEPOSIT(GAS, SOLID);

        //初始状态
        private final Phase src;
        //目标状态
        private final Phase dst;

        Transition(Phase src, Phase dst) {
            this.src = src;
            this.dst = dst;
        }
    }
    //初始化状态和过程的map
    private static final Map<Phase, Map<Phase, Transition>> map =
            new EnumMap<Phase, Map<Phase, Transition>>(Phase.class);
    static {
        for (Phase p : Phase.values()) {
            map.put(p, new EnumMap<Phase, Transition>(Phase.class));
        }
        for (Transition transition : Transition.values()) {
            map.get(transition.src).put(transition.dst, transition);
        }
    }

    public static Transition from (Phase src, Phase dst) {
        return map.get(src).get(dst);
    }

    public static void main(String[] args) {
        Phase src = Phase.GAS;
        Phase dst = Phase.LIQUID;
        log.info(src + " to " + dst + " transition is " + Phase.from(src, dst));
    }
}
