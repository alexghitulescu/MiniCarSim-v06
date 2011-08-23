/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GProduction;

import GProduction.CollisionHelper.CarPoly;
import GProduction.CollisionHelper.point2D;
import GProduction.ErrorSystem.ErrorLog;
import java.io.InputStream;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 *
 * @author Blondu
 */
public class Car {

    public final static int FLAG = 0;
    private static final int x64 = StaticDATA.BlockDimensions;
    private static final int x16 = math.floor(x64 / 4.d);
    private static final double dif = x64 / 64.d;
    public int x = 50, y = 50, x1 = x << 10, y1 = y << 10, xC, yC;
    public int xi = x, yi = y, xdiv64, ydiv64, directionCode1;
    public int mass = 1300, vxc, vyc, vxi, vyi, repeatCodeD, repeatCodeS, directionCode3;
    private int gameaction, colUUID, UUID, directionCode2;
    protected Image img;
    protected ImageRotator rotator;
    int speed = 0, maxCourseIncrease;
    protected int vx, vy, speedKMH;
    protected int courseIncrease, tempspeedcalc1;
    private int course = 67, deacelerate, deacelerateT, lastcourse = course;
    boolean keyRepeatD = false, keyRepeatS = false, inertia, STOP, s, panicCode;
    private int turnSpeed, distantaPunteSpate = math.floor(dif * 6), lastSpeed;
    private Ecran e;
    Maps m;
    public CarPoly cp;
    public int X, Y, X1, Y1, VX, VY;
    private int lastX, lastY;
    point2D hood = new point2D(8, 0);
    private static int w, h;

    public Car(Maps map, Ecran ecran, String name) {
        Image temp = null;
        InputStream file;
        try {
            if (name != null) {
                file = getClass().getResourceAsStream("/img/" + name + ".png");
            } else {
                file = getClass().getResourceAsStream("/img/carB.png");
            }
            try {
                temp = Image.createImage(file);
            } catch (Exception ex) {
                System.out.println(ex.toString());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(name);
        }
        try {
            //temp = ImageRotator_1.resizeImage(temp, math.floor(dif * 32), math.floor(dif * 16));
            rotator = new ImageRotator(temp);
        } catch (Exception ex) {
            System.out.println(name);
        }
        e = ecran;
        img = rotator.rotate_Img(course);
        m = map;
        cp = new CarPoly(this, rotator.getWidth(), rotator.getHeight());
        find_pos();
        lastX = xdiv64;
        lastY = ydiv64;
        directionCode1 = math.CadranII(math.courseCorrection(course + 90));
        directionCode2 = directionCode1;
        switch (directionCode1) {
            case 1:
                directionCode3 = 1;
                break;
            case 2:
                directionCode3 = 1 << 8;
                break;
            case 3:
                directionCode3 = 1 << 16;
                break;
            case 4:
                directionCode3 = 1 << 24;
                break;
        }
        try {
            StaticDATA.carposition[xdiv64][ydiv64] += directionCode3;
        } catch (Exception e) {
            System.out.println("xdiv64=" + xdiv64 + " ydiv64=" + ydiv64);
        }
    }

    protected void paint(Graphics g) {
        xC = (x - m.x);
        yC = (y - m.y);
        try {
            if (lastcourse != course) {
                img = rotator.rotate_Img(course);
            }
            if (xC > -20 && xC < w && yC > -20 && yC < h) {
                g.drawImage(img, xC, yC, Graphics.HCENTER | Graphics.VCENTER);
            }
        } catch (Exception ex) {
            ErrorLog.add(ex);
        }
    }

    protected void calc() {
        lastcourse = course;
        if (keyRepeatD) {
            keyPressed(repeatCodeD);
        } else {
            courseIncrease = 0;
        }
        if (keyRepeatS) {
            keyPressed(repeatCodeS);
        }
        //courseIncreaseR = math.degreeToRadian(courseIncrease);
        //courseR = courseR + courseIncreaseR * Math.abs(speed) / 2;
        //course = math.radianToDegree(courseR);
        turnSpeed = Math.abs(speed);
        if (turnSpeed < 1024 && turnSpeed != 0) {
            turnSpeed = 1024;
        }
        //course = (course << 10) + ((((courseIncrease * Ecran.raportMilisecunde) >> 10) * turnSpeed) >> 10);
        course = (course << 10) + ((courseIncrease * turnSpeed) >> 10);
        if (course % 1024 > 512) {
            course = course >> 10;
            course++;
        } else {
            course = course >> 10;
        }
        /*if(courseIncrease>0){course++;}*/
        course = math.courseCorrection(course);
        vx = (speed * ImageRotator.cosInts[course]) >> 10;
        vy = (speed * ImageRotator.sinInts[course]) >> 10;
        vx += vxc;
        vy += vyc;/*vx = (vx * Ecran.raportMilisecunde) >> 10;vy = (vy * Ecran.raportMilisecunde) >> 10;*/
        /*x1 = (x1 + vx + vxc);y1 = (y1 + vy + vyc);*/
        x1 += vx;
        y1 += vy;
        vxc = 0;
        vyc = 0;
        xi = x;
        yi = y;
        x = (x1 + distantaPunteSpate * ImageRotator.cosInts[course]) >> 10;
        y = (y1 + distantaPunteSpate * ImageRotator.sinInts[course]) >> 10;
        xC = (x - m.x);
        yC = (y - m.y);
        if (speed != 0) {
            lastSpeed = speed;
        }
        setCarpozition();
    }

    protected void keyPressed(int keyCode) {
        gameaction = e.getGameAction(keyCode);
        if (speed != 0) {
            if (keyCode == Ecran.KEY_NUM4 || gameaction == Ecran.LEFT) {
                if (!keyRepeatD) {
                    if (speed < 2048) {
                        courseIncrease = -600;
                    } else {
                        courseIncrease = -70;
                    }
                }
                keyRepeatD = true;
                repeatCodeD = keyCode;
                if (speed > 0) {
                    if (courseIncrease > -maxCourseIncrease) {
                        if (courseIncrease > tempspeedcalc1 || courseIncrease <= 0) {
                            courseIncrease -= tempspeedcalc1;
                        } else {
                            courseIncrease = 0;
                        }
                    } else {
                        courseIncrease = -maxCourseIncrease;
                    }
                } else {
                    if (courseIncrease < maxCourseIncrease) {
                        if (courseIncrease < tempspeedcalc1 || courseIncrease >= 0) {
                            courseIncrease += tempspeedcalc1;
                        } else {
                            courseIncrease = 0;
                        }
                    } else {
                        courseIncrease = maxCourseIncrease;
                    }
                }
            } else if (keyCode == Ecran.KEY_NUM6 || gameaction == Ecran.RIGHT) {
                if (!keyRepeatD) {
                    if (speed < 2048) {
                        courseIncrease = 600;
                    } else {
                        courseIncrease = 70;
                    }
                }
                keyRepeatD = true;
                repeatCodeD = keyCode;
                if (speed > 0) {
                    if (courseIncrease < maxCourseIncrease) {
                        if (courseIncrease < tempspeedcalc1 || courseIncrease >= 0) {
                            courseIncrease += tempspeedcalc1;
                        } else {
                            courseIncrease = 0;
                        }
                    } else {
                        courseIncrease = maxCourseIncrease;
                    }
                } else {
                    if (courseIncrease > -maxCourseIncrease) {
                        if (courseIncrease > tempspeedcalc1 || courseIncrease <= 0) {
                            courseIncrease -= tempspeedcalc1;
                        } else {
                            courseIncrease = 0;
                        }
                    } else {
                        courseIncrease = -maxCourseIncrease;
                    }
                }
            }
        }
        if (keyCode == Ecran.KEY_NUM8 || keyCode == Ecran.KEY_NUM2 || gameaction == Ecran.UP) {
            keyRepeatS = true;
            repeatCodeS = keyCode;
            if (speed < 0 && speed + 30 > 0) {
                speed = 0;
                keyRepeatS = false;
            } else {
                if (speed < 7577) {
                    speed += 30;
                }
            }
            speedKMH = (speed * 27) >> 10;
            maxCourseIncrease = 2048 - Math.abs(speedKMH) * 9;
            //tempspeedcalc1 = Math.abs(8000 - speed) >> 7;
            tempspeedcalc1 = Math.abs(9000 - speed) >> 7;
        } else if (keyCode == Ecran.KEY_NUM5 || gameaction == Ecran.DOWN) {
            keyRepeatS = true;
            repeatCodeS = keyCode;
            deacelerateT += 1;
            deacelerate = 45 + deacelerateT;
            if (speed > 0 && speed - deacelerate < 0) {
                speed = 0;
                keyRepeatS = false;
                deacelerate = 0;
            } else {
                if (speed > 0) {
                    speed -= deacelerate;
                } else if (speed > -1024) {
                    speed -= 30;
                }

            }
            speedKMH = (speed * 27) >> 10;
            maxCourseIncrease = 2048 - Math.abs(speedKMH) * 9;
            //tempspeedcalc1 = Math.abs(8000 - speed) >> 7;
            tempspeedcalc1 = Math.abs(9000 - speed) >> 7;
        }
        if (keyCode == Ecran.KEY_NUM0) {
            speed = 0;
            courseIncrease = 0;
        }
    }

    protected void keyReleased(int keyCode) {
        gameaction = e.getGameAction(keyCode);
        if (keyCode == Ecran.KEY_NUM4 || keyCode == Ecran.KEY_NUM6 || gameaction == Ecran.LEFT || gameaction == Ecran.RIGHT) {
            courseIncrease = 0;
            keyRepeatD = false;
            repeatCodeD = 0;
        } else if (keyCode == Ecran.KEY_NUM2 || keyCode == Ecran.KEY_NUM8 || keyCode == Ecran.KEY_NUM5 || gameaction == Ecran.UP || gameaction == Ecran.DOWN) {
            keyRepeatS = false;
            repeatCodeS = 0;
            deacelerate = 0;
        }
    }

    void setCourse(int angle) {
        course = angle;
        courseIncrease = 0;
        keyRepeatD = false;
        repeatCodeD = 0;
    }

    public int getCourse() {
        return math.courseCorrection(course);
    }

    public int getImgWidth() {
        return img.getWidth();
    }

    public int getImgHeight() {
        return img.getHeight();
    }

    void futurePosition() {
        X = x;
        Y = y;
        X1 = x1;
        Y1 = y1;
        VX = lastSpeed * ImageRotator.cosInts[course] >> 10;
        VY = lastSpeed * ImageRotator.sinInts[course] >> 10;
        x1 += (VX * 6);
        y1 += (VY * 6);
        x = (x1 + distantaPunteSpate * ImageRotator.cosInts[course]) >> 10;
        y = (y1 + distantaPunteSpate * ImageRotator.sinInts[course]) >> 10;
    }

    void restorePosition() {
        x = X;
        y = Y;
        x1 = X1;
        y1 = Y1;
    }

    public int getSpeedKMH() {
        //return (int) ((speed / 4) * 30);
        //return (int) (speed * 7.5)*3.6;
        return speedKMH;
    }

    public void setX(int x) {
        x1 = x;
        this.x = (x1 + distantaPunteSpate * ImageRotator.cosInts[course]) >> 10;
        find_pos();
        lastX = xdiv64;
        lastY = ydiv64;
    }

    public void setY(int y) {
        y1 = y;
        this.y = (y1 + distantaPunteSpate * ImageRotator.sinInts[course]) >> 10;
        find_pos();
        lastX = xdiv64;
        lastY = ydiv64;
    }

    public void setXWC(int x) {
        x1 = x;
        this.x = x1 >> 10;
    }

    public void setYWC(int y) {
        y1 = y;
        this.y = y1 >> 10;
    }

    public void STOP(int c) {
        colUUID = c;
        if (!STOP) {
            STOP = true;
            lastSpeed = speed;
            s = true;
        }
        speed = 0;
    }

    public void restoreSpeed(int c) {
        if (STOP && colUUID == c) {
            STOP = false;
            s = false;
            speed = lastSpeed;
        }
    }

    public void setSpeed(int speed) {
        if (!STOP) {
            this.speed = speed;
        }
    }

    void find_pos() {
        /*xdiv64 = x / x64;
        ydiv64 = y / x64;*/
        hood.setX(0);
        hood.setY(x16);
        hood.rotate(course);
        hood.addX(x);
        hood.addY(y);
        xdiv64 = (hood.getX()) / x64;
        ydiv64 = (hood.getY()) / x64;
    }

    public static void setScreenBorders(int x, int y) {
        w = x + 20;
        h = y + 20;
    }

    public void setCarpozition() {
        find_pos();
        directionCode1 = math.CadranII(math.courseCorrection(course + 90));
        if (lastX != xdiv64 || lastY != ydiv64 || directionCode2 != directionCode1) {
            if (xdiv64 >= 0 && ydiv64 >= 0 && xdiv64 < Ecran.NOBx && ydiv64 < Ecran.NOBy) {
                directionCode2 = directionCode1;
                StaticDATA.carposition[lastX][lastY] -= directionCode3;
                lastX = xdiv64;
                lastY = ydiv64;
                switch (directionCode1) {
                    case 1:
                        directionCode3 = 1;
                        break;
                    case 2:
                        directionCode3 = 1 << 8;
                        break;
                    case 3:
                        directionCode3 = 1 << 16;
                        break;
                    case 4:
                        directionCode3 = 1 << 24;
                        break;
                }
                StaticDATA.carposition[xdiv64][ydiv64] += directionCode3;
            }
        }
    }

    void remove() {
        StaticDATA.carposition[lastX][lastY] -= directionCode3;
    }
}
