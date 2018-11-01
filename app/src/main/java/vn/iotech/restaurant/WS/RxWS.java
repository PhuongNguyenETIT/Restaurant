package vn.iotech.restaurant.WS;

import android.util.Log;

import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import vn.iotech.rxwebsocket.RxWebSocket;

public class RxWS {

    private RxWebSocket rxWebSocket;
    private String url;
    public RxWS(String url) {
        this.url = url;
    }

    public String rxWebSocketHome(){

        AtomicReference<String> rawJSON = null;
        rxWebSocket = new RxWebSocket(url);
        rxWebSocket.onOpen()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(socketOpenEvent -> {

                }, Throwable::printStackTrace);

        rxWebSocket.onClosed()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(socketClosedEvent -> {
                    Log.i("TAG", socketClosedEvent.toString());
                }, Throwable::printStackTrace);

        rxWebSocket.onClosing()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(socketClosingEvent -> {
                    Log.i("TAG", socketClosingEvent.toString());
                }, Throwable::printStackTrace);

        rxWebSocket.onTextMessage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(socketMessageEvent -> {
                    rawJSON.set(socketMessageEvent.getText());
                });

        rxWebSocket.onFailure()
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(socketFailureEvent -> {
                    Log.i("TAG", socketFailureEvent.getResponse().toString());
                }, Throwable::printStackTrace);

        rxWebSocket.connect();
        return rawJSON.get();
    }
}
