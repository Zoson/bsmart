package com.koalitech.service.netstate;

import android.content.Context;
import android.net.ConnectivityManager;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Zoson on 15/8/29.
 */
public class NetWorkState implements ConnectivityManager.OnNetworkActiveListener{

    public static final int ON = 0;
    public static final int OFF = 1;
    public static final int WIFI = 2;
    public static final int MOBILE = 3;

    private List<NetStateListener> listeners;
    private Context context;
    public NetWorkState(Context context){
        this.context = context;
        listeners = new LinkedList<>();
    }

    public void addListener(NetStateListener listener){
        listeners.add(listener);
    }

    public void rmListener(){
        listeners.remove(listeners);
    }

    private void notifyAllListeners(int state,int type){
        synchronized (this){
            for(NetStateListener listener:listeners){
                listener.stateChanage(state,type);
            }
        }
    }

    public  boolean isConnecting(){
        ConnectivityManager con=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean wifi=con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        boolean internet=con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        if(wifi|internet){
            return true;
        }else{
            return false;
        }
    }
    public boolean isWifiOn(){
        ConnectivityManager con=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean wifi=con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        return wifi;
    }

    public boolean isMobileNetworkOn(){
        ConnectivityManager con=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean internet=con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        return internet;
    }

    @Override
    public void onNetworkActive() {
        boolean isWifi = isWifiOn();
        boolean isMobile = isMobileNetworkOn();
        int state = OFF;
        int type = WIFI;
        if(isWifi||isMobile)state = ON;
        if(isMobile)state = type = MOBILE;
    }
}
