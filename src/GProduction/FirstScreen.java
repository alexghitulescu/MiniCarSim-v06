/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GProduction;

import GProduction.ErrorSystem.CustomFont;
import GProduction.ErrorSystem.ErrorLog;
import java.io.IOException;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author Blondu
 */
public class FirstScreen extends Canvas {

    private miniCarSim_Alpha2 parent;

    public FirstScreen(miniCarSim_Alpha2 parent) {
        this.parent = parent;
        setFullScreenMode(true);
    }

    protected void paint(Graphics g) {
        g.setColor(255, 255, 255);
        g.fillRect(0, 0, 400, 400);
        g.setColor(0, 0, 255);
        g.drawString("Loading", getWidth() / 2, getHeight() / 2, Graphics.BASELINE | Graphics.HCENTER);
        try {
            CustomFont.initialise(getClass().getResourceAsStream("/img/Font2.png"));          
        } catch (IOException ex) {
            ErrorLog.add(ex);
        }
        Ecran.NOBx = 20;
        Ecran.NOBy = 20;
        ErrorLog.setBounds(getWidth(), getHeight());
        parent.e = new Ecran(parent);
        parent.getDisplay().setCurrent(parent.e);
    }
}
