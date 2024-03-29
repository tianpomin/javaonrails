package me.jor.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import me.jor.common.CommonConstant;

public class MessageDigestUtil{
	public static final int MESSAGE_DIGEST_OUTPUT_TYPE_BASE64=0;
	public static final int MESSAGE_DIGEST_OUTPUT_TYPE_HEX=1;
	public static final String MESSAGE_DIGEST_MD2="md2";
	public static final String MESSAGE_DIGEST_MD5="md5";
	public static final String MESSAGE_DIGEST_SHA="sha";
	public static final String MESSAGE_DIGEST_SHA1="sha-1";
	public static final String MESSAGE_DIGEST_HMAC_SHA1="hmac-sha1";
	private static final char[] HEXBUF=new char[]{'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};

	public static String messageDigest(long src, String algorithm, int outputType){
		byte[] b=new byte[8];
		for(int i=0;i<8;i++){
			b[i]=(byte)(src>>>(7-i)*8);
		}
		return messageDigest(b,algorithm, outputType);
	}
	
	public static String messageDigest(String src, String charset, String algorithm, int outputType){
		try{
			return messageDigest(src.getBytes(charset), algorithm, outputType);
		}catch(UnsupportedEncodingException e){
			throw new SecurityException(e);
		}
	}
	public static String md5Hex(String src, String charset){
		return messageDigest(src,charset,MESSAGE_DIGEST_MD5,MESSAGE_DIGEST_OUTPUT_TYPE_HEX);
	}
	public static String md5Base64(String src, String charset){
		return messageDigest(src,charset,MESSAGE_DIGEST_MD5,MESSAGE_DIGEST_OUTPUT_TYPE_BASE64);
	}
	public static String md5Hex(String src){
		return messageDigest(src,CommonConstant.DEFAULT_CHARSET,MESSAGE_DIGEST_MD5,MESSAGE_DIGEST_OUTPUT_TYPE_HEX);
	}
	public static String md5Base64(String src){
		return messageDigest(src,CommonConstant.DEFAULT_CHARSET,MESSAGE_DIGEST_MD5,MESSAGE_DIGEST_OUTPUT_TYPE_BASE64);
	}
	public static String shaHex(String src, String charset){
		return messageDigest(src,charset,MESSAGE_DIGEST_SHA,MESSAGE_DIGEST_OUTPUT_TYPE_HEX);
	}
	public static String shaBase64(String src, String charset){
		return messageDigest(src,charset,MESSAGE_DIGEST_SHA,MESSAGE_DIGEST_OUTPUT_TYPE_BASE64);
	}
	public static String shaHex(String src){
		return messageDigest(src,CommonConstant.DEFAULT_CHARSET,MESSAGE_DIGEST_SHA,MESSAGE_DIGEST_OUTPUT_TYPE_HEX);
	}
	public static String shaBase64(String src){
		return messageDigest(src,CommonConstant.DEFAULT_CHARSET,MESSAGE_DIGEST_SHA,MESSAGE_DIGEST_OUTPUT_TYPE_BASE64);
	}
	public static byte[] messageDigest(String src, String algorithm) throws UnsupportedEncodingException{
		return messageDigest(src,CommonConstant.DEFAULT_CHARSET,algorithm);
	}
	public static byte[] messageDigest(String src, String charset, String algorithm) throws UnsupportedEncodingException{
		return messageDigest(src.getBytes(charset),algorithm);
	}
	public static byte[] md5(String src) throws UnsupportedEncodingException{
		return messageDigest(src,MESSAGE_DIGEST_MD5);
	}
	public static byte[] sha(String src) throws UnsupportedEncodingException{
		return messageDigest(src,MESSAGE_DIGEST_SHA);
	}
	public static byte[] messageDigest(byte[] src, String algorithm){
		try{
			return MessageDigest.getInstance(algorithm).digest(src);
		}catch(NoSuchAlgorithmException e){
			throw new SecurityException(e);
		}
	}
	public static byte[] md5(byte[] src){
		return messageDigest(src,MESSAGE_DIGEST_MD5);
	}
	public static byte[] sha(byte[] src){
		return messageDigest(src,MESSAGE_DIGEST_SHA);
	}
	public static String messageDigest(byte[] src, String algorithm, int outputType){
		return output(messageDigest(src,algorithm),outputType);
	}
	private static String output(byte[] src, int outputType){
		switch(outputType){
		case MESSAGE_DIGEST_OUTPUT_TYPE_BASE64:
			return Base64.encode(src);
		case MESSAGE_DIGEST_OUTPUT_TYPE_HEX:
			StringBuilder result=new StringBuilder();
			int bitmask=0xf;
			for(int i=0,l=src.length;i<l;i++){
				byte b=src[i];
				//0x00&bitmask==>>"0" 0x0a&bitmask==>>"a"
				//but we want 0x00&bitmask==>>"00"
				//(b>>4)&bitmask is necessary, or b>>>4 will be a very large number if b is less than 0
				result.append(HEXBUF[(b>>4)&bitmask]).append(HEXBUF[b&bitmask]);
			}
			return result.toString();
		default:
			throw new IllegalArgumentException("illegal outputType value, only MessageDigestUtil.MESSAGE_DIGEST_OUTPUT_TYPE_BASE64 and MessageDigestUtil.MESSAGE_DIGEST_OUTPUT_TYPE_HEX are accepted");
		}
	}
	public static byte[] hmac(byte[] src, String algorithm) throws InvalidKeyException, NoSuchAlgorithmException{
		SecretKeySpec signingKey = new SecretKeySpec(src, algorithm);  
        Mac mac = Mac.getInstance(algorithm);  
        mac.init(signingKey);  
        return mac.doFinal(src);
	}
	public static String hmac(String src, String algorithm,int outputType, String charset) throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException{
		return output(hmac(src.getBytes(charset),algorithm),outputType);
	}
	public static String hmacBase64(String src,String algorithm,String charset) throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException{
		return hmac(src,algorithm,MessageDigestUtil.MESSAGE_DIGEST_OUTPUT_TYPE_BASE64,charset);
	}
	public static String hmacHex(String src,String algorithm,String charset) throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException{
		return hmac(src,algorithm,MessageDigestUtil.MESSAGE_DIGEST_OUTPUT_TYPE_HEX,charset);
	}
	
	public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
		System.out.println(shaHex("1332004436").toUpperCase());
		SecretKeySpec signingKey = new SecretKeySpec("a".getBytes("UTF-8"), "hmac-sha1");  
        Mac mac = Mac.getInstance("hmac-sha1");  
        mac.init(signingKey);  
        byte[] rawHmac = mac.doFinal("1332004436".getBytes("UTF-8"));
        System.out.println(Base64.encode(rawHmac));
//		String[] pwds=new String[]
//		         {"666666","888888","123456","root","root1357","root123","root1234","rootroot",
//				  "admin","adminroot","rootadmin","adminadmin","admin123","admin1234","admin1357"};
//		for(String pwd:pwds){
//			System.out.println(pwd+"\t"+messageDigest(pwd,"utf8","md5",MESSAGE_DIGEST_OUTPUT_TYPE_BASE64)+" "+messageDigest(pwd,"utf8","md5",MESSAGE_DIGEST_OUTPUT_TYPE_HEX));
//		}
//		long s=System.nanoTime();
//		for(int i=0;i<1000000;i++){
//			messageDigest("","utf8","md5",MESSAGE_DIGEST_OUTPUT_TYPE_BASE64);
//		}
//		System.out.println((System.nanoTime()-s)/1000000);
//		System.out.println(org.apache.commons.codec.digest.DigestUtils.shaHex("abcde"));
//		System.out.println(MessageDigestUtil.shaHex("abcde"));
	}
}