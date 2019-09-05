package tech.mhuang.core.file;

import tech.mhuang.core.check.CheckAssert;
import tech.mhuang.core.io.IOUtil;
import tech.mhuang.core.system.SystemSupport;
import tech.mhuang.core.util.CollectionUtil;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件工具类.
 * <p>参照apache工具类、spring 工具类 取优方式</p>
 *
 * @author mhuang
 * @since 1.0.0
 */
public class FileUtil {

    /**
     * 1KB
     */
    public static final long KB = 1024;

    /**
     * 1MB
     */
    public static final long MB = KB * KB;

    /**
     * 1GB
     */
    public static final long GB = KB * MB;

    /**
     * 1TB
     */
    public static final long TB = KB * GB;

    /**
     * 1PB
     */
    public static final long PB = KB * TB;

    /**
     * The file copy buffer size (30 MB)
     */
    private static final long FILE_COPY_BUFFER_SIZE = MB * 30;

    /**
     * An empty array of type <code>File</code>.
     */
    public static final File[] EMPTY_FILE_ARRAY = new File[0];

    /**
     * 创建文件
     *
     * @param file 文件
     * @throws IOException IO异常
     */
    public static void createFile(final File file) throws IOException {
        CheckAssert.check(file, "创建的文件不能为空");
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
        if (!file.exists()) {
            file.createNewFile();
        }
    }

    /**
     * 创建目录（上级存在时创建)
     *
     * @param directory 创建的目录
     * @throws IOException 抛出IO异常
     */
    public static void createDirectory(File directory) throws IOException {
        createDirectory(directory, false);
    }

    /**
     * 创建文件夹是否级联创建.
     * <p>如果<code>cascade</code>是真则级联、若不是只创建自己</p>
     *
     * @param directory 文件夹
     * @param cascade   是否级联
     * @throws IOException 抛出IO异常
     */
    public static void createDirectory(File directory, boolean cascade) throws IOException {
        CheckAssert.check(directory, "创建的文件夹不存在");
        if (cascade) {
            directory.mkdirs();
        } else {
            directory.mkdir();
        }
    }

    /**
     * 文件打开流
     *
     * @param file 需要打开的file
     * @return FileInputStream 文件流
     * @throws IOException 抛出IO异常
     */
    public static FileInputStream openInputStream(final File file) throws IOException {
        checkFileRequired(file, true);
        CheckAssert.check(file.isDirectory(), String.format("文件'%s'是一个文件夹", file));
        CheckAssert.check(!file.canRead(), String.format("文件'%s'不能读,请检查权限", file));
        return new FileInputStream(file);
    }

    /**
     * 文件打开输出流
     *
     * @param file 文件
     * @return FileOutputStream 文件输出流
     * @throws IOException 抛出IO异常
     */
    public static FileOutputStream openOutputStream(final File file) throws IOException {
        return openOutputStream(file, false);
    }

    /**
     * 文件打开输出流
     *
     * @param file   文件
     * @param append 是否可增加
     * @return FileOutputStream 文件输出流
     * @throws IOException 抛出IO异常
     */
    public static FileOutputStream openOutputStream(File file, boolean append) throws IOException {
        checkFileRequired(file, true);
        CheckAssert.check(file.isDirectory(), String.format("文件'%s'是一个文件夹", file));
        CheckAssert.check(!file.canRead(), String.format("文件'%s'不能读,请检查权限", file));
        return new FileOutputStream(file, append);
    }

    /**
     * URL 转 File.
     *
     * @param url URL对象
     * @return 返回File对象
     * @throws URISyntaxException 非法的URL
     */
    public static File toFile(final URL url) throws URISyntaxException {
        if (url == null || !"file".equalsIgnoreCase(url.getProtocol())) {
            return null;
        }
        return new File(url.toURI());
    }

    /**
     * URL Array convert File Array
     *
     * @param urls url array
     * @return File Array
     * @throws URISyntaxException URI无效连接异常
     */
    public static File[] toFiles(final URL[] urls) throws URISyntaxException {
        if (CollectionUtil.isEmpty(urls)) {
            return EMPTY_FILE_ARRAY;
        }
        final File[] files = new File[urls.length];
        for (int i = 0; i < urls.length; i++) {
            final URL url = urls[i];
            if (url != null) {
                files[i] = toFile(url);
            }
        }
        return files;
    }


    /**
     * 将源文件复制到对应文件夹
     *
     * @param srcFile 源文件
     * @param destDir 复制到的文件夹
     * @throws IOException 抛出IO异常
     */
    public static void copyFileToDirectory(final File srcFile, final File destDir) throws IOException {
        copyFileToDirectory(srcFile, destDir, true);
    }

    /**
     * 将源文件复制到对应文件夹
     *
     * @param srcFile          源文件
     * @param destDir          复制到的文件夹
     * @param preserveFileDate 是否更新时间
     * @throws IOException 抛出IO异常
     */
    public static void copyFileToDirectory(final File srcFile, final File destDir, final boolean preserveFileDate)
            throws IOException {
        checkFileRequired(srcFile, true);
        checkFileRequired(destDir, true);
        if (!destDir.isDirectory()) {
            throw new IllegalArgumentException("Destination '" + destDir + "' is not a directory");
        }
        final File destFile = new File(destDir, srcFile.getName());
        copyFile(srcFile, destFile, preserveFileDate);
    }

    /**
     * 将源文件复制到目标文件
     *
     * @param srcFile  源文件
     * @param destFile 目标文件
     * @throws IOException 抛出IO异常
     */
    public static void copyFile(final File srcFile, final File destFile) throws IOException {
        copyFile(srcFile, destFile, true);
    }

    /**
     * 将源文件复制到目标文件
     *
     * @param srcFile    源文件
     * @param destFile   目标文件
     * @param upFileDate 是否更新文件时间
     * @throws IOException 抛出IO异常
     */
    public static void copyFile(final File srcFile, final File destFile, final boolean upFileDate) throws IOException {
        checkFileRequired(srcFile, true);
        checkFileRequired(destFile, false);

        CheckAssert.check(srcFile.isDirectory(), "原始文件是个文件夹");

        if (srcFile.getCanonicalPath().equals(destFile.getCanonicalPath())) {
            throw new IOException("copy的文件地址相同");
        }

        if (destFile.exists() && destFile.canWrite() == false) {
            throw new IOException(String.format("目标文件 '%s' 存在但不可写", destFile));
        }

    }

    /**
     * 开始拷贝文件到目标文件
     *
     * @param srcFile    拷贝的文件
     * @param destFile   目标存放的文件
     * @param upFileDate 是否更新时间
     * @throws IOException 抛出IO异常
     */
    private static void doCopyFile(final File srcFile, final File destFile, final boolean upFileDate) throws IOException {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel input = null;
        FileChannel output = null;
        try {
            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(destFile);
            input = fis.getChannel();
            output = fos.getChannel();
            final long size = input.size();
            long pos = 0;
            long count = 0;
            while (pos < size) {
                final long remain = size - pos;
                count = remain > FILE_COPY_BUFFER_SIZE ? FILE_COPY_BUFFER_SIZE : remain;
                final long bytesCopied = output.transferFrom(input, pos, count);
                if (bytesCopied == 0) {
                    break;
                }
                pos += bytesCopied;
            }
        } finally {
            IOUtil.close(output, fos, input, fis);
        }

        final long srcLen = srcFile.length();
        final long dstLen = destFile.length();
        if (srcLen != dstLen) {
            throw new IOException(String.format("Failed to copy full contents from '%s' to '%s' Expected length: %d Actual: %d", srcFile, destFile, srcLen, dstLen));
        }
        if (upFileDate) {
            destFile.setLastModified(srcFile.lastModified());
        }
    }

    /**
     * 将源文件夹中文件拷贝到目标文件夹
     *
     * @param srcDir  源文件夹
     * @param destDir 目标文件夹
     * @throws IOException 抛出IO异常
     */
    public static void copyDirectoryToDirectory(final File srcDir, final File destDir) throws IOException {
        checkFileRequired(srcDir, true);
        CheckAssert.check(!srcDir.isDirectory(), String.format("源'%s'不是文件夹", srcDir));
        checkFileRequired(destDir, false);
        if (destDir.exists() && !destDir.isDirectory()) {
            throw new IllegalArgumentException(String.format("目标'%s'不是文件夹", destDir));
        }
        copyDirectory(srcDir, new File(destDir, srcDir.getName()), true);
    }

    /**
     * 拷贝目录
     *
     * @param srcDir  源目录
     * @param destDir 目标目录
     * @throws IOException 抛出IO异常
     */

    public static void copyDirectory(final File srcDir, final File destDir) throws IOException {
        copyDirectory(srcDir, destDir, true);
    }

    /**
     * 将源文件夹中文件拷贝到目标文件夹
     *
     * @param srcDir     源文件夹
     * @param destDir    目标文件夹
     * @param upFileDate 是否更新时间
     * @throws IOException 抛出IO异常
     */
    public static void copyDirectory(final File srcDir, final File destDir,
                                     final boolean upFileDate) throws IOException {
        copyDirectory(srcDir, destDir, null, upFileDate);
    }

    /**
     * 将源文件夹中文件拷贝到目标文件夹
     *
     * @param srcDir     源文件夹
     * @param destDir    目标文件夹
     * @param filter     过滤器
     * @param upFileDate 是否更新时间
     * @throws IOException 抛出IO异常
     */
    public static void copyDirectory(final File srcDir, final File destDir,
                                     final FileFilter filter, final boolean upFileDate) throws IOException {
        checkFileRequired(srcDir, true);
        checkFileRequired(destDir, false);
        CheckAssert.check(!srcDir.isDirectory(), String.format("源'%s'不是文件夹", srcDir));
        if (srcDir.getCanonicalPath().equals(destDir.getCanonicalPath())) {
            throw new IOException("copy的目录相同");
        }

        List<String> exclusionList = null;
        if (destDir.getCanonicalPath().startsWith(srcDir.getCanonicalPath())) {
            final File[] srcFiles = filter == null ? srcDir.listFiles() : srcDir.listFiles(filter);
            if (srcFiles != null && srcFiles.length > 0) {
                exclusionList = new ArrayList<>(srcFiles.length);
                for (final File srcFile : srcFiles) {
                    final File copiedFile = new File(destDir, srcFile.getName());
                    exclusionList.add(copiedFile.getCanonicalPath());
                }
            }
        }

        doCopyDirectory(srcDir, destDir, filter, upFileDate, exclusionList);
    }

    /**
     * 开始进行目录拷贝
     *
     * @param srcDir        需要拷贝的目录
     * @param destDir       拷贝到的目录
     * @param filter        拷贝时过滤器
     * @param upFileDate    是否更新时间
     * @param exclusionList 忽略的路径
     * @throws IOException 抛出IO异常
     */
    private static void doCopyDirectory(final File srcDir, final File destDir, final FileFilter filter,
                                        final boolean upFileDate, final List<String> exclusionList)
            throws IOException {
        final File[] srcFiles = filter == null ? srcDir.listFiles() : srcDir.listFiles(filter);
        CheckAssert.check(srcFiles, String.format("在'%s'没有可拷贝的目录文件", srcDir));
        if (destDir.exists()) {
            CheckAssert.check(!destDir.isDirectory(), String.format("目标 '%s' 存在不是一个文件夹", destDir));
        } else {
            CheckAssert.check(!destDir.mkdirs() && !destDir.isDirectory(),
                    String.format("目标 '%s' 目录无法创建", destDir));
        }
        CheckAssert.check(!destDir.canWrite(), String.format("目标目录'%s'无法进行拷贝", destDir));
        for (final File srcFile : srcFiles) {
            final File dstFile = new File(destDir, srcFile.getName());
            if (exclusionList == null || !exclusionList.contains(srcFile.getCanonicalPath())) {
                if (srcFile.isDirectory()) {
                    doCopyDirectory(srcFile, dstFile, filter, upFileDate, exclusionList);
                } else {
                    doCopyFile(srcFile, dstFile, upFileDate);
                }
            }
        }

        if (upFileDate) {
            destDir.setLastModified(srcDir.lastModified());
        }
    }

    /**
     * 将URL拷贝到File中
     *
     * @param source      URL
     * @param destination File
     * @throws IOException 抛出IO异常
     */
    public static void copyURLToFile(final URL source, final File destination) throws IOException {
        copyInputStreamToFile(source.openStream(), destination);
    }

    /**
     * 将URL拷贝到File中 指定对应的链接读取超时时间
     *
     * @param source            URL
     * @param destination       FILE
     * @param connectionTimeout 链接超时时间
     * @param readTimeout       读取超时时间
     * @throws IOException 抛出IO异常
     */
    public static void copyURLToFile(final URL source, final File destination,
                                     final int connectionTimeout, final int readTimeout) throws IOException {
        final URLConnection connection = source.openConnection();
        connection.setConnectTimeout(connectionTimeout);
        connection.setReadTimeout(readTimeout);
        copyInputStreamToFile(connection.getInputStream(), destination);
    }

    /**
     * 将输入流拷贝到File中
     *
     * @param source      输入流
     * @param destination File
     * @throws IOException 抛出IO异常
     */
    public static void copyInputStreamToFile(final InputStream source, final File destination) throws IOException {
        try {
            copyToFile(source, destination);
        } finally {
            IOUtil.close(source);
        }
    }

    /**
     * 将输入流拷贝到File中
     *
     * @param source      输入流
     * @param destination File
     * @throws IOException 抛出IO异常
     */
    public static void copyToFile(final InputStream source, final File destination) throws IOException {
        final FileOutputStream output = openOutputStream(destination);
        try {
            IOUtil.copy(source, output);
        } finally {
            IOUtil.close(output);
        }
    }

    /**
     * 删除目录
     *
     * @param directory 目录
     * @throws IOException 抛出IO异常
     */
    public static void deleteDirectory(final File directory) throws IOException {
        //不存在则返回
        if (!directory.exists()) {
            return;
        }

        //非链接时清除
        if (!isSymlink(directory)) {
            cleanDirectory(directory);
        }

        //删除失败抛出异常
        CheckAssert.check(!directory.delete(), String.format("无法删除目录 %s", directory));
    }

    /**
     * 清空目录
     *
     * @param directory 目录
     * @throws IOException 抛出IO异常
     */
    public static void cleanDirectory(final File directory) throws IOException {
        final File[] files = verifiedListFiles(directory);
        for (final File file : files) {
            forceDelete(file);
        }
    }

    /**
     * 删除文件、如果是目录则一起他目录下的文件和他自己
     *
     * @param file 删除的文件或目录
     * @throws IOException 抛出IO异常
     */
    public static void forceDelete(final File file) throws IOException {
        if (file.isDirectory()) {
            deleteDirectory(file);
        } else {
            final boolean filePresent = file.exists();
            if (!file.delete()) {
                CheckAssert.check(!filePresent, String.format("文件'%s'不存在", file));
                throw new IOException(String.format("无法删除文件:%s", file));
            }
        }
    }

    /**
     * 读取文件到字符串
     *
     * @param file 文件
     * @return 读取到文件的字符串
     * @throws IOException 抛出IO异常
     */
    public static String readFileToString(final File file) throws IOException {
        return readFileToString(file, Charset.defaultCharset().name());
    }

    /**
     * 读取文件到字节数组
     *
     * @param file 文件
     * @return 读取到文件的字节数组
     * @throws IOException 抛出IO异常
     */
    public static byte[] readFileToByteArray(final File file) throws IOException {
        InputStream in = null;
        try {
            in = openInputStream(file);
            return IOUtil.toByteArray(in); // Do NOT use file.length() - see IO-453
        } finally {
            IOUtil.close(in);
        }
    }

    /**
     * 读取文件到字符串
     *
     * @param file     文件
     * @param encoding 编码
     * @return 读取到的字符串以及固定的编码格式
     * @throws IOException 抛出IO异常
     */
    public static String readFileToString(final File file, final String encoding) throws IOException {
        InputStream in = null;
        try {
            in = openInputStream(file);
            return IOUtil.toString(in, encoding, false);
        } finally {
            //关闭资源
            IOUtil.close(in);
        }
    }

    /**
     * 将字符串写到文件里（覆盖）
     *
     * @param file     文件
     * @param data     字符串数据
     * @param encoding 编码
     * @throws IOException 抛出IO异常
     */
    public static void writeStringToFile(final File file, final String data, final Charset encoding)
            throws IOException {
        writeStringToFile(file, data, encoding, false);
    }

    /**
     * 将字符串写到文件里（覆盖）
     *
     * @param file     文件
     * @param data     字符串数据
     * @param encoding 编码字符串
     * @throws IOException 抛出IO异常
     */
    public static void writeStringToFile(final File file, final String data, final String encoding) throws IOException {
        writeStringToFile(file, data, encoding, false);
    }

    /**
     * 将字符串写到文件里
     * <p>if append 是true 则追加、false是覆盖</p>
     *
     * @param file     文件
     * @param data     字符串数据
     * @param encoding 编码字符串
     * @param append   是否是追加
     * @throws IOException 抛出IO异常
     */
    public static void writeStringToFile(final File file, final String data, final String encoding,
                                         final boolean append) throws IOException {
        writeStringToFile(file, data, Charset.forName(encoding), append);
    }

    /**
     * 将字符串写到文件里（覆盖）
     *
     * @param file 文件
     * @param data 字符串数据
     * @throws IOException 抛出IO异常
     */
    public static void writeStringToFile(final File file, final String data) throws IOException {
        writeStringToFile(file, data, Charset.defaultCharset(), false);
    }

    /**
     * 将字符串写到文件里
     *
     * @param file   文件
     * @param data   字符串数据
     * @param append 是否是追加
     * @throws IOException 抛出IO异常
     */
    public static void writeStringToFile(final File file, final String data, final boolean append) throws IOException {
        writeStringToFile(file, data, Charset.defaultCharset(), append);
    }

    /**
     * 将字符串写到文件里
     * <p>if append 是true 则追加、false是覆盖</p>
     *
     * @param file     文件
     * @param data     数据
     * @param encoding 编码
     * @param append   是否是追加
     * @throws IOException 抛出IO异常
     */
    public static void writeStringToFile(final File file, final String data, final Charset encoding, final boolean
            append) throws IOException {
        OutputStream out = null;
        try {
            out = openOutputStream(file, append);
            IOUtil.write(data, out, encoding);
        } finally {
            IOUtil.close(out);
        }
    }

    /**
     * 将字符序列写入文件里（覆盖）
     *
     * @param file 文件
     * @param data 字符序列
     * @throws IOException 抛出IO异常
     */
    public static void write(final File file, final CharSequence data) throws IOException {
        write(file, data, Charset.defaultCharset(), false);
    }

    /**
     * 将字符序列写入文件里
     * <p>如果 append 是true 则追加、false是覆盖</p>
     *
     * @param file   文件
     * @param data   字符序列
     * @param append 是否追加
     * @throws IOException 抛出IO异常
     */
    public static void write(final File file, final CharSequence data, final boolean append) throws IOException {
        write(file, data, Charset.defaultCharset(), append);
    }

    /**
     * 将字符序列写入文件里（覆盖）
     *
     * @param file     文件
     * @param data     字符序列
     * @param encoding 编码
     * @throws IOException 抛出IO异常
     */
    public static void write(final File file, final CharSequence data, final Charset encoding) throws IOException {
        write(file, data, encoding, false);
    }

    /**
     * 将字符序列写入文件里（覆盖）
     *
     * @param file     文件
     * @param data     字符序列
     * @param encoding 编码字符串
     * @throws IOException 抛出IO异常
     */
    public static void write(final File file, final CharSequence data, final String encoding) throws IOException {
        write(file, data, encoding, false);
    }

    /**
     * 将字符序列写入文件里
     * <p>如果 append 是true 则追加、false是覆盖</p>
     *
     * @param file     文件
     * @param data     字符序列
     * @param encoding 编码字符串
     * @param append   是否追加
     * @throws IOException 抛出IO异常
     */
    public static void write(final File file, final CharSequence data, final String encoding, final boolean append)
            throws IOException {
        write(file, data, Charset.forName(encoding), append);
    }

    /**
     * 将字符序列写入文件里
     * <p>如果 append 是true 则追加、false是覆盖</p>
     *
     * @param file     文件
     * @param data     字符序列
     * @param encoding 编码
     * @param append   是否追加
     * @throws IOException 抛出IO异常
     */
    public static void write(final File file, final CharSequence data, final Charset encoding, final boolean append)
            throws IOException {
        final String str = data == null ? null : data.toString();
        writeStringToFile(file, str, encoding, append);
    }

    /**
     * 字节数据写入文件里（覆盖）
     *
     * @param file 文件
     * @param data 字节数组
     * @throws IOException 抛出IO异常
     */
    public static void writeByteArrayToFile(final File file, final byte[] data) throws IOException {
        writeByteArrayToFile(file, data, false);
    }

    /**
     * 将字节数组写入文件里
     * <p>如果 append 是true 则追加、false是覆盖</p>
     *
     * @param file   文件
     * @param data   字节数组
     * @param append 是否追加
     * @throws IOException 抛出IO异常
     */
    public static void writeByteArrayToFile(final File file, final byte[] data, final boolean append)
            throws IOException {
        writeByteArrayToFile(file, data, 0, data.length, append);
    }

    /**
     * 将字节数组根据其实偏移后的数据长度写入文件里（覆盖）
     *
     * @param file 文件
     * @param data 字节数组
     * @param off  偏移
     * @param len  写入的长度
     * @throws IOException 抛出IO异常
     */
    public static void writeByteArrayToFile(final File file, final byte[] data, final int off, final int len)
            throws IOException {
        writeByteArrayToFile(file, data, off, len, false);
    }

    /**
     * 将字节数组根据其实偏移后的数据长度写入文件里
     * <p>如果 append 是true 则追加、false是覆盖</p>
     *
     * @param file   文件
     * @param data   字节数组
     * @param off    偏移
     * @param len    写入的长度
     * @param append 是否追加
     * @throws IOException 抛出IO异常
     */
    public static void writeByteArrayToFile(final File file, final byte[] data, final int off,
                                            final int len, final boolean append) throws IOException {
        OutputStream out = null;
        try {
            out = openOutputStream(file, append);
            out.write(data, off, len);
        } finally {
            IOUtil.close(out);
        }
    }

    /**
     * 文件夹移动
     *
     * @param srcDir  源文件夹
     * @param destDir 移动后的文件夹
     * @throws IOException 抛出IO异常
     */
    public static void moveDirectory(final File srcDir, final File destDir) throws IOException {

        CheckAssert.check(srcDir, "源文件夹不能为空");
        CheckAssert.check(destDir, "目标文件夹不能为空");

        CheckAssert.check(!srcDir.exists(), String.format("源文件夹%s不存在", srcDir));
        CheckAssert.check(!srcDir.isDirectory(), String.format("源%s不是一个文件夹", srcDir));
        CheckAssert.check(destDir.exists(), String.format("目标源文件夹%s已存在", destDir));

        final boolean rename = srcDir.renameTo(destDir);
        if (!rename) {
            if (destDir.getCanonicalPath().startsWith(
                    srcDir.getCanonicalPath() + File.separator)) {
                throw new IOException(String.format("无法移动文件夹:源 %s 是它的子目录: %s", srcDir, destDir));
            }
            copyDirectory(srcDir, destDir);
            deleteDirectory(srcDir);
            CheckAssert.check(srcDir.exists(), String.format("移动后删除源文件夹'%s'失败", srcDir));
        }
    }

    /**
     * 将原目录移动到目标目录
     *
     * @param src           原目录
     * @param destDir       目标目录
     * @param createDestDir 不存在时是否自动创建
     * @throws IOException 抛出IO异常
     */
    public static void moveDirectoryToDirectory(final File src, final File destDir, final boolean createDestDir)
            throws IOException {
        CheckAssert.check(src, "源文件夹不能为空");
        CheckAssert.check(destDir, "目标文件夹不能为空");
        if (!destDir.exists() && createDestDir) {
            destDir.mkdirs();
        }
        CheckAssert.check(!destDir.exists(),
                String.format("目标文件夹%s不存在", destDir));
        CheckAssert.check(!destDir.isDirectory(),
                String.format("目标源%s不是一个文件夹", destDir));
        moveDirectory(src, new File(destDir, src.getName()));

    }

    /**
     * 文件移动
     *
     * @param srcFile  源文件
     * @param destFile 目标文件
     * @throws IOException 抛出IO异常
     */
    public static void moveFile(final File srcFile, final File destFile) throws IOException {
        CheckAssert.check(srcFile, "源文件不能为空");
        CheckAssert.check(destFile, "目标文件不能为空");
        CheckAssert.check(!srcFile.exists(), String.format("源文件'%s'不存在", srcFile));
        CheckAssert.check(srcFile.isDirectory(), String.format("源文件'%s'是一个文件夹", srcFile));
        CheckAssert.check(destFile.exists(), String.format("目标文件'%s'已存在", destFile));
        CheckAssert.check(destFile.isDirectory(), String.format("目标文件'%s'是一个文件夹", destFile));
        final boolean rename = srcFile.renameTo(destFile);
        if (!rename) {
            copyFile(srcFile, destFile);
            if (!srcFile.delete()) {
                delete(destFile);
                throw new IOException("删除源文件 '" + srcFile +
                        "' 失败、删除拷贝的文件 '" + destFile + "'");
            }
        }
    }

    /**
     * 将源文件移动到目标文件夹下
     *
     * @param srcFile       源文件
     * @param destDir       目标文件夹
     * @param createDestDir 是否创建目标文件夹
     * @throws IOException 抛出IO异常
     */
    public static void moveFileToDirectory(final File srcFile, final File destDir, final boolean createDestDir)
            throws IOException {
        CheckAssert.check(srcFile, "源文件不能为空");
        CheckAssert.check(destDir, "目标文件不能为空");
        if (!destDir.exists() && createDestDir) {
            destDir.mkdirs();
        }
        CheckAssert.check(!destDir.exists(), "目标文件没有创建、请设置createDestDir参数为True");
        CheckAssert.check(!destDir.isDirectory(), String.format("目标文件'%s'不是一个文件夹", destDir));
        moveFile(srcFile, new File(destDir, srcFile.getName()));
    }

    /**
     * 将原文件或文件夹移动到目标文件夹下
     *
     * @param src           原文件或文件夹
     * @param destDir       目标文件夹
     * @param createDestDir 是否创建目标文件夹、目标文件夹不存在时请创建
     * @throws IOException 抛出IO异常
     */
    public static void moveToDirectory(final File src, final File destDir, final boolean createDestDir)
            throws IOException {
        CheckAssert.check(src, "源文件不能为空");
        CheckAssert.check(destDir, "目标文件不能为空");
        CheckAssert.check(!src.exists(), String.format("源文件'%s'不存在", src));
        if (src.isDirectory()) {
            moveDirectoryToDirectory(src, destDir, createDestDir);
        } else {
            moveFileToDirectory(src, destDir, createDestDir);
        }
    }

    /**
     * 若是文件夹则清空文件夹删除本文件夹、若不是文件夹则删除文件
     *
     * @param file 删除的文件或文件夹
     * @return 删除文件成功或失败
     */
    public static boolean delete(final File file) {
        if (file == null) {
            return false;
        }
        try {
            if (file.isDirectory()) {
                cleanDirectory(file);
            }
        } catch (final Exception ignored) {
        }

        try {
            return file.delete();
        } catch (final Exception ignored) {
            return false;
        }
    }

    /**
     * 检测并获取目录下的所有文件
     *
     * @param directory 目录
     * @return 返回目录下的所有文件
     * @throws IOException 抛出IO异常
     */
    private static File[] verifiedListFiles(File directory) throws IOException {
        CheckAssert.check(!directory.exists(), String.format("%s 目录不存在", directory));
        CheckAssert.check(!directory.isDirectory(), String.format("%s 不是一个目录", directory));
        final File[] files = directory.listFiles();
        CheckAssert.check(files, String.format("没有找到目录下的东西 %s", directory));
        return files;
    }

    /**
     * 检测是否是目录、而不是软连接
     *
     * @param file 目录或文件
     * @return 返回是否是软连接
     * @throws IOException 抛出IO异常
     */
    public static boolean isSymlink(final File file) throws IOException {
        return SystemSupport.isSymLink(file);
    }

    /**
     * 检测文件
     *
     * @param file           检测的文件
     * @param checkNotExists 检测是否不存在
     */
    private static void checkFileRequired(final File file, final boolean checkNotExists) {
        CheckAssert.check(file, "文件不存在");
        if (checkNotExists) {
            CheckAssert.check(!file.exists(), String.format("文件'%s'不存在", file));
        }
    }
}
