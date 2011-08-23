/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GProduction.auxiliary;

import GProduction.miniCarSim_Alpha2;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author Blondu
 */
public class resumeScreen extends Canvas {

    miniCarSim_Alpha2 parent;

    public resumeScreen(miniCarSim_Alpha2 parent) {
        this.parent = parent;
    }

    protected void paint(Graphics g) {
        g.setColor(0);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(0xFFFFFF);
        g.drawString("Press any key to resume game.", getWidth() / 2, getHeight() / 2, Graphics.BASELINE | Graphics.VCENTER);
    }

    protected void keyPressed(int keyCode) {
        parent.resume();
    }
}
