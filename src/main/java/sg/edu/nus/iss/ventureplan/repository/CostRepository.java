package sg.edu.nus.iss.ventureplan.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.ventureplan.model.Cost;

@Repository
public class CostRepository {
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    private static final String COST_LIST = "costList";
    private static final String COST_MAP = "costMap";

    public Cost save(final Cost cost) {
        // redisTemplate.opsForList().leftPush(TEAM_LIST, team.getTeamName());
        redisTemplate.opsForHash().put(COST_MAP, cost.getVersion(), cost);
        return findByVersion(cost.getVersion());
    }

    public Cost findByVersion(final String version) {
        Cost cost = (Cost) redisTemplate.opsForHash().get(COST_MAP, version);
        return cost;
    }

    public List<Cost> getAllCost() {

        List<Object> allCostVersions = new ArrayList<>();
        HashOperations<String, Object, Object> hashOps = redisTemplate.opsForHash();
        Map<Object, Object> hashObjects = hashOps.entries(COST_MAP);
        for (Object key : hashObjects.keySet()) {
            allCostVersions.add(key);
        }

        List<Cost> allCost = redisTemplate.opsForHash()
                .multiGet(COST_MAP, allCostVersions)
                .stream()
                .filter(c -> Cost.class.isInstance(c))
                .map(t -> Cost.class.cast(t))
                .sorted(Comparator.comparing(Cost::getVersion))
                .collect(Collectors.toList());

        Collections.reverse(allCost);
        return allCost;
    }

    public List<String> getAllVersion() {

        List<Object> allVersion = new ArrayList<>();
        HashOperations<String, Object, Object> hashOps = redisTemplate.opsForHash();
        Map<Object, Object> hashObjects = hashOps.entries(COST_MAP);
        for (Object key : hashObjects.keySet()) {
            allVersion.add(key);
        }

        List<String> versionList = allVersion.stream()
                .map(s -> String.class.cast(s))
                .collect(Collectors.toList());

        return versionList;
    }

    public void deleteCost(final String version) {
        redisTemplate.opsForHash().delete(COST_MAP, version);
    }

}