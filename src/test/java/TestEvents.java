import com.corundumstudio.socketio.SocketIOClient;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import ru.reaclicker.domain.User;

import java.net.URISyntaxException;

/**
 * Created by Artur Belogur on 09.03.17.
 */

public class TestEvents {

    @Test
    public void testEvents() throws URISyntaxException {
        final Socket socket = IO.socket("http://192.168.1.109:8999");
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                socket.disconnect();
            }

        }).on("event", new Emitter.Listener() {

            @Override
            public void call(Object... args) {}

        }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

            @Override
            public void call(Object... args) {}

        });
        socket.connect();
    }
}
