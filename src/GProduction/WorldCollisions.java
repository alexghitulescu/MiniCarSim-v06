    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GProduction;

import GProduction.ErrorSystem.ErrorLog;

/**
 *
 * @author Blondu
 */
public class WorldCollisions {

    private static final int x64 = StaticDATA.BlockDimensions;
    //private Maps map;
    private int xdiv64, ydiv64;
    private int xdiv64ori64, xdiv64ori64plus33, xdiv64ori64plus29;
    private int ydiv64ori64, ydiv64ori64plus33, ydiv64ori64plus29;
    private int xdiv64ori64plus1, xdiv64ori64plus61;
    private int ydiv64ori64plus1, ydiv64ori64plus61;
    private int val, signX, signY, signS;
    private int x, y, w, h;
    private int x1, x61;

    public WorldCollisions() {
        double temp1, temp2, temp3;
        temp1 = 64.d / x64;
        temp2 = 61 / temp1;
        temp3 = 1 / temp1;
        x1 = math.floor(temp3);
        x61 = math.floor(temp2);
    }

    void calc(Car c) {
        val = math.Cadran(c.getCourse() + 90);
        w = c.getImgWidth() / 2;
        h = c.getImgHeight() / 2;
        if (c.speed >= 0) {
            signS = +1;
        } else {
            signS = -1;
        }
        switch (val) {
            case 1:
                signX = -1 * signS;
                signY = +1 * signS;
                x = c.x + w * signS;
                y = c.y - h * signS;
                break;
            case 2:
                signX = -1 * signS;
                signY = -1 * signS;
                x = c.x + w * signS;
                y = c.y + h * signS;
                break;
            case 3:
                signX = +1 * signS;
                signY = -1 * signS;
                x = c.x - w * signS;
                y = c.y + h * signS;
                break;
            case 4:
                signX = +1 * signS;
                signY = +1 * signS;
                x = c.x - w * signS;
                y = c.y - h * signS;
                break;
        }
        find_pos(x, y);
        for (int i = 0; i < 2; i++) {
            try {
                val = Maps.map2d[xdiv64][ydiv64];
            } catch (Exception e) {
                val = 0;
                //ErrorLog.add(e);
            }
            AlphaCity(c);
            find_pos(c.x, c.y);
        }
    }

    private void find_pos(int x, int y) {
        xdiv64 = (x) / x64;
        xdiv64ori64 = xdiv64 * x64;
        ydiv64 = (y) / x64;
        ydiv64ori64 = ydiv64 * x64;
        xdiv64ori64plus1 = xdiv64ori64 + x1;
        xdiv64ori64plus61 = xdiv64ori64 + x61;
        ydiv64ori64plus1 = ydiv64ori64 + x1;
        ydiv64ori64plus61 = ydiv64ori64 + x61;
    }

    boolean instructionYplus1(Car c) {
        if (c.yi > ydiv64ori64plus1) {
            if (y <= ydiv64ori64plus1) {
                c.vyi = ydiv64ori64plus1 - y;
                c.setYWC((ydiv64ori64plus1 + h * signY) << 10);
            }
            return true;
        }
        return false;
    }

    void instructionYplus61(Car c) {
        if (c.yi < ydiv64ori64plus61) {
            if (y >= ydiv64ori64plus61) {
                c.vyi = ydiv64ori64plus61 - y;
                c.setYWC((ydiv64ori64plus61 + h * signY) << 10);
            }
        }
    }

    boolean instructionXplus1(Car c) {
        if (c.xi > xdiv64ori64plus1) {
            if (x <= xdiv64ori64plus1) {
                c.vxi = xdiv64ori64plus1 - x;
                c.setXWC((xdiv64ori64plus1 + w * signX) << 10);
            }
            return true;
        }
        return false;
    }

    void instructionXplus61(Car c) {
        if (c.xi < xdiv64ori64plus61) {
            if (x >= xdiv64ori64plus61) {
                c.vxi = xdiv64ori64plus61 - x;
                c.setXWC((xdiv64ori64plus61 + w * signX) << 10);
            }
        }
    }

    private void AlphaCity(Car c) {
        switch (val) {
            case 1:
                instructionYplus1(c);
                instructionYplus61(c);
                break;
            case 2:
                instructionXplus1(c);
                instructionXplus61(c);
                break;
            case 3:
                instructionYplus1(c);
                break;
            case 4:
                instructionYplus1(c);
                instructionXplus1(c);
                break;
            case 5:
                instructionYplus1(c);
                instructionXplus61(c);
                break;
            case 6:
                instructionXplus1(c);
                break;
            case 7:
                instructionYplus61(c);
                break;
            case 9:
                instructionXplus61(c);
                break;
            case 10:
                instructionYplus61(c);
                instructionXplus61(c);
                break;
            case 11:
                instructionYplus61(c);
                instructionXplus1(c);
        }
    }
}
