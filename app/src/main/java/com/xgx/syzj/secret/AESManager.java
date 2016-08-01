package com.xgx.syzj.secret;


import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.NoSuchAlgorithmException;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


/**
 * @author lichengming
 * @version 1.0 创建时间：2015年7月7日 下午4:33:33
 * @company 广州新关系科技有限公司
 *
 * 功能描述：AES算法加密，传输，解密过程示例(AES可以使用128、192、和256位密钥，并且用128位分组加密和解密数据)
 * 默认只能用16位密钥加密，但用security包下的java包换掉jre中对应的后，可任意密钥加解密。
 */
public class AESManager {

//	private static final Logger logger = LoggerFactory.getLogger(AESManager.class);
	
	static {
		try {
			Security.addProvider(new BouncyCastleProvider());
		} catch (Exception e) {
		}
	}

	
	/**
	  * @Title: encrypt
	  * @Description: AES加密，并将结果通过Base64编码返回
	  * @param message
	  * @param aesKey
	  * @return
	  * @return String
	  * @throws
	*/
	public static String encrypt(String message, String aesKey) {
		try {
			/* AES算法 */
			SecretKey secretKey = new SecretKeySpec(aesKey.getBytes(), "AES");// 获得密钥
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", BouncyCastleProvider.PROVIDER_NAME);
			/* 获得一个私鈅加密类Cipher，DESede-》AES算法，ECB是加密模式，PKCS5Padding是填充方式 */
			cipher.init(Cipher.ENCRYPT_MODE, secretKey); // 设置工作模式为加密模式，给出密钥
			byte[] resultBytes = cipher.doFinal(message.getBytes("UTF-8")); // 正式执行加密操作
			return Base64Util.encode(resultBytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	  * @Title: encrypt
	  * @Description: AES加密，并将结果通过Base64编码返回
	  * @param message
	  * @param aesKey	AES秘钥
	  * @return
	  * @return String
	  * @throws
	*/
	public static String encrypt(String message, byte[] aesKey) {
		try {
			/* AES算法 */
			SecretKey secretKey = new SecretKeySpec(aesKey, "AES");// 获得密钥
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", BouncyCastleProvider.PROVIDER_NAME);
			/* 获得一个私鈅加密类Cipher，DESede-》AES算法，ECB是加密模式，PKCS5Padding是填充方式 */
			cipher.init(Cipher.ENCRYPT_MODE, secretKey); // 设置工作模式为加密模式，给出密钥
			byte[] resultBytes = cipher.doFinal(message.getBytes("UTF-8")); // 正式执行加密操作
			return Base64Util.encode(resultBytes);
		} catch (Exception e) {
		}
		return null;
	}
	
	/**
	  * @Title: encrypt
	  * @Description: AES加密，返回加密后的字节数组
	  * @param plainText
	  * @param aesKey
	  * @return
	  * @return byte[]
	  * @throws
	*/
	public static byte [] encrypt(byte [] plainText, String aesKey) {
		try {
			/* AES算法 */
			SecretKey secretKey = new SecretKeySpec(aesKey.getBytes(), "AES");// 获得密钥
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", BouncyCastleProvider.PROVIDER_NAME);
			/* 获得一个私鈅加密类Cipher，DESede-》AES算法，ECB是加密模式，PKCS5Padding是填充方式 */
			cipher.init(Cipher.ENCRYPT_MODE, secretKey); // 设置工作模式为加密模式，给出密钥
			byte[] resultBytes = cipher.doFinal(plainText); // 正式执行加密操作
			return resultBytes;
		} catch (Exception e) {
		}
		return null;
	}
	
	
	/**
	  * @Title: encrypt4Byte
	  * @Description: 解密，返回解密后的字节数组
	  * @param encrypt
	  * @param aesKey
	  * @return
	  * @return byte[]
	  * @throws
	*/
	public static byte[] encrypt4Byte(byte [] encrypt, String aesKey) {
		try {
			/* AES算法 */
			SecretKey secretKey = new SecretKeySpec(aesKey.getBytes(), "AES");// 获得密钥
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", BouncyCastleProvider.PROVIDER_NAME);
			/* 获得一个私鈅加密类Cipher，DESede-》AES算法，ECB是加密模式，PKCS5Padding是填充方式 */
			cipher.init(Cipher.ENCRYPT_MODE, secretKey); // 设置工作模式为加密模式，给出密钥
			byte[] resultBytes = cipher.doFinal(encrypt); // 正式执行加密操作
			return resultBytes;
		} catch (Exception e) {
		}
		return null;
	}

	/**
	  * @Title: randomKey
	  * @Description: 生成AES秘钥
	  * @return
	  * @return String
	  * @throws
	*/
	public static String randomKey() {
		
		KeyGenerator kgen;
		try {
			kgen = KeyGenerator.getInstance("AES");
	        //设置密钥长度  
	        kgen.init(128);  
	        //生成密钥  
	        SecretKey skey = kgen.generateKey();  
	        //返回密钥的二进制编码  
	        byte[] encodeBytes = skey.getEncoded();
	        return byteToHexString(encodeBytes);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	/**
	 * 解密
	 * 
	 * @param messageBytes
	 * @param passWord
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(byte[] messageBytes, String passWord) {
		String result = "";
		try {
			/* AES算法 */
			SecretKey secretKey = new SecretKeySpec(passWord.getBytes(), "AES");// 获得密钥
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", BouncyCastleProvider.PROVIDER_NAME);
			cipher.init(Cipher.DECRYPT_MODE, secretKey); // 设置工作模式为解密模式，给出密钥
			byte[] resultBytes = cipher.doFinal(messageBytes);// 正式执行解密操作
			result = new String(resultBytes, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			//DLog.e("AES解密错误 key:{},text:{},message:{}", passWord, new String(messageBytes), e.getMessage());
		}
		return result;
	}
	
	public static String decrypt(String messageFromClient, byte[] passWords) {
		String result = "";
		try {
			byte[] messageBytes =  Base64Util.decode(messageFromClient);
			/* AES算法 */
			SecretKey secretKey = new SecretKeySpec(passWords, "AES");// 获得密钥
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", BouncyCastleProvider.PROVIDER_NAME);
			cipher.init(Cipher.DECRYPT_MODE, secretKey); // 设置工作模式为解密模式，给出密钥
			byte[] resultBytes = cipher.doFinal(messageBytes);// 正式执行解密操作
			result = new String(resultBytes, "UTF-8");
		} catch (Exception e) {
		}
		return result;
	}
	
	/**
	  * @Title: byteToHexString
	  * @Description: 二进制byte[]转十六进制string 
	  * @param bytes
	  * @return
	  * @return String
	  * @throws
	*/
	protected static String byteToHexString(byte[] bytes){     
         StringBuffer sb = new StringBuffer();     
         for (int i = 0; i < bytes.length; i++) {     
              String strHex=Integer.toHexString(bytes[i]);     
              if(strHex.length() > 3){     
                     sb.append(strHex.substring(6));     
              } else {  
                   if(strHex.length() < 2){  
                      sb.append("0" + strHex);  
                   } else {  
                      sb.append(strHex);     
                   }     
              }  
         }  
        return  sb.toString();     
    }  
      
    /**
      * @Title: hexStringToByte
      * @Description: 十六进制string转二进制byte[] 
      * @param s
      * @return
      * @return byte[]
      * @throws
    */
    protected static byte[] hexStringToByte(String s) {     
        byte[] baKeyword = new byte[s.length() / 2];     
        for (int i = 0; i < baKeyword.length; i++) {     
            try {     
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));     
            } catch (Exception e) {     
                System.out.println("十六进制转byte发生错误！！！");     
                e.printStackTrace();     
            }     
        }     
        return baKeyword;     
    } 
    
    public static void main(String[] args) {
		
    	String key = randomKey();
    	String message = "fsdfsdfsdfdsfsdf";
    	long start = System.currentTimeMillis();
    	byte [] encrypt = encrypt(message.getBytes(), key);
    	System.out.println((System.currentTimeMillis() - start));
    	System.out.println(decrypt(encrypt,key));
	}
}
