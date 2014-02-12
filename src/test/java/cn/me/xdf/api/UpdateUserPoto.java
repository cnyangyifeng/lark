package cn.me.xdf.api;

import cn.me.xdf.JunitBaseTest;
import cn.me.xdf.common.httpclient.FilePart2;
import cn.me.xdf.common.json.JsonUtils;
import cn.me.xdf.model.base.DocInterfaceModel;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-12-24
 * Time: 上午11:55
 * To change this template use File | Settings | File Templates.
 */
public class UpdateUserPoto extends JunitBaseTest{

    private static final Logger log = LoggerFactory
            .getLogger(UpdateUserPoto.class);

    public void testUpload(){
        updatePersonToAd("yangyifeng","d:\\a.png");
    }



    private void updatePersonToAd(String loginName, String fdFilePath) {

        File file = new File(fdFilePath);


        try {
            FilePart2 fp = new FilePart2("file", file);

            HttpClient client = new HttpClient();
            client.getParams().setParameter(
                    HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
            PostMethod filePost = new PostMethod(DocInterfaceModel.user_poto_upload_url+"?loginName="+loginName);
            filePost.getParams().setParameter(
                    HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");

           /* StringPart2 loginNamePart = new StringPart2("loginName",
                    loginName, "utf-8");*/

            Part[] parts = {fp};
            MultipartRequestEntity mre = new MultipartRequestEntity(
                    parts, filePost.getParams());

            filePost.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
            filePost.setRequestEntity(mre);
            int status = 0;
            status = client.executeMethod(filePost);
            if (status == HttpStatus.SC_OK) {
                String json = filePost.getResponseBodyAsString();
                Map<String, Object> map = JsonUtils.readObjectByJson(json, Map.class);
                if (!"1".equals(map.get("state").toString())) {
                    throw new RuntimeException(map.get("errorMsg").toString());
                }
            } else {
                log.error("连接失败");
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

}
