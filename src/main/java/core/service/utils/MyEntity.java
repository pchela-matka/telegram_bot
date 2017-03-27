package core.service.utils;

/**
 * Created by astegnienko on 01.03.2017.
 */
public class MyEntity {
    private String desc;
    private String longdesc;

    public MyEntity(String desc, String longdesc) {
        this.desc = desc;
        this.longdesc = longdesc;
    }

    public MyEntity() {
        this.desc = desc;
        this.longdesc = longdesc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLongdesc() {
        return longdesc;
    }

    public void setLongdesc(String longdesc) {
        this.longdesc = longdesc;
    }
}
