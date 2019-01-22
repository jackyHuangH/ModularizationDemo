package cn.jacky.bundle_main.wrapper.glide.glideprogress;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * @author:Hzj
 * @date :2018/3/21/021
 * desc  ：
 * record：
 */

public class ProgressResponseBody extends ResponseBody {


    private static final String TAG = "XGlide";

    private BufferedSource bufferedSource;

    private ResponseBody responseBody;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    private ProgressListener listener;

    public ProgressResponseBody(String url, ResponseBody responseBody) {
        this.responseBody = responseBody;
        listener = ProgressInterceptor.LISTENER_MAP.get(url);
    }


    @Nullable
    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(new ProgressSource(responseBody.source()));
        }
        return bufferedSource;
    }

    private class ProgressSource extends ForwardingSource {

        long totalBytesRead = 0;

        int currentProgress;

        ProgressSource(Source source) {
            super(source);
        }

        @Override
        public long read(Buffer sink, long byteCount) throws IOException {
            long bytesRead = super.read(sink, byteCount);
            final long fullLength = responseBody.contentLength();
            if (bytesRead == -1) {
                totalBytesRead = fullLength;
            } else {
                totalBytesRead += bytesRead;
            }
            final int progress = (int) (100f * totalBytesRead / fullLength);
            Log.d(TAG, "download progress is " + progress);
            Log.d(TAG, "currentProgress is " + currentProgress);

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (listener != null && currentProgress <= progress) {
                        //主线程回调
                        listener.onProgress(progress);
                    }
                    currentProgress = progress;
                    if (listener != null && currentProgress >= 100) {
                        listener = null;
                    }
                }
            });
            return bytesRead;
        }
    }
}
