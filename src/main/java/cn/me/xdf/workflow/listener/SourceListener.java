package cn.me.xdf.workflow.listener;

import cn.me.xdf.model.base.BamProcess;
import cn.me.xdf.workflow.event.SourceEvent;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-10-25
 * Time: 上午11:51
 * To change this template use File | Settings | File Templates.
 * 资源监听事件
 * 1、当资源内容发生改变时，触发此监听
 * 2、资源监听接收到通过数据，通知上层的节，
 * 3、节接受到数据后，校验自身数据通知章，
 * 4、章接收到通知课程。
 * 5、课程再到系列。
 * <p/>
 * 资源--->节--->章---->课程---->系列
 * 资源进度由前端事件触发，触发后上传至节，节自动上传到章、课程、系列。
 */
@Component
public class SourceListener implements SmartApplicationListener {

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return eventType == SourceEvent.class;
    }

    @Override
    public boolean supportsSourceType(Class<?> sourceType) {
        return (BeanUtils.instantiateClass(sourceType) instanceof BamProcess);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("资源发生了改变---------------" + event.getSource());
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
