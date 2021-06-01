package csv;

import javax.management.monitor.CounterMonitorMBean;
import java.util.Arrays;
import java.util.Vector;

public class ColumnImpl implements Column {

    private Vector obj;
    private String header;



    public String getHeader() {
        return header;
    }

    /* cell 값을 String으로 반환 */
    public String getValue(int index) {
        try {
            return obj.get(index).toString();
        }
        catch (NullPointerException e) {
            return "null";
        }
    }

    /**
     * @param index
     * @param t 가능한 값으로 Double.class, Integer.class
     * @return Double 혹은 Integer로 반환 불가능할 시, 예외 발생
     */
    public <T extends Number> T getValue(int index, Class<T> t) {
        try {
            return t.cast(obj.get(index));
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void setValue(int index, String value) {
        if(index == -1) {
            header = value;
            return;
        }
        if(obj == null)
            obj = new Vector<String>();
        while (obj.size() <= index) {
            String temp = new String();
            obj.add(temp);
        }
        if(value.isEmpty())
            obj.set(index, null);
        else
            obj.set(index, value);
    }

    /**
     * @param value double, int 리터럴을 index의 cell로 건네고 싶을 때 사용
     */
    public <T extends Number> void setValue(int index, T value) {
        if(obj == null) {
            obj = new Vector<T>();

        }
        while (obj.size() <= index) {
            T temp = null;
            obj.add(temp);
        }
        obj.set(index, value);
    }

    /**
     * @return null 포함 모든 cell 개수 반환
     */
    public int count() {
        if(obj == null)
            return 0;
        return obj.size();
    }

    public void print() {

    }

    /**
     * @return (int or null)로 구성된 컬럼 or (double or null)로 구성된 컬럼이면 true 반환
     */
    public boolean isNumericColumn() {
        int n = 0;
        while(obj.get(n) == null) { n++; };
        if(obj.get(n).getClass() == String.class)
            return false;
        return true;
    }

    public long getNullCount() {
        int ncnt = 0;
        for(int i = 0; i < obj.size(); i++) {
            if(obj.get(i) == null)
                ncnt ++;
        }
        return ncnt;
    }

    /**
     * @return int 혹은 double로 평가될 수 있는 cell의 개수
     */
    public long getNumericCount() {
        int cnt = 0;
        for(int i = 0; i < count(); i++) {
            if(getValue(i) == "null") continue;
            try {
                getValue(i, Double.class);
                cnt++;
            } catch(ClassCastException e) {
                try {
                    getValue(i, Integer.class);
                    cnt++;
                } catch(ClassCastException re) {
                    continue;
                }
            }
        }
        return cnt;
    }

    // 아래 7개 메소드는 String 타입 컬럼에 대해서 수행 시, 예외 발생 시켜라.
    public double getNumericMin() {
        double min = 1999999999;
        try {
            for(int i = 0; i < count(); i++) {
                try {
                    min = (min > getValue(i, Double.class) ? getValue(i, Double.class) : min);
                } catch (ClassCastException e) {
                    try {
                        min = (min > getValue(i, Integer.class) ? getValue(i, Integer.class) : min);
                    } catch (ClassCastException re) {
                        continue;
                    }
                } catch (NullPointerException e) {
                    continue;
                }
            }
        } catch(ClassCastException e) {
            e.printStackTrace();
        }
        return min;
    }
    public double getNumericMax() {
        double max = -1;
        try {
            for(int i = 0; i < count(); i++) {
                try {
                    max = (max < getValue(i, Double.class) ? getValue(i, Double.class) : max);
                } catch (ClassCastException e) {
                    try {
                        max = (max < getValue(i, Integer.class) ? getValue(i, Integer.class) : max);
                    } catch (ClassCastException re) {
                        continue;
                    }
                } catch (NullPointerException e) {
                    continue;
                }
            }
        } catch(ClassCastException e) {
            e.printStackTrace();
        }
        return max;
    }
    public double getMean() {
        double sum = 0;
        try {
            for(int i = 0; i < count(); i++) {
                try {
                    sum += getValue(i, Double.class);
                } catch (ClassCastException e) {
                    try {
                        sum += getValue(i, Integer.class);
                    } catch (ClassCastException re) {
                        continue;
                    }
                } catch (NullPointerException e) {
                    continue;
                }
            }
        } catch(ClassCastException e) {
            e.printStackTrace();
        }
        return sum/getNumericCount();
    }
    public double getStd() {
        double mean = getMean();
        double sum = 0;

        try {
            for(int i = 0; i < count(); i++) {
                try {
                    sum += Math.pow(mean - getValue(i, Double.class), 2);
                } catch (ClassCastException e) {
                    try {
                        sum += Math.pow(mean - getValue(i, Integer.class), 2);
                    } catch (ClassCastException re) {
                        continue;
                    }
                } catch (NullPointerException e) {
                    continue;
                }
            }
        } catch(ClassCastException e) {
            e.printStackTrace();
        }
        return Math.sqrt(sum/(getNumericCount()-1));
    }
    public double getQ1() {
        Object[] arr = getNumericSort();
        int mid = (arr.length % 2 == 1 ? arr.length-1 : arr.length) / 2;
        arr = Arrays.copyOfRange(arr, 0, mid);
        mid = (arr.length % 2 == 1 ? arr.length-1 : arr.length) / 2;
        if(arr.length % 2 == 1) {
            double temp = Double.parseDouble(arr[(arr.length-1)/2].toString())
                    + Double.parseDouble(arr[(arr.length+1)/2].toString());
            return temp/2.0;
        }
        return Double.parseDouble((arr[mid]).toString());
    }
    public double getMedian() {
        Object[] arr = getNumericSort();
        int mid = (arr.length % 2 == 1 ? arr.length-1 : arr.length) / 2;
        return Double.parseDouble((arr[mid]).toString());
    }
    public double getQ3() {
        Object[] arr = getNumericSort();
        int mid = (arr.length % 2 == 1 ? arr.length-1 : arr.length) / 2;
        arr = Arrays.copyOfRange(arr, mid, arr.length-1);
        mid = (arr.length % 2 == 1 ? arr.length-1 : arr.length) / 2;
        if(arr.length % 2 == 1) {
            double temp = Double.parseDouble(arr[(arr.length-1)/2].toString())
                    + Double.parseDouble(arr[(arr.length+1)/2].toString());
            return temp/2.0;
        }
        return Double.parseDouble((arr[mid]).toString());
    }
    private Object[] getNumericSort() {
        Vector<Object> temp = new Vector<Object>();
        try {
            for(int i = 0; i < count(); i++) {
                if(getValue(i) == "null") continue;
                try {
                    temp.add(getValue(i, Double.class));
                } catch (ClassCastException e) {
                    try {
                        temp.add(Double.parseDouble(getValue(i, Integer.class).toString()));
                    } catch (ClassCastException re) {
                        continue;
                    }
                } catch (NullPointerException e) {
                    continue;
                }
            }
        } catch(ClassCastException e) {
            e.printStackTrace();
        }
        Object[] arr = temp.toArray();
        Arrays.sort(arr);
        return arr;
    }

    // 아래 2개 메소드는 1개 cell이라도 치환했으면, true 반환.
    public boolean fillNullWithMean() {
        if(getNumericCount() == 0)
            return false;
        boolean change = false;
        for(int j = 0; j < count(); j++) {
            if(getValue(j).equals("null")) {
                setValue(j, getMean());
                change = true;
            }
        }
        return change;
    }
    public boolean fillNullWithZero() {
        if(getNumericCount() == 0)
            return false;
        boolean change = false;
        for(int j = 0; j < count(); j++) {
            if(getValue(j).equals("null")) {
                setValue(j, 0);
                change = true;
            }
        }
        return change;
    }

    // 아래 3개 메소드는 null 값은 메소드 호출 후에도 여전히 null.
    // standardize()와 normalize()는 String 타입 컬럼에 대해서는 false 반환
    // factorize()는 컬럼 타입과 무관하게 null 제외하고 2가지 값만으로 구성되었다면 수행된다. 조건에 부합하여 수행되었다면 true 반환
    public boolean standardize() {
        boolean change = false;
        if(!isNumericColumn())
            return false;
        double std = getStd();
        double mean = getMean();
        for(int i = 0; i < count(); i++) {
            if(getValue(i).equals("null"))
                continue;
            try {
                setValue(i, (getValue(i, Double.class)-mean) / std);
                change = true;
            } catch (ClassCastException e) {
                try {
                    setValue(i, ((double)getValue(i, Integer.class)-mean) / std);
                    change = true;
                } catch (ClassCastException se) {
                    continue;
                }
            }
        }

    return change;
    }
    public boolean normalize() {
        boolean change = false;
        if(!isNumericColumn())
            return false;
        double max = getNumericMax();
        double min = getNumericMin();
        for(int i = 0; i < count(); i++) {
            if(getValue(i).equals("null"))
                continue;
            try {
                setValue(i, (getValue(i, Double.class) - min) / (max - min));
                change = true;
            } catch (ClassCastException e) {
                try {
                    setValue(i, ((double)getValue(i, Integer.class) - min) / (max - min));
                    change = true;
                } catch (ClassCastException se) {
                    continue;
                }
            }
        }

        return change;
    }
    public boolean factorize() {
        String check1 = getValue(0);
        String check2 = "";
        boolean check = false;
        for(int i = 0; i < count(); i++) {
            if(getValue(i).equals("null")) continue;
            if((!check1.equals(getValue(i)) && !check2.equals(getValue(i)))) {
                if(check) return false;
                check = true;
                check2 = getValue(i);
            }
        }
        for(int i = 0; i < count(); i++) {
            if(getValue(i).equals(check1))
                setValue(i, 1);
            else
                setValue(i, 0);
        }
        return true;
    }
}
