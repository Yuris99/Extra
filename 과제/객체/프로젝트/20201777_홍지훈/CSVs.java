package csv;

import java.io.*;
import java.util.Random;
import java.util.Vector;

public class CSVs {
    /**
     * @param isFirstLineHeader csv 파일의 첫 라인을 헤더(타이틀)로 처리할까요?
     */
    public static Table createTable(File csv, boolean isFirstLineHeader) throws FileNotFoundException {
        TableImpl table = new TableImpl();
        try{
            BufferedReader br = new BufferedReader(new FileReader(csv));

            int line = 0;
            int col = 0;
            Column c = null;

            String text = "";
            if(isFirstLineHeader) {
                text = br.readLine();
                String[] emp = text.split(",");
                for(int i = 0; i < emp.length; i++) {
                    c = table.getColumn(i);
                    c.setValue(-1, emp[i]);
                }
            }
            while((text = br.readLine()) != null) {
                String[] emp = text.split( ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                for(int i = 0; i < emp.length; i++) {
                    boolean doublecheck = false;
                    for(int j = 0; j < emp[i].length(); j++) {
                        if(emp[i].charAt(j) == '.') {
                            doublecheck = true;
                            break;
                        }
                    }
                    c = table.getColumn(i);
                    try {
                        if(doublecheck) {
                            double temp = Double.parseDouble(emp[i]);
                            c.setValue(line, temp);
                        } else {
                            int temp = Integer.parseInt(emp[i]);
                            c.setValue(line, temp);
                        }
                    }
                    catch (NumberFormatException e) {
                        c.setValue(line, emp[i]);
                    }
                }
                line++;
            }


        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return table;
    }

    /**
     * @return 새로운 Table 객체를 반환한다. 즉, 첫 번째 매개변수 Table은 변경되지 않는다.
     */
    public static Table sort(Table table, int byIndexOfColumn, boolean isAscending, boolean isNullFirst) {
        TableImpl t = new TableImpl();
        for (int i = 0; i < table.getColumnCount(); i++) {
            t.getColumn(i).setValue(-1, table.getColumn(i).getHeader());
            for (int j = 0; j < table.getRowCount(); j++) {
                boolean doublecheck = false;
                for(int k = 0; k < table.getColumn(i).getValue(j).length(); k++) {
                    if(table.getColumn(i).getValue(j).charAt(k) == '.') {
                        doublecheck = true;
                        break;
                    }
                }
                Column c = t.getColumn(i);
                try {
                    if(doublecheck) {
                        double temp = Double.parseDouble(table.getColumn(i).getValue(j));
                        c.setValue(j, temp);
                    } else {
                        int temp = Integer.parseInt(table.getColumn(i).getValue(j));
                        c.setValue(j, temp);
                    }
                }
                catch (NumberFormatException e) {
                    c.setValue(j, table.getColumn(i).getValue(j));
                }
            }
        }
        int ncnt = 0;
        if(isNullFirst) {
            for(int i = 0; i < table.getRowCount(); i++) {
                if(t.getColumn(byIndexOfColumn).getValue(i).equals("null")) {
                    changeField(t, ncnt, i);
                    ncnt ++;
                }
            }
        }
        for(int i = ncnt; i < t.getRowCount()-1; i++) {
            int index = i;
            for(int j = i+1; j < t.getRowCount(); j++) {
                try {
                    Double a = Double.parseDouble(t.getColumn(byIndexOfColumn).getValue(j));
                    Double b = Double.parseDouble(t.getColumn(byIndexOfColumn).getValue(index));
                    if(isAscending) {
                        if(a < b)
                            index = j;
                    }
                    else {
                        if(a > b)
                            index = j;
                    }
                } catch(NumberFormatException e){
                    if(isAscending && t.getColumn(byIndexOfColumn).getValue(j).compareTo(t.getColumn(byIndexOfColumn).getValue(index)) < 0) {
                        index = j;
                    }
                    if(!isAscending && t.getColumn(byIndexOfColumn).getValue(j).compareTo(t.getColumn(byIndexOfColumn).getValue(index)) > 0) {
                        index = j;
                    }
                }
            }
            changeField(t, i, index);
        }
        return t;
    }
    private static void changeField(Table t, int a, int b) {
        Table temp = t.selectRowsAt(a);
        for(int i = 0; i < t.getColumnCount(); i++) {
            t.getColumn(i).setValue(a, t.getColumn(i).getValue(b));
            t.getColumn(i).setValue(b, temp.getColumn(i).getValue(0));
        }
    }

    /**
     * @return 새로운 Table 객체를 반환한다. 즉, 첫 번째 매개변수 Table은 변경되지 않는다.
     */
    public static Table shuffle(Table table) {
        TableImpl t = new TableImpl();
        for (int i = 0; i < table.getColumnCount(); i++) {
            t.getColumn(i).setValue(-1, table.getColumn(i).getHeader());
            for (int j = 0; j < table.getRowCount(); j++) {
                    boolean doublecheck = false;
                    for(int k = 0; k < table.getColumn(i).getValue(j).length(); k++) {
                        if(table.getColumn(i).getValue(j).charAt(k) == '.') {
                            doublecheck = true;
                            break;
                        }
                    }
                    Column c = t.getColumn(i);
                    try {
                        if(doublecheck) {
                            double temp = Double.parseDouble(table.getColumn(i).getValue(j));
                            c.setValue(j, temp);
                        } else {
                            int temp = Integer.parseInt(table.getColumn(i).getValue(j));
                            c.setValue(j, temp);
                        }
                    }
                    catch (NumberFormatException e) {
                        c.setValue(j, table.getColumn(i).getValue(j));
                    }
                }
            }
        for(int n = 0; n < 1000; n++) {
            changeField(t,  (int)(Math.random()*t.getRowCount()), (int)(Math.random()*t.getRowCount()));
        }
        return t;
    }
}
