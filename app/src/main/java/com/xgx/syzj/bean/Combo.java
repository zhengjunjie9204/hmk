package com.xgx.syzj.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 32918 on 2016/8/6.
 */
public class Combo implements Serializable {


    /**
     * price : 12.0
     * name : A套餐
     * id : 5
     * items : [{"name":"精洗1","count":3}]
     */

    private double price;
    private String name;
    private int id;
    /**
     * name : 精洗1
     * count : 3
     */

    private List<ItemsBean> items;

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public List<ItemsBean> getItems()
    {
        return items;
    }

    public void setItems(List<ItemsBean> items)
    {
        this.items = items;
    }

    public static class ItemsBean  implements Serializable{
        private String name;
        private int count;

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public int getCount()
        {
            return count;
        }

        public void setCount(int count)
        {
            this.count = count;
        }
    }
}
