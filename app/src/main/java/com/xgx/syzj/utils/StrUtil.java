/*
 * @大熊
 */
package com.xgx.syzj.utils;

import android.text.TextUtils;

import com.xgx.syzj.bean.CardType;

import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述：字符串处理类.
 *
 * @author daxiong
 * @date：2013-1-17 上午10:07:09
 * @version v1.0
 */
public final class StrUtil {
    
    /**
     * 描述：将null转化为“”.
     *
     * @param str 指定的字符串
     * @return 字符串的String类型
     */
    public static String parseEmpty(String str) {
        if(str==null || "null".equals(str.trim())){
        	str = "";
        }
        return str.trim();
    }
    
    /**
     * 描述：判断一个字符串是否为null或空值.
     *
     * @param str 指定的字符串
     * @return true or false
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }
    
    /**
     * 获取字符串中文字符的长度（每个中文算2个字符）.
     *
     * @param str 指定的字符串
     * @return 中文字符的长度
     */
    public static int chineseLength(String str) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        if(!isEmpty(str)){
	        for (int i = 0; i < str.length(); i++) {
	            /* 获取一个字符 */
	            String temp = str.substring(i, i + 1);
	            /* 判断是否为中文字符 */
	            if (temp.matches(chinese)) {
	                valueLength += 2;
	            }
	        }
        }
        return valueLength;
    }
    
    /**
     * 描述：获取字符串的长度.
     *
     * @param str 指定的字符串
     * @return  字符串的长度（中文字符计2个）
     */
     public static int strLength(String str) {
         int valueLength = 0;
         String chinese = "[\u0391-\uFFE5]";
         if(!isEmpty(str)){
	         //获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
	         for (int i = 0; i < str.length(); i++) {
	             //获取一个字符
	             String temp = str.substring(i, i + 1);
	             //判断是否为中文字符
	             if (temp.matches(chinese)) {
	                 //中文字符长度为2
	                 valueLength += 2;
	             } else {
	                 //其他字符长度为1
	                 valueLength += 1;
	             }
	         }
         }
         return valueLength;
     }
     
     /**
      * 描述：获取指定长度的字符所在位置.
      *
      * @param str 指定的字符串
      * @param maxL 要取到的长度（字符长度，中文字符计2个）
      * @return 字符的所在位置
      */
     public static int subStringLength(String str,int maxL) {
    	 int currentIndex = 0;
         int valueLength = 0;
         String chinese = "[\u0391-\uFFE5]";
         //获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
         for (int i = 0; i < str.length(); i++) {
             //获取一个字符
             String temp = str.substring(i, i + 1);
             //判断是否为中文字符
             if (temp.matches(chinese)) {
                 //中文字符长度为2
                 valueLength += 2;
             } else {
                 //其他字符长度为1
                 valueLength += 1;
             }
             if(valueLength >= maxL){
            	 currentIndex = i;
            	 break;
             }
         }
         return currentIndex;
     }
     
    /**
     * 描述：手机号格式验证.
     *
     * @param str 指定的手机号码字符串
     * @return 是否为手机号码格式:是为true，否则false
     */
 	public static Boolean isMobileNo(String str) {
		if (TextUtils.isEmpty(str)){
			return false;
		}
 		Boolean isMobileNo = false;
 		try {
			Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
			Matcher m = p.matcher(str);
			isMobileNo = m.matches();
		} catch (Exception e) {
			e.printStackTrace();
		}
 		return isMobileNo;
 	}

	/**
	 * 电话号码验证
	 *
	 * @param  str
	 * @return 验证通过返回true
	 */
	public static boolean isPhone(String str) {
		if (TextUtils.isEmpty(str)){
			return false;
		}
		Pattern p1 = null,p2 = null;
		Matcher m = null;
		boolean b = false;
		p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");  // 验证带区号的
		p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // 验证没有区号的
		if(str.length() >9)
		{   m = p1.matcher(str);
			b = m.matches();
		}else{
			m = p2.matcher(str);
			b = m.matches();
		}
		return b;
	}

	/**
	  * 描述：是否只是字母和数字.
	  *
	  * @param str 指定的字符串
	  * @return 是否只是字母和数字:是为true，否则false
	  */
 	public static Boolean isNumberLetter(String str) {
 		Boolean isNoLetter = false;
 		String expr = "^[A-Za-z0-9]+$";
 		if (str.matches(expr)) {
 			isNoLetter = true;
 		}
 		return isNoLetter;
 	}
 	
 	/**
	  * 描述：是否只是数字.
	  *
	  * @param str 指定的字符串
	  * @return 是否只是数字:是为true，否则false
	  */
 	public static Boolean isNumber(String str) {
 		Boolean isNumber = false;
 		String expr = "^[0-9]+$";
 		if (str.matches(expr)) {
 			isNumber = true;
 		}
 		return isNumber;
 	}
 	
 	/**
	  * 描述：是否是邮箱.
	  *
	  * @param str 指定的字符串
	  * @return 是否是邮箱:是为true，否则false
	  */
 	public static Boolean isEmail(String str) {
 		Boolean isEmail = false;
 		String expr = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
 		if (str.matches(expr)) {
 			isEmail = true;
 		}
 		return isEmail;
 	}
 	
 	/**
	  * 描述：是否是中文.
	  *
	  * @param str 指定的字符串
	  * @return  是否是中文:是为true，否则false
	  */
    public static Boolean isChinese(String str) {
    	Boolean isChinese = true;
        String chinese = "[\u0391-\uFFE5]";
        if(!isEmpty(str)){
	         //获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
	         for (int i = 0; i < str.length(); i++) {
	             //获取一个字符
	             String temp = str.substring(i, i + 1);
	             //判断是否为中文字符
	             if (temp.matches(chinese)) {
	             }else{
	            	 isChinese = false;
	             }
	         }
        }
        return isChinese;
    }
    
    /**
     * 描述：是否包含中文.
     *
     * @param str 指定的字符串
     * @return  是否包含中文:是为true，否则false
     */
    public static Boolean isContainChinese(String str) {
    	Boolean isChinese = false;
        String chinese = "[\u0391-\uFFE5]";
        if(!isEmpty(str)){
	         //获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
	         for (int i = 0; i < str.length(); i++) {
	             //获取一个字符
	             String temp = str.substring(i, i + 1);
	             //判断是否为中文字符
	             if (temp.matches(chinese)) {
	            	 isChinese = true;
	             }else{
	            	 
	             }
	         }
        }
        return isChinese;
    }
 	
 	/**
	  * 描述：从输入流中获得String.
	  *
	  * @param is 输入流
	  * @return 获得的String
	  */
 	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			
			//最后一个\n删除
			if(sb.indexOf("\n")!=-1 && sb.lastIndexOf("\n") == sb.length()-1){
				sb.delete(sb.lastIndexOf("\n"), sb.lastIndexOf("\n")+1);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

 	
 	/**
	  * 描述：不足2个字符的在前面补“0”.
	  *
	  * @param str 指定的字符串
	  * @return 至少2个字符的字符串
	  */
    public static String strFormat2(String str) {
		try {
			if(str.length()<=1){
				str = "0"+str;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return str;
	}
    
    /**
     * 描述：截取字符串到指定字节长度.
     *
     * @param str the str
     * @param length 指定字节长度
     * @return 截取后的字符串
     */
    public static String cutString(String str,int length){
		return cutString(str, length, "");
	}
    
    /**
     * 描述：截取字符串到指定字节长度.
     *
     * @param str 文本
     * @param length 字节长度
     * @param dot 省略符号
     * @return 截取后的字符串
     */
	public static String cutString(String str,int length,String dot){
		int strBLen = strlen(str,"GBK");
		if( strBLen <= length ){
     		return str;
     	}
		int temp = 0;
		StringBuffer sb = new StringBuffer(length);
		char[] ch = str.toCharArray();
		for ( char c : ch ) {
			sb.append( c );
			if ( c > 256 ) {
	    		temp += 2;
	    	} else {
	    		temp += 1;
	    	}
			if (temp >= length) {
				if( dot != null) {
					sb.append( dot );
				}
	            break;
	        }
		}
		return sb.toString();
    }
	
	/**
	 * 描述：截取字符串从第一个指定字符.
	 *
	 * @param str1 原文本
	 * @param str2 指定字符
	 * @param offset 偏移的索引
	 * @return 截取后的字符串
	 */
	public static String cutStringFromChar(String str1,String str2,int offset){
		if(isEmpty(str1)){
			return "";
		}
		int start = str1.indexOf(str2);
		if(start!=-1){
			if(str1.length()>start+offset){
				return str1.substring(start+offset);
			}
		}
		return "";
    }
	
	/**
	 * 描述：获取字节长度.
	 *
	 * @param str 文本
	 * @param charset 字符集（GBK）
	 * @return the int
	 */
	public static int strlen(String str,String charset){
		if(str==null||str.length()==0){
			return 0;
		}
		int length=0;
		try {
			length = str.getBytes(charset).length;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return length;
	}
    
	/**
	 * 获取大小的描述.
	 *
	 * @param size 字节个数
	 * @return  大小的描述
	 */
    public static String getSizeDesc(long size) {
	   	 String suffix = "B";
	   	 if (size >= 1024){
			suffix = "K";
			size = size>>10;
			if (size >= 1024){
				suffix = "M";
				//size /= 1024;
				size = size>>10;
				if (size >= 1024){
	    		        suffix = "G";
	    		        size = size>>10;
	    		        //size /= 1024;
		        }
			}
	   	}
        return size+suffix;
    }

	
    /**
	 * 
	 * @param content
	 * @return md5加密后的字符串
	 */
	public static String getMD5(String content) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(content.getBytes());
			return getHashString(digest);

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取hash字符串
	 * @param digest
	 * @return
	 */
	private static String getHashString(MessageDigest digest) {
		StringBuilder builder = new StringBuilder();
		for (byte b : digest.digest()) {
			builder.append(Integer.toHexString((b >> 4) & 0xf));
			builder.append(Integer.toHexString(b & 0xf));
		}
		return builder.toString();
	}


	/**
	 * 保留小数前N位
	 * @param v
	 * @param n
	 * @return
	 */
	public final static double getDoubleScaleVal(double v, int n) {
		BigDecimal b = new BigDecimal(v);
		double newVal = b.setScale(n, BigDecimal.ROUND_HALF_UP).doubleValue();
		return newVal;
	}

	/**
	 *	得到米、公里等计量
	 */
	public static String getFriendlyDistance0(int distance) {
		if (distance < 1000)
			return (((distance + 9) / 10) * 10) + "m";
		//if(distance<1000)
		//	return distance+"米";
		if (distance < 1000 * 10)
			return getDoubleScaleVal(((double) distance) / 1000, 1) + "km";
		if (distance < 1000 * 10000)
			return distance / 1000 + "km";
		else
			return getDoubleScaleVal(((double) distance) / (1000 * 10000), 2) + "万km";
	}
	/**
	 * 得到米、公里等计量---km计
	 * @param distance
	 * @return
	 */
	public static String getFriendlyDistance(int distance) {
		if (distance < 100) //2013-8-19新增
			return "<100m";
		if (distance < 1000)
			return (((distance + 50) / 100) * 100) + "m";//(((distance+9)/10)*10)+"m";
		//if(distance<1000)
		//	return distance+"米";
		if (distance < 1000 * 10)
			return getDoubleScaleVal(((double) distance) / 1000, 1) + "km";//原来是1
		if (distance < 1000 * 10000)
			return distance / 1000 + "km";
		else
			return "未知";
		//return getDoubleScaleVal(((double)distance)/(1000*10000),2)+"万km";
	}

	/**
	 * 返回友好的大小显示
	 * @param size
	 * @return
	 */
	public static String getFriendlyMSize(int size) {
		if (size == 0)
			return "0";
		if (size < 1024 * 1)
			return "<1K";
		if (size < 1024 * 100)//10.5KB
			return getDoubleScaleVal((double) size / 1024, 1) + "K";
		if (size < 1024 * 1024)//101KB
			return (int) ((double) size / 1024) + "K";
		return StrUtil.getDoubleScaleVal(((double) size) / (1024 * 1024), 2)
				+ "M";//+((int)((double)size/1024)+"K");
	}

	public static String toCharset(final String str, final String charset, int judgeCharsetLength) {
		try {
			String oldCharset = getEncoding(str, judgeCharsetLength);
			return new String(str.getBytes(oldCharset), charset);
		} catch (Throwable ex) {
			ex.printStackTrace();
			return str;
		}
	}

	public static String getEncoding(final String str, int judgeCharsetLength) {
		String encode = HTTP.DEFAULT_CONTENT_CHARSET;
		for (String charset : SUPPORT_CHARSET) {
			if (isCharset(str, charset, judgeCharsetLength)) {
				encode = charset;
				break;
			}
		}
		return encode;
	}

	public static boolean isCharset(final String str, final String charset, int judgeCharsetLength) {
		try {
			String temp = str.length() > judgeCharsetLength ? str.substring(0, judgeCharsetLength) : str;
			return temp.equals(new String(temp.getBytes(charset), charset));
		} catch (Throwable e) {
			return false;
		}
	}

	public static final List<String> SUPPORT_CHARSET = new ArrayList<String>();

	static {
		SUPPORT_CHARSET.add("ISO-8859-1");

		SUPPORT_CHARSET.add("GB2312");
		SUPPORT_CHARSET.add("GBK");
		SUPPORT_CHARSET.add("GB18030");

		SUPPORT_CHARSET.add("US-ASCII");
		SUPPORT_CHARSET.add("ASCII");

		SUPPORT_CHARSET.add("ISO-2022-KR");

		SUPPORT_CHARSET.add("ISO-8859-2");

		SUPPORT_CHARSET.add("ISO-2022-JP");
		SUPPORT_CHARSET.add("ISO-2022-JP-2");

		SUPPORT_CHARSET.add("UTF-8");
	}

	/**
	 * 获取会员卡内容
	 * @param card
	 * @return
	 */
	public static String getCardContent(CardType card){
		String content = "";
		if (card.isSupportIntegral()) {
			content = "支持积分：每消费" + card.getIntegralRatio() + "元1积分\n";
		}
		boolean isCut = false;
		if (card.isSupportValue()) {
			content += "支持储值";
			isCut = true;
		}
		if (card.isSupportCount()) {
			if (isCut) content += "，";
			content += "支持计次卡";
			isCut = true;
		}
		if (isCut) {
			content += "\n";
		}
		content += "享受折扣：" + (Integer.parseInt(card.getDiscount())*10) + "%（" + card.getDiscount() + "折）";
		return content;
	}

	//获取指定格式时间"yyyy-MM-dd HH:mm:ss"
	public static String getFriendlyTime(long time, String format){
		if (time <= 0 || isEmpty(format)) return "";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date(time));
	}
}
