/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GProduction.ErrorSystem;

import me.regex.RE;

/**
 *
 * @author Blondu
 */
public class ErrorLog {

    public final static String newLine = " *1n";
    public final static String newLineEqals = "*1n";
    public static StringBuffer sb = new StringBuffer();
    private static RE regex = new RE("\\s");
    static String[] Msgs = new String[128];
    private static String[] temp;
    //public static final Font font = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);
    private static int ntemp, i, widthtemp;
    static int n, w, h, spacewidth = 10;//font.charWidth(' ');
    //static int fontheigh = font.getHeight(), x[] = new int[500], y[] = new int[500];
    static int fontheigh = CustomFont.getHeight(), x[] = new int[500], y[] = new int[500];
    static boolean isModified;

    public static void add(String str) {
        sb.append(str).append(newLine);
        toMsgs();
    }

    public static void addStringNONewLine(String str) {
        sb.append(str);
        toMsgs();
    }

    public static void add(Exception e) {
        sb.append(e.getMessage()).append(" ").append(e.toString()).append(newLine);
        sb.append(e.getClass().getName()).append(newLine);
        e.printStackTrace();
        System.out.println(e.getMessage());
        toMsgs();
    }

    public static void add(Error e) {
        sb.append(e.getMessage()).append(" ").append(e.toString()).append(newLine);
        toMsgs();
    }

    private static void toMsgs() {
        temp = regex.split(sb.toString());
        sb = new StringBuffer();
        ntemp = temp.length;
        for (i = 0; i < ntemp; i++) {
            Msgs[n] = temp[i];
            //widthtemp = font.stringWidth(Msgs[n]);
            widthtemp = CustomFont.stringWidth(Msgs[n]);
            if (Msgs[n].equalsIgnoreCase(newLineEqals)) {
                widthtemp = 0;
                y[n]++;
                x[n] = 2;
                Msgs[n] = "";
            } else if (x[n] + widthtemp > w) {
                y[n]++;
                x[n] = 2;
            }
            x[n + 1] = x[n] + widthtemp + spacewidth;
            y[n + 1] = y[n];
            n++;
        }
        if (n > 96) {
            doCleanUp();
        }
        isModified = true;
    }

    private static void doCleanUp() {
        int npe2 = n / 2;
        if (n % 2 != 0) {
            npe2++;
        }
        for (i = 0; i < npe2 / 2; i++) {
            Msgs[i] = Msgs[npe2 + i];
            x[i] = x[npe2 + i];
            y[i] = y[npe2 + i] - y[npe2];
        }
        n = npe2;
        isModified = true;
    }

    public static void clean() {
        Msgs = new String[128];
        x = new int[128];
        y = new int[128];
        n = 0;
    }

    public static void setBounds(int width, int height) {
        w = width;
        h = height;
        x[0] = 2;
    }
}
