package com.koalitech.bsmart.domain.context;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.koalitech.bsmart.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

/**
 * Created by Zoson on 16/10/4.
 */
public class BSmartContext {
    public final String TAG = BSmartContext.class.getSimpleName();
    public final static int NET_ERROR = -1;
    public static Context context;
    public static BSmartContext baseContext;
    private ServiceManager mServiceManager;
    private HashMap<String,BSmartContext> contexts;
    private ImageLoader imageLoader;
    DisplayImageOptions options;

    public BSmartContext(){

    }

    public BSmartContext(Context context){
        super();
        BSmartContext.context = context;
        mServiceManager = new ServiceManager(context);

        imageLoader = ImageLoader.getInstance();
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(context);
        imageLoader.init(configuration);

        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.ico_app)
                .showImageForEmptyUri(R.drawable.ico_app)
                .showImageOnFail(R.drawable.ico_app)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .bitmapConfig(Bitmap.Config.RGB_565)     //设置图片的解码类型
                .build();
    }

    public static BSmartContext getBaseContext(){
        return baseContext;
    }

    public Object getAppService(int service){
        return  mServiceManager.getService(service,context);
    }

    public String getEnvironmentDir(){
        String state = Environment.getExternalStorageState();
        if(state.equals(Environment.MEDIA_MOUNTED)){
            return Environment.getDataDirectory().getAbsolutePath()+"/bsmart/";
        }else{
            return "/bsmart/";
        }
    }

    public String getDiskCacheDir() {
        String cachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))
        {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return cachePath;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    public void saveBitmapToCache(String url, Bitmap bitmap) {
        String cacheDirPath = getDiskCacheDir();
        File cacheDir = new File(cacheDirPath);
        if(!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        File bitmapCache = new File(cacheDir, String.valueOf(url.hashCode()));
        if(!bitmapCache.exists()) {
            try {
                OutputStream os = new FileOutputStream(bitmapCache);
                bitmap.compress(Bitmap.CompressFormat.PNG, 50, os);
                os.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public Context getContext(){
        return baseContext.context;
    }

    public Bitmap getBitmapFromCache(String url) {
        String cacheDirPath = getDiskCacheDir();
        File cacheDir = new File(cacheDirPath);
        if(!cacheDir.exists()) {
            return null;
        }
        return BitmapFactory.decodeFile(cacheDirPath + "/" + url.hashCode());
    }

}
