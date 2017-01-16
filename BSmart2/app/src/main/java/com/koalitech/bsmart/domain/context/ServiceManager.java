package com.koalitech.bsmart.domain.context;

import android.app.Activity;
import android.content.Context;

import com.koalitech.bsmart.domain.remote.HttpManager;
import com.koalitech.bsmart.domain.remote.RemoteServer;
import com.koalitech.service.Database.DatabaseService;
import com.koalitech.service.Database.SharedPreference;
import com.koalitech.service.gps.GpsService;
import com.koalitech.service.im.IMService;
import com.koalitech.service.netstate.NetWorkState;
import com.koalitech.service.pay.alipay.AliPayService;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Zoson on 15/11/22.
 */
public class ServiceManager {
    private Map<Integer,Object> container;
    private Context mContext = null;

    public ServiceManager(Context context){
        this.mContext = context;
        container = new HashMap<Integer, Object>();
    }

    public Object getService(int service,Context context){
        Object object = null;
        object = container.get(service);
        if(object!=null)return object;
        switch (service){
            case ALIPAY:
                object = new AliPayService((Activity)context);
                break;
            case WXPAY:
                ///object = new WxpayService(mContext);
                break;
            case DATABASE:
                object = new DatabaseService(mContext);
                break;
            case SHAREPREFERENCE:
                object = new SharedPreference(mContext);
                break;
            case GPS:
                object = new GpsService(mContext);
                break;
            case HTTP:
                object = HttpManager.getDefault();
                break;
            case IM:
                object = new IMService();
                break;
            case NETSTATE:
                object = new NetWorkState(context);
            case REMOTESERVER:
                object = new RemoteServer();
            default:
        }
        container.put(service,object);
        return object;
    }

    public final static int ALIPAY = 0x1;
    public final static int WXPAY = 0x2;
    public final static int DATABASE = 0x3;
    public final static int SHAREPREFERENCE = 0x4;
    public final static int GPS = 0x5;
    public final static int HTTP = 0x6;
    public final static int IM = 0x7;
    public final static int NETSTATE = 0x8;
    public final static int REMOTESERVER = 0x9;

}
