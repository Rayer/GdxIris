package com.dr.iris.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Rayer on 5/31/15.
 */
public class EventNexus {

    private EventNexus() {}
    private static EventNexus defInst;
    public static EventNexus getInst() {
        if(defInst == null) defInst = new EventNexus();
        return defInst;
    }

    Map<EventPrototype, List<EventProxy>> in_eventMap = new HashMap<>();

    public void registerEvent(EventProxy proxy, EventPrototype... eventTypeList) {
        if(proxy == null) throw new RuntimeException("Sender null is not allowed");

        for(EventPrototype ep : eventTypeList) {
            List<EventProxy> receiverList;

            if(in_eventMap.containsKey(ep) == false) {
                receiverList = new ArrayList<>();
                receiverList.add(proxy);
                in_eventMap.put(ep, receiverList);
            } else {
                receiverList = in_eventMap.get(ep);
                if(receiverList.contains(proxy) == false) receiverList.add(proxy);
            }
        }
    }

    public void deregisterEvent(EventProxy proxy) {

    }

    void deregisterEvent(EventProxy proxy, EventPrototype... eventTypeList) {

    }

    void sendEvent(EventProxy sender, Event event) {

    }

}
