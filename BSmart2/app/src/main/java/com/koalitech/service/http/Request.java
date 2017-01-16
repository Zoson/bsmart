package com.koalitech.service.http;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Zoson on 16/5/7.
 */
public class Request {

    String url;
    String api;
    Map<String,String> params;
    Map<String,String> fileparams;
    int reqType;
    Response response;
    Object obj;//listener or other
    public Request(){

    }

    public String getUrl() {
        return url;
    }

    public int getReqType() {
        return reqType;
    }

    public Response getResponse() {
        return response;
    }

    public String getApi() {
        return api;
    }

    public Object getObj()
    {
        return obj;
    }

    public boolean isTranferFile(){
        if (fileparams != null){
            return true;
        }else{
            return false;
        }
    }

    public String getStringParams(){
        StringBuilder sb = new StringBuilder();
        for (String key:params.keySet()){
            sb.append(key);
            sb.append("=");
            sb.append(params.get(key));
            sb.append("&");
        }
        sb.delete(sb.length()-1,sb.length());
        return sb.toString();
    }

    public static class Builder{
        Request request;
        public Builder(){
            request = new Request();
        }

        public Request build()
        {
            return request;
        }

        public Builder setApi(String api)
        {
            request.api = api;
            return this;
        }

        public Builder setUrl(String url){
            request.url = url;
            return this;
        }
        public Builder setParams(Map params){
            request.params = params;
            return this;
        }

        public Builder addParams(String key,String val)
        {
            if(request.params==null)
            {
                request.params = new HashMap<String,String>();
            }
            request.params.put(key,val);
            return this;
        }

        public Builder setFileParams(Map fileParams){
            request.fileparams = fileParams;
            return this;
        }
        
        public Builder setObject(Object ob)
        {
            request.obj = ob;
            return this;
        }

        public Builder addFileParams(String key,String val){
            if(request.fileparams == null)
            {
                request.fileparams = new HashMap<>();
            }
            request.fileparams.put(key,val);
            return this;
        }
    }

}
