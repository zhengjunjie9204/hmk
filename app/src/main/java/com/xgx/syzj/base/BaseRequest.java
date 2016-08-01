package com.xgx.syzj.base;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.cache.DiskLruBasedCache;
import com.android.volley.cache.plus.ImageLoader;
import com.android.volley.cache.plus.SimpleImageLoader;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.orhanobut.logger.Logger;
import com.xgx.syzj.R;
import com.xgx.syzj.app.AppConfig;
import com.xgx.syzj.app.Constants;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.secret.Base64Util;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @author zajo
 * @created 2015年08月06日 15:09
 */
public class BaseRequest {

    private static RequestQueue mRequestQueue;
    private static SimpleImageLoader mImageLoader;
    public static Context mContext;
    private static final int TIME_OUT = 5 * 1000;//超时时间

    /**
     * 请求各个状态的回调接口
     */
    public interface OnRequestListener {
        void onSuccess(Result result);

        void onError(String errorCode, String message);
    }

    public static void init(Context context) {
        mContext = context;
        mRequestQueue = Volley.newRequestQueue(mContext);
        DiskLruBasedCache.ImageCacheParams cacheParams = new DiskLruBasedCache.ImageCacheParams(mContext, AppConfig.IMAGE_DIR_NAME);
        cacheParams.setMemCacheSizePercent(0.25f);
        mImageLoader = new SimpleImageLoader(mContext, cacheParams);
    }

    /**
     * 获取请求队列
     *
     * @return
     */
    public static RequestQueue getRequestQueue() {
        if (mRequestQueue != null) {
            return mRequestQueue;
        } else {
            throw new IllegalStateException("RequestQueue not initialized");
        }
    }

    public static void loadBitmap(final ImageView iv, String url, final int postion) {
        SimpleImageLoader.ImageListener listener = new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                int defaultImageResId = R.mipmap.xiangjji;
                if (postion == (Integer)iv.getTag()) {
                    if (response.getBitmap() != null) {
                        iv.setImageDrawable(response.getBitmap());
                    } else {
                        iv.setImageResource(defaultImageResId);
                    }
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                int errorImageResId = R.mipmap.xiangjji;
                if (postion == (Integer)iv.getTag()) {
                    iv.setImageResource(errorImageResId);
                }
            }
        };
        getImageLoader().get(url, listener);
    }

    /**
     * 获取ImageLoader实例
     *
     * @return
     */
    public static SimpleImageLoader getImageLoader() {
        if (mImageLoader != null) {
            return mImageLoader;
        } else {
            throw new IllegalStateException("ImageLoader not initialized");
        }
    }

    /**
     * 发送请求并且返回请求实例
     *
     * @param requestUrl
     * @param map
     * @param listener
     * @return
     */
    public static StringRequest getRequest(String requestUrl, Map<String, String> map, OnRequestListener listener) {
        StringRequest stringRequest = generateRequest(requestUrl, map, null, listener);
        showReuqestDetail(requestUrl, map, null);
        getRequestQueue().add(stringRequest);
        return stringRequest;
    }

    /**
     * 发送请求并且返回请求实例
     *
     * @param requestUrl
     * @param map
     * @param header     头部数据
     * @param listener
     * @return
     */
    public static StringRequest getRequest(String requestUrl, Map<String, String> map, Map<String, String> header, OnRequestListener listener) {
        StringRequest stringRequest = generateRequest(requestUrl, map, header, listener);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                TIME_OUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, //最大尝试次数
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        showReuqestDetail(requestUrl, map, header);
        getRequestQueue().add(stringRequest);
        return stringRequest;
    }

    private static StringRequest generateRequest(final String requestUrl, final Map<String, String> map, final Map<String, String> header, final OnRequestListener listener) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("==",response);
                            JSONObject object=JSON.parseObject(response);
                            Result result = JSON.parseObject(object.getString("result"), Result.class);
                            showResponseDetail(result);
                            if (listener != null) {
                                if (result.APISuccess()) {
                                    listener.onSuccess(result);
                                }else {
                                    listener.onError(result.getCode(), result.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            if (listener != null) {
                                listener.onError("-2", "数据解析异常...");
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        volleyError.printStackTrace();
                        if (listener != null) {
                            listener.onError("-1", "请求时发生异常...");
                        }
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (map != null) {
                    return map;
                }
                return super.getParams();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (header != null) {
                    return header;
                }
                return super.getHeaders();
            }
        };
        return stringRequest;
    }

    /**
     * 显示请求中提交的数据
     *
     * @param url
     * @param map
     */
    private static void showReuqestDetail(String url, Map<String, String> map, Map<String, String> header) {
        if (!Constants.DEBUG) return;
        String out = "------------ReuqestDetail begin-------------\n";
        out += "api address:" + url + "\n";

        if (header != null) {
            for (String key : header.keySet()) {
                out += key + ":" + header.get(key) + "\n";
            }
            out += "------------------\n";
        }
        if (map != null) {
            if (map.size() == 1 && map.containsKey("info")) {
                //解析base64编码
                String info = map.get("info");
                byte[] data = Base64Util.decode(info);
                String str = "";
                try {
                    str = new String(data, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                out += "params:" + str + "\n";
            } else {
                //正常请求
                for (String key : map.keySet()) {
                    out += key + ":" + map.get(key) + "\n";
                }
            }
        }
        out += "------------ReuqestDetail end-------------";

        System.out.println(out);
    }

    /**
     * 显示接口返回的数据描述
     *
     * @param result
     */
    private static void showResponseDetail(Result result) {
        if (!Constants.DEBUG) return;
        System.out.println("------------------ResponseDetail begin------------------- \n " +
                "code:" + result.getCode() + "\n" +
                "msg:" + result.getMessage() + "\n" +
                "sercet:" + result.getSecret() + "\n" +
                "------------------ResponseDetail end--------------------");
        Logger.json(result.getResult());
    }
}
