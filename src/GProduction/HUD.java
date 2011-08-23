/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GProduction;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 *
 * @author Blondu
 */
public class HUD extends Canvas {

    public HUD(int width, int height) {
        this.width = width;
        this.height = height;
        hminus60 = height - 61;
    }
    private int width, height, hminus60;

    protected void paint(Graphics g) {
        g.setColor(0);
        g.fillRect(0, hminus60, width, 60);
        g.setColor(255, 170, 0);
        g.drawRect(0, hminus60, width - 1, 60);
    }

}
