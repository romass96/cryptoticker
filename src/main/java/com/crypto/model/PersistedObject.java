package com.crypto.model;

import java.lang.reflect.Method;

public abstract class PersistedObject {
    public void merge(CryptoCurrency source){
        Method[] methods = this.getClass().getMethods();

        for(Method fromMethod: methods){
            if(fromMethod.getDeclaringClass().equals(this.getClass())
                    && fromMethod.getName().startsWith("get")){

                String fromName = fromMethod.getName();
                String toName = fromName.replace("get", "set");

                try {
                    Method toMetod = this.getClass().getMethod(toName, fromMethod.getReturnType());
                    Object value = fromMethod.invoke(source, (Object[])null);
                    if(value != null){
                        toMetod.invoke(this, value);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
