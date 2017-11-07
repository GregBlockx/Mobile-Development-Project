package com.blockx.greg.becomerich.Util;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;

import java.util.HashMap;
import java.util.Map;

import static com.blockx.greg.becomerich.Fragments.MainFragment.AD_ID;

/**
 * Created by Bryan on 17/10/2017.
 */

public class MyApp extends Application{

    private Map<String, Object> mData;

    @Override
    public void onCreate() {
        super.onCreate();
        mData = new HashMap<String, Object>();
        MobileAds.initialize(getApplicationContext(), AD_ID);
    }

    public Object get(String key){
        return mData.get(key);
    }
    public void put(String key,Object value){
        mData.put(key, value);
    }
}