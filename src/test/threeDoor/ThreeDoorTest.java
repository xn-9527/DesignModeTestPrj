package test.threeDoor;

import java.util.Random;

/**
 * @author Created by xiaoni on 2018/10/7.
 * 三门问题
 * 1.三扇门：后面对应三个奖品，车，羊，羊
 * 2.观众随机先指定一个门
 * 3.主持人知道每个面后面是什么，会打开一个不是车的门并问观众是不是要换选择
 * 4.问题：观众换门的得奖概率大还是不换的概率大？
 *
 * 数学推算换的中奖概率是2/3,不换的中奖概率是1/3
 *
 * 本程序用实验模拟，换和不换的概率
 */
public class ThreeDoorTest {
    //奖品序号
    private final static int reward = 0;
    //不换正确次数
    Long noChangeBingo = 0L;
    //换正确次数
    Long changeBingo = 0L;

    Random random = new Random();
    private void calThreeDoor(Long tryTime, Integer totalReward) throws Exception {
        noChangeBingo = 0L;
        changeBingo = 0L;
        for (long i = 0;i < tryTime;i++) {
            int take = random.nextInt(totalReward);
//            System.out.println("用户选择:" + take);

            //不换正确次数
            if (take == reward) {
                noChangeBingo ++;
            }

            //换正确次数
            if (take == reward) {
                //如果一开始选对了，换，必然就错了
            } else {
                //如果一开始选错了，主持人会帮忙去掉一个错误答案
                //初始化主持人排除
                int wrongTake = random.nextInt(totalReward);
                //主持人帮忙排除的肯定不是reward这个正确答案,所以只要主持人排除的不是用户选择的就行
                while (wrongTake == take || wrongTake == reward) {
                    wrongTake = random.nextInt(totalReward);
                }
                //用户重新选择,不是主持人排除的
                int lastTake = take;
                take = random.nextInt(totalReward);
                while (take == wrongTake || take == lastTake) {
                    take = random.nextInt(totalReward);
                }
                if (take == reward) {
                    changeBingo ++;
                }
            }
        }
        System.out.println("========================================");
        System.out.println("总奖品数:" + totalReward);
        System.out.println("不换的正确率:" + (double)noChangeBingo/tryTime);
        System.out.println("换的正确率:" + (double)changeBingo/tryTime);
    }

    public static void main(String[] args) {
        try {
            Long tryTime = 100000L;
            System.out.println("总尝试次数:" + tryTime);
            new ThreeDoorTest().calThreeDoor(tryTime, 3);
            new ThreeDoorTest().calThreeDoor(tryTime, 4);
            new ThreeDoorTest().calThreeDoor(tryTime, 5);
//            new ThreeDoorTest().calThreeDoor(tryTime, 6);
//            new ThreeDoorTest().calThreeDoor(tryTime, 7);
            new ThreeDoorTest().calThreeDoor(tryTime, 10);
            new ThreeDoorTest().calThreeDoor(tryTime, 20);
            new ThreeDoorTest().calThreeDoor(tryTime, 100);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }
}
