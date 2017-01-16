package com.koalitech.bsmart;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.koalitech.bsmart.domain.context.ServiceManager;
import com.koalitech.bsmart.domain.remote.HttpListener;
import com.koalitech.bsmart.domain.remote.HttpManager;
import com.koalitech.bsmart.domain.remote.RemoteServer;
import com.koalitech.service.http.HttpRequest;
import com.koalitech.service.http.Request;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void test_Http_SendGet()
    {
        HttpRequest httpRequest = new HttpRequest();
        Request request = new Request.Builder().setUrl("http://120.25.12.205:8086").setApi("get_InIndustry").addParams("rid","1").build();
        httpRequest.sendGet(request);
        if (request.getResponse().getState()!=HttpRequest.SUCC){
            Log.e(HttpRequest.TAG,"HttpGet fail");
        }
    }

    public void test_Http_SendPost()
    {
        HttpRequest httpRequest = new HttpRequest();
        Request request = new Request.Builder().setUrl("http://120.25.12.205:8086").setApi("login").addParams("phone", "18814111116").addParams("password","zzx").build();
        httpRequest.sendPost(request);
        if (request.getResponse().getState()!=HttpRequest.SUCC){
            Log.e(HttpRequest.TAG,"HttpPost fail");
        }
    }

    public void test_Http_SendPostByFile()
    {
        HttpRequest httpRequest = new HttpRequest();
        Request request = new Request.Builder().setUrl("http://120.25.12.205:8086").setApi("add_Dynamic").addParams("rid","96").addParams("content","hahah").build();
        httpRequest.sendPost(request);
        if (request.getResponse().getState()!=HttpRequest.SUCC){
            Log.e(HttpRequest.TAG,"HttpPost fail");
        }
    }

    public void test_Http_download()
    {
        HttpRequest httpRequest = new HttpRequest();
        Request request = new Request.Builder().setUrl("http://120.25.12.205:8086/site_media/upload/avatar_rknlQnL.jpg").build();
        httpRequest.downloadFile(request);
    }

    public void test_HttpManager_sendPost(){
        HttpManager httpManager = HttpManager.getDefault();
        Request request = new Request.Builder().setUrl("http://120.25.12.205:8086").setApi("login").addParams("phone", "18814111116").addParams("password","zzx").setObject(new HListener()).build();
        httpManager.sendPost(request);
    }

    public void test_RemoteServer_login() {
        RemoteServer remoteServer = new RemoteServer();
        remoteServer.login("18814111116","zzx",true,new HListener());
        Log.i("TestRemoteServer","test_RemoteServer_login:::");
    }

    class HListener implements HttpListener{

        @Override
        public void succToRequire(String msg, String data) {
            for (int i=0;i<10;++i){
                System.out.println(msg+data);
            }
            Log.i("TestHListener","SUCC msg::"+msg+" data::"+data);
        }

        @Override
        public void failToRequire(String msg, String data) {
            Log.i("TestHListener","FAIL msg::"+msg+" data::"+data);
        }

        @Override
        public void netWorkError(String msg, String data) {
            Log.i("TestHListener","ERROR msg::"+msg+" data::"+data);
        }
    }
}