package L4P12;

import java.util.Scanner;

public class ResApp {

    public static void printErr() { System.out.println("잘못된 번호입니다."); }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Concert concert = new Concert();
        System.out.println("명품 콘서트홀 예약 시스템입니다.");
        int select, sit, num;
        String name;
        while(true) {
            System.out.print("예약:1, 조회:2, 취소:3, 끝내기:4>>");
            select = scanner.nextInt();
            switch(select) {
                case 1: // 예약
                    System.out.print("좌석구분 S(1), A(2), B(3)>>");
                    sit = scanner.nextInt();
                    if(sit < 1 || sit > 3) {
                        printErr();
                        break;
                    }
                    concert.printSit(sit);
                    System.out.print("이름>>");
                    name = scanner.next();
                    System.out.print("번호>>");
                    num = scanner.nextInt();
                    if(num < 1 || num > 10) {
                        printErr();
                        break;
                    }
                    concert.reservation(sit, num, name);
                    break;
                case 2: // 조회
                    concert.printSit(1);
                    concert.printSit(2);
                    concert.printSit(3);
                    System.out.println("<<<조회를 완료하였습니다.>>>");
                    break;
                case 3: // 취소
                    System.out.print("좌석 S:1, A:2, B:3>>");
                    sit = scanner.nextInt();
                    if(sit < 1 || sit > 3) {
                        printErr();
                        break;
                    }
                    concert.printSit(sit);
                    System.out.print("이름>>");
                    name = scanner.next();
                    concert.cancel(sit, name);
                    break;
                case 4: // 끝내기
                    break;
                default: // 오류
                    printErr();
                    break;
            }
            if(select == 4) break;
        }
        scanner.close();
    }
}
