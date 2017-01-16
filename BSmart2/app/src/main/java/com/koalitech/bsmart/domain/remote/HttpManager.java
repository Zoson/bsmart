package com.koalitech.bsmart.domain.remote;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.koalitech.utils.ThreadPool;
import com.koalitech.service.http.HttpRequest;
import com.koalitech.service.http.IHttpRequest;
import com.koalitech.service.http.Request;
import com.koalitech.service.http.Response;

import org.json.JSONException;

/**
 * Created by Zoson on 2016/10/5.
 */
public class HttpManager implements IHttpRequest {
    private HttpRequest httpRequest;
    public static final int SUCC = HttpRequest.SUCC;
    public static final int FAIL = HttpRequest.FAIL;
    public static final int ERROR = HttpRequest.ERROR;

    private HttpHandler httpHandler;
    private static HttpManager instance;

    public static HttpManager getDefault() {
        if (instance == null){
            synchronized (HttpManager.class){
                if(instance == null){
                    instance = new HttpManager();
                }
            }
        }
        return instance;
    }

    private HttpManager(){
        httpRequest = new HttpRequest();
        httpHandler = new HttpHandler();
    }

    @Override
    public void sendPost(final Request request) {
        ThreadPool.start(new Runnable() {
            @Override
            public void run() {
                httpRequest.sendPost(request);
                handleHttpResponse(request);
            }
        });
    }

    @Override
    public void sendGet(final Request request) {
        ThreadPool.start(new Runnable() {
            @Override
            public void run() {
                httpRequest.sendGet(request);
                handleHttpResponse(request);
            }
        });
    }

    private void handleHttpResponse(Request request){
        Response response = request.getResponse();
        HttpResponse result = new HttpResponse();
        Msg msg = new Msg();
        if (response==null){
            result.status = ERROR;
        }else{
            try {
                byte[] content = response.getContent();
                if(content==null){
                    result.status = ERROR;
                }else{
                    result.initByJson(new String(response.getContent()));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                result.status = ERROR;
            }
        }
        msg.response = result;
        Object ob = request.getObj();
        if(ob!=null&&ob instanceof HttpListener){
            msg.httpListener = (HttpListener)ob;
        }

        Message message = Message.obtain();
        switch (result.status)
        {
            case SUCC:
                message.what = SUCC;break;
            case 1:
            case 2:
            case 401:
            case 404:
                message.what = FAIL;break;
            case -1:
                message.what = ERROR;break;
        }
        message.obj = msg;
        httpHandler.sendMessage(message);
    }

    class Msg{
        HttpResponse response;
        HttpListener httpListener;
    }

    class HttpHandler extends Handler{
        public final String TAG = HttpHandler.class.getSimpleName();
        @Override
        public void handleMessage(Message msg) {
            Log.i(TAG,"handleMessage");
            Msg message = (Msg)msg.obj;
            HttpListener listener = message.httpListener;
            HttpResponse httpResponse = message.response;
            switch (msg.what){
                case SUCC:
                    if(listener!=null)listener.succToRequire(httpResponse.message,httpResponse.data);
                    break;
                case FAIL:
                    if(listener!=null)listener.failToRequire(httpResponse.message, httpResponse.data);
                    break;
                case ERROR:
                    if(listener!=null)listener.netWorkError(httpResponse.message, httpResponse.data);
                    break;
            }
        }
    }
}
