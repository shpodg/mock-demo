package demo.userfront.util;

import demo.userfront.exception.BusinessException;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by oneday on 2016/7/26 0026.
 */
public class HttpUtils {
    public String post(String url, Object params) throws Exception {
        return postString(url,bean2Map(params));
    }
    public String get(String url) throws Exception {
        return getString(url);
    }
    public String put(String url, Object params) throws Exception {
        return putString(url,bean2Map(params));
    }

    public static Map<String,String> bean2Map(Object obj){
        Map<String, String> map = new HashMap<>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    map.put(key, String.valueOf(value));
                }
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }

        return map;
    }
    public HttpUtils() {

    }

    public HttpUtils(String url) throws Exception {
        this.url = new URL(url);
    }

    private static final String CONTENT_CHARSET = "UTF-8";// 请求内容字符编码
    private static final int CONNECTION_TIMEOUT = 8 * 1000;// 定义了链接超时时间

    public static String getStreamString(InputStream is, String inCharset) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            writeFromInputStreamToOutputStream(null, is, baos);
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (Exception ee) {
                //do nothing
            }
        }
        return baos.toString(inCharset);
    }

    public static String send(String address, String xml, String outCharset, String inCharset, int connectTimeout,
                              int readTimeout) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        ByteArrayOutputStream baos = null;
        try {
            URL url = new URL(address);
            outCharset = outCharset == null ? CONTENT_CHARSET : outCharset;
            inCharset = inCharset == null ? CONTENT_CHARSET : inCharset;
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // conn
            // 设置User-Agent，防止有些网站或Web服务器不支持
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; CIBA)");
            // 设置超时时间
            conn.setConnectTimeout(connectTimeout);
            conn.setReadTimeout(readTimeout);

            // 设置连接既可以写入也可以读取（post）
            conn.setDoInput(true);
            conn.setDoOutput(true);

            // 开始写入数据
            os = conn.getOutputStream();
            os.write(xml.getBytes(outCharset));
            // 开始读取数据
            is = conn.getInputStream();
            baos = new ByteArrayOutputStream();
            writeFromInputStreamToOutputStream(null, is, baos);
            // System.out.println(baos.size());
            return baos.toString(inCharset);
        } catch (Exception ex) {
            throw new IOException("通过POST读取http出错:" + address, ex);
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    //do nothing
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (Exception ex) {
                    //do nothing
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (Exception ex) {
                    //do nothing
                }
            }
        }
    }

    public static InputStream readPostAsInputStream(String address, String data, String outCharset, String inCharset, int connectTimeout,
                                                    int readTimeout) throws IOException {
        OutputStreamWriter wr = null;
        try {
            URL url = new URL(address);
            data = data == null ? "" : data;
            outCharset = outCharset == null ? CONTENT_CHARSET : outCharset;
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // conn
            // 设置User-Agent，防止有些网站或Web服务器不支持
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; CIBA)");
            // 设置超时时间
            conn.setConnectTimeout(connectTimeout);
            conn.setReadTimeout(readTimeout);

            // 设置连接既可以写入也可以读取（post）
            conn.setDoInput(true);
            conn.setDoOutput(true);

            // 开始写入数据
            wr = new OutputStreamWriter(conn.getOutputStream(), outCharset);
            wr.write(data);
            wr.flush();

            return conn.getInputStream();
        } catch (Exception ex) {
            throw new IOException("通过POST读取http出错:" + address, ex);
        } finally {
            if (wr != null) {
                try {
                    wr.close();
                } catch (Exception ex) {
                    //do nothing
                }
            }
        }
    }

    public static String readPostAsString(String address, String data, String jssessionid) throws IOException {
        OutputStreamWriter wr = null;
        InputStream is = null;
        try {
            URL url = new URL(address);
            data = data == null ? "" : data;
            String outCharset = CONTENT_CHARSET;
            String inCharset = CONTENT_CHARSET;
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("cookie", "JSESSIONID=" + jssessionid);
            // conn
            // 设置User-Agent，防止有些网站或Web服务器不支持
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; CIBA)");
            // 设置超时时间
            conn.setConnectTimeout(100000);
            conn.setReadTimeout(10000);

            // 设置连接既可以写入也可以读取（post）
            conn.setDoInput(true);
            conn.setDoOutput(true);

            // 开始写入数据
            wr = new OutputStreamWriter(conn.getOutputStream(), outCharset);
            wr.write(data);
            wr.flush();

            // 开始读取数据
            is = conn.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            writeFromInputStreamToOutputStream(null, is, baos);
            return baos.toString(inCharset);
        } catch (Exception ex) {
            throw new IOException("通过POST读取http出错:" + address, ex);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception ex) {
                    //do nothing
                }
            }
            if (wr != null) {
                try {
                    wr.close();
                } catch (Exception ex) {
                    //do nothing
                }
            }
        }
    }

    public static String readPostAsString(String address, String data, String outCharset, String inCharset, int connectTimeout,
                                          int readTimeout) throws IOException {
        return readPostAsString(address, data, outCharset, inCharset, connectTimeout, readTimeout, null);
    }

    public static String readPostAsString(String address, String data, String outCharset, String inCharset, int connectTimeout,
                                          int readTimeout, String hostName) throws IOException {
        OutputStreamWriter wr = null;
        InputStream is = null;
        try {
            URL url = new URL(address);
            data = data == null ? "" : data;
            outCharset = outCharset == null ? CONTENT_CHARSET : outCharset;
            inCharset = inCharset == null ? CONTENT_CHARSET : inCharset;
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 设置User-Agent，防止有些网站或Web服务器不支持
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; CIBA)");
            if (hostName != null && hostName.trim().length() > 0) {
                conn.setRequestProperty("Host", hostName);
            }
            // 设置超时时间
            conn.setConnectTimeout(connectTimeout);
            conn.setReadTimeout(readTimeout);

            // 设置连接既可以写入也可以读取（post）
            conn.setDoInput(true);
            conn.setDoOutput(true);

            // 开始写入数据
            wr = new OutputStreamWriter(conn.getOutputStream(), outCharset);
            wr.write(data);
            wr.flush();

            // 开始读取数据
            is = conn.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            writeFromInputStreamToOutputStream(null, is, baos);
            return baos.toString(inCharset);
        } catch (Exception ex) {
            throw new IOException("通过POST读取http出错:" + address, ex);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception ex) {
                    //do nothing
                }
            }
            if (wr != null) {
                try {
                    wr.close();
                } catch (Exception ex) {
                    //do nothing
                }
            }
        }
    }

    public static long writeFromInputStreamToOutputStream(byte[] buffer, InputStream is, OutputStream os) throws IOException {
        long count = 0;
        if (buffer == null) {
            buffer = new byte[1024];
        }
        try {
            while (true) {
                int length = is.read(buffer);
                if (length == 0) {
                    continue;
                }
                if (length == -1) {
                    break;
                }
                os.write(buffer, 0, length);
                count += length;
            }
        } catch (IOException e) {
            throw new IOException("读流异常", e);
        }
        return count;
    }

    /**
     * 根据URL获取响应字符串内容
     *
     * @param urlString 请求的URL（注意：URL字符串不能包含URL特殊字符）
     * @return 响应字符串
     * @throws Exception
     */
    public static String getString(String urlString) throws Exception {
        return new String(getByteArray(urlString), CONTENT_CHARSET);
    }

    /**
     * 根据URL获取响应字符串内容
     *
     * @param urlString 请求的URL（注意：URL字符串不能包含URL特殊字符）
     * @return 响应字符串
     * @throws Exception
     */
    public static String getString(String urlString, Map<String, String> params) throws Exception {
        if (params != null && params.size() != 0) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (entry.getValue() != null) {
                    sb.append(entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), CONTENT_CHARSET) + "&");
                }
            }
            urlString = urlString + "?" + sb.toString();
        }
        return new String(getByteArray(urlString), CONTENT_CHARSET);
    }


    /**
     * 根据URL字符串和POST请求参数获取响应字节数组
     *
     * @param urlString 请求的URL（注意：URL字符串不能包含URL特殊字符）
     * @param params    请求参数的键值对
     * @return 响应字符串
     * @throws Exception
     */
    public static String postString(String urlString, Map<String, String> params) throws Exception {
        return new String(postByteArray(urlString, params, null), CONTENT_CHARSET);
    }

    public static String putString(String urlString, Map<String, String> params) throws Exception {
        StringBuilder paramsString = new StringBuilder();
        if (params!=null && !params.isEmpty()) {
            for (Map.Entry<String,String> e:params.entrySet()) {
                paramsString.append(e.getKey()).append("=").append(e.getValue()).append("&");
            }
            paramsString.deleteCharAt(paramsString.length()-1);
        }
        return new String(putByteArray(urlString, paramsString.toString(), null), CONTENT_CHARSET);
    }

    /**
     * 根据URL字符串和POST请求参数获取响应字节数组
     *
     * @param urlString 请求的URL（注意：URL字符串不能包含URL特殊字符）
     * @param params    请求参数的键值对
     * @param charSet   设定返回报文字符集
     * @return 响应字符串
     * @throws Exception
     */
    public static String postString(String urlString, Map<String, String> params, String charSet) throws Exception {
        return new String(postByteArray(urlString, params, null), charSet);
    }

    /**
     * 根据URL字符串和POST请求参数获取响应字节数组,自定义请求头
     *
     * @param urlString    请求的URL（注意：URL字符串不能包含URL特殊字符）
     * @param params       请求参数的键值对
     * @param customHeader 自定义请求头
     * @return 响应字符串
     * @throws Exception
     */
    public static String postStringCustomHeader(String urlString, Map<String, String> params,
                                                Map<String, String> customHeader) throws Exception {
        return new String(postByteArray(urlString, params, customHeader), CONTENT_CHARSET);
    }

    /**
     * 根据URL获取响应字节数组
     *
     * @param urlString 请求的URL（注意：URL字符串不能包含URL特殊字符）
     * @return 响应字节数组
     * @throws Exception
     */
    public static byte[] getByteArray(String urlString) throws Exception {
        byte[] result = null;
        HttpURLConnection connection = null;
        URL url;
        InputStream is = null;
        try {
            // 获得URL对象
            url = new URL(urlString);
            // 获得HttpURLConnection链接对象
            connection = (HttpURLConnection) url.openConnection();
            // 设置不适用缓存
            connection.setRequestProperty("Pragma", "no-cache");
            connection.setRequestProperty("Cache-Control", "no-cache");
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);// 设置链接超时时间
            connection.setRequestMethod("GET");
            connection.setInstanceFollowRedirects(true);
            // 进行连接，但是实际上connection.getInputStream()函数中才会真正发到
            connection.connect();
            // 取得输入流
            connection.getInputStream();
            // 获取输入流
            is = connection.getInputStream();
            // 创建缓冲输出流
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];// 1KB的字节缓冲区
            int length = 0;
            while ((length = is.read(buffer)) != -1) {
                bos.write(buffer, 0, length);
            }
            // 获得响应字节数组
            result = bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (is != null) {
                    // 关闭响应输入流
                    is.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    // 断开HTTP链接
                    connection.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 根据URL字符串和POST请求参数获取响应字节数组
     *
     * @param urlString 请求的URL（注意：URL字符串不能包含URL特殊字符）
     * @param params    请求参数的键值对
     * @return 响应字节数组
     * @throws Exception
     */
    public static byte[] postByteArray(String urlString, Map<String, String> params, Map<String, String> customHeader) throws Exception {
        byte[] result = null;
        HttpURLConnection connection = null;
        URL url;
        InputStream is = null;
        try {
            // 获得URL对象
            url = new URL(urlString);
            // 获得HttpURLConnection链接对象
            connection = (HttpURLConnection) url.openConnection();
            // 设置不适用缓存
            connection.setRequestProperty("Pragma", "no-cache");
            connection.setRequestProperty("Cache-Control", "no-cache");
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);// 设置链接超时时间
            connection.setRequestMethod("POST");
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + CONTENT_CHARSET);
            if (customHeader!=null && !customHeader.isEmpty()) {
                for (Map.Entry<String, String> entry : customHeader.entrySet()) {
                    if (entry.getValue() != null) {
                        connection.setRequestProperty(entry.getKey(), entry.getValue());
                    }
                }
            }
            // 进行连接，但是实际上connection.getInputStream()函数中才会真正发到
            connection.connect();
            // 判断是否有POST参数，如果有则将数据传到服务器
            if (params != null && params.size() != 0) {
                StringBuilder sb = new StringBuilder();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    if (entry.getValue() != null) {
                        sb.append(entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), CONTENT_CHARSET) + "&");
                    }
                }

                // 获得输出流
                OutputStream os = connection.getOutputStream();
                os.write(sb.toString().getBytes(CONTENT_CHARSET));
                // 刷新输出流，确保数据被传到服务器
                os.flush();
                // 关闭输出流
                os.close();
            }
            // 取得输入流
            connection.getInputStream();
            // 获取输入流
            is = connection.getInputStream();
            // 创建缓冲输出流
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];// 1KB的字节缓冲区
            int length = 0;
            while ((length = is.read(buffer)) != -1) {
                bos.write(buffer, 0, length);
            }
            // 获得响应字节数组
            result = bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (is != null) {
                    // 关闭响应输入流
                    is.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    // 断开HTTP链接
                    connection.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }


    /**
     * 根据URL字符串和POST请求参数获取响应字节数组
     *
     * @param urlString 请求的URL（注意：URL字符串不能包含URL特殊字符）
     * @param params    请求参数,放到POST请求BODY里
     * @return 响应字节数组
     * @throws Exception
     */
    public static byte[] putByteArray(String urlString, String params, Map<String, String> customHeader) throws Exception {
        byte[] result = null;
        HttpURLConnection connection = null;
        URL url;
        InputStream is = null;
        try {
            // 获得URL对象
            url = new URL(urlString);
            // 获得HttpURLConnection链接对象
            connection = (HttpURLConnection) url.openConnection();
            // 设置不适用缓存
            connection.setRequestProperty("Pragma", "no-cache");
            connection.setRequestProperty("Cache-Control", "no-cache");
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);// 设置链接超时时间
            connection.setRequestMethod("PUT");
            //connection.setInstanceFollowRedirects(true);
            //connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + CONTENT_CHARSET);
            if (customHeader!=null && !customHeader.isEmpty()) {
                for (Map.Entry<String, String> entry : customHeader.entrySet()) {
                    if (entry.getValue() != null) {
                        connection.setRequestProperty(entry.getKey(), entry.getValue());
                    }
                }
            }
            // 进行连接，但是实际上connection.getInputStream()函数中才会真正发到
            connection.connect();
            // 判断是否有POST参数，如果有则将数据传到服务器
            if (params != null && params.length() != 0) {

                // 获得输出流
                OutputStream os = connection.getOutputStream();
                os.write(params.getBytes(CONTENT_CHARSET));
                // 刷新输出流，确保数据被传到服务器
                os.flush();
                // 关闭输出流
                os.close();
            }
            // 取得输入流
            connection.getInputStream();
            // 获取输入流
            is = connection.getInputStream();
            // 创建缓冲输出流
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];// 1KB的字节缓冲区
            int length = 0;
            while ((length = is.read(buffer)) != -1) {
                bos.write(buffer, 0, length);
            }
            // 获得响应字节数组
            result = bos.toByteArray();
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (is != null) {
                    // 关闭响应输入流
                    is.close();
                }
            } catch (Exception e) {
                //do nothing
            } finally {
                try {
                    // 断开HTTP链接
                    connection.disconnect();
                } catch (Exception e) {
                    //do nothing
                }
            }
        }
        return result;
    }

    URL url;
    HttpURLConnection conn;
    String boundary = "--------httppost123";
    Map<String, String> textParams = new HashMap<String, String>();
    Map<String, File> fileParams = new HashMap<String, File>();
    DataOutputStream ds;
    BufferedOutputStream bos;



    // 重新设置要请求的服务器地址，即上传文件的地址。
    public void setUrl(String url) throws Exception {
        this.url = new URL(url);
    }

    // 增加一个普通字符串数据到form表单数据中
    public void addTextParameter(String name, String value) {
        textParams.put(name, value);
    }

    // 增加一个文件到form表单数据中
    public void addFileParameter(String name, File value) {
        fileParams.put(name, value);
    }

    // 清空所有已添加的form表单数据
    public void clearAllParameters() {
        textParams.clear();
        fileParams.clear();
    }

    // 发送数据到服务器，返回一个字节包含服务器的返回结果的数组
    public byte[] send() throws Exception {
        initConnection();
        try {
            conn.connect();
        } catch (SocketTimeoutException e) {
            // something
            throw new RuntimeException();
        }
        ds = new DataOutputStream(conn.getOutputStream());
        // bos = new BufferedOutputStream(conn.getOutputStream());
        writeFileParams();
        writeStringParams();
        paramsEnd();
        InputStream in = conn.getInputStream();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int b;
        while ((b = in.read()) != -1) {
            out.write(b);
        }
        conn.disconnect();
        return out.toByteArray();
    }

    // 文件上传的connection的一些必须设置
    private void initConnection() throws Exception {
        conn = (HttpURLConnection) this.url.openConnection();
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setConnectTimeout(10000); // 连接超时为10秒
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Accept-Charset", "utf-8");
        conn.setRequestProperty("contentType", "utf-8");
        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
    }

    // 普通字符串数据
    private void writeStringParams() throws Exception {
        Set<String> keySet = textParams.keySet();
        for (Iterator<String> it = keySet.iterator(); it.hasNext(); ) {
            String name = it.next();
            String value = textParams.get(name);
            ds.writeBytes("--" + boundary + "\r\n");
            ds.writeBytes("Content-Disposition: form-data; name=\"" + name + "\"\r\n");
            ds.writeBytes("\r\n");
            // ds.writeBytes(encode(value) + "\r\n");
            ds.write(value.getBytes("UTF-8"));
            ds.writeBytes("\r\n");

        }
    }

    // 文件数据
    private void writeFileParams() throws Exception {
        Set<String> keySet = fileParams.keySet();
        for (Iterator<String> it = keySet.iterator(); it.hasNext(); ) {
            String name = it.next();
            File value = fileParams.get(name);
            ds.writeBytes("--" + boundary + "\r\n");
            // ds.writeBytes("Content-Disposition: form-data; name=\"" + name + "\"; filename=\"" + encode(value.getName()) + "\"\r\n");
            ds.writeBytes("Content-Disposition: form-data; name=\"" + name + "\"; filename=\"");
            // + value.getName().getBytes("UTF-8") + "\"\r\n");
            ds.write(value.getName().getBytes("UTF-8"));
            ds.writeBytes("\"\r\n");
            ds.writeBytes("Content-Type: " + getContentType(value) + "\r\n");
            ds.writeBytes("\r\n");
            ds.write(getBytes(value));
            ds.writeBytes("\r\n");
        }
    }

    /**
     * 获取文件的上传类型
     * 图片格式为image/png,image/jpg等
     * 非图片为application/octet-stream
     */
    private String getContentType(File f) throws Exception {
        // 此行不再细分是否为图片，全部作为application/octet-stream 类型
        ImageInputStream imageIn = ImageIO.createImageInputStream(f);
        if (imageIn == null) {
            return "application/octet-stream";
        }
        Iterator<ImageReader> it = ImageIO.getImageReaders(imageIn);
        if (!it.hasNext()) {
            imageIn.close();
            return "application/octet-stream";
        }
        imageIn.close();
        return "image/" + it.next().getFormatName().toLowerCase();// 将FormatName返回的值转换成小写，默认为大写

    }

    // 把文件转换成字节数组
    private byte[] getBytes(File f) throws Exception {
        FileInputStream in = new FileInputStream(f);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int n;
        while ((n = in.read(b)) != -1) {
            out.write(b, 0, n);
        }
        in.close();
        return out.toByteArray();
    }

    // 添加结尾数据
    private void paramsEnd() throws Exception {
        ds.writeBytes("--" + boundary + "--" + "\r\n");
        ds.writeBytes("\r\n");
    }


}
