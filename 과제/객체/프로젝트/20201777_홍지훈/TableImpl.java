package csv;

import java.util.Vector;
import java.util.function.Predicate;

class TableImpl implements Table{

    private Vector<ColumnImpl> cells = new Vector<ColumnImpl>();

    @Override
    public String toString() {
        String result = "";
        result += "<csv.Table@" + Integer.toHexString(this.hashCode()) + ">\n";
        result += "RangeIndex: " + getRowCount() + " entries, 0 to " + (getRowCount()-1) + "\n";
        result += "Data columns (total " + getColumnCount() + " columns):\n";
        int maxcol = "Column".length();
        for(int i = 0; i < getColumnCount(); i++) {
            if(getColumn(i).getHeader().length() > maxcol)
                maxcol = getColumn(i).getHeader().length();
        }
        String temp = String.format("%2s |%" + maxcol + "s |%14s |%s\n", "#", "Column", "Non-Null Count", "Dtype");
        result += temp;
        int d = 0, in = 0, s = 0;
        for(int i = 0; i < getColumnCount(); i++) {
            String c = "";
            if(getColumn(i).isNumericColumn()) {
                try {
                    for(int j = 0; j < getColumn(i).count(); j++)
                        getColumn(i).getValue(j, Integer.class);
                    c = int.class.toString();
                    in++;
                }
                catch (ClassCastException e) {
                    c = double.class.toString();
                    d++;
                }
            }else {
                c = getColumn(i).getValue(0).getClass().getName();
                c = "String";
                s++;
            }
            temp = String.format("%2s |%" + maxcol + "s |%14s |%s\n", i, getColumn(i).getHeader(),((getColumn(i).count() - getColumn(i).getNullCount()) + " non-null"), c);
            result += temp;
        }
        result += "dtypes: double(" + d + "), int(" + in + "), String(" + s + ")";
        return result;
    }

    public void print() {
        Vector<Integer> weight = new Vector<Integer>();
        for(int i = 0; i < getColumnCount(); i++) {
            int max = cells.get(i).getHeader().length();
            for(int j = 0; j < cells.get(i).count(); j++) {
                try {
                    double d = cells.get(i).getValue(j, Double.class);
                    d = (double)Math.round(d*1000000) / 1000000.0;
                    if(max < Double.toString(d).length())
                        max = Double.toString(d).length();
                } catch (ClassCastException e) {
                    if(max < cells.get(i).getValue(j).length())
                        max = cells.get(i).getValue(j).length();
                } catch (NullPointerException e) {
                    if(max < cells.get(i).getValue(j).length())
                        max = cells.get(i).getValue(j).length();
                }
            }
            weight.add(max);
        }

        for(int i = 0; i < getColumnCount(); i++)
            System.out.print(String.format("%"+weight.get(i)+"s | ", cells.get(i).getHeader()));
        System.out.println();
        for(int i = 0; i < getRowCount(); i++) {
            for(int j = 0; j < getColumnCount(); j++) {
                try {
                    double d = cells.get(j).getValue(i, Double.class);
                    d = (double)Math.round(d*1000000) / 1000000.0;
                    System.out.print(String.format("%"+weight.get(j)+"s | ", d));
                } catch (ClassCastException e) {
                    System.out.print(String.format("%"+weight.get(j)+"s | ", cells.get(j).getValue(i)));
                } catch (NullPointerException e) {
                    System.out.print(String.format("%"+weight.get(j)+"s | ", cells.get(j).getValue(i)));
                }
            }
            System.out.println();
        }

    }


    /**
     * String 타입 컬럼이더라도,
     * 그 컬럼에 double로 처리할 수 있는 값이 있다면,
     * 그 값을 대상으로 해당 컬럼 통계량을 산출
     */
    public Table getStats() {
        int cnt = 0;
        TableImpl table = new TableImpl();
        table.getColumn(0).setValue(-1, "");
        table.getColumn(0).setValue(0, "count");
        table.getColumn(0).setValue(1, "mean");
        table.getColumn(0).setValue(2, "std");
        table.getColumn(0).setValue(3, "min");
        table.getColumn(0).setValue(4, "25%");
        table.getColumn(0).setValue(5, "50%");
        table.getColumn(0).setValue(6, "75%");
        table.getColumn(0).setValue(7, "max");
        for(int i = 0; i < this.getColumnCount(); i++) {
            long count = getColumn(i).getNumericCount();
            if(count == 0) continue;
            cnt++;
            double sum = 0;
            table.getColumn(cnt).setValue(-1, getColumn(i).getHeader());
            table.getColumn(cnt).setValue(0, count);
            table.getColumn(cnt).setValue(1, getColumn(i).getMean());
            table.getColumn(cnt).setValue(2, getColumn(i).getStd());
            table.getColumn(cnt).setValue(3, getColumn(i).getNumericMin());
            table.getColumn(cnt).setValue(4, getColumn(i).getQ1());
            table.getColumn(cnt).setValue(5, getColumn(i).getMedian());
            table.getColumn(cnt).setValue(6, getColumn(i).getQ3());
            table.getColumn(cnt).setValue(7, getColumn(i).getNumericMax());

        }
        return table;
    }

    /**
     * @return 처음 (최대)5개 행으로 구성된 새로운 Table 생성 후 반환
     */
    public Table head() {
        TableImpl table = new TableImpl();
        for(int i = 0; i < this.getColumnCount(); i++) {
            table.getColumn(i).setValue(-1, this.getColumn(i).getHeader());
            for (int j = 0; j < Math.min(5, getRowCount()); j++) {
                table.getColumn(i).setValue(j, this.getColumn(i).getValue(j));
            }
        }
        return table;
    }
    /**
     * @return 처음 (최대)lineCount개 행으로 구성된 새로운 Table 생성 후 반환
     */
    public Table head(int lineCount){
        TableImpl table = new TableImpl();
        for(int i = 0; i < this.getColumnCount(); i++) {
            table.getColumn(i).setValue(-1, this.getColumn(i).getHeader());
            for (int j = 0; j < Math.min(lineCount, getRowCount()); j++) {
                table.getColumn(i).setValue(j, this.getColumn(i).getValue(j));
            }
        }
        return table;
    }

    /**
     * @return 마지막 (최대)5개 행으로 구성된 새로운 Table 생성 후 반환
     */
    public Table tail() {
        TableImpl table = new TableImpl();
        for(int i = 0; i < this.getColumnCount(); i++) {
            table.getColumn(i).setValue(-1, this.getColumn(i).getHeader());
            int cnt = 0;
            for (int j = Math.max(0, (this.getRowCount()-5)); j < getRowCount(); j++) {
                table.getColumn(i).setValue(cnt++, this.getColumn(i).getValue(j));
            }
        }
        return table;
    }
    /**
     * @return 마지막 (최대)lineCount개 행으로 구성된 새로운 Table 생성 후 반환
     */
    public Table tail(int lineCount) {
        TableImpl table = new TableImpl();
        for(int i = 0; i < this.getColumnCount(); i++) {
            table.getColumn(i).setValue(-1, this.getColumn(i).getHeader());
            int cnt = 0;
            for (int j = Math.max(0, (this.getRowCount()-lineCount)); j < getRowCount(); j++) {
                table.getColumn(i).setValue(cnt++, this.getColumn(i).getValue(j));
            }
        }
        return table;
    }

    /**
     * @param beginIndex 포함(이상)
     * @param endIndex 미포함(미만)
     * @return 검색 범위에 해당하는 행으로 구성된 새로운 Table 생성 후 반환
     */
    public Table selectRows(int beginIndex, int endIndex) {
        TableImpl table = new TableImpl();
        for(int i = 0; i < this.getColumnCount(); i++) {
            table.getColumn(i).setValue(-1, this.getColumn(i).getHeader());
            int cnt = 0;
            for (int j = Math.max(0, (beginIndex)); j < Math.min(getRowCount(), endIndex); j++) {
                table.getColumn(i).setValue(cnt++, this.getColumn(i).getValue(j));
            }
        }
        return table;
    }

    /**
     * @return 검색 인덱스에 해당하는 행으로 구성된 새로운 Table 생성 후 반환
     */
    public Table selectRowsAt(int ...indices) {
        TableImpl table = new TableImpl();
        for(int i = 0; i < this.getColumnCount(); i++) {
            table.getColumn(i).setValue(-1, this.getColumn(i).getHeader());
            int cnt = 0;
            for (int j = 0; j < indices.length; j++) {
                table.getColumn(i).setValue(cnt++, this.getColumn(i).getValue(indices[j]));
            }
        }
        return table;
    }

    /**
     * @param beginIndex 포함(이상)
     * @param endIndex 미포함(미만)
     * @return 검색 범위에 해당하는 열로 구성된 새로운 Table 생성 후 반환
     */
    public Table selectColumns(int beginIndex, int endIndex) {
        TableImpl table = new TableImpl();
        int cnt = 0;
        for(int i = Math.max(beginIndex, 0); i < Math.min(getColumnCount(), endIndex); i++) {
            table.getColumn(cnt).setValue(-1, this.getColumn(i).getHeader());
            for (int j = 0; j < getRowCount(); j++) {
                table.getColumn(cnt).setValue(j, this.getColumn(i).getValue(j));
            }
            cnt++;
        }
        return table;
    }

    /**
     * @return 검색 인덱스에 해당하는 열로 구성된 새로운 Table 생성 후 반환
     */
    public Table selectColumnsAt(int ...indices) {
        TableImpl table = new TableImpl();
        int cnt = 0;
        for(int i = 0; i < indices.length; i++) {
            table.getColumn(cnt).setValue(-1, this.getColumn(indices[i]).getHeader());
            for (int j = 0; j < getRowCount(); j++) {
                table.getColumn(cnt).setValue(j, this.getColumn(indices[i]).getValue(j));
            }
            cnt++;
        }
        return table;
    }

    /**
     * @param
     * @return 검색 조건에 해당하는 행으로 구성된 새로운 Table 생성 후 반환, 제일 나중에 구현 시도하세요.
     */
    public <T> Table selectRowsBy(String columnName, Predicate<T> predicate) {
        TableImpl table = new TableImpl();
        for (int i = 0; i < this.getColumnCount(); i++)
            table.getColumn(i).setValue(-1, this.getColumn(i).getHeader());
        int cnt = 0;
        for(int i = 0; i < getRowCount(); i++) {
            boolean check = false;
            try {
                check = predicate.test((T)this.getColumn(columnName).getValue(i, Double.class));
            } catch (ClassCastException e1) {
                try {
                    check = predicate.test((T)this.getColumn(columnName).getValue(i, Integer.class));
                } catch (ClassCastException e2){
                    try {
                        check = predicate.test((T)this.getColumn(columnName).getValue(i));
                    } catch (ClassCastException e5) {

                    }
                } catch(NullPointerException e3) {

                }
            } catch (NullPointerException e4) {

            }

            if(check) {
                for(int j = 0; j < getColumnCount(); j++) {
                    try {
                        boolean doublecheck = false;
                        for(int k = 0; k < this.getColumn(j).getValue(i).length(); k++) {
                            if(this.getColumn(j).getValue(i).charAt(k) == '.') {
                                doublecheck = true;
                                break;
                            }
                        }
                        if(doublecheck) {
                            double temp = Double.parseDouble(this.getColumn(j).getValue(i));
                            table.getColumn(j).setValue(cnt, temp);
                        } else {
                            int temp = Integer.parseInt(this.getColumn(j).getValue(i));
                            table.getColumn(j).setValue(cnt, temp);
                        }
                    }
                    catch (NumberFormatException e) {
                        table.getColumn(j).setValue(cnt, this.getColumn(j).getValue(i));
                    }
                }
                cnt++;
            }
        }
        return table;
    }

    /**
     * @return 원본 Table이 정렬되어 반환된다.
     */
    public Table sort(int byIndexOfColumn, boolean isAscending, boolean isNullFirst) {
        int ncnt = 0;
        if(isNullFirst) {
            for(int i = 0; i < getRowCount(); i++) {
                if(getColumn(byIndexOfColumn).getValue(i).equals("null")) {
                    changeField(ncnt, i);
                    ncnt ++;
                }
            }
        }
        for(int i = ncnt; i < getRowCount()-1; i++) {
            int index = i;
            for(int j = i+1; j < getRowCount(); j++) {
                try {
                    Double a = Double.parseDouble(getColumn(byIndexOfColumn).getValue(j));
                    Double b = Double.parseDouble(getColumn(byIndexOfColumn).getValue(index));
                    if(isAscending) {
                        if(a < b)
                            index = j;
                    }
                    else {
                        if(a > b)
                            index = j;
                    }
                } catch(NumberFormatException e){
                    if(isAscending && getColumn(byIndexOfColumn).getValue(j).compareTo(getColumn(byIndexOfColumn).getValue(index)) < 0) {
                        index = j;
                    }
                    if(!isAscending && getColumn(byIndexOfColumn).getValue(j).compareTo(getColumn(byIndexOfColumn).getValue(index)) > 0) {
                        index = j;
                    }
                }
            }
            changeField(i, index);
        }
        return this;
    }
    private void changeField(int a, int b) {
        Table temp = selectRowsAt(a);
        for(int i = 0; i < getColumnCount(); i++) {
            getColumn(i).setValue(a, getColumn(i).getValue(b));
            getColumn(i).setValue(b, temp.getColumn(i).getValue(0));
        }
    }

    /**
     * @return 원본 Table이 무작위로 뒤섞인 후 반환된다. 말 그대로 랜덤이어야 한다. 즉, 랜덤 로직이 존재해야 한다.
     */
    public Table shuffle() {
        for(int n = 0; n < 1000; n++)
            changeField((int)(Math.random()*getRowCount()), (int)(Math.random()*getRowCount()));
        return this;
    }

    public int getRowCount() {
        int n = 0;
        for(int i = 0; i < cells.size(); i++) {
            int cnt = cells.get(i).count();
            n = (n >= cnt ? n : cnt);
        }
        return n;
    }
    public int getColumnCount() { return cells.size(); }

    /**
     * @return 원본 Column이 반환된다. 따라서, 반환된 Column에 대한 조작은 원본 Table에 영향을 끼친다.
     */
    public Column getColumn(int index) {
        while (cells.size() <= index) {
            ColumnImpl temp = new ColumnImpl();
            cells.add(temp);
        }
        return cells.get(index);
    }
    /**
     * @return 원본 Column이 반환된다. 따라서, 반환된 Column에 대한 조작은 원본 Table에 영향을 끼친다.
     */
    public Column getColumn(String name) {
        for(int i = 0; i < getColumnCount(); i++) {
            if(getColumn(i).getHeader().equals(name))
                return cells.get(i);
        }
        return null;
    }

    /**
     * String 타입 컬럼들에는 영향을 끼치지 않는다.
     * double 혹은 int 타입 컬럼들에 한해서 null 값을 mean 값으로 치환한다.
     * 이 연산 후, int 타입 컬럼에 mean으로 치환된 cell이 있을 경우, 이 컬럼은 double 타입 컬럼으로 바뀐다.
     * 왜냐하면, mean 값이 double이기 때문이다.
     * @return 테이블에 mean으로 치환한 cell이 1개라도 발생했다면, true 반환
     */
    public boolean fillNullWithMean() {
        boolean change = false;
        for(int i = 0; i < getColumnCount(); i++)
            if(getColumn(i).fillNullWithMean())
                change = true;
        return change;
    }

    /**
     * String 타입 컬럼들에는 영향을 끼치지 않는다.
     * double 혹은 int 타입 컬럼들에 한해서 null 값을 0으로 치환한다.
     * 이 연산 후, int 타입 혹은 double 타입 컬럼 모두 그 타입이 유지된다.
     * @return 테이블에 0으로 치환한 cell이 1개라도 발생했다면, true 반환
     */
    public boolean fillNullWithZero() {
        boolean change = false;
        for(int i = 0; i < getColumnCount(); i++)
            if(getColumn(i).fillNullWithZero())
                change = true;
        return change;
    }

    /**
     * 평균 0, 표준편자 1인 컬럼으로 바꾼다. (null은 연산 후에도 null로 유지된다. 즉, null은 연산 제외)
     * String 타입 컬럼들에는 영향을 끼치지 않는다.
     * double 혹은 int 타입 컬럼들에 한해서 수행된다.
     * 이 연산 후, int 타입 컬럼은 double 타입 컬럼으로 바뀐다.
     * 왜냐하면, mean과 std가 double이기 때문이다.
     * @return 이 연산에 의해 값이 바뀐 열이 1개라도 발생했다면, true 반환
     */
    public boolean standardize() {
        boolean change = false;
        for(int i = 0; i < getColumnCount(); i++)
            if(getColumn(i).standardize())
                change = true;
        return change;
    }

    /**
     * 최솟값 0, 최댓값 1인 컬럼으로 바꾼다. (null은 연산 후에도 null로 유지된다.즉, null은 연산 제외)
     * String 타입 컬럼들에는 영향을 끼치지 않는다.
     * double 혹은 int 타입 컬럼들에 한해서 수행된다.
     * 이 연산 후, int 타입 컬럼은 double 타입 컬럼으로 바뀐다.
     * 왜냐하면, 0과 1사이의 값들은 double이기 때문이다.
     * @return 이 연산에 의해 값이 바뀐 열이 1개라도 발생했다면, true 반환
     */
    public boolean normalize() {
        boolean change = false;
        for(int i = 0; i < getColumnCount(); i++)
            if(getColumn(i).normalize())
                change = true;
        return change;
    }


    /**
     * null을 제외하고 2가지 값으로만 구성된 컬럼이기만 하면 수행된다.
     * 연산 후 0과 1로 구성된 컬럼으로 바뀐다. (null은 연산 후에도 null로 유지된다.즉, null은 연산 제외)
     * 모든 타입 컬럼들에 대해서 수행될 수 있다.
     * 이 연산이 수행된 컬럼은 int 타입 컬럼으로 바뀐다.
     * 왜냐하면, 0과 1이 int이기 때문이다.
     * @return 이 연산에 의해 값이 바뀐 열이 1개라도 발생했다면, true 반환
     */
    public boolean factorize() {
        boolean change = false;
        for(int i = 0; i < getColumnCount(); i++)
            if(getColumn(i).factorize())
                change = true;
        return change;
    }
}
