package com.sjtu.ist.charge.QRcode;

import java.io.File;
import java.util.Hashtable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;


public class Generate {
	protected BitMatrix generateBitMatrix(String content,String path){
		try{
			MultiFormatWriter multifromatwriter = new MultiFormatWriter();
			//Map<EncodeHintType, String> hints = new HashMap<EncodeHintType,String>();
			Hashtable hints = new Hashtable();
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
			BitMatrix bitmatrix = multifromatwriter.encode(content, BarcodeFormat.QR_CODE,500 , 500,hints);			
			//File file = new File(path,"test.jpg");
			//MatrixToImageWriter.writeToFile(bitmatrix, "jpg", file);
			System.out.println("generate successfully");
			return bitmatrix;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 默认当前项目路径下生成二维码目录及二维码图片
	 * @param content
	 */
	public String generateQRCode(String content, int id) {
		// TODO Auto-generated method stub
		//String path = System.getProperty("user.dir") + File.separator +  "QRcode";
		String path = "D:\\Program Files\\apache-tomcat-7.0.65\\webapps\\Charge\\img\\QRcode\\";
		System.out.println("path:"+path);
		return generateQRCode(content, path,id);
	}
	
	/**
	 * 在指定的目录下生成二维码目录及二维码图片
	 * @param content
	 * @param path
	 */
	public String generateQRCode(String content,String path,int id){
		BitMatrix bitmartix = generateBitMatrix(content, path);
		File dir = new File(path);
		if (!dir.exists() || !dir.isDirectory()) {
			dir.mkdir(); 
		}
		File file = new File(dir,id + ".jpg");
		if (file.exists()){
			file.delete();
		}
		try{
			MatrixToImageWriter.writeToFile(bitmartix, "jpg", file);
			System.out.println("writetofile successfully");
			System.out.println("direc:"+file.getAbsolutePath());
			return "/Charge/img/QRcode/"+id+".jpg";
		
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

}
