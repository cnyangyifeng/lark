package cn.me.xdf.common.utils;

import java.io.*;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * 用于制作zip压缩包
 * <pre>
 *     String backName="back";
 * if(names[0]!=null){
 * backName=names[0].substring(names[0].indexOf(Constants.BACKUP_PATH)+Constants.BACKUP_PATH.length()+1);
 * }
 * List<FileEntry> fileEntrys = new ArrayList<FileEntry>();
 * response.setContentType("application/x-download;charset=UTF-8");
 * response.addHeader("Content-disposition", "filename="
 * + backName+".zip");
 * for(String filename:names){
 * File file=new File(realPathResolver.get(filename));
 * fileEntrys.add(new FileEntry("", "", file));
 * }
 * try {
 * // 模板一般都在windows下编辑，所以默认编码为GBK,Linux改为UTF-8
 * Zipper.zip(response.getOutputStream(), fileEntrys, "GBK");
 * } catch (IOException e) {
 * log.error("export db error!", e);
 * }
 * </pre>
 *
 * @author xiaobin
 */
public class Zipper {
    private static final Logger log = LoggerFactory.getLogger(Zipper.class);

    /**
     * 制作压缩包
     */
    public static void zip(OutputStream out, List<FileEntry> fileEntrys,
                           String encoding) {
        new Zipper(out, fileEntrys, encoding);
    }

    /**
     * 创建Zipper对象
     *
     * @param out
     */
    protected Zipper(OutputStream out, List<FileEntry> fileEntrys,
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
                for (FileEntry fe : fileEntrys) {
                    zip(fe.getFile(), fe.getFilter(), fe.getZipEntry(),
                            fe.getPrefix());
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
    private void zip(File srcFile, FilenameFilter filter, ZipEntry pentry,
                     String prefix) throws IOException {
        ZipEntry entry;
        if (srcFile.isDirectory()) {
            if (pentry == null) {
                entry = new ZipEntry(srcFile.getName());
            } else {
                entry = new ZipEntry(pentry.getName() + "/" + srcFile.getName());
            }
            File[] files = srcFile.listFiles(filter);
            for (File f : files) {
                zip(f, filter, entry, prefix);
            }
        } else {
            if (pentry == null) {
                entry = new ZipEntry(prefix + srcFile.getName());
            } else {
                entry = new ZipEntry(pentry.getName() + "/" + prefix
                        + srcFile.getName());
            }
            FileInputStream in;
            try {
                log.debug("读取文件：{}", srcFile.getAbsolutePath());
                in = new FileInputStream(srcFile);
                try {
                    zipOut.putNextEntry(entry);
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        zipOut.write(buf, 0, len);
                    }
                    zipOut.closeEntry();
                } finally {
                    in.close();
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException("制作压缩包时，源文件不存在："
                        + srcFile.getAbsolutePath(), e);
            }
        }
    }

    private byte[] buf = new byte[1024];
    private ZipOutputStream zipOut;

    public static class FileEntry {
        private FilenameFilter filter;
        private String parent;
        private File file;
        private String prefix;

        public FileEntry(String parent, String prefix, File file,
                         FilenameFilter filter) {
            this.parent = parent;
            this.prefix = prefix;
            this.file = file;
            this.filter = filter;
        }

        public FileEntry(String parent, File file) {
            this.parent = parent;
            this.file = file;
        }

        public FileEntry(String parent, String prefix, File file) {
            this(parent, prefix, file, null);
        }

        public ZipEntry getZipEntry() {
            if (StringUtils.isBlank(parent)) {
                return null;
            } else {
                return new ZipEntry(parent);
            }
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

        public File getFile() {
            return file;
        }

        public void setFile(File file) {
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
