package cn.math.fibonacciSequence;

import cn.math.arithmeticSeries.ArithmeticSeries;
import cn.math.geometricSeries.GeometricSeries;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;

/**
 * Created by xiaoni on 2019/8/17.
 * 斐波那契弧线
 */
@Slf4j
public class FibonacciArc extends JPanel {
    //初始原点
    private final static int WIDTH = 1000;
    private final static int HEIGHT = 1000;
    private final static int X0 = WIDTH / 2;
    private final static int Y0 = HEIGHT / 2;
    //缩放倍数, 越大，越缩小(看到的螺旋越多)
    private final static int ZOOM = 1;
    //画几阶弧线
    private final static int N = 30;
    //画函数类型常量
    //斐波纳挈
    private final static int FUNCTION_FIBONACCI = 1;
    //等差级数
    private final static int FUNCTION_ARITHMETIC_SERIES = 2;
    //等比级数
    private final static int FUNCTION_GEOMETIRC_SERIES = 3;

    public FibonacciArc() {
        //默认斐波纳挈
        this.functionTypeId = FUNCTION_FIBONACCI;
    }

    public FibonacciArc(int functionTypeId) {
        this.functionTypeId = functionTypeId;
    }

    /**
     * 画什么类型的曲线
     */
    private int functionTypeId;

    @Override
    protected void paintComponent(Graphics g) {
        // TODO Auto-generated method stub
        super.paintComponent(g);

        //上一个斐波那契数
        int lastFibonacciI = 0;
        //扇形圆心点
        int x = X0;
        int y = Y0;
        //java画弧线图工具矩形区域左上角坐标
        int xArcRec = x;
        int yArcRec = y;
        //java画矩形图工具矩形区域左上角坐标
        int xRecRec = x;
        int yRecRec = y;
        //计算象限
        int startAngle = 0;
        //要画的度数
        int arcAngle = 90;

        ArithmeticSeries arithmeticSeries = new ArithmeticSeries(1, 10);
        GeometricSeries geometricSeries = new GeometricSeries(1, 2);

        //因为左上角是坐标原点，所以计算坐标平移量时要反着来
        for (int i = 3; i < N; i++) {
            //计算斐波那契数
            int fibonacciI;
            switch (functionTypeId) {
                case FUNCTION_FIBONACCI:
                    fibonacciI = FibonacciSequence.f(i);
                    break;
                case FUNCTION_ARITHMETIC_SERIES:
                    fibonacciI = arithmeticSeries.f(i);
                    break;
                case FUNCTION_GEOMETIRC_SERIES:
                    fibonacciI = new Long(geometricSeries.f(i)).intValue();
                    break;
                default:
                    fibonacciI = FibonacciSequence.f(i);
            }

            int change = fibonacciI - lastFibonacciI;
            switch (i % 4) {
                case 0:
                    //第一象限
                    startAngle = 0;
                    x = x - change;

                    xRecRec = x;
                    yRecRec = y - fibonacciI;
                    break;
                case 1:
                    //第二象限
                    startAngle = 90;
                    y = y + change;

                    xRecRec = x - fibonacciI;
                    yRecRec = y - fibonacciI;
                    break;
                case 2:
                    //第三象限
                    startAngle = 180;
                    x = x + change;

                    xRecRec = x - fibonacciI;
                    yRecRec = y;
                    break;
                case 3:
                    //第四象限
                    startAngle = 270;
                    y = y - change;

                    xRecRec = x;
                    yRecRec = y;
                    break;
            }
            xArcRec = x - fibonacciI;
            yArcRec = y - fibonacciI;

            /**
             * 绘制一个覆盖指定矩形的圆弧或椭圆弧边框。
             得到的弧从 startAngle 开始跨越 arcAngle 度，并使用当前颜色。
             对角度的解释如下：0 度角位于 3 点钟位置。正值指示逆时针旋转，负值指示顺时针旋转。

             弧的中心是矩形的中心，此矩形的原点为 (x1 = x + width/2, y1 = y + wideth / 2)，大小由 width 和 height 参数指定。

             得到的弧覆盖 width + 1 像素宽乘以 height + 1 像素高的区域。

             角度是相对于外接矩形的非正方形区域指定的，45 度角始终落在从椭圆中心到外接矩形右上角的连线上。
             因此，如果外接矩形在一个轴上远远长于另一个轴，则弧段的起点和结束点的角度将沿边框长轴发生更大的偏斜
             x - 要绘制弧的左上角的 x 坐标。（矩形的左上角）
             y - 要绘制弧的左上角的 y 坐标。（矩形的左上角）
             width - 要绘制弧的宽度。
             height - 要绘制弧的高度。
             startAngle - 开始角度。
             arcAngle - 相对于开始角度而言，弧跨越的角度。
             */
            int drawWidth = 2 * fibonacciI / ZOOM;
            int drawHeight = 2 * fibonacciI / ZOOM;
            int drawArcX = X0 + (xArcRec - X0) / ZOOM;
            int drawArcY = Y0 + (yArcRec - Y0) / ZOOM;
            //画矩形
            g.setColor(Color.RED);
            g.drawRect(xRecRec / ZOOM, yRecRec / ZOOM, drawWidth / 2, drawHeight / 2);
            //画斐波那契弧线
            g.setColor(Color.MAGENTA);
            g.drawArc(drawArcX, drawArcY, drawWidth, drawHeight, startAngle, arcAngle);

            lastFibonacciI = fibonacciI;
        }
//        g2.drawArc(100, 100, 100, 100, 0, 360);
//        g2.drawArc(100, 100, 50, 50, 0, 360);
//        g2.drawArc(100, 100, 20, 20, 270, 360);
//        g2.drawArc(60, 60, 20, 20, 270, 360);
    }

    public static void main(String[] args) {
        JFrame f = new JFrame();
//        FibonacciArc fibonacciArc = new FibonacciArc();
//        FibonacciArc fibonacciArc = new FibonacciArc(FibonacciArc.FUNCTION_ARITHMETIC_SERIES);
        FibonacciArc fibonacciArc = new FibonacciArc(FibonacciArc.FUNCTION_GEOMETIRC_SERIES);
        fibonacciArc.repaint();
        f.add(fibonacciArc);

        //设置画布大小
        f.setMinimumSize(new Dimension(FibonacciArc.WIDTH, FibonacciArc.HEIGHT));
        f.pack();
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
