package sg.edu.nus.iss.ventureplan.repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.ventureplan.model.Revenue;

@Repository
public class RevenueRepository {
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    private static final String REV_LIST = "revList";
    private static final String REV_MAP = "revMap";

    public Revenue save(final Revenue revenue) {
        // redisTemplate.opsForList().leftPush(TEAM_LIST, team.getTeamName());
        redisTemplate.opsForHash().put(REV_MAP, revenue.getVersion(), revenue);
        return findByVersion(revenue.getVersion());
    }

    public Revenue findByVersion(final String version) {
        Revenue revenue = (Revenue) redisTemplate.opsForHash().get(REV_MAP, version);
        return revenue;
    }

    public List<Revenue> getAllRevenue() {

        List<Object> allCostVersions = new ArrayList<>();
        HashOperations<String, Object, Object> hashOps = redisTemplate.opsForHash();
        Map<Object, Object> hashObjects = hashOps.entries(REV_MAP);
        for (Object key : hashObjects.keySet()) {
            allCostVersions.add(key);
        }

        List<Revenue> allRev = redisTemplate.opsForHash()
                .multiGet(REV_MAP, allCostVersions)
                .stream()
                .filter(r -> Revenue.class.isInstance(r))
                .map(r -> Revenue.class.cast(r))
                .sorted(Comparator.comparing(Revenue::getVersion))
                .collect(Collectors.toList());

        return allRev;
    }

    public List<String> getAllVersion() {

        List<Object> allVersion = new ArrayList<>();
        HashOperations<String, Object, Object> hashOps = redisTemplate.opsForHash();
        Map<Object, Object> hashObjects = hashOps.entries(REV_MAP);
        for (Object key : hashObjects.keySet()) {
            allVersion.add(key);
        }

        List<String> versionList = allVersion.stream()
                .map(s -> String.class.cast(s))
                .collect(Collectors.toList());

        return versionList;
    }

    public void deleteRevenue(final String version) {
        redisTemplate.opsForHash().delete(REV_MAP, version);
    }
}
