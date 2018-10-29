package vn.iotech.rxwebsocket;

import android.support.annotation.NonNull;
import java.util.concurrent.TimeUnit;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import vn.iotech.rxwebsocket.entities.SocketEvent;

public class WebSocketOnsubscribe implements FlowableOnSubscribe<SocketEvent>{

    private final OkHttpClient client;
    private final Request request;

    public WebSocketOnsubscribe(@NonNull String url){
        client = new OkHttpClient.Builder()
                .readTimeout(0, TimeUnit.MILLISECONDS)
                .build();

        request = new Request.Builder()
                .url(url)
                .build();
    }

    public WebSocketOnsubscribe(@NonNull OkHttpClient client, @NonNull String url){
        this.client = client;
        request = new Request.Builder()
                .url(url)
                .build();
    }

    @Override
    public void subscribe(FlowableEmitter<SocketEvent> emitter) {
        client.newWebSocket(request, new WebSocketEventRouter(emitter));
    }
}
