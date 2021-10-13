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
                if (num < low || num > high) {
                    System.out.printf("invalid. please input number from %d--%d\n", low, high);
                    continue;
                }
                return num;
            }
        }
    }

    public static double scanDouble(double low, double high) {
        while (true) {
            if (sc.hasNext()) {
                double num = sc.nextDouble();
                if (num < low || num > high) {
                    System.out.printf("invalid. please input number from %f--%f\n", low, high);
                    continue;
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
