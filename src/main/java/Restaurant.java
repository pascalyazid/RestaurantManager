

public class Restaurant {
    private String name;
    private String type;
    private String desc;
    private String address;
    private String opens;
    private String closes;

    public Restaurant(String name, String type, String desc, String address, String opens, String closes) {
        this.name = name;
        this.type = type;
        this.desc = desc;
        this.address = address;
        this.opens = opens;
        this.closes = closes;
    }

    public Restaurant() {}


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOpens() {
        return opens;
    }

    public void setOpens(String opens) {
        this.opens = opens;
    }

    public String getCloses() {
        return closes;
    }

    public void setCloses(String closes) {
        this.closes = closes;
    }
}
