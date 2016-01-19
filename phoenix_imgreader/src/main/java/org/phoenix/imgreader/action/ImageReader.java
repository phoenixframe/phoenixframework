package org.phoenix.imgreader.action;

import org.phoenix.imgreader.helper.ImageBase64Encoder;
import org.phoenix.imgreader.ocr.OcrImgReader;

/**
 * 图片识别实现类
 * @author mengfeiyang
 *
 */
public class ImageReader implements IImageReader{

	/*
	 * (non-Javadoc)
	 * @see org.phoenix.imgreader.action.IImageReader#configImageReader(java.lang.String)
	 */
	@Override
	public ImageReader configImageReader(String tesseractPath) {
		OcrImgReader.setTesseractPath(tesseractPath);	
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.phoenix.imgreader.action.IImageReader#readUrlImage(java.lang.String)
	 */
	@Override
	public String readUrlImage(String url) {
		return OcrImgReader.readUrlImage(url);
	}

	/*
	 * (non-Javadoc)
	 * @see org.phoenix.imgreader.action.IImageReader#readLocalImage(java.lang.String, java.lang.String)
	 */
	@Override
	public String readLocalImage(String imgPath,String imgFormat) {
		return OcrImgReader.readLocalImage(imgPath, imgFormat);
	}

	/*
	 * (non-Javadoc)
	 * @see org.phoenix.imgreader.action.IImageReader#getUrlImageBase64Code(java.lang.String)
	 */
	@Override
	public String getUrlImageBase64Code(String imgUrlPath) {
		return ImageBase64Encoder.getUrlImgBase64Code(imgUrlPath);
	}

	/*
	 * (non-Javadoc)
	 * @see org.phoenix.imgreader.action.IImageReader#getLocalImageBase64Code(java.lang.String)
	 */
	@Override
	public String getLocalImageBase64Code(String imgLocalPath) {
		return ImageBase64Encoder.getLocalImgBase64Code(imgLocalPath);
	}

	/*
	 * (non-Javadoc)
	 * @see org.phoenix.imgreader.action.IImageReader#ImageBase64Decoder(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean ImageBase64Decoder(String imgBase64Code, String imgOutputPath) {
		return ImageBase64Encoder.decoderImage(imgBase64Code, imgOutputPath);
	}

}
