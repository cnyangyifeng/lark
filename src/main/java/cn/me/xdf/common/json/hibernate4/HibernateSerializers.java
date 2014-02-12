package cn.me.xdf.common.json.hibernate4;

import org.codehaus.jackson.map.*;
import org.codehaus.jackson.map.type.*;
import org.codehaus.jackson.type.JavaType;
import org.hibernate.collection.internal.PersistentMap;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.proxy.HibernateProxy;

/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-10-29
 * Time: 上午10:16
 * To change this template use File | Settings | File Templates.
 */
public class HibernateSerializers extends Serializers.Base
{
    protected final int _moduleFeatures;

    public HibernateSerializers(int features)
    {
        _moduleFeatures = features;
    }

    @Override
    public JsonSerializer<?> findSerializer(
            SerializationConfig config, JavaType type,
            BeanDescription beanDesc, BeanProperty beanProperty )
    {
        Class<?> raw = type.getRawClass();

        /* Note: PersistentCollection does not implement Collection, so we
         * may get some types here...
         */
        if (PersistentCollection.class.isAssignableFrom(raw)) {
            // TODO: handle iterator types?
        }

        if (HibernateProxy.class.isAssignableFrom(raw)) {
            return new HibernateProxySerializer(beanProperty, isEnabled(Hibernate4Module.Feature.FORCE_LAZY_LOADING));
        }
        return null;
    }

    @Override
    public JsonSerializer<?> findCollectionSerializer(SerializationConfig config,
                                                      CollectionType type, BeanDescription beanDesc, BeanProperty property,
                                                      TypeSerializer elementTypeSerializer, JsonSerializer<Object> elementValueSerializer)
    {
        Class<?> raw = type.getRawClass();
        if (PersistentCollection.class.isAssignableFrom(raw)) {
            return new PersistentCollectionSerializer(property, type, isEnabled(Hibernate4Module.Feature.FORCE_LAZY_LOADING));
        }
        return null;
    }

    @Override
    public JsonSerializer<?> findMapSerializer(SerializationConfig config,
                                               MapType type, BeanDescription beanDesc, BeanProperty property,
                                               JsonSerializer<Object> keySerializer,
                                               TypeSerializer elementTypeSerializer, JsonSerializer<Object> elementValueSerializer)
    {
        Class<?> raw = type.getRawClass();
        if (PersistentMap.class.isAssignableFrom(raw)) {
            return new PersistentCollectionSerializer(property, type, isEnabled(Hibernate4Module.Feature.FORCE_LAZY_LOADING));
        }
        return null;
    }

    public final boolean isEnabled(Hibernate4Module.Feature f) {
        return (_moduleFeatures & f.getMask()) != 0;
    }
}