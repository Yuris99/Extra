import java.util.Scanner;

public class p12 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("연산>>");

        double a = scanner.nextDouble();
        String sign = scanner.next();
        double b = scanner.nextDouble();

        double result = 0;
        boolean zero = false;
        if(sign.equals("+"))
            result = a + b;
        else if(sign.equals("-"))
            result = a - b;
        else if(sign.equals("*"))
            result = a * b;
        else if(sign.equals("/")) {
            if(b == 0)
                zero = true;
            else
                result = a / b;
        }


        if(zero)
            System.out.println("0으로 나눌 수 없습니다.");
        else
            System.out.println(a+sign+b+"의 계산 결과는 "+result);

        scanner.close();
    }
}
