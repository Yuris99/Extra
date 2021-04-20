import java.util.Scanner;

public class p16 {
    public static void main(String[] args) {
        String rsp[] = {"가위", "바위", "보"};

        Scanner scanner = new Scanner(System.in);
        System.out.println("컴퓨터와 가위 바위 보 게임을 합니다.");
        while(true) {
            System.out.print("가위 바위 보!>>");
            String player = scanner.next();

            if(player.equals("그만"))
                break;
            int com = (int)(Math.random()*3);
            if(rsp[com].equals(player))
                System.out.println("사용자 = " + player + " , 컴퓨터 = " + rsp[com] + ", 비겼습니다.");
            else if(rsp[(com+1)%3].equals(player))
                System.out.println("사용자 = " + player + " , 컴퓨터 = " + rsp[com] + ", 사용자가 이겼습니다.");
            else if(rsp[(com+2)%3].equals(player))
                System.out.println("사용자 = " + player + " , 컴퓨터 = " + rsp[com] + ", 컴퓨터가 이겼습니다.");
        }
        System.out.println("게임을 종료합니다...");
        scanner.close();
    }
}
