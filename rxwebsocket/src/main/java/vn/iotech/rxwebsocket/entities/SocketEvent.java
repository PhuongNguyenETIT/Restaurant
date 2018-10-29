package vn.iotech.rxwebsocket.entities;

public class SocketEvent {
    private final SocketEventTypeEnum typeEnum;

    public SocketEvent(SocketEventTypeEnum typeEnum) {
        this.typeEnum = typeEnum;
    }

    public SocketEventTypeEnum getTypeEnum() {
        return typeEnum;
    }

    @Override
    public String toString() {
        return "SocketEvent{" +
                "type=" + typeEnum +
                '}';
    }
}
