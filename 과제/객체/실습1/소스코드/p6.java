import java.util.Scanner;

public class p6 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("1~99 사이의 정수를 입력하시오>>");
        int n = scanner.nextInt();
        int cnt = 0;
        //십의자리수
        if((n/10 == 3 || n/10 == 6 || n/10 == 9))
            cnt++;
        //일의자리수
        if((n%10 == 3 || n%10 == 6 || n%10 == 9))
            cnt++;
        
        //결과 출력
        if(cnt == 0)
            System.out.println(n);
        else if(cnt == 1)
            System.out.println("박수짝");
        else if(cnt == 2)
            System.out.println("박수짝짝");
            
        
        scanner.close();
    }
}
