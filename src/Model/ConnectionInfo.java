package Model;

import java.io.BufferedWriter;

/**
 * Created by Nadin on 21/11/16.
 */
public class ConnectionInfo {
    private BufferedWriter buf;
    private Conversation conv;

    public ConnectionInfo(BufferedWriter buff, Conversation conv){
        this.buf = buff;
        this.conv = conv;
    }

    public Conversation getConv() {
        return conv;
    }

    public void setConv(Conversation conv) {
        this.conv = conv;
    }

    public BufferedWriter getBuf() {
        return buf;
    }
}
