package com.koalitech.service.http;

import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by Zoson on 16/10/4.
 */
public class HttpRequest implements  IHttpRequest,IFileTransport {

    public static final String TAG = HttpRequest.class.getSimpleName();
    //state
    public static final int SUCC = 0;
    public static final int FAIL = 1;
    public static final int ERROR = -1;


    public void downloadFile(Request request)
    {
        downloadFile(request,null);
    }

    @Override
    public void downloadFile(Request request,IFtListener ftListener) {
        String url = request.url;
        try {
            URL imgUrl = new URL(url);
            // 使用HttpURLConnection打开连接
            HttpURLConnection connection = (HttpURLConnection) imgUrl
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            int total_size = connection.getContentLength();
            // 将得到的数据转化成InputStream
            InputStream is = connection.getInputStream();
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            int bn = 0;
            byte[] bs = new byte[1024];
            int curr_size = 0;
            if(ftListener!=null)ftListener.process(curr_size);
            while((bn=is.read(bs))!=-1)
            {
                bout.write(bs,0,bn);
                curr_size = curr_size+bn;
                if(ftListener!=null)ftListener.process((curr_size)*100/total_size);
            }
            Response response = new Response();
            response.content = bout.toByteArray();
            response.state = SUCC;
            request.response = response;
            bout.close();
            is.close();
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void uploadFile(Request request,IFtListener listener) {
        sendPost(request,listener);
    }


    @Override
    public void sendGet(Request request) {
        Log.i(TAG, "sendGet api " + request.getApi() + " params " + request.getStringParams());
        Response response = new Response();
        BufferedReader in = null;
        try {
            String urlNameString = getUrl(request.getUrl(),request.getApi(), request.getStringParams());
            Log.i(TAG,urlNameString);
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("contentType", "utf-8");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            //Map<String, List<String>> map = connection.getHeaderFields();
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuilder res = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null) {
                res.append(line);
            }
            Log.i(TAG,res.toString());
            response.state = SUCC;
            response.content = res.toString().getBytes();
            request.response = response;
        }  catch (IOException e) {
            e.printStackTrace();
            response.state = ERROR;
        }
        // 使用finally块来关闭输入流
        finally {

            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    protected String getUrl(String ip,String api,String param){
        StringBuilder url = new StringBuilder();
        if(api.equals("")){
            return ip;
        }else {
            if (param.equals("")) {
                url.append(ip).append("/").append(api).append("/");
            } else {
                url.append(ip).append("/").append(api).append("/?").append(param);
            }
            return url.toString();
        }
    }

    public void sendPost(Request request){
        sendPost(request,null);
    }

    public void sendPost(Request request,IFtListener listener){
        try {
            /**
             * 第一部分
             */
            URL urlObj = new URL(getUrl(request.url,request.api, ""));
            HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
            Log.i(TAG,"sendPost "+urlObj.toString());
            /**
             * 设置关键值
             */
            connection.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false); // post方式不能使用缓存
            // 设置请求头信息
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Charset", "UTF-8");

            // 设置边界
            String BOUNDARY = "----------" + System.currentTimeMillis();
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary="
                    + BOUNDARY);
            // 请求正文信息

            OutputStream out = new DataOutputStream(connection.getOutputStream());
            // 第一部分：
            for (String key:request.params.keySet()) {
                StringBuilder sb_param = new StringBuilder();
                sb_param.append("--").append(BOUNDARY).append("\r\n");
                sb_param.append("Content-Disposition: form-data; name=\"" + key + "\"\r\n\r\n");
                sb_param.append(request.params.get(key));
                out.write(sb_param.toString().getBytes("utf-8"));
            }


            if(request.fileparams!=null)
            {
                Map<String,String> fileparams = request.fileparams;
                int num= fileparams.size();
                File[] files = new File[num];
                String[] keys = new String[num];
                int i=0;
                int total_size = 0;
                int curr_size = 0;
                for(String key:fileparams.keySet())
                {
                    File file = new File(fileparams.get(key));
                    if(!file.exists()||!file.isDirectory())
                    {
                        continue;
                    }
                    files[i] = file;
                    total_size += file.length();
                    keys[i] = key;
                    i++;
                }
                for (i=0;i<num;++i) {
                    if(files[i]==null)continue;
                    StringBuilder sb_file = new StringBuilder();
                    sb_file.append("\r\n");
                    sb_file.append("--"); // ////////必须多两道线
                    sb_file.append(BOUNDARY);
                    sb_file.append("\r\n");
                    sb_file.append("Content-Disposition: form-data;name=\"" + keys[i] + "\";filename=\""
                            + files[i].getName() + "\"\r\n");
                    sb_file.append("Content-Type:application/octet-stream\r\n\r\n");

                    byte[] head = sb_file.toString().getBytes("utf-8");

                    out.write(head);
                    //out.write(param.getBytes());
                    // 文件正文部分
                    DataInputStream in = new DataInputStream(new FileInputStream(files[i]));
                    int bytes = 0;
                    byte[] bufferOut = new byte[1024];
                    while ((bytes = in.read(bufferOut)) != -1) {
                        out.write(bufferOut, 0, bytes);
                        if(listener!=null)
                        {
                            curr_size+=bytes;
                            listener.process(curr_size*100/total_size);
                        }
                    }
                    in.close();
                    // 结尾部分
                    byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
                    out.write(foot);
                }
            }

            out.flush();
            out.close();
            // 定义BufferedReader输入流来读取URL的响应
            BufferedReader in_buff = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder res = new StringBuilder();
            while ((line = in_buff.readLine()) != null) {
                res.append(line);
            }
            Log.i(TAG,"sendPost "+res.toString());
            Response response = new Response();
            response.content = res.toString().getBytes("utf-8");
            response.state = SUCC;
            request.response = response;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

