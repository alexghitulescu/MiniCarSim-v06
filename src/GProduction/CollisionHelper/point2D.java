/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GProduction.CollisionHelper;

import GProduction.ImageRotator;
import GProduction.Maps;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author Blondu
 */
public class point2D {

    private int x;
    private int y;
    private int temp1, temp2;
    private int sin, cos;
    private static point2D temp;

    public point2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static void drawLine(Graphics g, point2D a, point2D b, Maps m) {
        g.drawLine((a.getX() >> 10) - m.getX(), (a.getY() >> 10) - m.getY(), (b.getX() >> 10) - m.getX(), (b.getY() >> 10) - m.getY());
    }

    public static void substract(point2D a, point2D b) {
        a.x -= b.x;
        a.y -= b.y;
    }

    public static point2D mod(point2D a, int h) {
        temp = new point2D(a.x, a.y);
        temp.x = temp.x % h;
        temp.y = temp.y % h;
        return temp;
    }

    void setXY(point2D p2d) {
        setX(p2d.getX());
        setY(p2d.getY());
    }

    public void rotate(int angle) {
        try {
            sin = ImageRotator.sinInts[angle];
            cos = ImageRotator.cosInts[angle];
            temp1 = (cos * getY() - sin * getX());
            temp2 = (sin * getY() + cos * getX());
            setX(temp1 >> 10);
            setY(temp2 >> 10);
        } catch (Exception e) {
            System.out.println("error: angle = " + angle);
        }
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }

    public void addX(int x) {
        this.x += x;
    }

    public void addY(int y) {
        this.y += y;
    }
}
