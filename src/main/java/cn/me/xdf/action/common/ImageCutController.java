package cn.me.xdf.action.common;

import cn.me.xdf.common.image.ImageScale;
import cn.me.xdf.common.image.ImageScaleImpl;
import cn.me.xdf.model.base.AttMain;
import cn.me.xdf.service.base.AttMainService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-11-21
 * Time: 上午10:49
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/common/imageCut")
public class ImageCutController {

    @Autowired
    private AttMainService attMainService;

    /**
     * 上传文件
     */
    @RequestMapping("/page")
    public String execute(WebRequest request, ModelMap model) {
        String uploadBase = request.getParameter("uploadBase");
        String imgSrcPath = request.getParameter("imgSrcPath");
        String zoomWidth = request.getParameter("zoomWidth");
        String zoomHeight = request.getParameter("zoomHeight");
        String imgId = request.getParameter("imgId");
        model.addAttribute("uploadBase", uploadBase);
        model.addAttribute("imgId", imgId);
        model.addAttribute("imgSrcPath", imgSrcPath);
        model.addAttribute("zoomWidth", zoomWidth);
        model.addAttribute("zoomHeight", zoomHeight);
        return "common/image_area_select";
    }

    /**
     * 圖片剪切
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/imageCut")
    @ResponseBody
    public String imageCut(WebRequest request)
            throws Exception {
        String imgId = request.getParameter("imgId");
        int reMinWidth = toInt(request.getParameter("reMinWidth"));
        int reMinHeight = toInt(request.getParameter("reMinHeight"));
        int imgTop = toInt(request.getParameter("imgTop"));
        float imgScale = NumberUtils.createFloat(request.getParameter("imgScale"));
        int imgLeft = toInt(request.getParameter("imgLeft"));
        int imgWidth = toInt(request.getParameter("imgWidth"));
        int imgHeight = toInt(request.getParameter("imgHeight"));
        String imgSrcPath = getImgPath(imgId);
        ImageScale imageScale = new ImageScaleImpl();

        if (imgWidth > 0) {
            File file = retrieve(imgSrcPath);
            BufferedImage srcImgBuff = ImageIO.read(file);
            int imgW = srcImgBuff.getWidth();
            int imgH = srcImgBuff.getHeight();
            int topX = getLen(imgTop, imgScale);
            int topY = getLen(imgLeft, imgScale);
            int width = getLen(imgWidth, imgScale);
            int height = getLen(imgHeight, imgScale);
            if ((topX + width) > imgW) {
                width = imgW - topX;
            }
            if ((topY + height) > imgH) {
                height = imgH - topY;
            }
            imageScale.resizeFix(file, file, reMinWidth, reMinHeight,
                    topX, topY, width, height);
        } else {
            File file = retrieve(imgSrcPath);
            imageScale.resizeFix(file, file, reMinWidth, reMinHeight);
        }
        return "true";// success
    }


    private int getLen(int len, float imgScale) {
        return Math.round(len / imgScale);
    }

    private int toInt(String arg) {
        if (StringUtils.isBlank(arg)) {
            return 0;
        }
        if (arg.indexOf('.') > 0) {
            arg = arg.substring(0, arg.indexOf('.'));
        }
        return NumberUtils.createInteger(arg);
    }

    private File retrieve(String name) {
        return new File(name);
    }

    private String getImgPath(String imgId) {
        AttMain attMain = attMainService.get(imgId);
        return attMain.getFdFilePath();
    }
}
