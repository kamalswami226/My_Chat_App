package com.icmi.mychat.view.common.view;

public interface ViewObservable<LISTENER_TYPE> extends ViewMVC{

    void registerListener(LISTENER_TYPE listener);
    void removeListener(LISTENER_TYPE listener);

}
