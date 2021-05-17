package L4P12;

public class Concert {
    public final static char Sit[] = {' ', 'S', 'A', 'B'};
    private String sitState[][] = new String[4][10];

    public Concert() {
        for(int i = 0; i < 10; i++) {
            sitState[1][i] = "---";
            sitState[2][i] = "---";
            sitState[3][i] = "---";
        }
    }

    public void printSit(int grade) {
        System.out.print(Sit[grade] + ">> ");
        for(int i = 0; i < 10; i++)
            System.out.print(sitState[grade][i] + " ");
        System.out.println();
    }

    public void reservation (int grade, int num, String name) { sitState[grade][num-1] = name; }
    public void cancel (int grade, String name) {
        for(int i = 0; i < 10; i++) {
            if (sitState[grade][i].equals(name)) {
                sitState[grade][i] = "---";
                return;
            }
        }
        System.out.println("<<<이름을 찾을 수 없습니다!>>>");
    }

}
