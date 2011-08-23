/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GProduction.ErrorSystem;

import java.io.IOException;
import java.io.InputStream;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 *
 * @author Blondu
 */
public class CustomFont {

    //private static int CELL_WIDTH = 10;
    private static int CELL_WIDTH = 12;
    //private static int CELL_HEIGHT = 12;
    private static int CELL_HEIGHT = 15;
    private static String sequence = " !\"#$%&'()*+,-./"
            + "0123456789:;<=>?"
            + "@ABCDEFGHIJKLMNO"
            + "PQRSTUVWXYZ[\\]^_"
            + "`abcdefghijklmno"
            + "pqrstuvwxyz{|}~" + (char) 0;
    private static Image fontimage;
    private static int width;
    private static int height;
    private static int charsPerRow;
    private static int[] cellWidth, cellStart;
    private static int i, cx, cy;

    public static void initialise(InputStream fontname) throws IOException {
        fontimage = Image.createImage(fontname);
        width = fontimage.getWidth();
        height = fontimage.getHeight();
        charsPerRow = width / CELL_WIDTH;
        cellWidth = new int[sequence.length()];
        cellStart = new int[sequence.length()];
        precalculate();
    }

    /** @return width of the character */
    public static int drawChar(Graphics g, char ch, int x, int y) {
        // find the position in the font
        i = sequence.indexOf(ch);
        if (i == -1) {
            System.out.println((int) ch);
        }

        // find that character in the image
        cx = (i % charsPerRow) * CELL_WIDTH;
        cy = (i / charsPerRow) * CELL_HEIGHT;

        // draw it
        g.setClip(x, y, cellWidth[i], CELL_HEIGHT);
        g.drawImage(fontimage, x - cx - cellStart[i], y - cy, Graphics.TOP | Graphics.LEFT);

        //return CELL_WIDTH;
        return cellWidth[i];
    }

    public static void drawChars(Graphics g, char[] chs, int x, int y) {
        for (int i = 0; i < chs.length; i++) {
            x += drawChar(g, chs[i], x, y);
        }
    }

    public static void drawString(Graphics g, String s, int x, int y) {
        // this is faster than using s.charAt()
        char[] chs = s.toCharArray();
        for (int i = 0; i < chs.length; i++) {
            x += drawChar(g, chs[i], x, y);
        }
    }

    static void test(Graphics g) {
        drawString(g, " !\"#$%&'()*+,-./", 5, 160);
        drawString(g, "0123456789:;<=>?", 5, 160 + CELL_HEIGHT * 1);
        drawString(g, "@ABCDEFGHIJKLMNO", 5, 160 + CELL_HEIGHT * 2);
        drawString(g, "PQRSTUVWXYZ[\\]^_", 5, 160 + CELL_HEIGHT * 3);
        drawString(g, "`abcdefghijklmno", 5, 160 + CELL_HEIGHT * 4);
        drawString(g, "pqrstuvwxyz{|}~", 5, 160 + CELL_HEIGHT * 5);
    }

    static int getHeight() {
        return CELL_HEIGHT;
    }

    static int stringWidth(String str) {
        //return CELL_WIDTH * str.length();
        int temp = 0;
        i = 0;
        while (str.length() > i) {
            try {
                temp += cellWidth[sequence.indexOf(str.charAt(i))];
            } catch (Exception e) {
                System.out.println("i=" + i);
                System.out.println(str.charAt(i));
                System.out.println(sequence.indexOf(str.charAt(i)));
            }
            i++;
        }
        return temp;
    }

    static void precalculate() {
        int ARGB[], w, h, cw, ch;
        w = fontimage.getWidth();
        h = fontimage.getHeight();
        ARGB = new int[w * h];
        fontimage.getRGB(ARGB, 0, w, 0, 0, w, h);
        for (int i = 0; i < sequence.length(); i++) {
            cx = (i % charsPerRow) * CELL_WIDTH;
            cy = (i / charsPerRow) * CELL_HEIGHT;
            cw = cx + CELL_WIDTH;
            ch = cy + CELL_HEIGHT;
            for (int j = cx; j < cw; j++) {
                for (int k = cy; k < ch; k++) {
                    if (ARGB[k * w + j] != 0) {
                        cellStart[i] = j - cx - 1;
                        j = cw;
                        k = ch;
                        System.out.println(sequence.charAt(i) + " startsAt:" + cellStart[i]);
                    }
                }
            }
            cellWidth[i] = CELL_WIDTH;
            for (int j = cw; j > cx; j--) {
                for (int k = ch; k > cy; k--) {
                    if (ARGB[k * w + j] != 0) {
                        cellWidth[i] = j - cx - cellStart[i] + 1;
                        j = cx;
                        k = cy;
                        System.out.println(sequence.charAt(i) + " width:" + cellWidth[i]);
                    }
                }
            }
        }
    }
}
