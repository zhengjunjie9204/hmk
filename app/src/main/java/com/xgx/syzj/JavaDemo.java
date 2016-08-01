package com.xgx.syzj;

import com.xgx.syzj.secret.Base64Util;

import java.io.UnsupportedEncodingException;
import java.util.Random;

/**
 * @author zajo
 * @created 2015年09月24日 10:07
 */
public class JavaDemo {


    public static void main(String[] arg){
//        Map<String, Integer> map = new HashMap<>();
//        map.put("type1", 100);
//        map.put("type2", 200);
//        map.put("type1", map.get("type1")+11);
//        for(String key : map.keySet()){
//            System.out.println("key:"+key+" value:"+map.get(key));
//        }
        String tem = "a";
        System.out.println(tem.substring(0, tem.length() - 1));

        String temp = "商品都夸你呢看看对方那么累斯大林开房记录独守空房";
        try {
            String info = Base64Util.encode(temp.getBytes("UTF-8"));
            System.out.println("encode:"+info);
            byte d[] = Base64Util.decode(info);
            System.out.println("decode:"+new String(d, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        System.out.println("class name:"+Shaop.class.getSimpleName());

        Random random = new Random();
        for (int j = 0; j < 20; j++){
            int rd = random.nextInt(10);
            System.out.println(rd);
        }

        String info = "eyJjYXJkSWQiOiI2MTU1MTAiLCJiaXJ0aGRheSI6IjIwMTUtMTAtMjciLCJncm91cElkIjoiZ3BfMTEiLCJzZXgiOiLnlLciLCJhZGRyZXNzIjoiZGZzZGZlZCIsIndlY2hhdElkIjoid3hfMTEiLCJjYXJkVHlwZUlkIjoyMiwiYXNzb2NpYXRvck5hbWUiOiLlr4bnoIEiLCJ0ZWxlcGhvbmUiOiIxMzQ1MjYxNTI0NSJ9";
        byte[] data = Base64Util.decode(info);
        try {
            System.out.println(new String(data, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String sss = "10.23";
        System.out.println(sss.substring(0, sss.indexOf(".")));
    }

    class Shaop{
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Shaop(String id) {
            this.id = id;
        }
    }
}
