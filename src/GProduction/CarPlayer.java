/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GProduction;

import GProduction.ErrorSystem.ErrorLog;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author Blondu
 */
public class CarPlayer extends Car {

    public CarPlayer(Maps map, Ecran ecran, String name) {
        super(map, ecran, name);
        m = map;
        mass = 1200;
    }

    protected void paint(Graphics g) {
        xC = (x - m.x);
        yC = (y - m.y);
        try {
            g.drawImage(img, xC, yC, Graphics.HCENTER | Graphics.VCENTER);
        } catch (Exception ex) {
            ErrorLog.add(ex);
        }
    }

    protected void calc() {
        super.calc();
        img = rotator.rotate_Img(super.getCourse());
        m.xyCalculation(x, y);
        xC = (x - m.x);
        yC = (y - m.y);
    }
}
