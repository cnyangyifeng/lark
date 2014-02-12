package cn.me.xdf.common.json.hibernate4;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.impl.Indenter;
import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.KeyDeserializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.introspect.*;
import org.codehaus.jackson.type.JavaType;

import javax.persistence.Transient;
import java.lang.annotation.Annotation;

/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-10-29
 * Time: 上午10:16
 * To change this template use File | Settings | File Templates.
 */
public class HibernateAnnotationIntrospector extends NopAnnotationIntrospector
{

    protected boolean _cfgCheckTransient;

    /*
    /**********************************************************************
    /* Construction, configuration
    /**********************************************************************
     */

    public HibernateAnnotationIntrospector() { }


    public HibernateAnnotationIntrospector setUseTransient(boolean state) {
        _cfgCheckTransient = state;
        return this;
    }


    @Override
    public boolean isHandled(Annotation a)
    {
        return (a.annotationType() == Transient.class);
    }

    public boolean isIgnorableConstructor(AnnotatedConstructor c)
    {
        return _cfgCheckTransient && c.hasAnnotation(Transient.class);
    }

    public boolean isIgnorableField(AnnotatedField f)
    {
        return _cfgCheckTransient && f.hasAnnotation(Transient.class);
    }

    public boolean isIgnorableMethod(AnnotatedMethod m)
    {
        return _cfgCheckTransient && m.hasAnnotation(Transient.class);
    }
}
