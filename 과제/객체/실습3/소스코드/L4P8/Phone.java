package L4P8;

public class Phone {
    private String name, tel;

    public Phone(String name, String tel) {
        this.name = name;
        this.tel = tel;
    }

    public boolean compare(String tag) {
        if(this.name.equals(tag)) return true;
        return false;
    }

    public String getTel() {
        return tel;
    }
}
