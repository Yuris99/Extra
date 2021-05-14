package L4P8;

import java.util.Scanner;

public class PhoneBook {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("인원수>>");
        int count = scanner.nextInt();
        Phone td[] = new Phone[count];
        String name, tel;
        for(int i = 0; i < count; i++) {
            System.out.print("이름과 전화번호(이름과 번호는 빈 칸없이 입력)>>");
            name = scanner.next();
            tel = scanner.next();
            td[i] = new Phone(name, tel);
        }
        System.out.println("저장되었습니다...");
        String target, targetel = null;
        while(true) {
            System.out.print("검색할 이름>>");
            target = scanner.next();
            if(target.equals("그만"))
                break;
            for(int i = 0; i < count; i++) {
                if(td[i].compare(target)) {
                    targetel = td[i].getTel();
                    break;
                }
            }
            if(targetel != null)
                System.out.println(target + "의 번호는 " + targetel + " 입니다.");
            else
                System.out.println(target + "이 없습니다.");
            targetel = null;
        }
        scanner.close();
    }
}
