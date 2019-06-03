package com.gobravery.format.poi.excel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.gobravery.format.poi.excel.row.RowPostCallBack;
import com.gobravery.format.poi.excel.row.RowSpanPostCall;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ExcelUtils {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static String appNativePath = "";
	public static int curListIndex=0;
	public static void export(HSSFWorkbook tpl, int sheetIndex, ExcelBuilderConfig cfg, JSONObject obj, ExcelBuilderCallback cb) {
		HSSFSheet ws = tpl.getSheetAt(sheetIndex);
		if(cb != null) cb.preBuild(ws);
		ExcelBuilder builder = new ExcelBuilder(ws);
		for(SimplePropertyConfig config : cfg.getSimplePropertyConfig()) {
			Object value = toCellValue(obj, config.getPropertyName(), config.getCellType());
			//	判断是否需要电子签名
			int[] cSize = insertImgForLeaders(tpl, value!=null?(String)value:"", (short) config.getRowIndex(), config.getCellIndex());
			if(cSize != null){
				HSSFRow nr = ws.getRow(config.getRowIndex());
				//	ws.setColumnWidth(config.getCellIndex(), (int)((cSize[0]) * 32));
				//nr.setHeight((short)((cSize[1]) * 14.3));
				value = "";
			}
			builder.buildSimpleProperty(config.getRowIndex(), config.getCellIndex(), value);
		}
		//删除的行
		int deledIndex=0;
		//
		for(ListPropertyConfig config : cfg.getListPropertyConfig()) {
			JSONArray arr = obj.getJSONArray(config.getPropertyName());
			System.out.println(arr.size());
			if(arr != null) {
				builder.prepareRows(config.getTplRow()-deledIndex, arr.size());
				for(int i = 0; i < arr.size(); i++) {
					JSONObject item = arr.getJSONObject(i);
					curListIndex=i+1;
					for(ListItemConfig itemConfig : config.getItemConfig()) {
						builder.buildRowProperty(i, (int) itemConfig.getCellIndex(), itemConfig.getColSpan(), toCellValue(item, itemConfig.getPropertyName(), itemConfig.getCellType()));
						
					}
				}
				//如果没有则删除一行
				if(arr.size()<1){
					deledIndex++;
				}
			}
		}
		//行操作
		for(RowPropertyConfig config : cfg.getRowPropertyConfig()) {
			RowPostCallBack rcb=new RowSpanPostCall();
			rcb.postBuild(ws, config);
		}
		//
		if(cb != null) cb.postBuild(ws);
	}
	
	//	电子签名人员
	private static int calcImgLocation(String value, int[] lc, int w, int h){
		
		int count = 1;
		int ni = 0;
		/*while((ni = value.indexOf("\n"))>=0){
			//System.out.println("\n: " + value);
			value.substring(ni+1);
		}*/
		//System.out.println(w + " " + h);
		//	像素
		lc[0] = 50;
		lc[1] = 280*count;
		lc[2] = w;
		lc[3] = h;
		return count;
	}
	
	/**
	 * 获取所有用户电子前面的领导
	 * @return
	 */
	private static List<Map.Entry<Object, Object>> allENames;
	
	private static List<Map.Entry<Object, Object>> getLeaderImgPath(String value){
		List<Map.Entry<Object, Object>> lds = new ArrayList<Map.Entry<Object, Object>>();
		if(allENames == null)
			allENames = new ArrayList<Map.Entry<Object, Object>>(0);
		else
			for (Map.Entry<Object, Object> e : allENames) {
				if(value.indexOf((String)e.getValue()) >= 0){
					lds.add(e);
				}
			}
		return lds;
	}
	
	private static Map.Entry<Object, Object> getLeaderImgPathForExists(String value, List<Map.Entry<Object, Object>> lds){
			for (Map.Entry<Object, Object> e : allENames) {
				if(value.indexOf((String)e.getValue()) >= 0){
					return e;
				}
			}
		return null;
	}
	
	private static int[] insertImgForLeaders(HSSFWorkbook wb, String value, short r, int c) {
	    int   imgWidth   =   350; 
	    int   imgHeight   =   0;
	    ////System.out.println(123123);
		//	获取当前值中所有需要电子签名的领导
		List<Map.Entry<Object, Object>> allLds = getLeaderImgPath(value);
		if(allLds.size() <= 0) return null;
		int baseY = 24;
		int baseX = 20;
		String nv1 = "";
		String[] nvs1 = value.split("\n");
		for (int i=0; i<nvs1.length; i++) {
			nv1 = nvs1[i];
			imgHeight += baseY;
			if(i%2==0){
			}else{
				Map.Entry<Object, Object> nld = getLeaderImgPathForExists(nv1, allLds);
				if(nld != null)
					imgHeight = imgHeight + 69 - baseY + 3;
			}
		}
		BufferedImage bufImg = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
	    Graphics2D g2d = bufImg.createGraphics(); 
	    //g2d.setBackground(Color.BLUE);
	    //g2d.drawImage(bufImg, imgWidth, imgHeight, null);
	    //bufImg = g2d.getDeviceConfiguration().createCompatibleImage(imgWidth, imgHeight, Transparency.OPAQUE);
	    g2d.setBackground(Color.WHITE); 
	    g2d.clearRect(0,0,imgWidth,imgHeight);
	    g2d.dispose();
		//g2d.dispose(); 
		BufferedImage pressImg = null;
		try {
			ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
			String nv = "";
			String[] nvs = value.split("\n");
			int nowHeight = 0;
			for (int j=0; j<nvs.length; j++) {
				nv = nvs[j];
				nowHeight += baseY;
				if(j%2==0){
					bufImg = ENameUtils.pressText(nv,bufImg,"",16,16,16, baseX, nowHeight, 1f);
				}else{
					Map.Entry<Object, Object> nld = getLeaderImgPathForExists(nv, allLds);
					if(nld != null){
						//	//System.out.println(appNativePath + "WEB-INF/leaderImgs/" + (String)nld.getKey());
						//String filePath=appNativePath +File.separatorChar+ "WEB-INF"+File.separatorChar+"leaderImgs"+File.separatorChar+ (String)nld.getKey();
						//File nf = new File(filePath);
						String filPath="leaderImgs/"+(String)nld.getKey();
						InputStream nf=ExcelUtils.class.getClassLoader().getResourceAsStream(filPath);
						pressImg = ImageIO.read(nf);
								//	缩小
								/*if(pressImg.getWidth() > 200){
									pressImg = resize(pressImg, 200, 200);
								}*/
						bufImg = ENameUtils.pressText(nv.replaceAll((String)nld.getValue(), ""), bufImg,"",16,16,16,
								pressImg.getWidth()+ 6, nowHeight+(pressImg.getHeight()/2), 1f);
						bufImg = ENameUtils.pressImage(pressImg, bufImg, baseX, nowHeight - baseY + 3, 1f);
						nowHeight = nowHeight+pressImg.getHeight() - baseY + 3;
					}else{
						bufImg = ENameUtils.pressText(nv,bufImg,"",16,16,16, baseX, nowHeight, 1f);
					}
				}
			}
			ImageIO.write(bufImg, "png", byteArrayOut);
			HSSFSheet sheet1 = wb.getSheetAt(0);
			HSSFPatriarch patriarch = sheet1.createDrawingPatriarch();
			HSSFClientAnchor anchor = new HSSFClientAnchor(5, 5, 0, 0,
					(short)c, r, (short)(c+2),(r+1));
			patriarch.createPicture(anchor, wb.addPicture(byteArrayOut 
                    .toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));
			//patriarch.setCoordinates(1, 1, (int)(imgWidth*13.4), (int)(*13.4));
		} catch (IOException io) {
			io.printStackTrace();
			//System.out.println("io erorr : " + io.getMessage());
		}
		/*g2d = bufImgFinal.createGraphics();
		g2d.drawImage(bufImg, 0, 0, imgWidth, finalRowHeight, null);
		g2d.dispose();*/
		/*try {
			BufferedImage bufImgFinal = resize(bufImg, imgWidth, finalRowHeight);
			FileOutputStream out = new FileOutputStream("D:/ooyyyeFinal.png");
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(bufImgFinal);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ImageFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return new int[]{imgWidth, imgHeight};
	}
	
	
	

	/**
		 * 改变图片的大小
		 * 
		 * @param source
		 *            源文件
		 * @param targetW
		 *            目标长
		 * @param targetH
		 *            目标宽
		 * @return
		 * @throws IOException 
		 */
		public static BufferedImage resize(BufferedImage source ,int targetW, int targetH) throws IOException
		{
			BufferedImage target = null;
			int type = source.getType();
			double sx = (double) targetW / source.getWidth();
			double sy = (double) targetH / source.getHeight();
			// 这里想实现在targetW，targetH范围内实现等比缩放。如果不需要等比缩放
			// 则将下面的if else语句注释即可
			if (sx > sy)
			{
				sx = sy;
				targetW = (int) (sx * source.getWidth());
			}
			else
			{
				sy = sx;
				targetH = (int) (sy * source.getHeight());
			}
			if (type == BufferedImage.TYPE_CUSTOM)
			{ // handmade
				ColorModel cm = source.getColorModel();
				WritableRaster raster = cm.createCompatibleWritableRaster(targetW,
						targetH);
				boolean alphaPremultiplied = cm.isAlphaPremultiplied();
				target = new BufferedImage(cm, raster, alphaPremultiplied, null);
			}
			else{
				//固定宽高，宽高一定要比原图片大
				//target = new BufferedImage(targetW, targetH, type);
				//target = new BufferedImage(800, 600, type);
				target = new BufferedImage(targetW, targetH, BufferedImage.TYPE_INT_RGB);
			}
			Graphics2D g = target.createGraphics();
			/*//写入背景
			g.drawImage(source, 0, 0, null);*/
			g.setBackground(Color.WHITE); 
		    //g.clearRect(0,0,targetW,targetH); 
			// smoother than exlax:
			g.setRenderingHint(RenderingHints.KEY_RENDERING,
					RenderingHints.VALUE_RENDER_QUALITY);
			g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
			g.dispose();
			return target;
		}

	
	
	private static Object toCellValue(JSONObject obj,
			String propertyName, CellType cellType) {
		Object value = null;
		if(propertyName==null || propertyName.equals("")){
			return null;
		}else if(propertyName.equals("seq")){
			return curListIndex+"";
		}else if(!obj.has(propertyName)){
			return null;
		}
		if(cellType.equals(CellType.String)) {
			value = obj.getString(propertyName);
		} else if(cellType.equals(CellType.Date)) {
			String v = obj.getString(propertyName);
			try {
				if(v != null) value = sdf.parse(v);
			} catch (Exception e) {}
		} else {
			if(obj.has(propertyName)) {
				double d = obj.getDouble(propertyName);
				value = d;
			}
		}
		return value;
	}
}
