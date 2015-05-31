package com.dr.iris.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Rayer on 5/31/15.
 */
public class EventNexus {

    private static EventNexus defInst;
    Map<EventPrototype, List<EventProxy>> in_eventMap = new HashMap<>();
    private EventNexus() {}

    public static EventNexus getInst() {
        if(defInst == null) defInst = new EventNexus();
        return defInst;
    }

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
        for (List<EventProxy> list : in_eventMap.values()) {
            list.remove(proxy);
        }
    }

    public void deregisterEvent(EventProxy proxy, EventPrototype... eventTypeList) {
        for (EventPrototype ep : eventTypeList) {
            if (in_eventMap.containsKey(ep) == false) continue;
            in_eventMap.get(ep).remove(proxy);
        }
    }

    public int sendEvent(EventProxy sender, EventInstance eventInstance, int Flag) {
        int sent = 0;
        for (EventProxy ev : in_eventMap.get(eventInstance.getPrototype())) {
            //On-something
            ev.handleEvent(sender, eventInstance);
            sent++;
        }
        return sent;
    }

    public int sendEvent(EventProxy sender, EventInstance eventInstance) {
        return sendEvent(sender, eventInstance, 0);
    }

    public boolean sendEvent(EventProxy sender, EventProxy receiver, EventInstance eventInstance) {

        if (in_eventMap.get(eventInstance.getPrototype()).contains(receiver)) {
            receiver.handleEvent(sender, eventInstance);
            return true;
        }

        return false;

    }

}
