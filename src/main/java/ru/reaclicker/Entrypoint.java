package ru.reaclicker;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketConfig;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Artur Belogur on 09.03.17.
 */
@Slf4j
public class Entrypoint {

    private static final String REDISSON_CONFIG = "redisson.json";

    public static void main(String[] args) throws IOException {
        final RedissonClient redisson = Redisson.create(getRedissonConfig());
        RedissonFactory.setRedissonClient(redisson);

        Configuration conf = new Configuration();
        conf.setHostname("localhost");
        conf.setPort(8999);
        final SocketConfig socketConfig = new SocketConfig();
        socketConfig.setReuseAddress(true);
        conf.setSocketConfig(socketConfig);
        PushServer pushService = new PushServer(conf);
        pushService.start();
    }

    private static Config getRedissonConfig() throws IOException {
        final String config = System.getenv("REDIS_CONFIG");
        if (config != null) {
            int i = config.lastIndexOf('.');
            final String ext = i > 0 ? config.substring(i + 1) : null;
            if (ext != null && ext.toLowerCase().equals("yaml"))
                return Config.fromYAML(new File(config));
            return Config.fromJSON(new File(config));
        }
        InputStream inputStream = log.getClass().getClassLoader().getResourceAsStream(REDISSON_CONFIG);
        return Config.fromJSON(inputStream);
    }
}
