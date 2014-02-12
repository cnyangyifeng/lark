package cn.me.xdf.common.json.hibernate4;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.BeanProperty;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.TypeSerializer;
import org.codehaus.jackson.map.ser.impl.PropertySerializerMap;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-10-29
 * Time: 上午10:27
 * To change this template use File | Settings | File Templates.
 */
public class HibernateProxySerializer
        extends JsonSerializer<HibernateProxy>
{
    /**
     * Property that has proxy value to handle
     */
    protected final BeanProperty _property;

    protected final boolean _forceLazyLoading;


    protected PropertySerializerMap _dynamicSerializers;


    public HibernateProxySerializer(BeanProperty property, boolean forceLazyLoading)
    {
        _property = property;
        _forceLazyLoading = forceLazyLoading;
        _dynamicSerializers = PropertySerializerMap.emptyMap();
    }


    @Override
    public void serialize(HibernateProxy value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException
    {
        Object proxiedValue = findProxied(value);
        if (proxiedValue == null) {
            provider.defaultSerializeNull(jgen);
            return;
        }
        findSerializer(provider, proxiedValue).serialize(proxiedValue, jgen, provider);
    }

    public void serializeWithType(HibernateProxy value, JsonGenerator jgen, SerializerProvider provider,
                                  TypeSerializer typeSer)
            throws IOException, JsonProcessingException
    {
        Object proxiedValue = findProxied(value);
        if (proxiedValue == null) {
            provider.defaultSerializeNull(jgen);
            return;
        }

        findSerializer(provider, proxiedValue).serializeWithType(proxiedValue, jgen, provider, typeSer);
    }

    /*
    /**********************************************************************
    /* Helper methods
    /**********************************************************************
     */

    protected JsonSerializer<Object> findSerializer(SerializerProvider provider, Object value)
            throws IOException, JsonProcessingException
    {
        /* TODO: if Hibernate did use generics, or we wanted to allow use of Jackson
         *  annotations to indicate type, should take that into account.
         */
        Class<?> type = value.getClass();

        PropertySerializerMap.SerializerAndMapResult result = _dynamicSerializers.findAndAddSerializer(type,
                provider, _property);
        if (_dynamicSerializers != result.map) {
            _dynamicSerializers = result.map;
        }
        return result.serializer;
    }


    protected Object findProxied(HibernateProxy proxy)
    {
        LazyInitializer init = proxy.getHibernateLazyInitializer();
        if (!_forceLazyLoading && init.isUninitialized()) {
            return null;
        }
        return init.getImplementation();
    }
}
