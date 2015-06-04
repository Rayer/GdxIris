package com.dr.iris.event;

import com.dr.iris.log.Log;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rayer on 2015/6/4.
 */
public class EventSelfParseProxy implements EventProxy {

    Log log = Log.getLogger(this.getClass());
    Object host;
    Map<EventPrototype, MethodInfo> handlingMap = new HashMap<>();

    public EventSelfParseProxy(Object self) {
        host = self;

        Method[] methods = self.getClass().getDeclaredMethods();
        for (Method m : methods) {
            m.setAccessible(true);
            Annotation eventAnnotation = m.getDeclaredAnnotation(EventHandler.class);
            if (eventAnnotation == null) continue;

            MethodInfo methodInfo = new MethodInfo();
            methodInfo.method = m;
            String prototype = ((EventHandler) eventAnnotation).value();
            EventPrototype prototypeInst = EventPrototypes.getPrototypeByName(prototype);
            EventNexus.getInst().registerEvent(this, prototypeInst);

            Annotation[][] parameterAnnotations = m.getParameterAnnotations();
            methodInfo.parameterInfo = new ParameterInfo[parameterAnnotations.length];
            for (int index = 0; index < parameterAnnotations.length; ++index) {
                Annotation[] annotations = parameterAnnotations[index];
                for (Annotation anno : annotations) {
                    if (anno.annotationType() != EventParameter.class) continue;
                    String eventAttrName = ((EventParameter) anno).value();
                    methodInfo.parameterInfo[index] = new ParameterInfo();
                    methodInfo.parameterInfo[index].relatedToEventParam = eventAttrName;
                    break;
                }
            }

            Class<?>[] type = m.getParameterTypes();
            for (int index = 0; index < type.length; ++index) {
                methodInfo.parameterInfo[index].clazz = type[index];
            }

            handlingMap.put(prototypeInst, methodInfo);
        }
    }

    @Override
    public void handleEvent(EventProxy sender, EventInstance eventInstance) {
        if (sender == this) return;

        MethodInfo info = handlingMap.get(eventInstance.getPrototype());
        if(info == null) {
            handleExtraEvent(sender, eventInstance);
            return;
        }

        Object[] parameters = null;
        if (info.parameterInfo != null && info.parameterInfo.length > 0) {
            parameters = new Object[info.parameterInfo.length];
            for (int index = 0; index < info.parameterInfo.length; ++index) {
                ParameterInfo pinfo = info.parameterInfo[index];
                parameters[index] = (pinfo.clazz).cast(eventInstance.getValue(pinfo.relatedToEventParam));
            }
        }


        try {
            if (parameters == null) info.method.invoke(host);
            else info.method.invoke(host, parameters);
            log.debug("handling " + info.method.getName() + "with param : " + parameters);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    public void handleExtraEvent(EventProxy sender, EventInstance eventInstance) {
        log.error("Enter handleExtraEvent");
    }

    class ParameterInfo {
        String relatedToEventParam;
        Class<?> clazz;
    }

    class MethodInfo {
        Method method;
        ParameterInfo[] parameterInfo;
    }
}
