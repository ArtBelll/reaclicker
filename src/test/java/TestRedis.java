import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import ru.reaclicker.domain.User;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Date;
import java.util.Random;
import java.util.Set;

/**
 * Created by Artur Belogur on 18.03.17.
 */
@Slf4j
public class TestRedis {

    private static final String REDISSON_CONFIG = "redisson.json";

    private RedissonClient redis;

    private void connectRedis() throws IOException {
        InputStream inputStream = log.getClass().getClassLoader().getResourceAsStream(REDISSON_CONFIG);
        Config config = Config.fromJSON(inputStream);
        redis = Redisson.create(config);
    }

    @Test
    public void testSortStructure() throws IOException {
        connectRedis();
        Date dateStart = new Date();
        RScoredSortedSet<Integer> users = redis.getScoredSortedSet("test");
        Integer userId = users.valueRange(6, 6).iterator().next();
//        for(int i = 0; i <= 10; i++) {
//            users.add(new Random().nextInt(10000), i);
//        }
        Date dateEnd = new Date();

        log.info("Time: {}", new Date(dateEnd.getTime() - dateStart.getTime()));
    }
}
