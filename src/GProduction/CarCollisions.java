/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GProduction;

import GProduction.CollisionHelper.CarPoly;
import GProduction.CollisionHelper.point2D;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 *
 * @author Blondu
 */
public class CarCollisions {

    private static final int x64 = StaticDATA.BlockDimensions;
    private static final double dif = x64 / 64.d;
    Car[] car;
    CarPoly polygon1, polygon2;
    private int n, distance = math.floor(dif * 40);
    private point2D projection1x = new point2D(0, 0), projection2x = new point2D(0, 0);
    private point2D projection1y = new point2D(0, 0), projection2y = new point2D(0, 0);
    private point2D projection3x = new point2D(0, 0), projection4x = new point2D(0, 0);
    private point2D projection3y = new point2D(0, 0), projection4y = new point2D(0, 0);
    private boolean crash, crashTest1, crashTest2, crashTest3, crashTest4;
    private boolean crashTest[][];
    private double m21, dvx2, a, x21, y21, vx21, vy21, fy21, sign, vx_cm, vy_cm, R = 1;
    private Image grid;
    private miniCarSim_Alpha2 parent;

    public CarCollisions(Car[] c, int n, miniCarSim_Alpha2 parent) {
        car = c;
        this.n = n;
        crashTest = new boolean[n][n];
        //gridInit();
        this.parent = parent;
    }

    public void calc() {
        for (int i = 1; i < n; i++) {
            if (detect(car[0], car[i], distance)) {
                crashTest[0][i] = true;
                crashTest[i][0] = true;
                collision2Ds(car[0], car[i]);
                try {
                    //parent.getDisplay().vibrate(50);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                car[i].futurePosition();
                if (detect(car[0], car[i], distance)) {
                    car[i].STOP(0);
                } else {
                    car[i].restoreSpeed(0);
                }
                car[i].restorePosition();
                crashTest[0][i] = false;
                crashTest[i][0] = false;
            }
        }
        for (int i = 1; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (detect(car[i], car[j], distance)) {
                    crashTest[i][j] = true;
                    crashTest[j][i] = true;
                    collision2Ds(car[i], car[j]);
                } else {
                    car[i].futurePosition();
                    if (detect(car[j], car[i], distance)) {
                        car[i].STOP(j);
                        car[j].panicCode = true;
                    } else {
                        car[i].restoreSpeed(j);
                    }
                    car[i].restorePosition();
                    car[j].futurePosition();
                    if (detect(car[i], car[j], distance)) {
                        car[j].STOP(i);
                        car[i].panicCode = true;
                    } else {
                        car[j].restoreSpeed(i);
                    }
                    car[j].restorePosition();
                    crashTest[i][j] = false;
                    crashTest[j][i] = false;
                }
            }
        }
    }

    public void draw(Graphics g) {
        /*g.setColor(255, 0, 0);
        point2D.drawLine(g, car[0].cp.a1, car[0].cp.b1, map);
        point2D.drawLine(g, car[0].cp.b1, car[0].cp.c1, map);
        point2D.drawLine(g, car[0].cp.c1, car[0].cp.d1, map);
        point2D.drawLine(g, car[0].cp.d1, car[0].cp.a1, map);
        g.setColor(0, 255, 0);
        point2D.drawLine(g, car[1].cp.a1, car[1].cp.b1, map);
        point2D.drawLine(g, car[1].cp.b1, car[1].cp.c1, map);
        point2D.drawLine(g, car[1].cp.c1, car[1].cp.d1, map);
        point2D.drawLine(g, car[1].cp.d1, car[1].cp.a1, map);
        /*g.setColor(255, 0, 0);
        point2D.drawLine(g, car[0].cp.a5, car[0].cp.b5, map);
        point2D.drawLine(g, car[0].cp.b5, car[0].cp.c5, map);
        point2D.drawLine(g, car[0].cp.c5, car[0].cp.d5, map);
        point2D.drawLine(g, car[0].cp.d5, car[0].cp.a5, map);
        g.setColor(0, 255, 0);
        point2D.drawLine(g, car[1].cp.a5, car[1].cp.b5, map);
        point2D.drawLine(g, car[1].cp.b5, car[1].cp.c5, map);
        point2D.drawLine(g, car[1].cp.c5, car[1].cp.d5, map);
        point2D.drawLine(g, car[1].cp.d5, car[1].cp.a5, map);*/
        g.drawImage(grid, 20, 0, Graphics.LEFT | Graphics.TOP);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (crashTest[i][j]) {
                    g.setColor(255, 96, 0);
                    g.fillRect(i * 16 + 20, j * 16, 15, 15);
                    g.setColor(255, 255, 255);
                    g.drawString(String.valueOf(i - 1), i * 16 + 28, j * 16 + 8, Graphics.HCENTER | Graphics.TOP);
                }
            }
        }
    }

    private boolean detect(Car c1, Car c2, int dist) {
        if (CloseEnough(c1, c2, dist)) {
            polygon1 = c1.cp;
            polygon2 = c2.cp;
            polygon1.update();
            polygon2.update();
            polygon1.RotateP(c1.getCourse());
            polygon2.RotateP(c1.getCourse());
            /*polygon1.TestRotateP(c1.getCourse());        polygon2.TestRotateP(c1.getCourse());*/
            crashTest1 = crashTest2 = crashTest3 = crashTest4 = false;
            projection1x.setX(c1.cp.minX());
            projection1x.setY(c1.cp.maxX());
            projection2x.setX(c2.cp.minX());
            projection2x.setY(c2.cp.maxX());
            if (projection1x.getX() >= projection2x.getX() && projection1x.getX() <= projection2x.getY()) {
                crashTest1 = true;
            } else if (projection1x.getY() >= projection2x.getX() && projection1x.getY() <= projection2x.getY()) {
                crashTest1 = true;
            } else if (projection1x.getX() <= projection2x.getX() && projection1x.getY() >= projection2x.getX()) {
                crashTest1 = true;
            }
            projection1y.setX(c1.cp.minY());
            projection1y.setY(c1.cp.maxY());
            projection2y.setX(c2.cp.minY());
            projection2y.setY(c2.cp.maxY());
            if (projection1y.getX() >= projection2y.getX() && projection1y.getX() <= projection2y.getY()) {
                crashTest2 = true;
            } else if (projection1y.getY() >= projection2y.getX() && projection1y.getY() <= projection2y.getY()) {
                crashTest2 = true;
            } else if (projection1y.getX() <= projection2y.getX() && projection1y.getY() >= projection2y.getX()) {
                crashTest2 = true;
            }
            polygon1.RotateP(c2.getCourse());
            polygon2.RotateP(c2.getCourse());
            projection3x.setX(c1.cp.minX());
            projection3x.setY(c1.cp.maxX());
            projection4x.setX(c2.cp.minX());
            projection4x.setY(c2.cp.maxX());
            if (projection3x.getX() >= projection4x.getX() && projection3x.getX() <= projection4x.getY()) {
                crashTest3 = true;
            } else if (projection3x.getY() >= projection4x.getX() && projection3x.getY() <= projection4x.getY()) {
                crashTest3 = true;
            } else if (projection3x.getX() <= projection4x.getX() && projection3x.getY() >= projection4x.getX()) {
                crashTest3 = true;
            }
            projection3y.setX(c1.cp.minY());
            projection3y.setY(c1.cp.maxY());
            projection4y.setX(c2.cp.minY());
            projection4y.setY(c2.cp.maxY());
            if (projection3y.getX() >= projection4y.getX() && projection3y.getX() <= projection4y.getY()) {
                crashTest4 = true;
            } else if (projection3y.getY() >= projection4y.getX() && projection3y.getY() <= projection4y.getY()) {
                crashTest4 = true;
            } else if (projection3y.getX() <= projection4y.getX() && projection3y.getY() >= projection4y.getX()) {
                crashTest4 = true;
            }
            if (crashTest1 && crashTest2 && crashTest3 && crashTest4) {
                crash = true;
            } else {
                crash = false;
            }
        } else {
            crash = false;
        }
        return crash;
    }

    void collision2Ds(Car c1, Car c2) {
        double vxj, vyj, vxi, vyi;
        vxi = c1.vxi + c2.vxi /*/ 1024*/;
        vyi = c1.vyi + c2.vyi /*/ 1024*/;
        vxj = c2.vxi + c1.vxi /*/ 1024*/;
        vyj = c2.vyi + c1.vyi /*/ 1024*/;
        c1.vxi = 0;
        c1.vyi = 0;
        c2.vxi = 0;
        c2.vyi = 0;
        m21 = c2.mass / c1.mass;
        x21 = c2.x - c1.x;
        y21 = c2.y - c1.y;
        vx21 = c2.vx /*/ 1024*/ - c1.vx /*/ 1024*/;
        vy21 = c2.vy /*/ 1024*/ - c1.vy /*/ 1024*/;
        vx_cm = (c1.mass * c1.x + c2.mass * c2.x) / (c1.mass + c2.mass);
        vy_cm = (c1.mass * c1.y + c2.mass * c2.y) / (c1.mass + c2.mass);
        if ((vx21 * x21 + vy21 * y21) >= 0) {
            return;
        }
        fy21 = (1.0E-12 * Math.abs(y21));
        if (Math.abs(x21) < fy21) {
            if (x21 < 0) {
                sign = -1;
            } else {
                sign = 1;
            }
            x21 = fy21 * sign;
        }
        a = y21 / x21;
        dvx2 = -2 * (vx21 + a * vy21) / ((1 + a * a) * (1 + m21));
        vxj = vxj + dvx2;
        vyj = vyj + a * dvx2;
        vxi = vxi - m21 * dvx2;
        vyi = vyi - a * m21 * dvx2;
        vxi = (vxi - vx_cm) * R + vx_cm;
        vyi = (vyi - vy_cm) * R + vy_cm;
        vxj = (vxj - vx_cm) * R + vx_cm;
        vyj = (vyj - vy_cm) * R + vy_cm;
        c1.vxc += (int) (vxi /*/ 1024*/);
        c1.vyc += (int) (vyi /*/ 1024*/);
        c2.vxc += (int) (vxj /*/ 1024*/);
        c2.vyc += (int) (vyj /*/ 1024*/);
        c1.inertia = true;
        c2.inertia = true;
        return;
    }

    /*private void gridInit() {
    Graphics g;
    int RGB[] = new int[(n * 16 + 2) * (n * 16 + 2)];
    for (int i = 0; i < n * 16 + 2; i++) {
    for (int j = 0; j < n * 16 + 2; j++) {
    RGB[i * (n * 16 + 2) + j] = 0x00FFFFFF;
    if (i == j) {
    RGB[i * (n * 16 + 2) + j] = 0xFFFFFFFF;
    }}}grid = Image.createRGBImage(RGB, n * 16 + 1, n * 16 + 1, true);
    g = grid.getGraphics();
    g.setColor(0, 96, 255);
    for (int i = 0; i <= n; i++) {
    g.drawLine(0, i * 16, n * 16, i * 16);
    g.drawLine(i * 16, 0, i * 16, n * 16);        }   }*/
    private boolean CloseEnough(Car c1, Car c2, int dist) {
        if (Math.abs(c1.x - c2.x) < dist && Math.abs(c1.y - c2.y) < dist) {
            return true;
        }
        return false;
    }
}
