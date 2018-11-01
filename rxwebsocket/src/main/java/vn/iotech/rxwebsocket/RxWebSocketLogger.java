package vn.iotech.rxwebsocket;

import android.util.Log;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import vn.iotech.rxwebsocket.entities.SocketEvent;

public class RxWebSocketLogger implements Subscriber<SocketEvent> {

    private final String TAG;

    public RxWebSocketLogger(String tag){
        TAG = tag + ": ";
    }

    @Override
    public void onComplete() {
        //Log.d(TAG, "Complete");
    }

    @Override
    public void onError(Throwable t) {
//        Log.e(TAG, "Error");
//        Log.e(TAG, t.getMessage());
        t.printStackTrace();
    }

    @Override
    public void onSubscribe(Subscription s) {
        //Log.e(TAG, "Subscribe");
    }

    @Override
    public void onNext(SocketEvent socketEvent) {
//        Log.d(TAG, "Next");
//        Log.d(TAG, socketEvent.toString());
    }
}
