import java.util.Scanner;

/**
 * @className: ScanUtils
 * @description: utils class to scan user input
 * @author: Yan Tong
 **/
public class ScanUtils {
    private static Scanner sc = new Scanner(System.in);

    public static int scanInt(int low, int high) {
        while (true) {
            if (sc.hasNext()) {
                int num = sc.nextInt();
                if (low == Integer.MIN_VALUE && high == Integer.MAX_VALUE) {
                    return num;
                }
                if (low == Integer.MIN_VALUE) {
                    if (num > high) {
                        System.out.printf("invalid. please input number <= %d:\n", high);
                        continue;
                    }
                } else if (high == Integer.MAX_VALUE) {
                    if (num < low) {
                        System.out.printf("invalid. please input number >= %d:\n", low);
                        continue;
                    }
                } else {
                    if (num < low || num > high) {
                        System.out.printf("invalid. please input number from %d--%d\n", low, high);
                        continue;
                    }
                }
                return num;
            }
        }
    }

    public static double scanDouble(double low, double high) {
        while (true) {
            if (sc.hasNext()) {
                double num = sc.nextDouble();
                if (low == Double.MIN_VALUE && high == Double.MAX_VALUE) {
                    return num;
                }
                if (low == Double.MIN_VALUE) {
                    if (num > high) {
                        System.out.printf("invalid. please input number <= %f:\n", high);
                        continue;
                    }
                } else if (high == Double.MAX_VALUE) {
                    if (num < low) {
                        System.out.printf("invalid. please input number >= %f:\n", low);
                        continue;
                    }
                } else {
                    if (num < low || num > high) {
                        System.out.printf("invalid. please input number from %f--%f\n", low, high);
                        continue;
                    }
                }
                return num;
            }
        }
    }

    public static String scanString() {
        while (true) {
            if (sc.hasNext()) {
                String s = sc.next();
                return s;
            }
        }
    }

}
