import java.util.Scanner;

public class p8 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("x1, y1, x2, y2를 입력하시오>>");
        int x1 = scanner.nextInt();
        int y1 = scanner.nextInt();
        int x2 = scanner.nextInt();
        int y2 = scanner.nextInt();

        if(((x1 <= 200) && (x2 >= 100)) && ((y1 <= 200) && (y2 >= 100)))
            System.out.println("오브젝트와 충돌!");
        else
            System.out.println("오브젝트와 충돌하지 않음!");

        scanner.close();
    }
}
