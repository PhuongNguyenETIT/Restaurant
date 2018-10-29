package vn.iotech.rxwebsocket.entities;

public class SocketClosedEvent extends SocketEvent {

    private final int code;
    private final String reason;

    public SocketClosedEvent(int code, String reason) {
        super(SocketEventTypeEnum.CLOSED);
        this.code = code;
        this.reason = reason;
    }

    public int getCode(){
        return code;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public String toString() {
        return "SocketClosingEvent{" +
                "code=" + code +
                ", reason='" + reason + '\'' +
                '}';
    }
}
