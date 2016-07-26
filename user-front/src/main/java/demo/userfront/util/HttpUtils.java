package demo.userfront.util;

import demo.userfront.exception.BusinessException;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by oneday on 2016/7/26 0026.
 */
public class HttpUtils {
    public String post(String url, Object param){
        return "";
    }
    public String get(String url){

        return "";
    }
    public String put(String url, Object param){

        return "";
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

}
