/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GProduction;

import java.util.Random;

/**
 *
 * @author Blondu
 */
public class StaticDATA {

    public static int BlockDimensions = 64;
    //static int colmap[][];
    static int carposition[][];
    static Random r = new Random();
    private static int temp1, temp2, temp3;

    static boolean cedeazaPrioritateaLeft(int course, int x, int y, int type, int directionCode) {
        temp1 = 0;
        temp2 = 0;
        temp3 = 0;
        switch (course) {
            case 1:
                switch (type) {
                    case 3:
                        try {
                            temp1 += carposition[x - 1][y];
                            temp1 += carposition[x - 2][y];
                            //temp1 += carposition[x - 3][y];
                        } catch (Exception e) {
                        }
                        try {
                            temp3 += carposition[x + 1][y];
                            temp3 += carposition[x + 2][y];
                            //temp3 += carposition[x + 3][y];
                        } catch (Exception e) {
                        }
                        try {
                            temp2 += carposition[x][y] - directionCode;
                            temp1 += temp2;
                            temp3 += temp2;
                        } catch (Exception e) {
                        }
                        if (transformation2(temp1) > 0 || transformation4(temp3) > 0) {
                            return true;
                        }
                        break;
                    case 9:
                        /*try {
                        temp1 += carposition[x - 1][y];
                        } catch (Exception e) {
                        }*/
                        try {
                            temp1 += carposition[x][y - 1];
                            temp1 += carposition[x][y - 2];
                            //temp1 += carposition[x][y - 3];
                        } catch (Exception e) {
                        }
                        try {
                            temp1 += carposition[x][y] - directionCode;
                        } catch (Exception e) {
                        }
                        if (transformation3(temp1) > 0) {
                            return true;
                        }
                        break;
                }
                break;
            case 2:
                switch (type) {
                    case 7:
                        try {
                            temp1 += carposition[x + 1][y];
                            temp1 += carposition[x + 2][y];
                            //temp1 += carposition[x + 3][y];
                        } catch (Exception e) {
                        }
                        /*try {
                        temp1 += carposition[x][y-1];
                        } catch (Exception e) {
                        }*/
                        try {
                            temp1 += carposition[x][y] - directionCode;
                        } catch (Exception e) {
                        }
                        if (transformation4(temp1) > 0) {
                            return true;
                        }
                        break;
                    case 9:
                        try {
                            temp3 += carposition[x][y - 1];
                            temp3 += carposition[x][y - 2];
                            //temp3 += carposition[x][y - 3];
                        } catch (Exception e) {
                        }
                        try {
                            temp1 += carposition[x][y + 1];
                            temp1 += carposition[x][y + 2];
                            //temp1 += carposition[x][y + 3];
                        } catch (Exception e) {
                        }
                        try {
                            temp2 += carposition[x][y] - directionCode;
                            temp1 += temp2;
                            temp3 += temp2;
                        } catch (Exception e) {
                        }
                        if (transformation1(temp1) > 0 || transformation3(temp3) > 0) {
                            return true;
                        }
                        break;
                }
                break;
            case 3:
                switch (type) {
                    case 6:
                        /*try {
                        temp1 += carposition[x][y + 1];
                        } catch (Exception e) {
                        }*/
                        try {
                            temp1 += carposition[x + 1][y];
                            temp1 += carposition[x + 2][y];
                            //temp1 += carposition[x + 3][y];
                        } catch (Exception e) {
                        }
                        try {
                            temp1 += carposition[x][y] - directionCode;
                        } catch (Exception e) {
                        }
                        if (transformation1(temp1) > 0) {
                            return true;
                        }
                        break;
                    case 7:
                        try {
                            temp1 += carposition[x - 1][y];
                            temp1 += carposition[x - 2][y];
                            //temp1 += carposition[x - 3][y];
                        } catch (Exception e) {
                        }
                        try {
                            temp3 += carposition[x + 1][y];
                            temp3 += carposition[x + 2][y];
                            //temp3 += carposition[x + 3][y];
                        } catch (Exception e) {
                        }
                        try {
                            temp2 += carposition[x][y] - directionCode;
                            temp1 += temp2;
                            temp3 += temp2;
                        } catch (Exception e) {
                        }
                        if (transformation2(temp1) > 0 || transformation4(temp3) > 0) {
                            return true;
                        }
                        break;
                }
                break;
            case 4:
                switch (type) {
                    case 3:
                        /*try {
                        temp1 += carposition[x][y + 1];
                        } catch (Exception e) {
                        }*/
                        try {
                            temp1 += carposition[x - 1][y];
                            temp1 += carposition[x - 2][y];
                            //temp1 += carposition[x - 3][y];
                        } catch (Exception e) {
                        }
                        try {
                            temp1 += carposition[x][y] - directionCode;
                        } catch (Exception e) {
                        }
                        if (transformation2(temp1) > 0) {
                            return true;
                        }
                        break;
                    case 6:
                        try {
                            temp1 += carposition[x][y + 1];
                            temp1 += carposition[x][y + 2];
                            //temp1 += carposition[x][y + 3];
                        } catch (Exception e) {
                        }
                        try {
                            temp3 += carposition[x][y - 1];
                            temp3 += carposition[x][y - 2];
                            //temp3 += carposition[x][y - 3];
                        } catch (Exception e) {
                        }
                        try {
                            temp2 += carposition[x][y] - directionCode;
                            temp1 += temp2;
                            temp3 += temp2;
                        } catch (Exception e) {
                        }
                        if (transformation1(temp1) > 0 || transformation3(temp3) > 0) {
                            return true;
                        }

                        break;
                }
                break;
        }
        return false;
    }

    static boolean cedeazaPrioritateaRight(int course, int x, int y, int type, int directionCode) {
        temp1 = 0;
        temp2 = 0;
        switch (course) {
            case 1:
                if (type == 3) {
                    try {
                        temp1 += carposition[x - 1][y];
                    } catch (Exception e) {
                    }
                    try {
                        temp2 += carposition[x][y] - directionCode;
                        temp1 += temp2;
                    } catch (Exception e) {
                    }
                    if (transformation2(temp1) > 0) {
                        return true;
                    }
                }
                break;
            case 2:
                if (type == 9) {
                    try {
                        temp1 += carposition[x][y - 1];
                    } catch (Exception e) {
                    }
                    try {
                        temp2 += carposition[x][y] - directionCode;
                        temp1 += temp2;
                    } catch (Exception e) {
                    }
                    if (transformation3(temp1) > 0) {
                        return true;
                    }
                }
                break;
            case 3:
                if (type == 7) {
                    try {
                        temp1 += carposition[x + 1][y];
                    } catch (Exception e) {
                    }
                    try {
                        temp2 += carposition[x][y] - directionCode;
                        temp1 += temp2;
                    } catch (Exception e) {
                    }
                    if (transformation4(temp1) > 0) {
                        return true;
                    }
                }
                break;
            case 4:
                if (type == 6) {
                    try {
                        temp1 += carposition[x][y + 1];
                    } catch (Exception e) {
                    }
                    try {
                        temp2 += carposition[x][y] - directionCode;
                        temp1 += temp2;
                    } catch (Exception e) {
                    }
                    if (transformation1(temp1) > 0) {
                        return true;
                    }
                }
                break;
        }
        return false;
    }

    static boolean cedeazaPrioritateaDeDreapta(int course, int x, int y, int type, int directionCode) {
        temp1 = 0;
        temp2 = 0;
        if (type == 8) {
            switch (course) {
                case 1:
                    try {
                        temp1 += carposition[x + 1][y];
                        temp1 += carposition[x + 2][y];
                    } catch (Exception e) {
                    }
                    try {
                        temp1 += carposition[x][y] - directionCode;
                    } catch (Exception e) {
                    }
                    if (transformation4(temp1) > 0) {
                        return true;
                    }
                    break;
                case 2:
                    try {
                        temp1 += carposition[x][y + 1];
                        temp1 += carposition[x][y + 2];
                    } catch (Exception e) {
                    }
                    try {
                        temp1 += carposition[x][y] - directionCode;
                    } catch (Exception e) {
                    }
                    if (transformation1(temp1) > 0) {
                        return true;
                    }
                    break;
                case 3:
                    try {
                        temp1 += carposition[x - 1][y];
                        temp1 += carposition[x - 2][y];
                    } catch (Exception e) {
                    }
                    try {
                        temp1 += carposition[x][y] - directionCode;
                    } catch (Exception e) {
                    }
                    if (transformation2(temp1) > 0) {
                        return true;
                    }
                    break;
                case 4:
                    try {
                        temp1 += carposition[x][y - 1];
                        temp1 += carposition[x][y - 2];
                    } catch (Exception e) {
                    }
                    try {
                        temp1 += carposition[x][y] - directionCode;
                    } catch (Exception e) {
                    }
                    if (transformation3(temp1) > 0) {
                        return true;
                    }
                    break;
            }
        }
        return false;
    }

    private static int transformation1(int nr) {
        return nr % 256;
    }

    private static int transformation2(int nr) {
        return (nr >> 8) % 256;
    }

    private static int transformation3(int nr) {
        return (nr >> 16) % 256;
    }

    private static int transformation4(int nr) {
        return (nr >> 24);
    }

    public static void placeCars(CarPlayer car, SmartAI[] bots) {
        car.setCourse(0);
        car.setX((Maps.startblockX * 64 + 10) << 10);
        car.setY((Maps.startblockY * 64 + SmartAI.ROAD) << 10);
        car.keyReleased(Ecran.KEY_NUM4);
        car.keyReleased(Ecran.KEY_NUM6);
        carposition[Maps.startblockX][Maps.startblockY] += (1 << 8);
        int k = 0;
        for (int i = 0; i < Ecran.NOBy; i++) {
            for (int j = 0; j < Ecran.NOBx; j++) {
                //System.out.println("i=" + i + " j=" + j + " k=" + k);
                if (k < bots.length) {
                    if (Maps.map2d[j][i] == 1) {
                        bots[k] = new SmartAI(bots[k]);
                        bots[k].car.setCourse(0);
                        bots[k].car.setX((j * 64 + 10) << 10);
                        bots[k].car.setY((i * 64 + SmartAI.ROAD) << 10);
                        bots[k].setvals(1);
                        bots[k].car.keyReleased(Ecran.KEY_NUM4);
                        bots[k].car.keyReleased(Ecran.KEY_NUM6);
                        carposition[j][i] += (1 << 8);
                        k++;
                    } else if (Maps.map2d[j][i] == 2) {
                        bots[k] = new SmartAI(bots[k]);
                        bots[k].car.setCourse(90);
                        bots[k].car.setX((j * 64 + SmartAI.ROAD2) << 10);
                        bots[k].car.setY((i * 64 + 10) << 10);
                        bots[k].setvals(2);
                        bots[k].car.keyReleased(Ecran.KEY_NUM4);
                        bots[k].car.keyReleased(Ecran.KEY_NUM6);
                        carposition[j][i] += (1 << 16);
                        k++;
                    }
                } else {
                    i = Ecran.NOBy;
                    j = Ecran.NOBx;
                }
            }
        }
    }
}
