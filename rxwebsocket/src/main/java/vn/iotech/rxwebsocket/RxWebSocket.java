package vn.iotech.rxwebsocket;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.gson.Gson;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.WebSocket;
import okio.ByteString;
import vn.iotech.rxwebsocket.entities.SocketClosedEvent;
import vn.iotech.rxwebsocket.entities.SocketClosingEvent;
import vn.iotech.rxwebsocket.entities.SocketEvent;
import vn.iotech.rxwebsocket.entities.SocketFailureEvent;
import vn.iotech.rxwebsocket.entities.SocketMessageEvent;
import vn.iotech.rxwebsocket.entities.SocketOpenEvent;

public class RxWebSocket {

    private final WebSocketOnsubscribe webSocketOnsubscribe;
    private PublishProcessor<SocketEvent> eventPublishProcessor = PublishProcessor.create();
    private CompositeDisposable disposables = new CompositeDisposable();
    private CompositeDisposable connectionDisposables = null;
    private WebSocket webSocket = null;

    public RxWebSocket(@NonNull String connectionUrl){
        this.webSocketOnsubscribe = new WebSocketOnsubscribe(connectionUrl);
    }

    public RxWebSocket(@NonNull OkHttpClient client, @NonNull String connectionUrl){
        this.webSocketOnsubscribe = new WebSocketOnsubscribe(client, connectionUrl);
    }

    private Flowable<SocketEvent> getEventSource(){
        return eventPublishProcessor.onErrorResumeNext(throwable -> {
            throwable.printStackTrace();
            eventPublishProcessor = PublishProcessor.create();
            return eventPublishProcessor;
        });
    }

    public Flowable<SocketOpenEvent> onOpen(){
        return getEventSource()
                .ofType(SocketOpenEvent.class);
                //.doOnEach(new RxWebSocketLogger("onOpen"));
    }

    public Flowable<SocketClosedEvent> onClosed(){
        return getEventSource()
                .ofType(SocketClosedEvent.class);
                //.doOnEach(new RxWebSocketLogger("onClosed"));
    }

    public Flowable<SocketClosingEvent> onClosing(){
        return getEventSource()
                .ofType(SocketClosingEvent.class);
                //.doOnEach(new RxWebSocketLogger("onClosing"));
    }

    public Flowable<SocketFailureEvent> onFailure(){
        return getEventSource()
                .ofType(SocketFailureEvent.class);
                //.doOnEach(new RxWebSocketLogger("onFailure"));
    }

    public Flowable<SocketMessageEvent> onTextMessage(){
        return getEventSource()
                .ofType(SocketMessageEvent.class)
                .filter(SocketMessageEvent::isText);
                //.doOnEach(new RxWebSocketLogger("onTextMessage"));
    }

    public Flowable<SocketMessageEvent> onBinaryMessage(){
        return getEventSource()
                .ofType(SocketMessageEvent.class)
                .filter(event -> !event.isText());
                //.doOnEach(new RxWebSocketLogger("onBinaryMessage"));
    }

    public synchronized void connect(){
        connectionDisposables = new CompositeDisposable();
        Disposable webSocketInstanceDisposable = getEventSource()
                .ofType(SocketOpenEvent.class)
                .firstElement()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .subscribe(
                        socketOpenEvent -> webSocket = socketOpenEvent.getWebSocket(),
                        throwable -> {
                            throwable.printStackTrace();
                        });

        Disposable connectionDisposable = Flowable.create(webSocketOnsubscribe, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .subscribe(
                        event -> eventPublishProcessor.onNext(event),
                        throwable -> {
                            throwable.printStackTrace();
                        });

        connectionDisposables.add(webSocketInstanceDisposable);
        connectionDisposables.add(connectionDisposable);
        disposables.add(connectionDisposable);
    }

    public synchronized Single<Boolean> sendMessage(@NonNull Gson gson, @Nullable Object payload) {
        return Single.fromCallable(() -> {
            if (webSocket != null) {
                String jsonBody = new Gson().toJson(payload);
                return webSocket.send(jsonBody);
            } else {
                throw new RuntimeException("WebSocket not connected!");
            }
        });
    }

    public synchronized Single<Boolean> sendMessage(@Nullable String content) {
        return Single.fromCallable(() -> {
            if (webSocket != null) {
                return webSocket.send(content);
            } else {
                throw new RuntimeException("WebSocket not connected!");
            }
        });
    }

    public synchronized Single<Boolean> sendMessage(@NonNull ByteString bytes) {
        return Single.fromCallable(() -> {
            if (webSocket != null) {
                return webSocket.send(bytes);
            } else {
                throw new RuntimeException("WebSocket not connected!");
            }
        });
    }

    public synchronized Single<Boolean> close() {
        return Single.fromCallable(() -> {
            if (webSocket != null) {
                disposables.add(getEventSource()
                        .ofType(SocketClosedEvent.class)
                        .subscribe(event -> {
                            connectionDisposables.clear();
                            disposables.clear();
                        }, Throwable::printStackTrace));
                return webSocket.close(1000, "closed");
            } else {
                throw new RuntimeException("WebSocket not connected!");
            }
        }).doOnSuccess(success -> webSocket = null);
    }

    public synchronized Single<Boolean> close(int code, @Nullable String reason) {
        return Single.fromCallable(() -> {
            if (webSocket != null) {
                disposables.add(getEventSource()
                        .ofType(SocketClosedEvent.class)
                        .subscribe(event -> {
                            connectionDisposables.clear();
                            disposables.clear();
                        }, Throwable::printStackTrace));
                return webSocket.close(code, reason);
            } else {
                throw new RuntimeException("WebSocket not connected!");
            }
        }).doOnSuccess(success -> webSocket = null);
    }
}
