package com.kuxue.service.plugin;

import com.kuxue.api.bokecc.config.Config;
import com.kuxue.api.bokecc.util.APIServiceFunction;
import com.kuxue.api.bokecc.util.DemoUtil;
import com.kuxue.common.json.JsonUtils;
import com.kuxue.model.base.AttMain;
import com.kuxue.model.base.DocInterfaceModel;
import com.kuxue.service.material.MaterialService;

import com.converter.docConverter.DocConverter;
import com.converter.pdfConverter.OpenOfficePDFConverter;
import com.converter.pdfConverter.PDFConverter;
import com.converter.swfConverter.SWFConverter;
import com.converter.swfConverter.SWFToolsSWFConverter;
import com.dreamwin.upfile.main.CCUploader;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA. User: xiaobin268 Date: 13-12-2 Time: 上午10:36 To
 * change this template use File | Settings | File Templates.
 */
public class AttMainPlugin {

    private static final Logger log = LoggerFactory
            .getLogger(AttMainPlugin.class);


    public static String addDocToCC(AttMain attMain, String callbackUrl) {
       CCUploader.uid = Config.userid;
        CCUploader.apiKey = Config.key;
        String vid = CCUploader.upload(attMain.getFdFilePath(), attMain.getFdFileName(), "NTP", null, callbackUrl);
        log.info("CCUploader==="+vid);
        return vid;

    }


    public static String DeleteDocToCC(AttMain attMain) {
        Map<String, String> paramsMap = new HashMap<String, String>();
        String videoId = attMain.getPlayCode();
        paramsMap.put("videoid", videoId);
        paramsMap.put("userid", Config.userid);
        long time = System.currentTimeMillis();
        String salt = Config.key;
        String requestURL = APIServiceFunction.createHashedQueryString(
                paramsMap, time, salt);
        //get方式
        String responsestr = APIServiceFunction
                .HttpRetrieve(Config.api_deleteVideo + "?" + requestURL);
        Document doc = DemoUtil.build(responsestr);
        return doc.getRootElement().getText();
    }

    public static void deleteDoc(AttMain attMain) {
        try {
            DocInterfaceModel model = new DocInterfaceModel(attMain,
                    DocInterfaceModel.deleteDocByAttId, "");
            HttpClient client = new HttpClient();
            client.getParams().setParameter(
                    HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
            PostMethod filePost = new PostMethod(DocInterfaceModel.url);
            filePost.getParams().setParameter(
                    HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");

            filePost.addParameters(model.deleteDocByAttIdModel());

            filePost.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
            int status = client.executeMethod(filePost);
            if (status == HttpStatus.SC_OK) {
                log.info("获取附件成功：" + filePost.getResponseBodyAsString());
            } else {
                log.error("连接失败");
            }
        } catch (Exception e) {
            log.error("addDoc:" + e.getCause());
            //throw new RuntimeException("出现异常addDoc:" + e.getCause());
        }
    }

    public static ByteArrayOutputStream getDocByAttId(AttMain attMain) {
           
                InputStream inputStream;
				try {
					inputStream = new FileInputStream(new File(attMain.getFdFilePath()));
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
	                byte[] buffer = new byte[1024];
	                int len;
	                while ((len = inputStream.read(buffer)) > -1) {
	                    baos.write(buffer, 0, len);
	                }
	                baos.flush();
	                return baos;
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
                
     
    }
/*    public static ByteArrayOutputStream getDocByAttId(AttMain attMain) {
    	try {
    		DocInterfaceModel model = new DocInterfaceModel(attMain,
    				DocInterfaceModel.getDocByAttId, "");
    		HttpClient client = new HttpClient();
    		client.getParams().setParameter(
    				HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
    		PostMethod filePost = new PostMethod(DocInterfaceModel.url);
    		filePost.getParams().setParameter(
    				HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
    		
    		filePost.addParameters(model.getDocByAttIdModel());
    		
    		filePost.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
    		int status = client.executeMethod(filePost);
    		if (status == HttpStatus.SC_OK) {
    			log.info("获取附件成功");
    			InputStream inputStream = filePost.getResponseBodyAsStream();
    			ByteArrayOutputStream baos = new ByteArrayOutputStream();
    			byte[] buffer = new byte[1024];
    			int len;
    			while ((len = inputStream.read(buffer)) > -1) {
    				baos.write(buffer, 0, len);
    			}
    			baos.flush();
    			return baos;
    		} else {
    			log.error("连接失败");
    			return null;
    		}
    	} catch (Exception e) {
    		log.error("addDoc:" + e.getCause());
    		//throw new RuntimeException("出现异常addDoc:" + e.getCause());
    		return null;
    	}
    }
*/
    /**
     * 传输文档
     *
     * @param attMain
     * @return
     */
    @SuppressWarnings("unchecked")
    public static String addDoc(AttMain attMain, String isConvert) {
        //不再存入filenet，仅仅作为文档的转换服务，其余保持不变。
    	if("1".equals(isConvert)){
    		System.out.println(attMain.getFdFileName().substring(attMain.getFdFileName().lastIndexOf(".")+1,attMain.getFdFileName().length()));
    		if("pdf".equalsIgnoreCase(attMain.getFdFileName().substring(attMain.getFdFileName().lastIndexOf(".")+1,attMain.getFdFileName().length()))){
    			SWFConverter swfConverter = new SWFToolsSWFConverter();
        		DocConverter converter = new DocConverter(swfConverter);
        		converter.ConvertPDF2SWF(attMain.getFdFilePath());
    		}else{
    			PDFConverter pdfConverter = new OpenOfficePDFConverter();
        		SWFConverter swfConverter = new SWFToolsSWFConverter();
        		DocConverter converter = new DocConverter(pdfConverter,swfConverter);
        		converter.convert(attMain.getFdFilePath());
    		}
    	}
		return UUID.randomUUID().toString();
        /*try {
            log.info("附件开始传输到Filenet里");
            DocInterfaceModel model = new DocInterfaceModel(attMain,
                    DocInterfaceModel.addDoc, isConvert);
            HttpClient client = new HttpClient();
            client.getParams().setParameter(
                    HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
            PostMethod filePost = new PostMethod(DocInterfaceModel.url);
            filePost.getParams().setParameter(
                    HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");

            MultipartRequestEntity mre = new MultipartRequestEntity(
                    model.getCCToAddModel(), filePost.getParams());
            filePost.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
            filePost.setRequestEntity(mre);
            int status = client.executeMethod(filePost);
            log.info("status==="+status);
            if (status == HttpStatus.SC_OK) {
                String json = filePost.getResponseBodyAsString();
                log.info(json);
                Map<String, Object> map = JsonUtils.readObjectByJson(json,
                        Map.class);
                if ("1".equals(map.get("Status").toString())) {// 返回成功
                    List<Map<String, String>> lists = (List<Map<String, String>>) map
                            .get("ResponseData");
                    return lists.get(0).get("filenetId");
                } else {
                    log.error("addDoc:" + map.get("Error").toString());
                    return null;
                }
            } else {
                log.error("addDoc：连接失败,httpCode=" + status);
                //throw new RuntimeException("addDoc：连接失败,httpCode=" + status);
                return null;
                // e.printStackTrace();
            }
        } catch (Exception e) {
            log.error("addDoc出现异常:"+attMain.getFdId()+ ";" + e);
            //throw new RuntimeException("出现异常addDoc:" + e.getCause());
            return null;
            // e.printStackTrace();
        }*/
    }

    /**
     * 获取文档
     *
     * @param attMain
     * @return
     */
    @SuppressWarnings("unchecked")
    public static String getSwfPath(AttMain attMain, String type) {

        try {
            DocInterfaceModel model = new DocInterfaceModel(attMain, type, "1");
            HttpClient client = new HttpClient();
            client.getParams().setParameter(
                    HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
            PostMethod post = new PostMethod(DocInterfaceModel.url);
            post.addParameters(model.getCCToReturnPlayUrlModel());
            int status = client.executeMethod(post);
            if (status == HttpStatus.SC_OK) {
                String json = post.getResponseBodyAsString();
                Map<String, Object> map = JsonUtils.readObjectByJson(json,
                        Map.class);
                if ("1".equals(map.get("Status").toString())) {
                    List<Map<String, String>> lists = (List<Map<String, String>>) map
                            .get("ResponseData");
                    return lists.get(0).values()
                            .toArray(new String[lists.get(0).values().size()])[0];

                } else {
                    log.error("getSwfPath:" + map.get("Error").toString());
                    throw new RuntimeException("getSwfPath:"
                            + map.get("Error").toString());
                }
            } else {
                throw new RuntimeException("getSwfPath:连接失败");
            }
        } catch (Exception e) {
            log.error("getSwfPath:" + e.getMessage());
            throw new RuntimeException("getSwfPath:" + e.getCause());
            // e.printStackTrace();
        }
    }
    /*
     * filenet附件获取方式
     */
    public static ByteArrayOutputStream getDocByIdAndName(String fileNetId,String fileNetName) {
        try {
            DocInterfaceModel model = new DocInterfaceModel(fileNetId,fileNetName,
                    DocInterfaceModel.getDocByAttId, "");
            HttpClient client = new HttpClient();
            client.getParams().setParameter(
                    HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
            PostMethod filePost = new PostMethod(DocInterfaceModel.url);
            filePost.getParams().setParameter(
                    HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");

            filePost.addParameters(model.getDocByAttIdModel());

            filePost.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
            int status = client.executeMethod(filePost);
            if (status == HttpStatus.SC_OK) {
                log.info("获取附件成功");
                InputStream inputStream = filePost.getResponseBodyAsStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len;
                while ((len = inputStream.read(buffer)) > -1) {
                    baos.write(buffer, 0, len);
                }
                baos.flush();
                return baos;
            } else {
                log.error("连接失败");
                return null;
            }
        } catch (Exception e) {
            log.error("addDoc:" + e.getCause());
            //throw new RuntimeException("出现异常addDoc:" + e.getCause());
            return null;
        }
    }
}
