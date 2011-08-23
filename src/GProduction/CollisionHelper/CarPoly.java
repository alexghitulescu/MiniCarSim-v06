/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GProduction.CollisionHelper;

import GProduction.Car;
import GProduction.StaticDATA;
import GProduction.math;

/**
 *
 * @author Blondu
 */
public class CarPoly {

    private static final int x64 = StaticDATA.BlockDimensions;
    private static final double dif = x64 / 64.d;
    private point2D a, b, c, d;
    public point2D a1, b1, c1, d1;
    public point2D a2, b2, c2, d2;
    /*private point2D a3, b3, c3, d3;
    private point2D a4, b4, c4, d4;
    public point2D a5, b5, c5, d5;*/
    private int x, y;
    public Car car;

    public CarPoly(Car car, int w, int h) {
        this.car = car;
        a = new point2D(math.floor(dif * -(h / 2 - 1)) << 10, math.floor(dif * -(w / 2 - 1)) << 10);
        b = new point2D(math.floor(dif * (h / 2 - 1)) << 10, math.floor(dif * -(w / 2 - 1)) << 10);
        c = new point2D(math.floor(dif * (h / 2 - 1)) << 10, math.floor(dif * (w / 2 - 1)) << 10);
        d = new point2D(math.floor(dif * -(h / 2 - 1)) << 10, math.floor(dif * (w / 2 - 1)) << 10);
        a1 = new point2D(math.floor(dif * -(h / 2)), math.floor(dif * -(w / 2)));
        b1 = new point2D(math.floor(dif * (h / 2)), math.floor(dif * -(w / 2)));
        c1 = new point2D(math.floor(dif * (h / 2)), math.floor(dif * (w / 2)));
        d1 = new point2D(math.floor(dif * -(h / 2)), math.floor(dif * (w / 2)));
        a2 = new point2D(math.floor(dif * -(h / 2)), math.floor(dif * -(w / 2)));
        b2 = new point2D(math.floor(dif * (h / 2)), math.floor(dif * -(w / 2)));
        c2 = new point2D(math.floor(dif * (h / 2)), math.floor(dif * (w / 2)));
        d2 = new point2D(math.floor(dif * -(h / 2)), math.floor(dif * (w / 2)));
        /*a = new point2D(math.floor(dif * -7) << 10, math.floor(dif * -15) << 10);
        b = new point2D(math.floor(dif * 7) << 10, math.floor(dif * -15) << 10);
        c = new point2D(math.floor(dif * 7) << 10, math.floor(dif * 15) << 10);
        d = new point2D(math.floor(dif * -7) << 10, math.floor(dif * 15) << 10);
        a1 = new point2D(math.floor(dif * -8), math.floor(dif * -16));
        b1 = new point2D(math.floor(dif * 8), math.floor(dif * -16));
        c1 = new point2D(math.floor(dif * 8), math.floor(dif * 16));
        d1 = new point2D(math.floor(dif * -8), math.floor(dif * 16));
        a2 = new point2D(math.floor(dif * -8), math.floor(dif * -16));
        b2 = new point2D(math.floor(dif * 8), math.floor(dif * -16));
        c2 = new point2D(math.floor(dif * 8), math.floor(dif * 16));
        d2 = new point2D(math.floor(dif * -8), math.floor(dif * 16));*/
    }

    private void Rotate(int angle) {
        a1.setXY(a);
        b1.setXY(b);
        c1.setXY(c);
        d1.setXY(d);
        a1.rotate(angle);
        b1.rotate(angle);
        c1.rotate(angle);
        d1.rotate(angle);
    }

    public void update() {
        x = car.x << 10;
        y = car.y << 10;
        Rotate(car.getCourse());
        //TestRotate(car.getCourse());
        /*a1.setX(a1.getX() + x);        b1.setX(b1.getX() + x);
        c1.setX(c1.getX() + x);        d1.setX(d1.getX() + x);
        a1.setY(a1.getY() + y);       b1.setY(b1.getY() + y);
        c1.setY(c1.getY() + y);       d1.setY(d1.getY() + y);*/
        a1.addX(x);
        b1.addX(x);
        c1.addX(x);
        d1.addX(x);
        a1.addY(y);
        b1.addY(y);
        c1.addY(y);
        d1.addY(y);
    }

    public void RotateP(int angle) {
        a2.setXY(a1);
        b2.setXY(b1);
        c2.setXY(c1);
        d2.setXY(d1);
        a2.rotate(angle);
        b2.rotate(angle);
        c2.rotate(angle);
        d2.rotate(angle);
    }

    /*private void TestRotate(int angle) {
    a3.setXY(a4);    b3.setXY(b4);
    c3.setXY(c4);    d3.setXY(d4);    a3.rotate(angle);
    b3.rotate(angle);    c3.rotate(angle);    d3.rotate(angle);
    }    public void TestRotateP(int angle) {
    a5.setXY(a3);    b5.setXY(b3);    c5.setXY(c3);    d5.setXY(d3);
    a5.rotate(angle);    b5.rotate(angle);        c5.rotate(angle);
    d5.rotate(angle);    a5.addX(50 << 10);    b5.addX(50 << 10);
    c5.addX(50 << 10);    d5.addX(50 << 10);    a5.addY(50 << 10);
    b5.addY(50 << 10);   c5.addY(50 << 10);    d5.addY(50 << 10);    }*/
    public int minX() {
        return min(a2.getX(), b2.getX(), c2.getX(), d2.getX());
    }

    public int maxX() {
        return max(a2.getX(), b2.getX(), c2.getX(), d2.getX());
    }

    public int minY() {
        return min(a2.getY(), b2.getY(), c2.getY(), d2.getY());
    }

    public int maxY() {
        return max(a2.getY(), b2.getY(), c2.getY(), d2.getY());
    }

    private int min(int a, int b, int c, int d) {
        return Math.min(a, Math.min(b, Math.min(c, d)));
    }

    private int max(int a, int b, int c, int d) {
        return Math.max(a, Math.max(b, Math.max(c, d)));
    }
}
