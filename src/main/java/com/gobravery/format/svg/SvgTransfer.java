package com.gobravery.format.svg;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;

import com.gobravery.format.poi.word.WordUtils;

public class SvgTransfer {
	public static final String SVG_CODE_UTF8="UTF-8";
	/**
	 * @param svgCode
	 * @return 返回文件地址
	 */
	public String toPNG(String svgCode){
		String tmpDir = WordUtils.getTempFileDir("svg");
		//
		String tempFile = WordUtils.getTempFileName(".png");
		//临时文件名
		String file = tmpDir+"/"+tempFile;
		//转换
		toPNG(svgCode,file);
		//
		return file;
	}
	/**
	 * @param svgCode
	 * @param toPath
	 */
	public void toPNG(String svgCode,String toPath){
		InputStream is=null;
		OutputStream os=null;
		try {
			byte[] bytes = svgCode.getBytes (SVG_CODE_UTF8);
			//
			is = new ByteArrayInputStream(bytes);
			TranscoderInput inp =new TranscoderInput ( is);
			//
			File of =new File(toPath);
			os = new FileOutputStream(of);
			//
			toPNG(inp,os);
		} catch (UnsupportedEncodingException e) {
			
			throw new RuntimeException("UTF-8编码问题:"+svgCode,e);
		} catch (FileNotFoundException e) {
			
			throw new RuntimeException("IO异常，可能输出文件有问题:"+toPath,e);
		} catch (IOException e) {
			
			throw new RuntimeException("IO异常，可能输出文件有问题:"+toPath,e);
			//
		} catch (TranscoderException e) {
			throw new RuntimeException("svg转换异常，可能不是标准的svg文件",e);
		}finally{
			try {
				if(is!=null){
					is.close();
				}
				if(os!=null){
					os.close();
				}
			} catch (IOException e) {
				throw new RuntimeException("流关闭异常",e);
			}
		}
		//
		
	}
	public void toPNG(TranscoderInput input,OutputStream opts) throws IOException, TranscoderException{
		TranscoderOutput output = new TranscoderOutput (opts);
		//
		PNGTranscoder t = new PNGTranscoder ();
		t.transcode(input, output);
		opts.flush ();
		
	}
}
