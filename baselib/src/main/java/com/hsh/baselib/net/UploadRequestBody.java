package com.hsh.baselib.net;

import android.util.Log;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * Created by guoli on 2016/7/4.
 */
public class UploadRequestBody extends RequestBody {

    private static final String MEDIATYPE_PNG="image/png";
    private static final String MEDIATYPE_JPG="image/jpeg";
    private static final String MEDIATYPE_GIF="image/gif";
    private static final String MEDIATYPE_MP4="video/mp4";
    private static final String MEDIATYPE_3GP="video/3gpp";
    private static final String MEDIATYPE_AVI="video/x-msvideo";
    private static final String MEDIATYPE_MP3="audio/x-mpeg";
    private static final String MEDIATYPE_WAV="audio/x-wav";

    private RequestBody mRequestBody;
    private OnProgressListener mProgressListener;

    private BufferedSink bufferedSink;


    public UploadRequestBody(File file , OnProgressListener progressListener) {
        MediaType contentType=createContentType(file.getPath());
        Log.i("上传的 MediaType name is",contentType.toString());
        this.mRequestBody = RequestBody.create(contentType, file);
        this.mProgressListener = progressListener ;
    }


    public UploadRequestBody(RequestBody requestBody, OnProgressListener progressListener) {
        this.mRequestBody = requestBody;
        this.mProgressListener = progressListener;
    }

    private MediaType createContentType(String url){
        if (url.endsWith(".png")){
            return MediaType.parse(MEDIATYPE_PNG);
        }else if (url.endsWith(".jpg")){
            return MediaType.parse(MEDIATYPE_JPG);
        }else if (url.endsWith(".gif")){
            return MediaType.parse(MEDIATYPE_GIF);
        }else if (url.endsWith(".mp4")){
            return MediaType.parse(MEDIATYPE_MP4);
        }else if (url.endsWith(".3gp")){
            return MediaType.parse(MEDIATYPE_3GP);
        }else if (url.endsWith(".avi")){
            return MediaType.parse(MEDIATYPE_AVI);
        }else if (url.endsWith(".mp3")){
            return MediaType.parse(MEDIATYPE_MP3);
        }else if (url.endsWith(".wav")){
            return MediaType.parse(MEDIATYPE_WAV);
        }
        return MediaType.parse("multipart/form-data");
    }

    @Override
    public MediaType contentType() {
        return mRequestBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        return mRequestBody.contentLength();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        if (bufferedSink == null) {
            //包装
            bufferedSink = Okio.buffer(sink(sink));
        }
        //写入
        mRequestBody.writeTo(bufferedSink);
        //必须调用flush，否则最后一部分数据可能不会被写入
        bufferedSink.flush();
    }

    private Sink sink(Sink sink) {
        return new ForwardingSink(sink) {
            //当前写入字节数
            long bytesWritten = 0L;
            //总字节长度，避免多次调用contentLength()方法
            long contentLength = 0L;

            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
                if (contentLength == 0) {
                    //获得contentLength的值，后续不再调用
                    contentLength = contentLength();
                }
                //增加当前写入的字节数
                bytesWritten += byteCount;
                int newProgress=(int)(bytesWritten*100/contentLength);

                //回调
                mProgressListener.onProgress(newProgress);
            }
        };
    }
}
