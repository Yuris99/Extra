package L4P10;

import java.util.Scanner;

public class DicApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Dictionary dic = new Dictionary();
        System.out.println("한영 단어 검색 프로그램입니다.");
        String word;
        while(true) {
            System.out.print("한글 단어?");
            word = scanner.next();
            if(word.equals("그만"))
                break;
            String result = dic.kor2Eng(word);
            if(result == null)
                System.out.println(word+"는 저의 사전에 없습니다.");
            else
                System.out.println(word+"은 "+result);
        }
        scanner.close();
    }
}
