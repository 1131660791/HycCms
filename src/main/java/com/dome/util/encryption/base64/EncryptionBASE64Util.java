package com.dome.util.encryption.base64;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
/**
 * 信息加密 解密工具类 BASE64
 * @author hzw
 */
public class EncryptionBASE64Util {

	private static Cipher enCipher;
	private static Cipher deCipher;
	public static final String KEY = "sclbxkey";
	public static final String VI = "sclbxkey";

	static {
		try {
			byte[] keyByte = KEY.getBytes();
			byte[] viByte =  VI.getBytes();
			// 创建md5散列对象
			MessageDigest md = MessageDigest.getInstance("MD5");
			// 散列密钥
			md.update(keyByte);
			byte[] key = md.digest();
			/*
			 * byte[] key2 = new byte[8]; for (int i = 0; i < 8; i++) { key2[i]
			 * = key[i]; } for (byte b : key) { System.out.println(b); }
			 */
			// System.out.println("key.length："+key.length);
			// 获得des密钥
			DESKeySpec desKey = new DESKeySpec(key);
			// 获得des加密密钥工厂
			SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
			// 获得加密后的des密钥
			SecretKey seKey = skf.generateSecret(desKey);
			// 获得初始化向量对象
			IvParameterSpec ivP = new IvParameterSpec(viByte);
			AlgorithmParameterSpec paramSpec = ivP;
			// 为加密算法指定填充方式，创建加密会话对象
			enCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			deCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			// 初始化加密回话对象
			enCipher.init(Cipher.ENCRYPT_MODE, seKey, paramSpec);
			deCipher.init(Cipher.DECRYPT_MODE, seKey, paramSpec);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see 信息加密  BASE64
	 * @param String
	 *            mes 要加密的信息
	 * @return String 返回加密后的信息
	 */
	public static String encrypt(String mes) {
		if (mes == null || "".equals(mes)) {
			return null;
		}
		byte[] data = mes.getBytes();
		byte[] enc = null;
		/*
		 * System.out.println("转化前："); for (byte b : data) {
		 * System.out.print(b); } System.out.println();
		 */
		synchronized (enCipher) {
			try {
				enc = enCipher.doFinal(data);
			} catch (IllegalBlockSizeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BadPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/*
		 * System.out.println("BASE64Encoder处理前"); for (byte b : enc) {
		 * System.out.println(b); } System.out.println();
		 */
		if (enc != null) {
			return new BASE64Encoder().encode(enc);
		} else {
			return null;
		}
	}

	/**
	 * @see 信息解密  BASE64
	 * @param String
	 *            mes 加密后的信息
	 * @return String 返回解密后的信息
	 */
	public static String decrypt(String mes) throws IOException {
		if (mes == null || "".equals(mes)) {
			return null;
		}

		byte[] des = new BASE64Decoder().decodeBuffer(mes);
		byte[] enc = null;

		synchronized (deCipher) {
			try {
				enc = deCipher.doFinal(des);
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			} catch (BadPaddingException e) {
				e.printStackTrace();
			}
		}

		if (enc != null) {
			return new String(enc);
		} else {
			return null;
		}
	}
	
	public static void main(String[] arg) throws Exception {
		// 加密
//		System.out.println(EncryptionBASE64Util.encrypt("阿旺"));

		// 解密
//		System.out.println(EncryptionBASE64Util.decrypt("MyhPBxWyZMM="));
	}
	
	
	/************************************ BASE64 图片 加密 解密 **************************************/
	/**
	 * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
	 * @param imgFilePath - 图片地址
	 * @return 返回 编码后的字符串
	 */
	public static String GetImageStr(String imgFilePath) {
	   byte[] data = null;
	   // 读取图片字节数组
	   try {
	      InputStream in = new FileInputStream(imgFilePath);
	      data = new byte[in.available()];
	      in.read(data);
	      in.close();
	   } catch (IOException e) {
	     e.printStackTrace();
	   }
	   // 对字节数组Base64编码
	   BASE64Encoder encoder = new BASE64Encoder();
	   // 返回Base64编码过的字节数组字符串
	   return encoder.encode(data);
	}
	 
	/**
	 * 对字节数组字符串进行Base64解码并生成图片
	 * @param imgStr -   图片  编码后的 Base64 字符串
	 * @param imgFilePath - 生成后 存放的地址
	 * @return 是否成功 成功 true 失败 false
	 */
	public static boolean GenerateImage(String imgStr, String imgFilePath) {
		
	   if (imgStr == null) // 图像数据为空
	     return false;
	   BASE64Decoder decoder = new BASE64Decoder();
	   try {
	     // Base64解码
		 byte[] bytes = decoder.decodeBuffer(imgStr);
	     for (int i = 0; i < bytes.length; ++i) {
	       if (bytes[i] < 0) {// 调整异常数据
	         bytes[i] += 256;
	       }
	     }
	     // 生成jpeg图片
	     OutputStream out = new FileOutputStream(imgFilePath);
	     out.write(bytes);
	     out.flush();
	     out.close();
	     return true;
	   } catch (Exception e) {
	      return false;
	   }
	   
	}
	

	
	
}
