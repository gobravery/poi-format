package com.gobravery.format.poi.excel;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public final class D {
    public D() {

    }

    /** *//**
     * 把图片印刷到图片上
     *
     * @param pressImg --
     *            水印文件
     * @param targetImg --
     *            目标文件
     * @param x
     *            --x坐标
     * @param y
     *            --y坐标
     * @param alpha
     *           --透明度     
     */
    public static BufferedImage pressImage(BufferedImage pressImg, BufferedImage targetImg,
            int x, int y,float alpha) {
        try {
            //目标文件
            /*File _file = new File(targetImg);
            Image src = ImageIO.read(_file);*/
            //int width = targetImg.getWidth();
            //int height = targetImg.getHeight();
            /*BufferedImage image = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);*/
            Graphics2D g = targetImg.createGraphics();
            //g.drawImage(targetImg, 0, 0, width, height, null);
            //g.drawString("娃哈哈.", 18, 18);
            //g.drawImage(bufferedimage, new AffineTransform(1f,0f,0f,1f,x,y), i, j);
            //水印文件
            //File _filebiao = new File(pressImg);
            //Image src_biao = ImageIO.read(_filebiao);
            int width_biao = pressImg.getWidth(null);
            int height_biao = pressImg.getHeight(null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
            		alpha));           
            g.drawImage(pressImg, x,
                        y, width_biao, height_biao, null);
            //水印文件结束
            g.dispose();
            FileOutputStream os = new FileOutputStream("D:/ooyyyeImg.png");
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
            encoder.encode(targetImg);
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return targetImg;
    }

    /** *//**
     * 打印文字水印图片
     *
     * @param pressText
     *            --文字
     * @param targetImg --
     *            目标图片
     * @param fontName --
     *            字体名
     * @param fontStyle --
     *            字体样式
     * @param color --
     *            字体颜色
     * @param fontSize --
     *            字体大小
     * @param x --
     *            偏移量
     * @param y
     */

    public static BufferedImage pressText(String pressText, BufferedImage targetImg,
            String fontName, int fontStyle, int color, int fontSize, int x,
            int y,float alpha) {
        try {
            /*File _file = new File(targetImg);
            Image src = ImageIO.read(_file);
            int width = src.getWidth(null);
            int height = src.getHeight(null);*/
	        //int width = targetImg.getWidth();
	        //int height = targetImg.getHeight();
            /*BufferedImage targetImg = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);*/
            Graphics2D g = targetImg.createGraphics();
            //g.drawImage(targetImg, 0, 0, width, height, null);
            g.setColor(Color.BLACK);
            g.setFont(new Font(fontName, fontStyle, fontSize));
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
            		alpha)); 
            g.drawString(pressText, x, y);
            g.dispose();
            FileOutputStream out = new FileOutputStream("D:/ooyyyeText.png");
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            encoder.encode(targetImg);
            out.close();
        } catch (Exception e) {
            //System.out.println(e);
        }
        return targetImg;
    }

    public static void main(String[] args) {
        /*pressImage("E:\\person\\foshan\\leaderName\\zhangzhongdong.bmp", "E:\\index.jpg", 300, 400,0.8f);
        pressText("http://www.csscis.com:毛", "E:\\index.jpg","隶书",12,12,12, 500, 200,0.8f);*/
        //System.out.print("添加成功");
    }
} 