package com.dr.iris.event;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rayer on 2015/6/4.
 */
public class EventSelfParseProxy implements EventProxy {
    @Override
    public void handleEvent(EventProxy sender, EventInstance eventInstance) {
        MethodInfo info = handlingMap.get(eventInstance.getPrototype());
        if(info == null) {
            handleExtraEvent(sender, eventInstance);
            return;
        }


    }

    public void handleExtraEvent(EventProxy sender, EventInstance eventInstance) {

    }

    Object host;

    Map<EventPrototype, MethodInfo> handlingMap = new HashMap<>();

    class ParameterInfo {
        String relatedToEventParam;
        Class<?> clazz;
    }

    class MethodInfo {
        Method method;
        ParameterInfo[] parameterInfo;
    }

    public void EventSelfParseProxy(Object self) {
        host = self;

        Method[] methods = self.getClass().getDeclaredMethods();
        for(Method m : methods) {
            Annotation eventAnnotation = m.getAnnotation(EventHandler.class);
            if(eventAnnotation == null) continue;

            MethodInfo methodInfo = new MethodInfo();
            methodInfo.method = m;
            Class<?> prototype = ((EventHandler)eventAnnotation).value();
            //EventPrototype prototypeInst = EventPrototypes.getPrototypeInstanceByClass(prototype);

            Annotation[][] parameterAnnotations = m.getParameterAnnotations();
            for(int index = 0; index < parameterAnnotations.length; ++index) {
                Annotation[] annotations = parameterAnnotations[index];
                for(Annotation anno : annotations) {
                    if(anno.annotationType() != EventParameter.class) continue;

                }
            }
        }
    }
}
