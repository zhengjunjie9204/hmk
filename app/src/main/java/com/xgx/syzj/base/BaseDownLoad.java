package com.xgx.syzj.base;

import android.text.TextUtils;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class BaseDownLoad extends Request<String> implements Response.ProgressListener{

        private final Response.Listener<String> mListener;
        private final String mDownloadPath;
        private Response.ProgressListener mProgressListener;

        /**
         * Creates a new request with the given method.
         *
         * @param url URL to fetch the string at
         * @param download_path path to save the file to
         * @param listener Listener to receive the String response
         * @param errorListener Error listener, or null to ignore errors
         */
        public BaseDownLoad(int method,String url, String download_path, Response.Listener<String> listener,
                            Response.ErrorListener errorListener) {
            super(method, url, errorListener);
            mDownloadPath = download_path;
            mListener = listener;
        }

        /**
         * Set listener for tracking download progress
         *
         * @param listener
         */
        public void setOnProgressListener(Response.ProgressListener listener){
            mProgressListener = listener;
        }

        @Override
        protected void deliverResponse(String response) {
            if(null != mListener){
                mListener.onResponse(response);
            }
        }

        @Override
        protected Response<String> parseNetworkResponse(NetworkResponse response) {
            String parsed = null;
            try {
                byte[] data = response.data;
                //convert array of bytes into file
                FileOutputStream fileOuputStream = new FileOutputStream(mDownloadPath);
                fileOuputStream.write(data);
                fileOuputStream.close();
                parsed = mDownloadPath;
            } catch (UnsupportedEncodingException e) {
                parsed = new String(response.data);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }  catch (IOException e) {
                e.printStackTrace();
            } finally{
                if(TextUtils.isEmpty(parsed)){
                    parsed = "";
                }
            }

            return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
        }

        @Override
        public void onProgress(long transferredBytes, long totalSize) {
            if(null != mProgressListener){
                mProgressListener.onProgress(transferredBytes, totalSize);
            }
        }
}
