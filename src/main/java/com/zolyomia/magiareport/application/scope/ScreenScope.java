package com.zolyomia.magiareport.application.scope;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.zolyomia.magiareport.ui.controller.base.BaseScreenController;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

public class ScreenScope implements Scope {

    private static final Map<String, BaseScreenController> screens = Collections.synchronizedMap(new HashMap<>());

    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        if(!screens.containsKey(name)) {
            screens.put(name, (BaseScreenController) objectFactory.getObject());
        }
        return screens.get(name);
    }

    @Override
    public Object remove(String name) {
        return screens.remove(name);
    }

    @Override
    public void registerDestructionCallback(String s, Runnable runnable) {

    }

    @Override
    public Object resolveContextualObject(String s) {
        return null;
    }

    @Override
    public String getConversationId() {
        return null;
    }
}
