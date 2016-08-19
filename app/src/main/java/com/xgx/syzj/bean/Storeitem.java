package com.xgx.syzj.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/8 0008.
 */
public class StoreItem implements Serializable{
    private String name;
    private long Price;
    private int id;
    private int LaborTime;
    private int picLength;
    private String Type;
    private ItemPic pictures;

    public int getLaborTime()
    {
        return LaborTime;
    }

    public void setLaborTime(int laborTime)
    {
        LaborTime = laborTime;
    }

    public int getId()
    {
        return id;

    }

    public void setId(int id)
    {
        this.id = id;
    }


    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public long getPrice()
    {
        return Price;
    }

    public void setPrice(long price)
    {
        Price = price;
    }


    public class ItemPic {
        public int index;
        public String pic;

        public int getIndex()
        {
            return index;
        }

        public void setIndex(int index)
        {
            this.index = index;
        }

        public String getPic()
        {
            return pic;
        }

        public void setPic(String pic)
        {
            this.pic = pic;
        }
    }
}
