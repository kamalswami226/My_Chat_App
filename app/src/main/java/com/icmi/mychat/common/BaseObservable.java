package com.icmi.mychat.common;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class BaseObservable<LISTENER_TYPE> {

    private Set<LISTENER_TYPE> mListeners = Collections.newSetFromMap(
            new ConcurrentHashMap<LISTENER_TYPE, Boolean>(1));


    public final void registerListener(LISTENER_TYPE listener) {
        mListeners.add(listener);
    }

    public final void removeListener(LISTENER_TYPE listener) {
        mListeners.remove(listener);
    }

    public final Set<LISTENER_TYPE> getListeners() {
        return Collections.unmodifiableSet(mListeners);
    }

}
