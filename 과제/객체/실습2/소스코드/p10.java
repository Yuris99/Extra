
public class p10 {
    public static void main(String[] args) {

        int [][] arr = new int[4][4];
        for(int i = 0; i < 4; i++)
            for(int j = 0; j < 4; j++)
                arr[i][j] = 0;
        for(int i = 0; i < 10; i++) {
            int temp = (int)(Math.random()*10 + 1);
            int rand = (int)(Math.random()*16);
            while(arr[rand/4][rand%4] != 0)
                rand = (int)(Math.random()*16);
            arr[rand/4][rand%4] = temp;
        }
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                System.out.print(arr[i][j]);
                System.out.print('\t');
            }
            System.out.println();
        }
    }
}
