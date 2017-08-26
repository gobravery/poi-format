package com.gobravery.format.poi.word;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import sun.misc.BASE64Encoder;



public class DocUtil {

    public DocUtil(){

    }
    /**
     * 根据Doc模板生成word文件
     * @param dataMap 需要填入模板的数据
     * @param path    模块路径(xxx/xxxx/)
     * @param savePath 保存路径(要含保存文档名加后缀 例子:xxxx/xxx.doc)
     * @param FileName 模块文件名字(无文件后缀名)
     * 
     */
    public static void createDoc(Map<String,Object> dataMap,String path,String savePath,String FileName){
        try {
            //加载需要装填的模板
            Template template=null;
            Configuration configure = new Configuration(Configuration.VERSION_2_3_22);
            configure.setDefaultEncoding("utf-8");
            //设置模板装置方法和路径，FreeMarker支持多种模板装载方法。可以重servlet，classpath,数据库装载。
            //加载模板文件，放在testDoc下
//            File file=new File(path);
            configure.setDirectoryForTemplateLoading(new File(path));
            //设置对象包装器
//          configure.setObjectWrapper(new DefaultObjectWrapper());
            //设置异常处理器
            configure.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
            //定义Template对象，注意模板类型名字与downloadType要一致
            template=configure.getTemplate(FileName+".ftl");
            File outFile=new File(savePath);
            Writer out=null;
            out=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"));
            template.process(dataMap, out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
//    /**
//     * 生成批复单，返回批复单类主键ID(默认保存在数据字典d_serverId code为89的路径)
//     * @param dataMap	数据
//     * @param path		模板路径(xxx/xxxx/)
//     * @param FileName	模板名字(无文件后缀名)
//     * @param uuid		对应表单UUID
//     * @param tx		事务
//     * @return
//     */
//    public static String createDoc(Map<String,Object> dataMap,String path,String FileName,String uuid,TransactionCache tx){
//        GhGReply reply=new GhGReply();
//    	try {
//            //加载需要装填的模板
//            Template template=null;
//            Configuration configure = new Configuration(Configuration.VERSION_2_3_22);
//            configure.setDefaultEncoding("utf-8");
//            configure.setDirectoryForTemplateLoading(new File(path));
//            configure.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
//            template=configure.getTemplate(FileName+".ftl");
//            String DocFileName=UuidUtil.getUuid()+".doc";
//            File outFile=new File(AttachItem.filePath(HyElectionStatus.HyElectionReply)+"/"+DocFileName);
//            Writer out=null;
//            out=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"));
//            template.process(dataMap, out);
//            out.close();
//            reply.setUrlType(HyElectionStatus.HyElectionReply);
//            reply.setFileName(DocFileName);
//            reply.setConnectUuid(uuid);
//            tx.save(reply);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (TemplateException e) {
//            e.printStackTrace();
//        }
//    	if(StringHelper.isNotEmpty(reply.getGhGReplyUuid())){
//            return reply.getGhGReplyUuid();
//    	}
//    	return "";
//    }
//    /**
//     * 生成批复单，自动保存数据库，并返回批复单类主键ID
//     * @param dataMap	数据
//     * @param path		模板路径(xxx/xxxx/)
//     * @param FileName	模板名字(无文件后缀名)
//     * @param uuid		对应表单UUID
//     * @return
//     */
//    public static String createDoc(Map<String,Object> dataMap,String path,String FileName,String uuid){
//        GhGReply reply=new GhGReply();
//    	try {
//            //加载需要装填的模板
//            Template template=null;
//            Configuration configure = new Configuration(Configuration.VERSION_2_3_22);
//            configure.setDefaultEncoding("utf-8");
//            configure.setDirectoryForTemplateLoading(new File(path));
//            configure.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
//            template=configure.getTemplate(FileName+".ftl");
//            String DocFileName=UuidUtil.getUuid()+".doc";
//            File outFile=new File(AttachItem.filePath(HyElectionStatus.HyElectionReply)+"/"+DocFileName);
//            Writer out=null;
//            out=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"));
//            template.process(dataMap, out);
//            out.close();
//            reply.setUrlType(HyElectionStatus.HyElectionReply);
//            reply.setFileName(DocFileName);
//            reply.setConnectUuid(uuid);
//            new TransactionCache().xsave(reply);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (TemplateException e) {
//            e.printStackTrace();
//        }
//    	if(StringHelper.isNotEmpty(reply.getGhGReplyUuid())){
//            return reply.getGhGReplyUuid();
//    	}
//    	return "";
//    }
//    
//    /**
//     * 生成批复单，返回批复单类主键ID
//     * @param dataMap	数据
//     * @param path		模板路径(xxx/xxxx/)
//     * @param FileName	模板名字(无文件后缀名)
//     * @param uuid		对应表单UUID
//     * @param type      保存路径(数据字典d_serverId中的code)
//     * @param tx		事务
//     * @return
//     */
//    public static String createDoc(Map<String,Object> dataMap,String path,String FileName,String uuid,Integer type,TransactionCache tx){
//        GhGReply reply=new GhGReply();
//    	try {
//            //加载需要装填的模板
//            Template template=null;
//            Configuration configure = new Configuration(Configuration.VERSION_2_3_22);
//            configure.setDefaultEncoding("utf-8");
//            configure.setDirectoryForTemplateLoading(new File(path));
//            configure.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
//            template=configure.getTemplate(FileName+".ftl");
//            String DocFileName=UuidUtil.getUuid()+".doc";
//            File outFile=new File(AttachItem.filePath(type)+"/"+DocFileName);
//            Writer out=null;
//            out=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"));
//            template.process(dataMap, out);
//            out.close();
//            reply.setUrlType(type);
//            reply.setFileName(DocFileName);
//            reply.setConnectUuid(uuid);
//            tx.save(reply);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (TemplateException e) {
//            e.printStackTrace();
//        }
//    	if(StringHelper.isNotEmpty(reply.getGhGReplyUuid())){
//            return reply.getGhGReplyUuid();
//    	}
//    	return "";
//    }
    
    private String getImageStr(String imgFile){
        InputStream in=null;
        byte[] data=null;
        try {
            in=new FileInputStream(imgFile);
            data=new byte[in.available()];
            in.read(data);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BASE64Encoder encoder=new BASE64Encoder();
        return encoder.encode(data);
    }
}
