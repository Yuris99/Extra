import java.util.Scanner;

public class p4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("소문자 알파벳 하나를 입력하시오>>");
        char n = scanner.next().charAt(0);
        for(char i = n; i >= 'a'; i--) {
            for(char j = 'a'; j <= i; j++)
                System.out.print(j);
            System.out.println();
        }
        scanner.close();
    }
}
