package cn.me.xdf.common.utils.excel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.poi.hslf.model.Slide;
import org.apache.poi.hslf.model.TextRun;
import org.apache.poi.hslf.usermodel.RichTextRun;
import org.apache.poi.hslf.usermodel.SlideShow;

import cn.me.xdf.common.file.FileUtil;
import cn.me.xdf.common.upload.FileModel;
import cn.me.xdf.common.upload.UploadUtils;

public class PPTtoImageUtils {

	public static List<FileModel> doPPTtoImage(File file, ServletContext ctx,
			String imageFolder) {
		if (ctx == null) {
			throw new RuntimeException("ctx is not null");
		}
		boolean isppt = checkFile(file);
		if (!isppt) {
			System.out.println("The image you specify don't exit!");
			return null;
		}
		List<FileModel> images = new ArrayList<FileModel>();
		try {
			FileInputStream is = new FileInputStream(file);
			SlideShow ppt = new SlideShow(is);
			is.close();
			Dimension pgsize = ppt.getPageSize();
			org.apache.poi.hslf.model.Slide[] slide = ppt.getSlides();
			NumberFormat formatter = NumberFormat.getNumberInstance();
			formatter.setMinimumIntegerDigits(4);
			formatter.setGroupingUsed(false);
			int index = 0;
			String page = null;
			FileModel fileModel = null;
			for (Slide de : slide) {
				fileModel = new FileModel();
				index++;
				page = formatter.format(index);
				System.out.print("第" + index + "页。");
				TextRun[] truns = de.getTextRuns();
				for (int k = 0; k < truns.length; k++) {
					RichTextRun[] rtruns = truns[k].getRichTextRuns();
					for (int l = 0; l < rtruns.length; l++) {
						rtruns[l].setFontIndex(1);
						rtruns[l].setFontName("宋体");
						System.out.println(rtruns[l].getText());
					}
				}
				BufferedImage img = new BufferedImage(pgsize.width,
						pgsize.height, BufferedImage.TYPE_INT_RGB);

				Graphics2D graphics = img.createGraphics();
				graphics.setPaint(Color.white);
				graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width,
						pgsize.height));
				try {
					de.draw(graphics);
				} catch (Exception ex) {

				}
				String filename = UploadUtils.generateFilename(imageFolder,
						"jpeg");
				File dest = new File(ctx.getRealPath(filename));

				UploadUtils.checkDirAndCreate(dest.getParentFile());
				String realName = FileUtil.getFileName(file) + "_" + (page)
						+ ".jpeg";

				/*
				 * String imagePath = imageFolder + FileUtil.getFileName(file) +
				 * "_" + (page) + ".jpeg";
				 */
				FileOutputStream out = new FileOutputStream(dest);
				javax.imageio.ImageIO.write(img, "jpeg", out);

				fileModel.setFileName(realName);
				fileModel.setFilePath(filename);

				images.add(fileModel);
				out.close();
			}

			return images;
		} catch (FileNotFoundException e) {
			System.out.println(e);
			// System.out.println("Can't find the image!");
		} catch (IOException e) {
		}
		return null;
	}

	// function 检查文件是否为PPT
	public static boolean checkFile(File file) {

		boolean isppt = false;
		String filename = file.getName();
		String suffixname = null;

		if (filename != null && filename.indexOf(".") != -1) {
			suffixname = filename.substring(filename.indexOf("."));
			if (suffixname.equals(".ppt") || suffixname.equals(".pptx")) {
				isppt = true;
			}
			return isppt;
		} else {
			return isppt;
		}
	}

}
