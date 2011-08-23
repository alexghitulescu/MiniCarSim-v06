/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GProduction;

/**
 *
 * @author Blondu
 */
public class math {

    static final double sq2p1 = 2.414213562373095048802e0;
    static final double sq2m1 = .414213562373095048802e0;
    static final double p4 = .161536412982230228262e2;
    static final double p3 = .26842548195503973794141e3;
    static final double p2 = .11530293515404850115428136e4;
    static final double p1 = .178040631643319697105464587e4;
    static final double p0 = .89678597403663861959987488e3;
    static final double q4 = .5895697050844462222791e2;
    static final double q3 = .536265374031215315104235e3;
    static final double q2 = .16667838148816337184521798e4;
    static final double q1 = .207933497444540981287275926e4;
    static final double q0 = .89678597403663861962481162e3;
    static final double PIO2 = 1.5707963267948966135E0;
    static final double nan = (0.0 / 0.0);
    // reduce

    private static double mxatan(double arg) {
        double argsq, value;

        argsq = arg * arg;
        value = ((((p4 * argsq + p3) * argsq + p2) * argsq + p1) * argsq + p0);
        value = value / (((((argsq + q4) * argsq + q3) * argsq + q2) * argsq + q1) * argsq + q0);
        return value * arg;
    }

    // reduce
    private static double msatan(double arg) {
        if (arg < sq2m1) {
            return mxatan(arg);
        }
        if (arg > sq2p1) {
            return PIO2 - mxatan(1 / arg);
        }
        return PIO2 / 2 + mxatan((arg - 1) / (arg + 1));
    }

    // implementation of atan
    public static double atan(double arg) {
        if (arg > 0) {
            return msatan(arg);
        }
        return -msatan(-arg);
    }

    // implementation of atan2
    public static double atan2(double arg1, double arg2) {
        if (arg1 + arg2 == arg1) {
            if (arg1 >= 0) {
                return PIO2;
            }
            return -PIO2;
        }
        arg1 = atan(arg1 / arg2);
        if (arg2 < 0) {
            if (arg1 <= 0) {
                return arg1 + Math.PI;
            }
            return arg1 - Math.PI;
        }
        return arg1;

    }

    // implementation of asin
    public static double asin(double arg) {
        double temp;
        int sign;

        sign = 0;
        if (arg < 0) {
            arg = -arg;
            sign++;
        }
        if (arg > 1) {
            return nan;
        }
        temp = Math.sqrt(1 - arg * arg);
        if (arg > 0.7) {
            temp = PIO2 - atan(temp / arg);
        } else {
            temp = atan(arg / temp);
        }
        if (sign > 0) {
            temp = -temp;
        }
        return temp;
    }

    // implementation of acos
    public static double acos(double arg) {
        if (arg > 1 || arg < -1) {
            return nan;
        }
        return PIO2 - asin(arg);
    }

    static double degreeToRadian(double d) {
        return (d * Math.PI) / 180;
    }

    static double radianToDegree(double r) {
        return (r * 180) / Math.PI;
    }

    static int courseCorrection(int c) {
        if (c < 0) {
            return 360 + (c) % 360;
        }
        return (c) % 360;
    }

    static int Cadran(int d) {
        int temp;
        temp = courseCorrection(d);
        if (temp < 90) {
            return 1;
        } else if (temp < 180) {
            return 2;
        } else if (temp < 270) {
            return 3;
        } else {
            return 4;
        }
    }

    static int CadranII(int d) {
        int temp;
        temp = courseCorrection(d);
        if (temp < 45) {
            return 1;
        } else if (temp < 135) {
            return 2;
        } else if (temp < 225) {
            return 3;
        } else if (temp < 315) {
            return 4;
        } else {
            return 1;
        }
    }

    static int CadranIIx(int d) {
        int temp;
        temp = courseCorrection(d);
        if (temp < 180) {
            return 1;
        } else {
            return -1;
        }
    }

    static int CadranIIy(int d) {
        int temp;
        temp = courseCorrection(d);
        if (temp < 90) {
            return 1;
        } else {
            return -1;
        }
    }

    public static int floor(double a) {
        double x;
        x = a - (int) a;
        if (x >= 0.5) {
            return (int) a + 1;
        } else {
            return (int) a;
        }
    }
}
