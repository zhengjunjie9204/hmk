package com.xgx.syzj.secret;

import android.content.Context;


import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;


/**
 * @author lichengming
 * @version 1.0 创建时间：2015年7月7日 下午5:48:39
 * @company 广州新关系科技有限公司
 *
 * 功能描述：RSA加密、解密处理，用于加密传送AES秘钥
 */
public class RSAManager {
	private static final String TAG = "com.gone.secret.RSAManager";

	/**
	 * 私钥
	 */
	private  RSAPrivateKey privateKey;

	/**
	 * 公钥
	 */
	private  RSAPublicKey publicKey;
	
	/**
	 * 公钥串
	 */
	private  String publicKeyString;


	
	/**
	 * 字节数据转字符串专用集合
	 */
	private  final char[] HEX_CHAR= {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

	private static RSAManager instance;
	private Context mContext;

	public static RSAManager getInstance(Context context){
		if(instance == null){
			instance = new RSAManager(context);
		}
		return instance;
	}

	private RSAManager(Context context) {
		mContext = context;
		init();
	}
	/**
	  * @Title: init
	  * @Description: 初始化，公钥、私钥
	  * @return void
	  * @throws
	*/
	public void init() {
		try {
			Security.addProvider(new BouncyCastleProvider());
		} catch (Exception e) {
		}
		try {
			loadPublicKey(initPublicKey("public_key.key"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		


		//加载私钥
		/*
		try {
			loadPrivateKey(initPrivateKey("private_key.pem"));
			DLog.i(TAG,"加载私钥成功");
		} catch (Exception e) {
			e.printStackTrace();
			DLog.e(TAG, e.getMessage());
			DLog.i(TAG,"加载私钥失败");
		}
		*/
	}


	/**
	 * 获取私钥
	 * @return 当前的私钥对象
	 */
	public RSAPrivateKey getPrivateKey() {
		return privateKey;
	}

	/**
	 * 获取公钥
	 * @return 当前的公钥对象
	 */
	public RSAPublicKey getPublicKey() {
		return publicKey;
	}
	
	public String getPublicKeyString() {
		return publicKeyString;
	}


	/**
	  * @Title: loadPublicKey
	  * @Description: 从字符串中加载公钥
	  * @param publicKeyStr 公钥数据字符串
	  * @throws Exception
	  * @return void
	*/
	private void loadPublicKey(String publicKeyStr) throws Exception{
		
		try {
			byte[] buffer= Base64Util.decode(publicKeyStr);
			KeyFactory keyFactory= KeyFactory.getInstance("RSA");
			X509EncodedKeySpec keySpec= new X509EncodedKeySpec(buffer);
			publicKey= (RSAPublicKey) keyFactory.generatePublic(keySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			throw new Exception("公钥非法");
		} catch (NullPointerException e) {
			throw new Exception("公钥数据为空");
		}
	}
	
	private void loadPrivateKey(String privateKeyStr) throws Exception{
		try {
			
			/**
			//PKCS1Padding填充方式
			RSAPrivateKeyStructure asn1PrivKey = new RSAPrivateKeyStructure((ASN1Sequence) ASN1Sequence.fromByteArray(privateKeyStr.getBytes("UTF-8")));  
			RSAPrivateKeySpec rsaPrivKeySpec = new RSAPrivateKeySpec(asn1PrivKey.getModulus(), asn1PrivKey.getPrivateExponent());  
			KeyFactory keyFactory= KeyFactory.getInstance("RSA");  
			privateKey  = (RSAPrivateKey) keyFactory.generatePrivate(rsaPrivKeySpec);
			**/
		
			//PKCS8Padding方式
			byte[] buffer= Base64Util.decode(privateKeyStr);
			PKCS8EncodedKeySpec keySpec= new PKCS8EncodedKeySpec(buffer);
			KeyFactory keyFactory= KeyFactory.getInstance("RSA");
			privateKey= (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			throw new Exception("私钥非法");
		} catch (NullPointerException e) {
			throw new Exception("私钥数据为空");
		}
	}

	/**
	 * 公钥加密
	 * @param str
	 * @return
	 */
	public String encrypt(String str){
		String result = "";
		try {
			result = encryptByPublicKey(str.getBytes("utf-8"));
		}catch (Exception e){
			e.printStackTrace();
			result = "-1";
		}
		return result;
	}

	/**
	 * 公钥解密
	 * @param str
	 * @return
	 */
	public String decrypt(String str){
		String result = "";
		try {
			result = decrypt4StringByPublicKey(str.getBytes("UTF-8"));
		}catch (Exception e){
			e.printStackTrace();
			result = "-1";
		}
		return result;
	}

	/**
	  * @Title: encrypt
	  * @Description: RSA加密
	  * @param plainTextData 明文数据
	  * @return
	  * @return String
	*/
	private String encryptByPublicKey(byte[] plainTextData) {
		try {
			String result = Base64Util.encode(encrypt4ByteByPublicKey(plainTextData));
			return result;
		} catch (Exception e) {
			return null;
		}
	}
	
	
	/**
	  * @Title: encryptByPrivateKey
	  * @Description: 私钥加密
	  * @param plainTextData
	  * @return
	  * @return String
	  * @throws
	*/
	private  String encryptByPrivateKey(byte[] plainTextData) {
		try {
			String result = Base64Util.encode(encrypt4ByteByPrivatekey(plainTextData));
			return result;
		} catch (Exception e) {
			return null;
		}
	}


	private  byte[] encrypt4ByteByPublicKey(byte[] plainTextData) throws Exception{
		if(publicKey== null){
			throw new Exception("加密公钥为空, 请设置");
		}
		try {
			long start = System.currentTimeMillis();
			Cipher encryptCipher= Cipher.getInstance("RSA", BouncyCastleProvider.PROVIDER_NAME);
			encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] output= encryptCipher.doFinal(plainTextData);
//			long end = System.currentTimeMillis();
//			DebugLog.i("RSA加密花费时间:{"+(end - start)+"}");
			return output;
		} catch (IllegalBlockSizeException e) {
			throw new Exception("明文长度非法");
		} catch (BadPaddingException e) {
			throw new Exception("明文数据已损坏");
		}
	}
	
	/**
	  * @Title: encrypt4ByteByPrivatekey
	  * @Description: 私钥加密
	  * @param plainTextData
	  * @return
	  * @throws Exception
	  * @return byte[]
	  * @throws
	*/
	private  byte[] encrypt4ByteByPrivatekey(byte[] plainTextData) throws Exception{
		
		if(privateKey == null){
			throw new Exception("加密私钥为空, 请设置");
		}
		try {
			long start = System.currentTimeMillis();
			Cipher encryptCipher= Cipher.getInstance("RSA", BouncyCastleProvider.PROVIDER_NAME);
			encryptCipher.init(Cipher.ENCRYPT_MODE, privateKey);
			byte[] output= encryptCipher.doFinal(plainTextData);
			long end = System.currentTimeMillis();
			return output;
		} catch (IllegalBlockSizeException e) {
			throw new Exception("明文长度非法");
		} catch (BadPaddingException e) {
			throw new Exception("明文数据已损坏");
		}
	}
	

	/**
	 * 解密过程
	 * @param cipherData 密文数据
	 * @return 明文
	 * @throws Exception 解密过程中的异常信息
	 */
	private  byte[] decryptByPrivateKey(byte[] cipherData) throws Exception{
		if (privateKey== null){
			throw new Exception("解密私钥为空, 请设置");
		}
		try {
			long start = System.currentTimeMillis();
			Cipher decryptCipher= Cipher.getInstance("RSA", BouncyCastleProvider.PROVIDER_NAME);
			decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] output= decryptCipher.doFinal(cipherData);
			long end = System.currentTimeMillis();
			return output;
		} catch (IllegalBlockSizeException e) {
			throw new Exception("密文长度非法");
		} catch (BadPaddingException e) {
			throw new Exception("密文数据已损坏");
		}		
	}
	
	
	/**
	  * @Title: decrypt4StringByPrivateKey
	  * @Description: RSA私钥解密
	  * @param cipherData
	  * @return
	  * @throws Exception
	  * @return String
	  * @throws
	*/
	private  String decrypt4StringByPrivateKey(byte[] cipherData) throws Exception {
		
		System.out.println("cipherData : " + cipherData.length);
		byte[] output = decryptByPrivateKey(cipherData);
		
		System.out.println("output : " + output.length);
		return new String(output, "UTF-8");
	}
	
	
	/**
	  * @Title: decryptByPublicKey
	  * @Description: RSA公钥解密
	  * @param cipherData
	  * @return
	  * @throws Exception
	  * @return byte[]
	  * @throws
	*/
	private  byte[] decryptByPublicKey(byte[] cipherData) throws Exception{
		if (publicKey== null){
			throw new Exception("解密公钥为空, 请设置");
		}
		try {
			long start = System.currentTimeMillis();
			Cipher decryptCipher= Cipher.getInstance("RSA", BouncyCastleProvider.PROVIDER_NAME);
			decryptCipher.init(Cipher.DECRYPT_MODE, publicKey);
			byte[] output= decryptCipher.doFinal(cipherData);
			long end = System.currentTimeMillis();
			return output;
		} catch (IllegalBlockSizeException e) {
			throw new Exception("密文长度非法");
		} catch (BadPaddingException e) {
			throw new Exception("密文数据已损坏");
		}		
	}
	
	
	/**
	  * @Title: decrypt4StringByPublicKey
	  * @Description: RSA公钥解密
	  * @param cipherData
	  * @return
	  * @throws Exception
	  * @return String
	  * @throws
	*/
	private String decrypt4StringByPublicKey(byte[] cipherData) throws Exception {
		
		byte[] output = decryptByPublicKey(cipherData);
		return new String(output, "UTF-8");
	}


	/**
	  * @Title: byteArrayToString
	  * @Description:字节数据转十六进制字符串
	  * @param data	输入数据
	  * @return	十六进制内容
	  * @return String
	  * @throws
	*/
	private String byteArrayToString(byte[] data){
		StringBuilder stringBuilder= new StringBuilder();
		for (int i=0; i<data.length; i++){
			//取出字节的高四位 作为索引得到相应的十六进制标识符 注意无符号右移
			stringBuilder.append(HEX_CHAR[(data[i] & 0xf0)>>> 4]);
			//取出字节的低四位 作为索引得到相应的十六进制标识符
			stringBuilder.append(HEX_CHAR[(data[i] & 0x0f)]);
			if (i<data.length-1){
				stringBuilder.append(' ');
			}
		}
		return stringBuilder.toString();
	}
	
	private String initPrivateKey(String keyPath) throws IOException {
		InputStream is = mContext.getAssets().open(keyPath);
		BufferedReader br = new BufferedReader(new InputStreamReader(is));  
        String s = br.readLine();  
        StringBuffer privatekey = new StringBuffer();  
        s = br.readLine();  
        while (s.charAt(0) != '-') {  
            privatekey.append(s + "\r");  
            s = br.readLine();  
        }  
        return privatekey.toString();
	}
	
	private String initPublicKey(String keyPath) throws IOException {

		InputStream is = mContext.getAssets().open(keyPath);
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String s = br.readLine();
        StringBuffer publicKeyBuffer = new StringBuffer();  
        s = br.readLine();  
        while (s.charAt(0) != '-') {  
            publicKeyBuffer.append(s + "\r");  
            s = br.readLine();  
        }  
        publicKeyString = publicKeyBuffer.toString();
        return publicKeyString;
	}
	
//	public static void main(String[] args) throws Exception {
//
//		RSAManager.init();
//
//		//String str = "{\"nickName\":\"15920421919\",\"psw\":\"e10adc3949ba59abbe56e057f20f883e\",\"tel\":\"15920421919\",\"code\":\"123456\"}";
//
//		//String base64Encode = RSAManager.encrypt(str.getBytes());
//
//		//String base64Encode = "QLVdp2qc66p92SjiPWGSdU9oPaNIH+X44MRmhVmdw6ij5LacDESJ6E8zNpqlM1z6vbyy4Z7rLUoiAjfzcmdBP6ZBPYZ9idrkTENAfGhbXbbFRdWaPJbYSisGAtLgiKvbfzdxOpesTrN/O5n0eTmFTC/g+4tVhDisYRVx5FYKp8g=";
//
//		//String base64Encode = "QLVdp2qc66p92SjiPWGSdU9oPaNIH+X44MRmhVmdw6ij5LacDESJ6E8zNpqlM1z6vbyy4Z7rLUoiAjfzcmdBP6ZBPYZ9idrkTENAfGhbXbbFRdWaPJbYSisGAtLgiKvbfzdxOpesTrN/O5n0eTmFTC/g+4tVhDisYRVx5FYKp8g=";
//
//		//System.out.println("plainText : " + RSAManager.decrypt4String(Base64Util.decode(base64Encode)));
//
//		String str = "{\"code\":\"123456\",\"nickName\":\"18024125176\",\"psw\":\"e10adc3949ba59abbe56e057f20f883e\",\"tel\":\"78965412332\"}";
//
//		String strEncrypt = RSAManager.encryptByPrivateKey(str.getBytes("UTF-8"));
//		System.out.println("base64 encode : " + strEncrypt);
//
//		String df = "CPSYEoLTVzKXBqdArjINy5FZAlLfoZMVeIMuLT9qnSzGNCJ0bIQyfnSIXaKrxSXKiJAx86WrYcifXYRuE4E+UC88x3E5GVRYjcc4HDcryf1SGBXrd3V+j4ABFUMBLI0dFDP4+3Hi0kbRvBBFYsj0UkNfw290PkguqB2WqiZEkMs=";
//		System.out.println("ios : " + RSAManager.decrypt4StringByPrivateKey(Base64Util.decode(df)));
//		//System.out.println("plainText2 : " + RSAManager.decrypt4String(Base64Util.decode(strEncrypt)));
//	}
}