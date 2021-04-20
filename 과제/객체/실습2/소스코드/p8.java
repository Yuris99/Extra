import java.util.Scanner;

public class p8 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("정수 몇개?");
        int n = scanner.nextInt();
        int [] arr = new int[n+1];
        boolean [] check = new boolean[101];
        for(int i = 0; i <= n; i++) {
            while(check[arr[i]])
                arr[i] = (int)(Math.random()*100 + 1);
            check[arr[i]] = true;
        }
        for(int i = 1; i <= n; i++) {
            System.out.print(arr[i] + " ");
            if(i% 10 == 0) System.out.println();
        }

        scanner.close();
    }
}
