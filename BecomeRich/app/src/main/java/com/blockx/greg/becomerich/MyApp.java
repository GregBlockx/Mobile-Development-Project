package com.blockx.greg.becomerich;

import android.app.Application;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bryan on 17/10/2017.
 */

public class MyApp extends Application{

    private Map<String, Object> mData;

    @Override
    public void onCreate() {
        super.onCreate();
        mData = new HashMap<String, Object>();
    }

    public Object get(String key){
        return mData.get(key);
    }
    public void put(String key,Object value){
        mData.put(key, value);
    }
}