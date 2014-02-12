package cn.me.xdf.common.json;

import java.io.IOException;
import java.lang.reflect.Type;
import cn.me.xdf.common.json.hibernate4.Hibernate4Module;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Json工具类
 *
 * @author xiaobin
 */
public class JsonUtils {

    private static final Logger log = LoggerFactory.getLogger(JsonUtils.class);

    private static ObjectMapper objectMapper = null;

    /**
     * 把对象转化为json
     *
     * @param entity
     * @return
     */
    public static String writeObjectToJson(Object entity) {
        try {
            if (objectMapper == null)
                objectMapper = new ObjectMapper();
            objectMapper.registerModule(new Hibernate4Module());
            return objectMapper.writeValueAsString(entity);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 把json字符串转化为Object
     *
     * @param json
     * @param clazz
     * @return
     */
    public static <T> T readObjectByJson(String json, Class<T> clazz) {
        try {
            if (objectMapper == null) {
                objectMapper = new ObjectMapper();
            }
            return objectMapper.readValue(json, clazz);
        } catch (JsonParseException e) {
            log.error("json==" + json + ",error(JsonParseException):"
                    + e.getMessage());
            throw new RuntimeException(e);
        } catch (JsonMappingException e) {
            log.error("json==" + json + ",error(JsonMappingExcption):"
                    + e.getMessage());
            throw new RuntimeException(e);
        } catch (IOException e) {
            log.error("json==" + json + ",error(IOException):" + e.getMessage());
            throw new RuntimeException(e);
        }
    }


    /**
     * 把json字符串转化为Object
     *
     * @param json
     * @param collectionClass
     * @param elementClasses
     * @return
     */
    public static <T> T readBeanByJson(String json, Class<?> collectionClass, Class<?> elementClasses) {
        try {
            if (objectMapper == null) {
                objectMapper = new ObjectMapper();
            }
            JavaType javaType = getCollectionType(collectionClass, elementClasses);
            return objectMapper.readValue(json, javaType);

        } catch (JsonParseException e) {
            log.error("json==" + json + ",error(JsonParseException):"
                    + e.getMessage());
            throw new RuntimeException(e);
        } catch (JsonMappingException e) {
            log.error("json==" + json + ",error(JsonMappingExcption):"
                    + e.getMessage());
            throw new RuntimeException(e);
        } catch (IOException e) {
            log.error("json==" + json + ",error(IOException):" + e.getMessage());
            throw new RuntimeException(e);
        }
    }


    public static <T> T readObjectByTypeJson(String json, Type type) {
        try {
            if (objectMapper == null) {
                objectMapper = new ObjectMapper();
            }
            JavaType javaType = objectMapper.constructType(type);
            return objectMapper.readValue(json, javaType);
        } catch (JsonParseException e) {
            log.error("json==" + json + ",error(JsonParseException):"
                    + e.getMessage());
            throw new RuntimeException(e);
        } catch (JsonMappingException e) {
            log.error("json==" + json + ",error(JsonMappingExcption):"
                    + e.getMessage());
            throw new RuntimeException(e);
        } catch (IOException e) {
            log.error("json==" + json + ",error(IOException):" + e.getMessage());
            throw new RuntimeException(e);
        }
    }


    private static JavaType getCollectionType(Class<?> collectionClass, Class<?> elementClasses) {
        return objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }
}
