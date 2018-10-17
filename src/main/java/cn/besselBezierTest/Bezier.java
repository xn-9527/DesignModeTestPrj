package cn.besselBezierTest;

/**
 * Created by xiaoni on 2018/1/2.
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Bezier extends JPanel implements MouseListener,MouseMotionListener{
    private static final long serialVersionUID = 1L;
    private Point2D[] controlPoint;
    private Ellipse2D.Double[] ellipse;
    private static final double SIDELENGTH = 8;
    //当前画的控制点的第几个点
    private int numPoints;
    //几阶贝塞尔(等于起点、终点、控制点数量的总和)
    private int orderNum;
    //画点的分辨率
//    private double t=0.002;
    private double t=0.002;
    public Bezier(){
        numPoints = 0;
        controlPoint = new Point2D[4];
        ellipse = new Ellipse2D.Double[4];
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }
    public Bezier(int orderNum){
        numPoints = 0;
        this.orderNum = orderNum;
        controlPoint = new Point2D[orderNum];
        ellipse = new Ellipse2D.Double[orderNum];
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }
    @Override
    protected void paintComponent(Graphics g) {
        // TODO Auto-generated method stub
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.MAGENTA);
        for(int i=0; i<numPoints; i++) {
            if(i>0 && i<(numPoints-1)) {
                g2.fill(ellipse[i]);
            }else {
                g2.draw(ellipse[i]);
            }
            if(numPoints>1 && i<(numPoints - 1)) {
                g2.drawLine((int) controlPoint[i].getX(), (int) controlPoint[i].getY(), (int) controlPoint[i + 1].getX(), (int) controlPoint[i + 1].getY());
            }
        }
        /*if(numPoints == 4) {
            double x,y;
            g2.setColor(Color.RED);
            for(double k=t; k<=1+t; k+=t) {
                x = (1-k)*(1-k)*(1-k)*controlPoint[0].getX() + 3*k*(1-k)*(1-k)*controlPoint[1].getX()
                        + 3*k*k*(1-k)*controlPoint[2].getX() + k*k*k*controlPoint[3].getX();
                y = (1-k)*(1-k)*(1-k)*controlPoint[0].getY() + 3*k*(1-k)*(1-k)*controlPoint[1].getY()
                        + 3*k*k*(1-k)*controlPoint[2].getY() + k*k*k*controlPoint[3].getY();
//              System.out.println(x + "  " + y );
                g2.drawLine((int)x, (int)y, (int)x, (int)y);
            }
        }*/
        if(numPoints == orderNum) {
            double x,y;
            g2.setColor(Color.RED);
            for(double k=t; k<=1+t; k+=t) {
                CvPoint2D cvPoint2D = calBezierPoint(k, controlPoint);
                x = cvPoint2D.getX();
                y = cvPoint2D.getY();
                /*x = (1-k)*(1-k)*(1-k)*controlPoint[0].getX() + 3*k*(1-k)*(1-k)*controlPoint[1].getX()
                        + 3*k*k*(1-k)*controlPoint[2].getX() + k*k*k*controlPoint[3].getX();
                y = (1-k)*(1-k)*(1-k)*controlPoint[0].getY() + 3*k*(1-k)*(1-k)*controlPoint[1].getY()
                        + 3*k*k*(1-k)*controlPoint[2].getY() + k*k*k*controlPoint[3].getY();*/
              System.out.println(x + "  " + y );
              g2.drawLine((int)x, (int)y, (int)x, (int)y);
            }
        }
    }

    /**
     * 根据控制点序列，计算n阶贝塞尔曲线上的一个点
     * @param u 步长
     * @param controlPoint 控制点序列，含起点、终点
     * @return
     */
    public CvPoint2D calBezierPoint(double u,Point2D[] controlPoint) {
        if(controlPoint == null || controlPoint.length <= 0) {
            System.out.println("控制点列表为空");
            return null;
        }
        int orderNum = controlPoint.length;
        CvPoint2D[] p = new CvPoint2D[orderNum];
        for (int i = 0; i < orderNum; i++) {
            if(controlPoint[i] == null) {
                System.out.println("控制点中有空对象，说明用户还没建立足够的点");
                return null;
            }

            p[i] = new CvPoint2D(controlPoint[i].getX(), controlPoint[i].getY());
        }

        for (int r = 1; r < orderNum; r++) {
            for (int i = 0; i < orderNum - r; i++) {
                p[i].setX((1 - u) * p[i].getX() + u * p[i + 1].getX());
                p[i].setY((1 - u) * p[i].getY() + u * p[i + 1].getY());
            }
        }
        return p[0];
    }

    @Override
    public Dimension getPreferredSize() {
        // TODO Auto-generated method stub
        return new Dimension(600,600);
    }

    public static void main(String[] agrs) {
        JFrame f = new JFrame();
        Bezier t2 = new Bezier(3);
        Bezier t1 = new Bezier(4);
        f.add(t2);
        f.add(t1);
        f.pack();
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
//        if(numPoints <4) {
        if(numPoints < orderNum) {
            double x = e.getX();
            double y = e.getY();
            controlPoint[numPoints] = new Point2D.Double(x, y);
            Ellipse2D.Double current = new Ellipse2D.Double(x-SIDELENGTH/2, y-SIDELENGTH/2, SIDELENGTH, SIDELENGTH);
            ellipse[numPoints] = current;

            numPoints++;
            repaint();
        }
    }
    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
    }
    private int find(Point2D p){
//        int flag = 5;
        int flag = orderNum + 1;
        for(int i=0; i<numPoints; i++) {
            if( ellipse[i].contains(p)) {
                flag = i;
                return flag;
            }
        }
        return flag;
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
    }
    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
    }
    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
    }
    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub
        int flag  = find((Point2D)e.getPoint());
//        if( flag<5) {
        if( flag<orderNum + 1) {
            double x = e.getX();
            double y = e.getY();
            controlPoint[flag] = new Point2D.Double(x, y);
            Ellipse2D.Double current = new Ellipse2D.Double(x-SIDELENGTH/2, y-SIDELENGTH/2, SIDELENGTH, SIDELENGTH);
            ellipse[flag] = current;

            repaint();
        }
    }
    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub
    }
}
