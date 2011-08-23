/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GProduction;

import java.io.IOException;
import java.io.InputStream;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 *
 * @author Blondu
 */
public class TouchCom {

    private Image up, down, left, right;
    private int aHT, aLT, aRT;
    private int L, R, A, B;

    public TouchCom(int l, int r, int a, int b) {
        InputStream file = getClass().getResourceAsStream("/img/arrow.png");
        try {
            up = Image.createImage(file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        ImageRotator_1 ir = new ImageRotator_1();
        right = ir.rotate_Img(up, 90);
        down = ir.rotate_Img(up, 180);
        left = ir.rotate_Img(up, 270);
        aHT = Graphics.HCENTER | Graphics.TOP;
        aLT = Graphics.LEFT | Graphics.TOP;
        aRT = Graphics.RIGHT | Graphics.TOP;
        L = l;
        R = r;
        A = a;
        B = b;
    }

    void paint(Graphics g) {
        g.drawImage(up, 120, 190, aHT);
        g.drawImage(down, 120, 250, aHT);
        g.drawImage(left, 10, 250, aLT);
        g.drawImage(right, 230, 250, aRT);
    }

    int touchPress(int x, int y) {
        if (x > 88 && x < 152) {
            if (y > 180 && y < 246) {
                return A;
            } else if (y > 250 && y < 316) {
                return B;
            }
        } else if (x > 10 && x < 74) {
            if (y > 250 && y < 316) {
                return L;
            }
        } else if (x > 166 && x < 230) {
            if (y > 250 && y < 316) {
                return R;
            }
        }
        return 0;
    }
}
