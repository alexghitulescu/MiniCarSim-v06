/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GProduction.ErrorSystem;

import GProduction.Ecran;
import GProduction.Maps;
import GProduction.miniCarSim_Alpha2;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author Blondu
 */
public class Console {

    private String[] Msgs;
    private int x[], y[], Y[], h = ErrorLog.h / 2, separatorheight;
    private char[] input = new char[20];
    private int current = -1, currentT, index, spacewidth;
    private long lastPressTime;
    private boolean timerActivated = false;
    private Thread t;
    private Ecran parent;
    private Maps map;

    public Console(Ecran parent, Maps map) {
        Msgs = ErrorLog.Msgs;
        x = ErrorLog.x;
        y = ErrorLog.y;
        t = new Thread(tt);
        t.start();
        this.parent = parent;
        this.map = map;
        spacewidth = ErrorLog.spacewidth;
        //separatorheight = h - ErrorLog.fontheigh;
        separatorheight = h - CustomFont.getHeight();
    }

    public void paint(Graphics g) {
        g.setColor(0);
        g.fillRect(0, 0, ErrorLog.w, h);
        g.setColor(0, 127, 255);
        g.drawRect(0, 0, ErrorLog.w - 1, h);
        if (ErrorLog.isModified) {
            recalculateY();
        }
        for (int i = ErrorLog.n - 1; i != -1; i--) {
            //g.drawString(Msgs[i], x[i], Y[y[i]], Graphics.BOTTOM | Graphics.LEFT);
            CustomFont.drawString(g, Msgs[i], x[i], Y[y[i]]);
        }
        //g.drawChars(input, 0, 20, 10, h, Graphics.BOTTOM | Graphics.LEFT);
        CustomFont.drawChars(g, input, 10, h - CustomFont.getHeight());
        //g.drawLine(10 + index * spacewidth, h - 2, 10 + (index + 1) * spacewidth, h - 2);
        g.drawLine(10 + index * spacewidth, h - 2, 10 + (index + 1) * spacewidth, h - 2);
        g.drawLine(0, separatorheight, ErrorLog.w, separatorheight);
        CustomFont.test(g);
    }

    private void recalculateY() {
        ErrorLog.isModified = false;
        Y = new int[y[ErrorLog.n - 1] + 1];
        for (int i = y[ErrorLog.n - 1]; i != -1; i--) {
            Y[i] = h - ErrorLog.fontheigh * (y[ErrorLog.n - 1] - i + 1);
        }
    }

    public void keyPressed(int keyCode) {
        int gameAction = parent.getGameAction(keyCode);
        if (gameAction == Ecran.FIRE && keyCode != Ecran.KEY_NUM5) {
            ErrorLog.add("|" + new String(input).trim() + "|");
            searchCommand(new String(input).trim());
        } else {
            switch (keyCode) {
                case -8:
                case -7:
                    current = -1;
                    currentT = 0;
                    if (timerActivated) {
                        input[index] = 0;
                    } else if (index > 0) {
                        index--;
                        input[index] = 0;
                    }
                    timerActivated = false;
                    break;
                case Ecran.KEY_NUM0:
                    acctionGeneralization(0);
                    break;
                case Ecran.KEY_NUM1:
                    acctionGeneralization(1);
                    break;
                case Ecran.KEY_NUM2:
                    acctionGeneralization(2);
                    break;
                case Ecran.KEY_NUM3:
                    acctionGeneralization(3);
                    break;
                case Ecran.KEY_NUM4:
                    acctionGeneralization(4);
                    break;
                case Ecran.KEY_NUM5:
                    acctionGeneralization(5);
                    break;
                case Ecran.KEY_NUM6:
                    acctionGeneralization(6);
                    break;
                case Ecran.KEY_NUM7:
                    acctionGeneralization(7);
                    break;
                case Ecran.KEY_NUM8:
                    acctionGeneralization(8);
                    break;
                case Ecran.KEY_NUM9:
                    acctionGeneralization(9);
                    break;
                default:
                    System.out.println(keyCode);
            }
        }
    }

    private void acctionGeneralization(int k) {
        if (current != k) {
            currentT = 0;
            if (current != -1 && index < 20) {
                index++;
            }
            current = k;
        }
        input[index] = Chars.keys[k][currentT];
        if (currentT < Chars.keys[k].length - 1) {
            currentT++;
        } else {
            currentT = 0;
        }
        lastPressTime = System.currentTimeMillis();
        timerActivated = true;
    }
    private Runnable tt = new Runnable() {

        public void run() {
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                ErrorLog.add(ex);
            }
            while (true) {
                if (timerActivated) {
                    if (System.currentTimeMillis() - 900 > lastPressTime) {
                        current = -1;
                        currentT = 0;
                        if (index < 20) {
                            index++;
                        }
                        timerActivated = false;
                    }
                }
                try {
                    if (parent.getConsoleStatus() == false) {
                        Thread.sleep(1000);
                    } else {
                        Thread.sleep(150);
                    }
                } catch (InterruptedException ex) {
                    ErrorLog.add(ex);
                }

            }
        }
    };

    private void searchCommand(String str) {
        index = 0;
        current = -1;
        currentT = 0;
        input = new char[20];
        timerActivated = false;
        if (str.equalsIgnoreCase("memory")) {
            long a = parent.r.freeMemory(), b = parent.r.totalMemory();
            ErrorLog.add("Total memory: " + a + "; Free memory: " + b + "; Used memory: " + (b - a));
        } else if (str.equalsIgnoreCase("exit")) {
            parent.p.exitMIDlet();
        } else if (str.equalsIgnoreCase("clean")) {
            ErrorLog.clean();
            Msgs = ErrorLog.Msgs;
            x = ErrorLog.x;
            y = ErrorLog.y;
        } else if (str.equalsIgnoreCase("runtime")) {
            ErrorLog.add(String.valueOf(System.currentTimeMillis() - miniCarSim_Alpha2.startTime));
        } else if (str.startsWith("load")) {
            map.loadMap(str.substring(5));
            parent.resetCars();
        } else if (str.equalsIgnoreCase("mapnames")) {
            ErrorLog.add("Avalaible maps: ");
            for (int i = 0; i < Maps.mapNames.length; i++) {
                ErrorLog.addStringNONewLine(Maps.mapNames[i] + "; ");
            }
            ErrorLog.add("");
        } else if (str.equalsIgnoreCase("help")) {
            ErrorLog.add("memory; exit; clean; runtime; load; mapnames; debugmap; setlim;");
            ErrorLog.add("Type 'help <command>' for specific details");
        } else if (str.startsWith("help")) {
            help(str.substring(5));
        } else if (str.startsWith("debugmap")) {
            try {
                if (Integer.parseInt(str.substring(8).trim()) == 0) {
                    Ecran.showNRs = false;
                } else {
                    Ecran.showNRs = true;
                }
            } catch (Exception e) {
                ErrorLog.add("Invalid input!");
            }
        } else if (str.startsWith("setlim")) {
            int temp;
            try {
                temp = Integer.parseInt(str.substring(6).trim());
                if (temp > 10 && temp < 500) {
                    Ecran.limitator = 1000 / temp;
                } else {
                    ErrorLog.add("The limit must be between 10 and 500");
                }
            } catch (Exception e) {
                ErrorLog.add("Invalid input!");
            }
        } else {
            ErrorLog.add("Unknown command! Type help for command list.");
        }
    }

    void help(String str) {
        if (str.equalsIgnoreCase("memory")) {
            ErrorLog.add("Displays memory usage information.");
        } else if (str.equalsIgnoreCase("exit")) {
            ErrorLog.add("Exits the game.");
        } else if (str.equalsIgnoreCase("clean")) {
            ErrorLog.add("Cleans the console buffer.");
        } else if (str.equalsIgnoreCase("runtime")) {
            ErrorLog.add("Displays how many milliseconds the game runned.");
        } else if (str.startsWith("load")) {
            ErrorLog.add("Loads the specified map." + ErrorLog.newLine + " Ussage: type 'load <mapname>'.");
        } else if (str.equalsIgnoreCase("mapnames")) {
            ErrorLog.add("Lists avalaibe maps.");
        } else if (str.equalsIgnoreCase("help")) {
            ErrorLog.add("Displays command list.");
        } else if (str.startsWith("debugmap")) {
            ErrorLog.add("Used only for debug. 0 = disable, 1 = enable");
        } else if (str.startsWith("setlim")) {
            ErrorLog.add("Used to set the maximum framerate.");
        } else {
            ErrorLog.add("Unknown command! Type help for command list.");
        }
    }
}
