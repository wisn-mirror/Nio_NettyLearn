package coder.androidclient.service.pool;

import java.io.IOException;
import java.nio.channels.SelectionKey;

public interface ByteToMessage {

    void read(SelectionKey key) throws IOException;
}
