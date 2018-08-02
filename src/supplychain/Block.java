package supplychain;

import java.util.Date;

public abstract class Block{
    int index;
    int prehash;
    int curhash;
    Date timestamp;
    String content;

    String userName;

    public abstract void save();
    public abstract void init();
    public abstract void buildContent();
    public abstract void renew();
}