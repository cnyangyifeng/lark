package cn.me.xdf.common.json.hibernate4;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.*;
import org.codehaus.jackson.type.JavaType;
import org.hibernate.collection.spi.PersistentCollection;

import javax.persistence.*;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-10-29
 * Time: 上午10:50
 * To change this template use File | Settings | File Templates.
 */
public class PersistentCollectionSerializer
        extends JsonSerializer<PersistentCollection>
        implements ContextualSerializer<PersistentCollection>,
        ResolvableSerializer
{

    protected final BeanProperty _property;

    protected final boolean _forceLazyLoading;


    protected final JavaType _serializationType;


    protected JsonSerializer<Object> _serializer;



    public PersistentCollectionSerializer(BeanProperty property, JavaType type,
                                          boolean forceLazyLoading)
    {
        _property = property;
        _serializationType = type;
        _forceLazyLoading = forceLazyLoading;
    }


    public JsonSerializer<PersistentCollection> createContextual(SerializationConfig config,
                                                                 BeanProperty property)
            throws JsonMappingException
    {
        if (property != null) {
            return new PersistentCollectionSerializer(property, property.getType(),
                    _forceLazyLoading);
        }
        return this;
    }

    public void resolve(SerializerProvider provider) throws JsonMappingException
    {
        _serializer = provider.findValueSerializer(_serializationType, _property);
    }

    /*
    /**********************************************************************
    /* JsonSerializer impl
    /**********************************************************************
     */

    @Override
    public void serialize(PersistentCollection coll, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException
    {
        // If lazy-loaded, not yet loaded, may serialize as null?
        if (!_forceLazyLoading && !coll.wasInitialized()) {
            jgen.writeNull();
            return;
        }
        Object value = coll.getValue();
        if (value == null) {
            provider.defaultSerializeNull(jgen);
        } else {
            if (_serializer == null) { // sanity check...
                throw new JsonMappingException("PersitentCollection does not have serializer set");
            }
            _serializer.serialize(value, jgen, provider);
        }
    }

    public void serializeWithType(PersistentCollection coll, JsonGenerator jgen, SerializerProvider provider,
                                  TypeSerializer typeSer)
            throws IOException, JsonProcessingException
    {
        if (!_forceLazyLoading && !coll.wasInitialized()) {
            jgen.writeNull();
            return;
        }
        Object value = coll.getValue();
        if (value == null) {
            provider.defaultSerializeNull(jgen);
        } else {
            if (_serializer == null) { // sanity check...
                throw new JsonMappingException("PersitentCollection does not have serializer set");
            }
            _serializer.serializeWithType(value, jgen, provider, typeSer);
        }
    }
}
