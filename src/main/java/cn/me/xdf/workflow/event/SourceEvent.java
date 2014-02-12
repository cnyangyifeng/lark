package cn.me.xdf.workflow.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-10-25
 * Time: 上午10:20
 * To change this template use File | Settings | File Templates.
 * 资源事件监听
 *
 * 1：素材
 *
 *  当前端用户出发素材
 *
 * 2：
 *
 */
public class SourceEvent extends ApplicationEvent {



    /**
     * Create a new ApplicationEvent.
     *
     * @param source the component that published the event (never <code>null</code>)
     */
    public SourceEvent(Object source) {
        super(source);
    }
}
