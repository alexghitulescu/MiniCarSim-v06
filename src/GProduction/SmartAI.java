/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GProduction;

import GProduction.CollisionHelper.point2D;
import java.util.Random;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author Blondu
 */
public class SmartAI {

    private static final int x64 = StaticDATA.BlockDimensions;
    private static final double dif = x64 / 64.d;

    public SmartAI(Maps map, Ecran e) {
        SmartAI.map = map;
        car = new Car(map, e, "car" + String.valueOf(Math.abs((r.nextInt() / 10) % 5)));
        car.setY(math.floor(dif * 30) << 10);
        car.setX(math.floor(dif * 70) << 10);
        car.setCourse(1);
        car.speed = (math.floor(dif * 30) << 10) / 27;
        car.keyPressed(Ecran.KEY_NUM2);
        car.calc();
        car.keyReleased(Ecran.KEY_NUM2);
        car.calc();
        find_pos();
        val = Maps.map2d[xdiv64][ydiv64];
        lastval = val;
        block = true;
        hood = car.hood;
    }

    public SmartAI(SmartAI last) {
        car = last.car;
        car.speed = (math.floor(dif * 30) << 10) / 27;
        hood = car.hood;
        block = true;
    }
    private static Maps map;
    Car car;
    private int xdiv64, ydiv64, xC, yC, limita_abatere = 4;
    private int xdiv64ori64, ydiv64ori64, lowspeed = math.floor(dif * 30), hispeed = math.floor(dif * 80), nspeed = math.floor(dif * 50);
    private int carcourse, val, val2 = 2, lastval = 0, tempval2 = 2, temp;
    public static final int ROAD = math.floor((x64 / 4.d) * 3), ROAD2 = math.floor(x64 / 4.d);
    private boolean block, center, left, left2, right, right2, prioritate_de_dreapta;
    private point2D hood;
    private Random r = new Random();

    public void paint(Graphics g) {
        car.paint(g);
        //g.setColor(255, 0, 0);
        //xC = hood.getX() - map.x - 1;
        //yC = hood.getY() - map.y - 1;
        //g.fillRect(xC, yC, 3, 3);
    }

    void setvals(int type) {
        switch (type) {
            case 1:
                val = 1;
                lastval = 1;
                val2 = 2;
                tempval2 = 2;
                break;
            case 2:
                val = 2;
                lastval = 2;
                val2 = 3;
                tempval2 = 3;
                break;
        }
    }

    void calc() {
        car.calc();
        carcourse = math.courseCorrection(car.getCourse() + 90);
        find_pos();
        try {
            val = Maps.map2d[xdiv64][ydiv64];
            if (lastval != val) {
                val2 = tempval2;
                block = false;
                prioritate_de_dreapta = true;
            }
        } catch (Exception e) {
            val = 0;
        }
        switch (val2) {
            case 1:
                alphaCity1();
                break;
            case 2:
                alphaCity2();
                break;
            case 3:
                alphaCity3();
                break;
            case 4:
                alphaCity4();
                break;
        }
        if (left) {
            if (StaticDATA.cedeazaPrioritateaLeft(val2, xdiv64, ydiv64, val, car.directionCode3) && /*!car.panicCode &&*/ left2) {
                car.STOP(-1);
            } else {
                car.restoreSpeed(-1);
                left2 = false;
            }
        }
        if (right) {
            if (StaticDATA.cedeazaPrioritateaRight(val2, xdiv64, ydiv64, val, car.directionCode3) && /*!car.panicCode &&*/ right2) {
                car.STOP(-2);
            } else {
                car.restoreSpeed(-2);
                right2 = false;
            }
        }
        if (StaticDATA.cedeazaPrioritateaDeDreapta(val2, xdiv64, ydiv64, val, car.directionCode3) && /*!car.panicCode &&*/ prioritate_de_dreapta) {
            car.STOP(-3);
        } else {
            car.restoreSpeed(-3);
            prioritate_de_dreapta = false;
        }
        car.panicCode = false;

    }

    private void find_pos() {
        xdiv64 = car.xdiv64;
        ydiv64 = car.ydiv64;
        xdiv64ori64 = xdiv64 * x64;
        ydiv64ori64 = ydiv64 * x64;
    }

    private void alphaCity1() {
        switch (val) {
            case 2:
                lastval = 2;
                tempval2 = 1;
                continue1();
                break;
            case 3:
                lastval = 3;
                if (!block) {
                    temp = Math.abs(r.nextInt()) % 10;
                    if (temp % 2 == 1) {
                        tempval2 = 2;
                        car.setSpeed((lowspeed << 10) / 27);
                        right();
                    } else {
                        tempval2 = 4;
                        car.setSpeed((hispeed << 10) / 27);
                        left();
                    }
                    block = true;
                }
                if (temp % 2 == 1) {
                    car.setSpeed((lowspeed << 10) / 27);
                } else {
                    car.setSpeed((hispeed << 10) / 27);
                }
                break;
            case 4:
                lastval = 4;
                tempval2 = 2;
                if (!block) {
                    car.setSpeed((lowspeed << 10) / 27);
                    right();
                    block = true;
                }
                car.setSpeed((lowspeed << 10) / 27);
                break;
            case 5:
                lastval = 5;
                tempval2 = 4;
                if (!block) {
                    car.setSpeed((hispeed << 10) / 27);
                    left();
                    block = true;
                }
                car.setSpeed((hispeed << 10) / 27);
                break;
            case 6:
                lastval = 6;
                if (!block) {
                    temp = Math.abs(r.nextInt()) % 10;
                    if (temp % 2 == 1) {
                        tempval2 = 1;
                    } else {
                        tempval2 = 2;
                        car.setSpeed((lowspeed << 10) / 27);
                        right();
                    }
                    block = true;
                }
                if (tempval2 == 1) {
                    continue1();
                } else {
                    car.setSpeed((lowspeed << 10) / 27);
                }
                break;
            case 8:
                lastval = 8;
                tempval2 = 1;
                continue1();
                break;
            case 9:
                lastval = 9;
                if (!block) {
                    temp = Math.abs(r.nextInt()) % 10;
                    if (temp % 2 == 1) {
                        tempval2 = 1;
                    } else {
                        tempval2 = 4;
                        car.setSpeed((hispeed << 10) / 27);
                        left();
                    }
                    block = true;
                }
                if (tempval2 == 1) {
                    continue1();
                } else {
                    car.setSpeed((hispeed << 10) / 27);
                }
                break;
        }
    }

    private void alphaCity2() {
        switch (val) {
            case 1:
                lastval = 1;
                tempval2 = 2;
                continue2();
                break;
            case 3:
                if (lastval != 7) {
                    lastval = 3;
                    if (!block) {
                        temp = Math.abs(r.nextInt()) % 10;
                        if (temp % 2 == 1) {
                            tempval2 = 2;
                        } else {
                            tempval2 = 3;
                            car.setSpeed((lowspeed << 10) / 27);
                            right();
                        }
                        block = true;
                    }
                } else {
                    lastval = 3;
                    block = true;
                }
                if (tempval2 == 2) {
                    continue2();
                } else {
                    car.setSpeed((lowspeed << 10) / 27);
                }
                break;
            case 5:
                lastval = 5;
                tempval2 = 3;
                if (!block) {
                    car.setSpeed((lowspeed << 10) / 27);
                    right();
                    block = true;
                }
                car.setSpeed((lowspeed << 10) / 27);
                break;
            case 7:
                if (lastval != 3) {
                    lastval = 7;
                    if (!block) {
                        temp = Math.abs(r.nextInt()) % 10;
                        if (temp % 2 == 1) {
                            tempval2 = 2;
                        } else {
                            tempval2 = 1;
                            car.setSpeed((hispeed << 10) / 27);
                            left();
                        }
                        block = true;
                    }
                } else {
                    lastval = 7;
                    block = true;
                }
                if (tempval2 == 2) {
                    continue2();
                } else {
                    car.setSpeed((hispeed << 10) / 27);
                }
                break;
            case 8:
                lastval = 8;
                tempval2 = 2;
                continue2();
                break;
            case 9:
                lastval = 9;
                if (!block) {
                    temp = Math.abs(r.nextInt()) % 10;
                    if (temp % 2 == 1) {
                        tempval2 = 1;
                        car.setSpeed((hispeed << 10) / 27);
                        left();
                    } else {
                        tempval2 = 3;
                        car.setSpeed((lowspeed << 10) / 27);
                        right();
                    }
                    block = true;
                }
                if (temp % 2 == 1) {
                    car.setSpeed((hispeed << 10) / 27);
                } else {
                    car.setSpeed((lowspeed << 10) / 27);
                }
                break;
            case 10:
                lastval = 10;
                tempval2 = 1;
                if (!block) {
                    car.setSpeed((hispeed << 10) / 27);
                    left();
                    block = true;
                }
                car.setSpeed((hispeed << 10) / 27);
                break;
        }
    }

    private void alphaCity3() {
        switch (val) {
            case 2:
                lastval = 2;
                tempval2 = 3;
                continue3();
                break;
            case 6:
                lastval = 6;
                if (!block) {
                    temp = Math.abs(r.nextInt()) % 10;
                    if (temp % 2 == 1) {
                        tempval2 = 2;
                        car.setSpeed((hispeed << 10) / 27);
                        left();
                    } else {
                        tempval2 = 3;
                    }
                    block = true;
                }
                if (tempval2 == 3) {
                    continue3();
                } else {
                    car.setSpeed((hispeed << 10) / 27);
                }
                break;
            case 7:
                lastval = 7;
                if (!block) {
                    temp = Math.abs(r.nextInt()) % 10;
                    if (temp % 2 == 1) {
                        tempval2 = 2;
                        car.setSpeed((hispeed << 10) / 27);
                        left();
                    } else {
                        tempval2 = 4;
                        car.setSpeed((lowspeed << 10) / 27);
                        right();
                    }
                    block = true;
                }
                if (temp % 2 == 1) {
                    car.setSpeed((hispeed << 10) / 27);
                } else {
                    car.setSpeed((lowspeed << 10) / 27);
                }
                break;
            case 8:
                lastval = 8;
                tempval2 = 3;
                continue3();
                break;
            case 9:
                lastval = 9;
                tempval2 = 3;
                continue3();
                break;
            case 10:
                lastval = 10;
                tempval2 = 4;
                if (!block) {
                    car.setSpeed((lowspeed << 10) / 27);
                    right();
                    block = true;
                }
                car.setSpeed((lowspeed << 10) / 27);
                break;
            case 11:
                lastval = 11;
                tempval2 = 2;
                if (!block) {
                    car.setSpeed((hispeed << 10) / 27);
                    left();
                    block = true;
                }
                car.setSpeed((hispeed << 10) / 27);
                break;
        }
    }

    private void alphaCity4() {
        switch (val) {
            case 1:
                lastval = 1;
                tempval2 = 4;
                continue4();
                break;
            case 3:
                if (lastval != 7) {
                    lastval = 3;
                    if (!block) {
                        temp = Math.abs(r.nextInt()) % 10;
                        if (temp % 2 == 1) {
                            tempval2 = 3;
                            car.setSpeed((hispeed << 10) / 27);
                            left();
                        } else {
                            tempval2 = 4;
                        }
                        block = true;
                    }
                } else {
                    lastval = 3;
                    block = true;
                }
                if (tempval2 == 4) {
                    continue4();
                } else {
                    car.setSpeed((hispeed << 10) / 27);
                }
                break;
            case 4:
                car.setSpeed(((hispeed + 15) << 10) / 27);
                left();
                tempval2 = 3;
                lastval = 4;
                break;
            case 6:
                lastval = 6;
                if (!block) {
                    temp = Math.abs(r.nextInt()) % 10;
                    if (temp % 2 == 1) {
                        tempval2 = 1;
                        car.setSpeed((lowspeed << 10) / 27);
                        right();
                    } else {
                        tempval2 = 3;
                        car.setSpeed((hispeed << 10) / 27);
                        left();
                    }
                    block = true;
                }
                if (temp % 2 == 1) {
                    car.setSpeed((lowspeed << 10) / 27);
                } else {
                    car.setSpeed((hispeed << 10) / 27);
                }
                break;
            case 7:
                if (lastval != 3) {
                    lastval = 7;
                    if (!block) {
                        temp = Math.abs(r.nextInt()) % 10;
                        if (temp % 2 == 1) {
                            tempval2 = 1;
                            car.setSpeed((lowspeed << 10) / 27);
                            right();
                        } else {
                            tempval2 = 4;
                        }
                        block = true;
                    }
                } else {
                    lastval = 7;
                    block = true;
                }
                if (tempval2 == 4) {
                    continue4();
                } else {
                    car.setSpeed((lowspeed << 10) / 27);
                }
                break;
            case 8:
                lastval = 8;
                tempval2 = 4;
                continue4();
                break;
            case 11:
                lastval = 11;
                tempval2 = 1;
                car.setSpeed((lowspeed << 10) / 27);
                right();
                break;
        }
    }

    private void continue1() {
        left = false;
        left2 = true;
        right = false;
        right2 = true;
        car.setSpeed((nspeed << 10) / 27);
        if (hood.getX() > xdiv64ori64 + ROAD) {
            if (carcourse > 0) {
                if (car.repeatCodeD == Ecran.KEY_NUM6) {
                    car.keyReleased(Ecran.KEY_NUM6);
                }
                car.keyPressed(Ecran.KEY_NUM4);
            } else if (hood.getX() < xdiv64ori64 + ROAD + limita_abatere) {
                if (car.repeatCodeD == Ecran.KEY_NUM4) {
                    car.keyReleased(Ecran.KEY_NUM4);
                }
                car.keyPressed(Ecran.KEY_NUM6);
                if (carcourse < 10 || carcourse > 360) {
                    car.setCourse(270);

                }
            } else if (carcourse > 30) {
                car.keyReleased(Ecran.KEY_NUM4);
            }
        } else if (hood.getX() < xdiv64ori64 + ROAD) {
            if (carcourse > 270) {
                if (car.repeatCodeD == Ecran.KEY_NUM4) {
                    car.keyReleased(Ecran.KEY_NUM4);
                }
                car.keyPressed(Ecran.KEY_NUM6);
            } else if (hood.getX() > xdiv64ori64 + ROAD - limita_abatere) {
                if (car.repeatCodeD == Ecran.KEY_NUM6) {
                    car.keyReleased(Ecran.KEY_NUM6);
                }
                car.keyPressed(Ecran.KEY_NUM4);
                if (carcourse < 10 || carcourse > 350) {
                    car.setCourse(270);
                }
            } else if (carcourse < 335) {
                car.keyReleased(Ecran.KEY_NUM6);
            }
        }
    }

    private void continue2() {
        left = false;
        left2 = true;
        right = false;
        right2 = true;
        car.setSpeed((nspeed << 10) / 27);
        if (hood.getY() > ydiv64ori64 + ROAD) {
            if (carcourse > 90) {
                if (car.repeatCodeD == Ecran.KEY_NUM6) {
                    car.keyReleased(Ecran.KEY_NUM6);
                }
                car.keyPressed(Ecran.KEY_NUM4);
            } else if (hood.getY() < ydiv64ori64 + ROAD + limita_abatere) {
                if (car.repeatCodeD == Ecran.KEY_NUM4) {
                    car.keyReleased(Ecran.KEY_NUM4);
                }
                car.keyPressed(Ecran.KEY_NUM6);
                if (Math.abs(carcourse - 90) < 10) {
                    car.setCourse(0);
                }
            } else if (carcourse < 60) {
                car.keyReleased(Ecran.KEY_NUM4);
            }
        } else if (hood.getY() < ydiv64ori64 + ROAD) {
            if (carcourse < 90) {
                if (car.repeatCodeD == Ecran.KEY_NUM4) {
                    car.keyReleased(Ecran.KEY_NUM4);
                }
                car.keyPressed(Ecran.KEY_NUM6);
            } else if (hood.getY() > ydiv64ori64 + ROAD - limita_abatere) {
                if (car.repeatCodeD == Ecran.KEY_NUM6) {
                    car.keyReleased(Ecran.KEY_NUM6);
                }
                car.keyPressed(Ecran.KEY_NUM4);
                if (Math.abs(carcourse - 90) < 10) {
                    car.setCourse(0);
                }
            } else if (carcourse > 120) {
                car.keyReleased(Ecran.KEY_NUM6);
            }
        }
    }

    private void continue3() {
        left = false;
        left2 = true;
        right = false;
        right2 = true;
        car.setSpeed((nspeed << 10) / 27);
        if (hood.getX() > xdiv64ori64 + ROAD2) {
            if (carcourse < 180) {
                if (car.repeatCodeD == Ecran.KEY_NUM4) {
                    car.keyReleased(Ecran.KEY_NUM4);
                }
                car.keyPressed(Ecran.KEY_NUM6);
            } else if (hood.getX() < xdiv64ori64 + ROAD2 + limita_abatere) {
                if (car.repeatCodeD == Ecran.KEY_NUM6) {
                    car.keyReleased(Ecran.KEY_NUM6);
                }
                car.keyPressed(Ecran.KEY_NUM4);
                if (Math.abs(carcourse - 180) < 10) {
                    car.setCourse(90);

                }
            } else if (carcourse > 210) {
                car.keyReleased(Ecran.KEY_NUM6);
            }
        } else if (hood.getX() < xdiv64ori64 + ROAD2) {
            if (carcourse > 180) {
                if (car.repeatCodeD == Ecran.KEY_NUM6) {
                    car.keyReleased(Ecran.KEY_NUM6);
                }
                car.keyPressed(Ecran.KEY_NUM4);
            } else if (hood.getX() > xdiv64ori64 + ROAD2 - limita_abatere) {
                if (car.repeatCodeD == Ecran.KEY_NUM4) {
                    car.keyReleased(Ecran.KEY_NUM4);
                }
                car.keyPressed(Ecran.KEY_NUM6);
                if (Math.abs(carcourse - 180) < 10) {
                    car.setCourse(90);
                }
            } else if (carcourse < 150) {
                car.keyReleased(Ecran.KEY_NUM4);
            }
        }
    }

    private void continue4() {
        left = false;
        left2 = true;
        right = false;
        right2 = true;
        car.setSpeed((nspeed << 10) / 27);
        if (hood.getY() < ydiv64ori64 + ROAD2) {
            if (carcourse > 270) {
                if (car.repeatCodeD == Ecran.KEY_NUM6) {
                    car.keyReleased(Ecran.KEY_NUM6);
                }
                car.keyPressed(Ecran.KEY_NUM4);
            } else if (hood.getY() > ydiv64ori64 + ROAD2 - limita_abatere) {
                if (car.repeatCodeD == Ecran.KEY_NUM4) {
                    car.keyReleased(Ecran.KEY_NUM4);
                }
                car.keyPressed(Ecran.KEY_NUM6);
                if (Math.abs(carcourse - 270) < 10) {
                    car.setCourse(180);
                }
            } else if (carcourse < 240) {
                car.keyReleased(Ecran.KEY_NUM4);
            }
        } else if (hood.getY() > ydiv64ori64 + ROAD2) {
            if (carcourse < 270) {
                if (car.repeatCodeD == Ecran.KEY_NUM4) {
                    car.keyReleased(Ecran.KEY_NUM4);
                }
                car.keyPressed(Ecran.KEY_NUM6);
            } else if (hood.getY() < ydiv64ori64 + ROAD2 + limita_abatere) {
                if (car.repeatCodeD == Ecran.KEY_NUM6) {
                    car.keyReleased(Ecran.KEY_NUM6);
                }
                car.keyPressed(Ecran.KEY_NUM4);
                if (Math.abs(carcourse - 270) < 10) {
                    car.setCourse(180);
                }
            } else if (carcourse > 300) {
                car.keyReleased(Ecran.KEY_NUM6);
            }
        }
    }

    private void left() {
        if (car.repeatCodeD == Ecran.KEY_NUM6) {
            car.keyReleased(Ecran.KEY_NUM6);
        }
        car.keyPressed(Ecran.KEY_NUM4);
        left = true;
        right = false;
        right2 = true;
    }

    private void right() {
        if (car.repeatCodeD == Ecran.KEY_NUM4) {
            car.keyReleased(Ecran.KEY_NUM4);
        }
        car.keyPressed(Ecran.KEY_NUM6);
        left = false;
        left2 = true;
        right = true;
    }
}
