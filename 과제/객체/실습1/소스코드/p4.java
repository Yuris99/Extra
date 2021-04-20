import java.util.Scanner;

public class p4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("정수 3개 입력>>");
        int n1 = scanner.nextInt();
        int n2 = scanner.nextInt();
        int n3 = scanner.nextInt();
        int mid = n1;
        if(((n2 >= n1) && (n2 <= n3)) || ((n2 <= n1) && n2 >= n3))
            mid = n2;
        else if(((n3 >= n1) && (n3 <= n2)) || ((n3 <= n1) && n3 >= n2))
            mid = n3;

        System.out.println("중간 값은 " + mid);
        scanner.close();
    }
}
