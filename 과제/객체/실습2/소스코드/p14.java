import java.util.Scanner;

public class p14 {
    public static void main(String[] args) {
        String course [] = {"Java", "C++", "HTML5", "컴퓨터구조", "안드로이드"};
        int score [] = {95, 88, 76, 62, 55};

        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.print("과목 이름>>");
            String learn = scanner.next();
            int n = -1;
            if(learn.equals("그만"))
                break;
            for(int i = 0; i < course.length; i++) {
                if(learn.equals(course[i])) {
                    n = i;
                    break;
                }
            }
            if(n != -1)
                System.out.println(course[n]+"의 점수는 "+score[n]);
            else
                System.out.println("없는 과목입니다.");
        }
        scanner.close();

    }
}
