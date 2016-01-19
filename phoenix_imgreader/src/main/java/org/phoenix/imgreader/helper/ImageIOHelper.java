package org.phoenix.imgreader.helper;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

import com.github.jaiimageio.plugins.tiff.TIFFImageWriteParam;


public class ImageIOHelper {  
      
    public static File createImage(File imageFile, String imageFormat) {  
        File tempFile = null;  
        try {  
            Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName(imageFormat);  
            ImageReader reader = (ImageReader)readers.next();  
          
            ImageInputStream iis = ImageIO.createImageInputStream(imageFile);  
            reader.setInput(iis);  
            //Read the stream metadata  
            IIOMetadata streamMetadata = reader.getStreamMetadata();  
              
            //Set up the writeParam  
            TIFFImageWriteParam tiffWriteParam = new TIFFImageWriteParam(Locale.CHINESE);  
            tiffWriteParam.setCompressionMode(ImageWriteParam.MODE_DISABLED);  
              
            //Get tif writer and set output to file  
            Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("tiff");  
            ImageWriter writer = (ImageWriter) writers.next();  
              
            BufferedImage bi = reader.read(0);  
            IIOImage image = new IIOImage(bi,null,reader.getImageMetadata(0));  
            tempFile = tempImageFile(imageFile);  
            ImageOutputStream ios = ImageIO.createImageOutputStream(tempFile);  
            writer.setOutput(ios);  
            writer.write(streamMetadata, image, tiffWriteParam);  
            ios.close();  
              
            writer.dispose();  
            reader.dispose();  
              
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return tempFile;  
    }  
  
    private static File tempImageFile(File imageFile) {  
        String path = imageFile.getPath();  
        StringBuffer strB = new StringBuffer(path);  
        strB.insert(path.lastIndexOf('.'),0);  
        return new File(strB.toString().replaceFirst("(?<=//.)(//w+)$", "tif"));  
    }  
  
}

