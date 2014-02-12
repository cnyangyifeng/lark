package cn.me.xdf.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-12-19
 * Time: 下午1:39
 * To change this template use File | Settings | File Templates.
 */
public class InputStreamZipper {

    private static final Logger log = LoggerFactory.getLogger(Zipper.class);

    /**
     * 制作压缩包
     */
    public static void zip(OutputStream out, List<InputStreamEntry> fileEntrys,
                           String encoding) {
        new InputStreamZipper(out, fileEntrys, encoding);
    }

    /**
     * 创建Zipper对象
     *
     * @param out
     */
    protected InputStreamZipper(OutputStream out, List<InputStreamEntry> fileEntrys,
                     String encoding) {
        Assert.notEmpty(fileEntrys);
        long begin = System.currentTimeMillis();
        log.debug("开始制作压缩包");
        try {
            try {
                zipOut = new ZipOutputStream(out);
                if (!StringUtils.isBlank(encoding)) {
                    log.debug("using encoding: {}", encoding);
                    zipOut.setEncoding(encoding);
                } else {
                    log.debug("using default encoding");
                }
                for (InputStreamEntry fe : fileEntrys) {
                    zip(fe.getFile(), fe.getFilter(), fe.getZipEntry(),
                            fe.getPrefix(),fe.getFileName());
                }
            } finally {
                zipOut.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("制作压缩包时，出现IO异常！", e);
        }
        long end = System.currentTimeMillis();
        log.info("制作压缩包成功。耗时：{}ms。", end - begin);
    }

    /**
     * 压缩文件
     *
     * @param srcFile 源文件
     * @param pentry  父ZipEntry
     * @throws IOException
     */
    private void zip(InputStream srcFile, FilenameFilter filter, ZipEntry pentry,
                     String prefix,String fileName) throws IOException {
        ZipEntry entry;
        if (pentry == null) {
            entry = new ZipEntry(prefix + fileName);
        } else {
            entry = new ZipEntry(pentry.getName() + "/" + prefix
                    + fileName);
        }

        try {
            try {
                zipOut.putNextEntry(entry);
                int len;
                while ((len = srcFile.read(buf)) > 0) {
                    zipOut.write(buf, 0, len);
                }
                zipOut.closeEntry();
            } finally {
                srcFile.close();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("制作压缩包时，源文件不存在：", e);
        }
    }

    private byte[] buf = new byte[1024];
    private ZipOutputStream zipOut;

    public static class InputStreamEntry {
        private FilenameFilter filter;
        private String fileName;
        private String parent;
        private InputStream file;
        private String prefix;

        public InputStreamEntry(String parent, String prefix, InputStream file,
                                FilenameFilter filter, String fileName) {
            this.parent = parent;
            this.prefix = prefix;
            this.file = file;
            this.filter = filter;
            this.fileName = fileName;
        }

        public InputStreamEntry(String parent, InputStream file) {
            this.parent = parent;
            this.file = file;
        }

        public InputStreamEntry(String parent, String prefix, InputStream file, String fileName) {
            this(parent, prefix, file, null, fileName);
        }

        public ZipEntry getZipEntry() {
            if (StringUtils.isBlank(parent)) {
                return null;
            } else {
                return new ZipEntry(parent);
            }
        }

        public String getFileName() {
            return fileName;
        }

        public FilenameFilter getFilter() {
            return filter;
        }

        public void setFilter(FilenameFilter filter) {
            this.filter = filter;
        }

        public String getParent() {
            return parent;
        }

        public void setParent(String parent) {
            this.parent = parent;
        }

        public InputStream getFile() {
            return file;
        }

        public void setFile(InputStream file) {
            this.file = file;
        }

        public String getPrefix() {
            if (prefix == null) {
                return "";
            } else {
                return prefix;
            }
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }
    }
}
