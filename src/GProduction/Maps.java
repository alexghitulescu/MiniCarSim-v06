/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GProduction;

import GProduction.ErrorSystem.ErrorLog;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 *
 * @author Blondu
 */
public class Maps {

    private DataInputStream dis = null;
    private static final int x64 = StaticDATA.BlockDimensions;
    int x, y, w, h;
    private int xdiv64, xmod64, ydiv64, ymod64, n;
    private int i1, j1, nrX, nrY, xBlocks, yBlocks, xBlocksTemp, yBlocksTemp;
    private int xdiv64PlusXBlocks, ydiv64PlusYBlocks;
    private int imgX, imgY, Anchor1 = Graphics.LEFT | Graphics.TOP;
    private int height = 320, width = 240, wpe2, hpe2;
    private long time1, renderTime;
    public static byte map2d[][];
    private Image texture[] = new Image[8];
    private int xLim1, yLim1;
    public static String[] mapNames = {"AlphaCity", "BetaCity", "firstmap", "test", "test2", "test3"};
    private Ecran parent;
    public static int startblockX = 0, startblockY = 0, startPoz = 1;

    public Maps(Ecran parent, int width, int height) {
        this.parent = parent;
        n = 14;
        ErrorLog.add("Welcome to miniCarSim");
        //System.out.println("Welcome to AlphaCity");
        texture = new Image[n];
        String nume[] = {"/0.png", "/1.png", "/2.png", "/3.png", "/4.png",
            "/5.png", "/6.png", "/7.png", "/8.png", "/9.png", "/10.png", "/11.png", "/12.png", "/13.png"};
        InputStream file = null;
        for (int i = 0; i < n; i++) {
            file = getClass().getResourceAsStream("/img/AlphaCity" + nume[i]);
            try {
                texture[i] = Image.createImage(file);
            } catch (IOException ex) {
                //ex.printStackTrace();
                ErrorLog.add(ex);
            }
        }
        this.width = width;
        this.height = height;
        loadMapNames();
        loadMap(mapNames[0]);
        System.out.println(mapNames[0]);
        for (int i = 0; i < n; i++) {
            //texture[i] = ImageRotator_1.resizeImage(texture[i], x64, x64);
        }
    }

    private void loadMapNames() {
        dis = new DataInputStream(getClass().getResourceAsStream("/GProduction/res/list.maps"));
        try {
            int temp = dis.readInt();
            mapNames = new String[temp];
            for (int i = 0; i < temp; i++) {
                mapNames[i] = dis.readUTF();
            }
        } catch (Exception e) {
            ErrorLog.add(e);
        }
    }

    public void loadMap(String name) {
        boolean validname = false;
        if (!name.endsWith(".map")) {
            name += ".map";
        }
        System.out.println("|" + name + "|");
        for (int i = 0; i < mapNames.length; i++) {
            if (name.equalsIgnoreCase(mapNames[i])) {
                validname = true;
                name = mapNames[i];
                ErrorLog.add("Loading " + name);
                i = mapNames.length;
            }
        }
        if (validname) {
            try {
                readMap(name, true);
                Ecran.NOBx = nrX;
                Ecran.NOBy = nrY;
                xBlocks = width / x64 + 2;
                yBlocks = height / x64 + 2;
                //System.out.println("Blocks:" + xBlocks + "x" + yBlocks);
                ErrorLog.add("Blocks:" + xBlocks + "x" + yBlocks);
                ErrorLog.add(" Current rezolution:" + width + " x " + height);
                wpe2 = width / 2;
                hpe2 = height / 2;
                xLim1 = x64 * nrX - width;
                yLim1 = x64 * nrY - height;
                w = x64 * nrX;
                h = x64 * nrY;
                StaticDATA.carposition = new int[nrX][nrY];

                //StaticDATA.colmap = getMap2d();
            } catch (IOException ex) {
                ErrorLog.add(ex);
                ex.printStackTrace();
            }
        } else {
            ErrorLog.add("Map not found! Type 'mapnames' for help.");
        }
    }

    public void rezolutionChange(int width, int height) {
        this.width = width;
        this.height = height;
        xBlocks = width / x64 + 2;
        yBlocks = height / x64 + 2;
        //System.out.println("Blocks:" + xBlocks + "x" + yBlocks);
        ErrorLog.add("Blocks:" + xBlocks + "x" + yBlocks);
        ErrorLog.add(" Current rezolution:" + width + " x " + height);
        wpe2 = width / 2;
        hpe2 = height / 2;
        xLim1 = x64 * nrX - width;
        yLim1 = x64 * nrY - height;
        Car.setScreenBorders(width, height);
    }

    public int[][] getMap2d() {
        int[][] temp = new int[nrX][nrY];
        for (int i = 0; i < nrX; i++) {
            for (int j = 0; j < nrY; j++) {
                temp[i][j] = map2d[i][j];
            }
        }
        return temp;
    }

    private void find_pos() {
        xdiv64 = (x) / x64;
        xmod64 = (x) % x64;
        ydiv64 = (y) / x64;
        ymod64 = (y) % x64;
    }

    private void paint(Graphics g) {
        time1 = System.currentTimeMillis();
        find_pos();
        xdiv64PlusXBlocks = xdiv64 + xBlocks;
        if (xdiv64PlusXBlocks > nrX) {
            xBlocksTemp = xdiv64PlusXBlocks - ((xdiv64PlusXBlocks) - nrX);
        } else {
            xBlocksTemp = xdiv64PlusXBlocks;
        }
        ydiv64PlusYBlocks = ydiv64 + yBlocks;
        if (ydiv64PlusYBlocks > nrY) {
            yBlocksTemp = ydiv64PlusYBlocks - ((ydiv64PlusYBlocks) - nrY);
        } else {
            yBlocksTemp = ydiv64PlusYBlocks;
        }
        imgX = -xmod64;
        imgY = -ymod64;
        for (i1 = xdiv64; i1 < xBlocksTemp; i1++) {
            for (j1 = ydiv64; j1 < yBlocksTemp; j1++) {
                try {
                    g.drawImage(texture[map2d[i1][j1]], imgX, imgY, Anchor1);
                    //g.setColor(0x0090ff);
                    if (Ecran.showNRs) {
                        g.drawString(String.valueOf(StaticDATA.carposition[i1][j1]), imgX, imgY, Anchor1);
                    }
                } catch (Exception e) {
                    //e.printStackTrace();
                    ErrorLog.add(e);
                    //System.out.println("i1=" + i1 + " j1=" + j1 + " nrX=" + nrX + " nrY" + nrY);
                    ErrorLog.add("i1=" + i1 + " j1=" + j1 + " nrX=" + nrX + " nrY" + nrY);
                }
                imgY += x64;
            }
            imgX += x64;
            imgY = -ymod64;
        }
        g.setColor(0, 0, 0);
        g.fillRect(width - 20, 0, 20, 20);
        g.setColor(255, 255, 255);
        g.drawString(String.valueOf(renderTime), width, 0, Graphics.RIGHT | Graphics.TOP);
        renderTime = System.currentTimeMillis() - time1;

    }

    void draw(Graphics g, int x, int y) {
        xyCalculation(x, y);
        paint(g);
    }

    void xyCalculation(int x, int y) {
        x -= wpe2;
        y -= hpe2;
        if (x < 0) {
            x = 0;
        } else if (x > xLim1) {
            x = xLim1;
        }
        if (y < 0) {
            y = 0;
        } else if (y > yLim1) {
            y = yLim1;
        }
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    void readMap(String name, boolean addExtension) throws IOException {
        dis = new DataInputStream(getClass().getResourceAsStream("/GProduction/res/" + name));
        nrX = dis.readInt();
        if (nrX == -1) {
            dis.readUTF();
            nrX = dis.readInt();
            nrY = dis.readInt();
        } else if (nrX == -2) {
            dis.readUTF();
            nrX = dis.readInt();
            nrY = dis.readInt();
            startblockX = dis.readInt();
            startblockY = dis.readInt();
            startPoz = dis.readInt();
        }       
        map2d = new byte[nrX][nrY];
        for (int i = 0; i < nrY; i++) {
            for (int j = 0; j < nrX; j++) {
                map2d[j][i] = (byte) dis.readInt();
            }
        }
        dis.close();
        ErrorLog.add("Succesfully loaded map");
    }
}
