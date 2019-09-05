package tech.mhuang.core.io;

import tech.mhuang.core.check.CheckAssert;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.nio.charset.Charset;

/**
 * IO工具类.
 * <p>提取各种比较好的工具类调用等</p>
 *
 * @author mhuang
 * @since 1.0.0
 */
public class IOUtil {

    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;
    public static final int EOF = -1;

    /**
     * InputStream convert String default close stream
     *
     * @param in inputstream
     * @return tring type data
     * @throws IOException IOException
     */
    public static String toString(final InputStream in) throws IOException {
        return toString(in, Charset.defaultCharset().name());
    }

    /**
     * InputStream convert String used encoding default close stream
     *
     * @param in       inputstream
     * @param encoding encoding
     * @return string type data
     * @throws IOException IOException
     */
    public static String toString(final InputStream in, final String encoding) throws IOException {
        return toString(in, encoding, true);
    }

    /**
     * InputStream convert String setter isClosed stream used  defaulte ncoding
     *
     * @param in       InputStream
     * @param isClosed if <code>true</code> closed input stream or <code>false</code> not closed
     * @return String
     * @throws IOException IOException
     */
    public static String toString(final InputStream in, final boolean isClosed) throws IOException {
        return toString(in, Charset.defaultCharset().name(), isClosed);
    }

    /**
     * inputsteam convert to string used encoding configuration closed stream
     *
     * @param in       inpustream
     * @param encoding encoding
     * @param isClosed if <code>true</code> closed input stream or <code>false</code> not closed
     * @return string type data
     * @throws IOException IOException
     */
    public static String toString(final InputStream in, final String encoding, final boolean isClosed) throws IOException {
        return ((ByteArrayOutputStream) toOutputStream(in, isClosed)).toString(encoding);
    }

    /**
     * InputStream convert byte[] default close stream
     *
     * @param input inputstream
     * @return bytearray type data
     * @throws IOException IOException
     */
    public static byte[] toByteArray(final InputStream input) throws IOException {
        return toByteArray(input, true);
    }

    /**
     * InputStream convert OutputStream default close stream
     *
     * @param input inputstream
     * @return OutputStream
     * @throws IOException IOException
     */
    public static OutputStream toOutputStream(final InputStream input) throws IOException {
        return toOutputStream(input, true);
    }

    /**
     * InputStream convert OutputStream configuration cloed stream
     *
     * @param input    inpustream
     * @param isClosed if <code>true</code> closed input stream or <code>false</code> not closed
     * @return OutputStream
     * @throws IOException IOException
     */
    public static OutputStream toOutputStream(final InputStream input, boolean isClosed) throws IOException {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = input.read(buffer)) != -1) {
                baos.write(buffer, 0, length);
            }
            baos.flush();
            return baos;
        } finally {
            if (isClosed) {
                close(input);
            }
        }
    }

    /**
     * InputStream convert <code>byte[]</code> configuration cloed stream
     *
     * @param input    inpustream
     * @param isClosed if <code>true</code> closed input stream or <code>false</code> not closed
     * @return byte[]
     * @throws IOException IOException
     */
    public static byte[] toByteArray(final InputStream input, boolean isClosed) throws IOException {
        return ((ByteArrayOutputStream) toOutputStream(input, isClosed)).toByteArray();
    }


    /**
     * copy inputstream to outputstream default set buffer size 4MB
     *
     * @param input  inputstream
     * @param output outputstream
     * @return size
     * @throws IOException IOException
     */
    public static int copy(final InputStream input, final OutputStream output) throws IOException {
        final long count = copyLarge(input, output);
        if (count > Integer.MAX_VALUE) {
            return -1;
        }
        return (int) count;
    }


    /**
     * copy inputstream to outputstream  default set buffer size 4M
     *
     * @param input  inputstream
     * @param output outputstream
     * @return size
     * @throws IOException IOException
     */
    public static long copyLarge(final InputStream input, final OutputStream output)
            throws IOException {
        return copy(input, output, DEFAULT_BUFFER_SIZE);
    }

    /**
     * copy inputstream to outputstream  set buffer size
     *
     * @param input      inputstream
     * @param output     outputstream
     * @param bufferSize buffer size
     * @return size
     * @throws IOException IOException
     */
    public static long copy(final InputStream input, final OutputStream output, final int bufferSize)
            throws IOException {
        return copyLarge(input, output, new byte[bufferSize]);
    }

    /**
     * copy inputstream to outputstream  set buffer size
     *
     * @param input  inputstream
     * @param output outputstream
     * @param buffer buffer size
     * @return size
     * @throws IOException IOException
     */
    public static long copyLarge(final InputStream input, final OutputStream output, final byte[] buffer)
            throws IOException {
        long count = 0;
        int n;
        while (EOF != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    /**
     * closed URLConnection
     *
     * @param conn URLConnection
     */
    public static void close(final URLConnection conn) {
        if (conn instanceof HttpURLConnection) {
            ((HttpURLConnection) conn).disconnect();
        }
    }

    /**
     * closed  Reader
     *
     * @param input Reader
     */
    public static void close(final Reader input) {
        close((Closeable) input);
    }

    /**
     * closed Writer
     *
     * @param output Writer
     */
    public static void close(final Writer output) {
        close((Closeable) output);
    }

    /**
     * closed  InputStream
     *
     * @param input InputStream
     */
    public static void close(final InputStream input) {
        close((Closeable) input);
    }

    /**
     * closed  OutputStream
     *
     * @param output OutputStream
     */
    public static void close(final OutputStream output) {
        close((Closeable) output);
    }

    /**
     * closed  Closeable
     *
     * @param closeable Closeable
     */
    public static void close(final Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (final IOException ioe) {
            // ignore
        }
    }

    /**
     * closed  Closeable...
     *
     * @param closeables Closeable...
     */
    public static void close(final Closeable... closeables) {
        if (closeables == null) {
            return;
        }
        for (final Closeable closeable : closeables) {
            close(closeable);
        }
    }

    /**
     * outputstream write data
     *
     * @param data     data type string
     * @param output   outputstream
     * @param encoding encoding
     * @throws IOException IOException
     */
    public static void write(final String data, final OutputStream output, final Charset encoding) throws IOException {
        CheckAssert.check(data, "data is null");
        write(data.getBytes(encoding), output);
    }

    /**
     * outputstream write data
     *
     * @param data   data type string
     * @param output outputstream
     * @throws IOException IOException
     */
    public static void write(final String data, final OutputStream output) throws IOException {
        write(data, output, Charset.defaultCharset());
    }

    /**
     * outputstream write data
     *
     * @param data   data type byte[]
     * @param output outputstream
     * @throws IOException IOException
     */
    public static void write(final byte[] data, final OutputStream output) throws IOException {
        CheckAssert.check(output, "outstream is null");
        CheckAssert.check(data, "data is null");
        output.write(data);
    }
}
