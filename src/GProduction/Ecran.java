/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GProduction;

import GProduction.ErrorSystem.Console;
import GProduction.ErrorSystem.ErrorLog;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author Blondu
 */
public class Ecran extends Canvas {

    private static final int x64 = StaticDATA.BlockDimensions;

    public Ecran(miniCarSim_Alpha2 parent) {
        setFullScreenMode(true);
        touch = hasPointerEvents();
        w = getWidth();
        h = getHeight();
        map = new Maps(this, w, h);
        Car.setScreenBorders(w, h);
        console = new Console(this, map);
        //map = new Maps(NOBx, NOBy, w, h - 60);
        //map = new Maps(NOBx, NOBy, 240, 320);
        car = new CarPlayer(map, this, null);
        worldColManager = new WorldCollisions();
        for (int i = 0; i < NOofDummy; i++) {
            bot[i] = new SmartAI(map, this);
        }
        p = parent;
        System.gc();
        t1 = new Thread(tt1);
        t1.start();
        t2 = new Thread(tt2);
        t2.start();
        Car[] cars;
        cars = new Car[NOofDummy + 1];
        cars[0] = car;
        for (int i = 1; i <= NOofDummy; i++) {
            cars[i] = bot[i - 1].car;
        }
        cc = new CarCollisions(cars, NOofDummy + 1, p);
        cars = null;
        System.gc();
        if (touch) {
            TC = new TouchCom(KEY_NUM4, KEY_NUM6, KEY_NUM2, KEY_NUM5);
        }
        r = Runtime.getRuntime();
        ErrorLog.add("Load complete. Free memory: " + r.freeMemory() + "; total memory: " + r.totalMemory());
        StaticDATA.carposition = new int[NOBx][NOBy];
        car.setCourse(0);
        car.setX(40 << 10);
        car.setY(40 << 10);
        car.setCarpozition();
        StaticDATA.placeCars(car, bot);
    }
    public miniCarSim_Alpha2 p;
    private Maps map;
    public static int NOBx = 20, NOBy = 30, limitator = 33;
    private static int NOofDummy = 18, w, h;
    private double fpsT;
    private int contor;
    private Thread t1, t2;
    private CarPlayer car;
    private WorldCollisions worldColManager;
    private long time, fps;
    private CarCollisions cc;
    private TouchCom TC;
    private boolean touch = false, midletPaused;
    private SmartAI bot[] = new SmartAI[NOofDummy];
    private boolean init = true, ShowConsole;
    private Console console;
    public Runtime r;
    public static boolean showNRs;
    //private long m1, m2, m3;//public static int raportMilisecunde = 1024;

    protected void paint(Graphics g) {
        if (ShowConsole) {
            console.paint(g);
        } else {
            map.draw(g, car.x, car.y);
            car.paint(g);
            for (int i = 0; i < NOofDummy; i++) {
                bot[i].paint(g);
            }
            g.setColor(0);
            g.fillRect(0, 0, 20, 20);
            g.setColor(0, 255, 0);
            g.drawString(String.valueOf(fps), 0, 0, Graphics.LEFT | Graphics.TOP);
            g.drawString(String.valueOf(car.getSpeedKMH()), 0, 20, Graphics.LEFT | Graphics.TOP);
            g.drawString(String.valueOf(car.courseIncrease), 0, 40, Graphics.LEFT | Graphics.TOP);
            g.drawString(String.valueOf(car.maxCourseIncrease), 0, 60, Graphics.LEFT | Graphics.TOP);
            /*m2 = System.currentTimeMillis();m3 = m2 - m1;m1 = System.currentTimeMillis();
            raportMilisecunde = (int) (m3 << 10) / 35;
            g.drawString(String.valueOf(raportMilisecunde), 0, 80, Graphics.LEFT | Graphics.TOP);*///minimap.paint(g);
            if (touch) {
                TC.paint(g);
            }
        }
    }

    protected void keyPressed(int keyCode) {
        if (ShowConsole) {
            if (keyCode == KEY_STAR) {
                ShowConsole = false;
            } else {
                console.keyPressed(keyCode);
            }
        } else {
            if (keyCode == -7) {
                p.exitMIDlet();
            } else if (keyCode == KEY_STAR) {
                if (ShowConsole) {
                    ShowConsole = false;
                } else {
                    ShowConsole = true;
                }
            } else if (keyCode == KEY_NUM7) {
            } else {
                car.keyPressed(keyCode);
            }
        }
    }

    protected void keyReleased(int keyCode) {
        car.keyReleased(keyCode);

    }

    protected void pointerPressed(int x, int y) {
        keyPressed(TC.touchPress(x, y));
    }

    protected void pointerReleased(int x, int y) {
        keyReleased(TC.touchPress(x, y));
    }

    public void midletPaused() {
        midletPaused = true;
    }

    public void resume() {
        midletPaused = false;
        t1.interrupt();
    }
    private Runnable tt1 = new Runnable() {

        long t;

        public void run() {
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                ErrorLog.add(ex);
            }
            while (true) {
                if (!ShowConsole) {
                    t = System.currentTimeMillis();
                    time = t - time;
                    contor++;
                    fpsT = fpsT + (1000d / time);
                    if (contor >= 10) {
                        fps = (long) (fpsT / 10);
                        contor = 0;
                        fpsT = 0;
                    }
                    time = System.currentTimeMillis();
                    try {
                        car.calc();
                        for (int i = 0; i < NOofDummy; i++) {
                            bot[i].calc();
                        }
                        cc.calc();
                        worldColManager.calc(car);
                        for (int i = 0; i < NOofDummy; i++) {
                            worldColManager.calc(bot[i].car);
                        }
                        repaint();
                        //minimap.repaint(179, 0, 60, 80);
                    } catch (Exception e) {
                        //e.printStackTrace();
                        //System.out.println(e.toString() + " " + e.getMessage());
                        ErrorLog.add(e);
                    }
                } else {
                    repaint();
                }
                t = System.currentTimeMillis() - t;
                try {
                    if (t > limitator) {
                        t = limitator;
                    }
                    if (midletPaused) {
                        Thread.sleep(3600000);
                    } else {
                        Thread.sleep(limitator - t);
                    }
                    //Thread.sleep(1000);
                } catch (Exception ex) {
                    //ex.printStackTrace();
                    ErrorLog.add(ex);
                }

            }
        }
    };
    private Runnable tt2 = new Runnable() {

        public void run() {
            while (true) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    //ex.printStackTrace();
                    ErrorLog.add(ex);
                }
                if (w != getWidth()) {
                    w = getWidth();
                    h = getHeight();
                    map.rezolutionChange(w, h);
                }
                System.gc();
            }
        }
    };

    public static int getScreenWidth() {
        return w;
    }

    public static int getcreenHeight() {
        return h;
    }

    public boolean getConsoleStatus() {
        return ShowConsole;
    }

    public void resetCars() {
        System.out.println("reset cars");
        StaticDATA.placeCars(car, bot);
    }

    void setNOofCars(int n) {
        NOofDummy = n;
    }
}
