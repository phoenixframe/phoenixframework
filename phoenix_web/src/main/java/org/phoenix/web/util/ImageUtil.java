package org.phoenix.web.util;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;


public class ImageUtil {
	private final static ImageUtil iu = new ImageUtil();
	private ImageUtil(){
		
	}
	
	public static ImageUtil getInstance() {
		return iu;
	}
	/*
	 * 切割图片
	 * os 切割后的输出流
	 * is 输入流
	 * type 文件的图片类型
	 * x x坐标
	 *  y y坐标
	 * width 宽度
	 * height 高度
	 */
	public void cropImg(OutputStream os,InputStream is,String type,int x,int y,int width,int height) {
		Image img = null;
		BufferedImage imgBuf = null;
		ImageFilter cropFilter = null;
		try {
			imgBuf = ImageIO.read(is);
			cropFilter = new CropImageFilter(x,y,width,height);
			img = Toolkit.getDefaultToolkit().
					createImage(new FilteredImageSource(imgBuf.getSource(), cropFilter)); 
			BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			tag.getGraphics().drawImage(img, 0, 0, null);
			ImageIO.write(tag, type, os); 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(os!=null) os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(is!=null) is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

/*
 * 切割图
 * os 切割后的输出流
 * is 输入流
 *  type 文件的图片类型
 * x x坐标
 *  y y坐标
 *  width 宽度
 * height 高度
 */
public String cropImg(String oPath,InputStream is,int x,int y,int width,int height) {
	String type = oPath.substring(oPath.lastIndexOf(".")+1);
	try {
		OutputStream os = new FileOutputStream(oPath);
		cropImg(os, is,type,x, y, width, height);
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}
	return null;
}

/*
 * 切割图片
 * oPath 输出文件
 * iPath 输入文件名
 * type 文件的图片类型
 *  x x坐标
 *  y y坐标
 * width 宽度
 *  height 高度
 */
public String cropImg(String oPath,String iPath,int x,int y,int width,int height) {
	String type = oPath.substring(oPath.lastIndexOf(".")+1);
	try {
		OutputStream os = new FileOutputStream(oPath);
		InputStream is = new FileInputStream(iPath);
		cropImg(os, is,type,x, y, width, height);
		return oPath;
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}
	return null;
}

/*
 * 切割图片自动根据输入的文件名转换为xxx_small.type
 *  iPath 输入文件名
 * type 文件的图片类型
 * x x坐标
 * y y坐标
 * width 宽度
 * height 高度
 */
public String cropImg(String iPath,int x,int y,int width,int height) {
	String type = iPath.substring(iPath.lastIndexOf(".")+1);
	String sname = generatorSmallFileName(iPath);
	try {
		InputStream is = new FileInputStream(iPath);
		OutputStream os = new FileOutputStream(sname);
		cropImg(os, is,type,x, y, width, height);
		return sname;
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}
	return null;
}

private String generatorSmallFileName(String name) {
	String fn = name.substring(0,name.lastIndexOf("."));
	return name.replace(fn,fn+"_small");
}
	
	/*
	 * 转换图片操作
	 */
	public void compressImg(OutputStream os,InputStream is,int width,int height,boolean proportion ) {
		compressImg(os, is, width, height, proportion,false);
	}
	
	/*
	 * 转换图片操作
	 * os 要转换图片的输出流
	 *  is 要转换图片的输入流
	 * param width 要压缩的宽度
	 * param height 要压缩的高度
	 * param proportion 是否进行等比例压缩
	 * param magnify 是否进行放大
	 * throws IOException 
	 */
	public void compressImg(OutputStream os,InputStream is,int width,int height,boolean proportion,boolean magnify) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(is);
			System.out.println(img);
			int newWidth; int newHeight; 
			int oldWidth = img.getWidth(null);
			int oldHeight = img.getHeight(null);
			boolean isWrite = false;
			if(!magnify) {
				boolean iw = width>=height?true:false;
				if(iw) {
					if(width>oldWidth) isWrite = true;
				} else {
					if(height>oldHeight) isWrite = true;
				}
//				System.out.println(width+","+height);
//				System.out.println(img.getWidth(null)+","+img.getHeight(null));
				if(isWrite) {
					System.out.println("write");
					ImageIO.write(img,"jpg", os);
					os.flush();
				}
//				System.out.println(newWidth+","+newHeight);
			}
			if(!isWrite) {
				// 判断是否是等比缩放  
				if (proportion) {  
					// 为等比缩放计算输出的图片宽度及高度  
					double rate1 = ((double) oldWidth) / (double) width + 0.1;  
					double rate2 = ((double) oldHeight) / (double) height + 0.1; 
					System.out.println((rate1+","+rate2));
					// 根据缩放比率大的进行缩放控制  
					double rate = rate1 < rate2 ? rate1 : rate2;  
					newWidth = (int) (((double) img.getWidth(null)) / rate);  
					newHeight = (int) (((double) img.getHeight(null)) / rate);  
				} else {  
					newWidth = width; // 输出的图片宽度  
					newHeight = height; // 输出的图片高度  
				}
				BufferedImage tag = new BufferedImage((int) newWidth, (int) newHeight, BufferedImage.TYPE_INT_RGB);
				tag.getGraphics().drawImage(img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);  
				//JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
				//encoder.encode(tag);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(os!=null) os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(is!=null) is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * 转换图片操作
	 * param outputFile 输出文件
	 * param inputFile 输入文件
	 * param width 要压缩的宽度
	 * param height 要压缩的高度
	 * param proportion 是否进行等比例压缩
	 * throws IOException 
	 */
	public String compressImg(File outputFile, File inputFile,int width,int height,boolean proportion ) {
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new FileInputStream(inputFile);
			os = new FileOutputStream(outputFile);
			compressImg(os, is, width, height, proportion);
			return outputFile.getAbsolutePath();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * 转换图片操作
	 * param outputFile 输出文件
	 * param is 输入流
	 * param width 要压缩的宽度
	 * param height 要压缩的高度
	 * param proportion 是否进行等比例压缩
	 * throws IOException 
	 */
	public String compressImg(File outputFile, InputStream is,int width,int height,boolean proportion ) {
		OutputStream os = null;
		try {
			os = new FileOutputStream(outputFile);
			compressImg(os, is, width, height, proportion);
			return outputFile.getAbsolutePath();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * 转换图片操作
	 * param oPath 输出路径
	 * param is 输入流
	 * param width 要压缩的宽度
	 * param height 要压缩的高度
	 * param proportion 是否进行等比例压缩
	 * throws IOException 
	 */
	public String compressImg(String oPath, InputStream is,int width,int height,boolean proportion ) {
		OutputStream os = null;
		try {
			os = new FileOutputStream(oPath);
			compressImg(os, is, width, height, proportion);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return oPath;
	}
	
	/*
	 * 转换图片操作
	 * param oPath 输出路径
	 * param iPath 输入路径
	 * param width 要压缩的宽度
	 * param height 要压缩的高度
	 * param proportion 是否进行等比例压缩
	 * throws IOException 
	 */
	public String compressImg(String oPath, String iPath,int width,int height,boolean proportion ) {
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new FileInputStream(iPath);
			os = new FileOutputStream(oPath);
			compressImg(os, is, width, height, proportion);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return oPath;
	}
	
	
	
	public int getWidth(InputStream is) throws IOException {
		BufferedImage img = null;
		img = ImageIO.read(is);
		return img.getWidth(null);
	}
	
	public int getWidth(String path) {
		InputStream is = null;
		try {
			is = new FileInputStream(path);
			return getWidth(is);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int getWidth(File file) {
		InputStream is = null;
		try {
			is = new FileInputStream(file);
			return getWidth(is);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int getHeight(InputStream is) throws IOException {
		BufferedImage img = null;
		img = ImageIO.read(is);
		return img.getHeight(null);
	}
	
	public int getHeight(String path) {
		InputStream is = null;
		try {
			is = new FileInputStream(path);
			return getHeight(is);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int getHeight(File file) {
		InputStream is = null;
		try {
			is = new FileInputStream(file);
			return getHeight(is);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}
	/*
	 * 返回一个数组，第一个值是宽，第二个值是高
	 * param is
	 * return 返回一个数组，第一个值是宽，第二个值是高
	 * throws IOException
	 */
	public int[] getWidthAndHeight(InputStream is) throws IOException {
		Image img = null;
		img = ImageIO.read(is);
		return new int[]{img.getWidth(null),img.getHeight(null)};
	}
	/*
	 * 返回一个数组，第一个值是宽，第二个值是高
	 * param is
	 * return 返回一个数组，第一个值是宽，第二个值是高
	 * throws IOException
	 */
	public int[] getWidthAndHeight(String path) {
		InputStream is = null;
		try {
			is = new FileInputStream(path);
			return getWidthAndHeight(is);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/*
	 * 返回一个数组，第一个值是宽，第二个值是高
	 * param is
	 * return 返回一个数组，第一个值是宽，第二个值是高
	 * throws IOException
	 */
	public int[] getWidthAndHeight(File file) {
		InputStream is = null;
		try {
			is = new FileInputStream(file);
			return getWidthAndHeight(is);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
