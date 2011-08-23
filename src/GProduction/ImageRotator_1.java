/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GProduction;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 *
 * @author Blondu
 */
public class ImageRotator_1 {

    private static int srcWidth, srcHeight;
    private int ARGB[];
    private int sinRPlusH, cosRPlusH, w, h;
    private int minusHDiv2, hDiv2minusr, minusWDiv2, wDiv2minusr, wr, hr, iOriWr, temp1, temp2;
    private int[] ARGB_T;
    private double ip;
    private int grad, gradMinAlpha, gradPlusAlpha;
    private long time1, time2;
    static long time;
    private int[] jvalue1, jvalue2;
    private int wrPe2x10, hrPe2x10;
    private short cosInts[] = {1024, 1023, 1023, 1022, 1021, 1020, 1018, 1016, 1014, 1011, 1008, 1005, 1001, 997, 993, 989, 984, 979, 973,
        968, 962, 955, 949, 942, 935, 928, 920, 912, 904, 895, 886, 877, 868, 858, 848, 838, 828, 817, 806, 795, 784, 772, 760, 748, 736,
        724, 711, 698, 685, 671, 658, 644, 630, 616, 601, 587, 572, 557, 542, 527, 512, 496, 480, 464, 448, 432, 416, 400, 383, 366, 350,
        333, 316, 299, 282, 265, 247, 230, 212, 195, 177, 160, 142, 124, 107, 89, 71, 53, 35, 17, 0, -17, -35, -53, -71, -89, -107, -124,
        -142, -160, -177, -195, -212, -230, -247, -265, -282, -299, -316, -333, -350, -366, -383, -400, -416, -432, -448, -464, -480, -496, -511,
        -527, -542, -557, -572, -587, -601, -616, -630, -644, -658, -671, -685, -698, -711, -724, -736, -748, -760, -772, -784, -795, -806, -817,
        -828, -838, -848, -858, -868, -877, -886, -895, -904, -912, -920, -928, -935, -942, -949, -955, -962, -968, -973, -979, -984, -989, -993,
        -997, -1001, -1005, -1008, -1011, -1014, -1016, -1018, -1020, -1021, -1022, -1023, -1023, -1024, -1023, -1023, -1022, -1021, -1020, -1018,
        -1016, -1014, -1011, -1008, -1005, -1001, -997, -993, -989, -984, -979, -973, -968, -962, -955, -949, -942, -935, -928, -920, -912, -904,
        -895, -886, -877, -868, -858, -848, -838, -828, -817, -806, -795, -784, -772, -760, -748, -736, -724, -711, -698, -685, -671, -658, -644,
        -630, -616, -601, -587, -572, -557, -542, -527, -512, -496, -480, -464, -448, -432, -416, -400, -383, -366, -350, -333, -316, -299, -282,
        -265, -247, -230, -212, -195, -177, -160, -142, -124, -107, -89, -71, -53, -35, -17, 0, 17, 35, 53, 71, 89, 107, 124, 142, 160, 177,
        195, 212, 230, 247, 265, 282, 299, 316, 333, 350, 366, 383, 400, 416, 432, 448, 464, 480, 496, 512, 527, 542, 557, 572, 587, 601, 616,
        630, 644, 658, 671, 685, 698, 711, 724, 736, 748, 760, 772, 784, 795, 806, 817, 828, 838, 848, 858, 868, 877, 886, 895, 904, 912, 920,
        928, 935, 942, 949, 955, 962, 968, 973, 979, 984, 989, 993, 997, 1001, 1005, 1008, 1011, 1014, 1016, 1018, 1020, 1021, 1022, 1023, 1023,
        1024};
    private short sinInts[] = {0, 17, 35, 53, 71, 89, 107, 124, 142, 160, 177, 195, 212, 230, 247, 265, 282, 299, 316, 333, 350, 366, 383, 400,
        416, 432, 448, 464, 480, 496, 511, 527, 542, 557, 572, 587, 601, 616, 630, 644, 658, 671, 685, 698, 711, 724, 736, 748, 760, 772, 784, 795, 806,
        817, 828, 838, 848, 858, 868, 877, 886, 895, 904, 912, 920, 928, 935, 942, 949, 955, 962, 968, 973, 979, 984, 989, 993, 997, 1001, 1005, 1008,
        1011, 1014, 1016, 1018, 1020, 1021, 1022, 1023, 1023, 1024, 1023, 1023, 1022, 1021, 1020, 1018, 1016, 1014, 1011, 1008, 1005, 1001, 997,
        993, 989, 984, 979, 973, 968, 962, 955, 949, 942, 935, 928, 920, 912, 904, 895, 886, 877, 868, 858, 848, 838, 828, 817, 806, 795, 784, 772,
        760, 748, 736, 724, 711, 698, 685, 671, 658, 644, 630, 616, 601, 587, 572, 557, 542, 527, 511, 496, 480, 464, 448, 432, 416, 400, 383, 366, 350,
        333, 316, 299, 282, 265, 247, 230, 212, 195, 177, 160, 142, 124, 107, 89, 71, 53, 35, 17, 0, -17, -35, -53, -71, -89, -107, -124, -142, -160, -177,
        -195, -212, -230, -247, -265, -282, -299, -316, -333, -350, -366, -383, -400, -416, -432, -448, -464, -480, -496, -512, -527, -542, -557, -572, -587,
        -601, -616, -630, -644, -658, -671, -685, -698, -711, -724, -736, -748, -760, -772, -784, -795, -806, -817, -828, -838, -848, -858, -868, -877, -886,
        -895, -904, -912, -920, -928, -935, -942, -949, -955, -962, -968, -973, -979, -984, -989, -993, -997, -1001, -1005, -1008, -1011, -1014, -1016, -1018,
        -1020, -1021, -1022, -1023, -1023, -1024, -1023, -1023, -1022, -1021, -1020, -1018, -1016, -1014, -1011, -1008, -1005, -1001, -997, -993, -989, -984, -979,
        -973, -968, -962, -955, -949, -942, -935, -928, -920, -912, -904, -895, -886, -877, -868, -858, -848, -838, -828, -817, -806, -795, -784, -772,
        -760, -748, -736, -724, -711, -698, -685, -671, -658, -644, -630, -616, -601, -587, -572, -557, -542, -527, -512, -496, -480, -464, -448, -432, -416,
        -400, -383, -366, -350, -333, -316, -299, -282, -265, -247, -230, -212, -195, -177, -160, -142, -124, -107, -89, -71, -53, -35, -17, 0};
    private int cosInt, sinInt;

    Image rotate_Img(Image img, int alpha) {
        w = img.getWidth();
        h = img.getHeight();
        ARGB = new int[w * h];
        ip = Math.sqrt(w * w + h * h);
        grad = (int) (math.asin(h / ip) * 180 / Math.PI);
        img.getRGB(ARGB, 0, w, 0, 0, w, h);
        wDiv2minusr = w / 2;
        hDiv2minusr = h / 2;
        wrPe2x10 = wDiv2minusr << 7;
        hrPe2x10 = hDiv2minusr << 7;
        time1 = System.currentTimeMillis();
        cosInt = cosInts[alpha];
        sinInt = sinInts[alpha];
        wr = rotWidth(alpha) >> 10;
        hr = rotHeight(alpha) >> 10;
        wr++;
        hr++;
        ARGB_T = new int[(wr) * (hr)];
        minusWDiv2 = -wr / 2;
        minusHDiv2 = -hr / 2;
        jvalue1 = new int[wr];
        jvalue2 = new int[wr];
        for (int i = 0; i < wr; i++) {
            jvalue1[i] = ((cosInt * (i + minusWDiv2)) >> 3) + wrPe2x10;
            jvalue2[i] = ((-sinInt * (i + minusWDiv2)) >> 3) + hrPe2x10;
        }
        for (int i = 0; i < hr; i++) {
            sinRPlusH = (sinInt * (i + minusHDiv2)) >> 3;
            cosRPlusH = (cosInt * (i + minusHDiv2)) >> 3;
            iOriWr = i * wr;
            for (int j = 0; j < wr; j++) {
                temp1 = (jvalue1[j] + sinRPlusH) >> 7;
                temp2 = (cosRPlusH + jvalue2[j]) >> 7;
                if (temp1 >= 0 && temp2 >= 0 && temp1 < w && temp2 < h) {
                    ARGB_T[iOriWr + j] = ARGB[temp2 * w + temp1];
                }
            }
        }
        time2 = System.currentTimeMillis();
        time = time2 - time1;
        return Image.createRGBImage(ARGB_T, wr, hr, true);
    }

    private int rotWidth(int alpha) {
        gradMinAlpha = grad - alpha;
        gradPlusAlpha = grad + alpha;
        while (gradMinAlpha < 0) {
            gradMinAlpha += 360;
        }
        while (gradPlusAlpha > 360) {
            gradPlusAlpha -= 360;
        }
        if (alpha >= 0 && alpha <= 90) {
            return (int) (cosInts[gradMinAlpha] * ip);
        } else if (alpha >= 180 && alpha <= 270) {
            return -(int) (cosInts[gradMinAlpha] * ip);
        } else if (alpha > 270) {
            return (int) (cosInts[gradPlusAlpha] * ip);
        } else {
            return -(int) (cosInts[gradPlusAlpha] * ip);
        }
    }

    private int rotHeight(int alpha) {
        if (alpha >= 0 && alpha <= 90) {
            return (int) (sinInts[gradPlusAlpha] * ip);
        } else if (alpha >= 180 && alpha <= 270) {
            return -(int) (sinInts[gradPlusAlpha] * ip);
        } else if (alpha > 270) {
            return (int) (sinInts[gradMinAlpha] * ip);
        } else {
            return -(int) (sinInts[gradMinAlpha] * ip);
        }
    }

    /* static Image resizeImage(Image src, int width, int height) {
    if (width != 0 && height != 0) {    srcWidth = src.getWidth();
    srcHeight = src.getHeight();   Image tmp = Image.createImage(width, srcHeight);
    Graphics g = tmp.getGraphics();   int ratio = (srcWidth << 16) / width;
    int pos = ratio / 2;   //Redimensionare orizontala
    for (int x = 0; x < width; x++) {    g.setClip(x, 0, 1, srcHeight);
    g.drawImage(src, x - (pos >> 16), 0, Graphics.LEFT | Graphics.TOP);    pos += ratio;   }
    Image resizedImage = Image.createImage(width, height);    g = resizedImage.getGraphics();
    ratio = (srcHeight << 16) / height;    pos = ratio / 2;    //Redimensionare verticala
    for (int y = 0; y < height; y++) {    g.setClip(0, y, width, 1);
    g.drawImage(tmp, 0, y - (pos >> 16), Graphics.LEFT | Graphics.TOP);    pos += ratio;   }
    return resizedImage;     }   return Image.createImage(1, 1);   }*/
}
