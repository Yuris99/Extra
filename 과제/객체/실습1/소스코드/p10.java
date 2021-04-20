import java.util.Scanner;

public class p10 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("첫번째 원의 중심과 반지름 입력>>");
        int x1 = scanner.nextInt();
        int y1 = scanner.nextInt();
        int r1 = scanner.nextInt();
        System.out.print("첫번째 원의 중심과 반지름 입력>>");
        int x2 = scanner.nextInt();
        int y2 = scanner.nextInt();
        int r2 = scanner.nextInt();
        int xdis = x2-x1;
        if(xdis < 0) xdis *= -1;
        int ydis = y2-y1;
        if(ydis < 0) ydis *= -1;

        int rdis = r1 + r2;

        if(((xdis * xdis) + (ydis * ydis)) <= (rdis * rdis))
            System.out.println("두 원은 서로 겹친다.");
        else
            System.out.println("두 원은 서로 겹치지 않는다.");

        scanner.close();
    }
}
