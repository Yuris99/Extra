import java.util.Scanner;

public class p6 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int [] unit = {50000, 10000, 1000, 500, 100, 50, 10, 1};
        int [] result = {0, 0, 0, 0, 0, 0, 0, 0};
        System.out.print("금액을 입력하시오>>");
        int money = scanner.nextInt();

        for(int i = 0; i < unit.length; i++) {
            result[i] = money / unit[i];
            money %= unit[i];
        }
        for(int i = 0; i < unit.length; i++) {
            if(result[i] == 0)
                continue;
            System.out.println(unit[i] + "원 짜리 : " + result[i] + "개");
        }

        scanner.close();
    }
}
