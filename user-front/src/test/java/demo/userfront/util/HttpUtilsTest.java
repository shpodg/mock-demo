package demo.userfront.util;

import demo.userfront.vo.UserVo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * HttpUtils Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>27, 2016</pre>
 */
public class HttpUtilsTest {
    HttpUtils httpUtils = new HttpUtils();
    String url = "http://localhost:8081/user";

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    private UserVo getUserVo() {
        UserVo zs = new UserVo();
        zs.setId("1");
        zs.setName("张三");
        zs.setAge(10);
        zs.setSex("男");
        return zs;
    }

    /**
     * Method: post(String url, Object params)
     */
    @Test
    public void testPost() throws Exception {
        UserVo user = getUserVo();
        String result = httpUtils.post(url,user);
        System.out.println(result);
    }

    /**
     * Method: get(String url)
     */
    @Test
    public void testGet() throws Exception {
        String result = httpUtils.get(url);
        System.out.println(result);
    }

    /**
     * Method: put(String url, Object params)
     */
    @Test
    public void testPut() throws Exception {
        UserVo user = getUserVo();
        String result = httpUtils.put(url,user);
        System.out.println(result);
    }
    @Test
    public void doPut() throws Exception{
        URL url = new URL("http://localhost:8081/user");
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("PUT");
        String paramStr = "id=1&name=张三&age=10&sex=男";//"{\"id\":1,\"name\":\"张三\",\"age\":123,\"sex\":\"Hello World\"}";//
        conn.setDoInput(true);
        conn.setDoOutput(true);
        //conn.setRequestProperty("Content-Type","application/json");
        OutputStream os = conn.getOutputStream();
        os.write(paramStr.toString().getBytes("utf-8"));
        os.close();

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line ;
        String result ="";
        while( (line =br.readLine()) != null ){
            result += "/n"+line;
        }
        System.out.println(result);
        br.close();

    }
} 
