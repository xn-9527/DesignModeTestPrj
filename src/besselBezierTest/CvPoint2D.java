package besselBezierTest;

import java.awt.geom.Point2D;

/**
 * @author Created by xiaoni on 2018/1/3.
 */
public class CvPoint2D extends Point2D {
    private double x;
    private double y;

    public CvPoint2D(){}

    public CvPoint2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public void setLocation(double x, double y) {
        this.setX(x);
        this.setY(y);
    }

    public void setY(double y) {
        this.y = y;
    }
}
