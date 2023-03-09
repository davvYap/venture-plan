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

import sg.edu.nus.iss.ventureplan.model.Workdone;

@Repository
public class WorkdoneRepository {
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    private static final String WORKDONE_LIST = "workdoneList";
    private static final String WORKDONE_MAP = "workdoneMap";

    public Workdone save(final Workdone workdone) {
        // redisTemplate.opsForList().leftPush(WORKDONE_LIST, workdone.getWorkdoneId());
        redisTemplate.opsForHash().put(WORKDONE_MAP, workdone.getWorkdoneId(), workdone);
        return findByWorkdoneId(workdone.getWorkdoneId());
    }

    public Workdone findByWorkdoneId(final String workdoneId) {
        Workdone wd = (Workdone) redisTemplate.opsForHash().get(WORKDONE_MAP, workdoneId);
        return wd;
    }

    public List<Workdone> findAllWorkdone() {
        // List<Object> allWorkdoneId = redisTemplate.opsForList().range(WORKDONE_LIST,
        // 0, -1);

        // IMPORTANT Another way to retrieve all workdoneId from Redis Map without using
        // Redis List
        List<Object> allWorkdoneId = new ArrayList<>();
        HashOperations<String, Object, Object> hashOps = redisTemplate.opsForHash();
        Map<Object, Object> hashObjects = hashOps.entries(WORKDONE_MAP);
        for (Object key : hashObjects.keySet()) {
            allWorkdoneId.add(key);
        }

        List<Workdone> allWorkdone = redisTemplate.opsForHash()
                .multiGet(WORKDONE_MAP, allWorkdoneId)
                .stream()
                .filter(wd -> Workdone.class.isInstance(wd))
                .map(wd -> Workdone.class.cast(wd))
                .sorted(Comparator.comparing(Workdone::getDateOfWork))
                .collect(Collectors.toList());

        return allWorkdone;
    }

    public List<String> getWorkdoneIds() {
        // List<Object> allWorkdoneId = redisTemplate.opsForList().range(WORKDONE_LIST,
        // 0, -1);

        // IMPORTANT Another way to retrieve all workdoneId from Redis Map
        List<Object> allWorkdoneId = new ArrayList<>();
        HashOperations<String, Object, Object> hashOps = redisTemplate.opsForHash();
        Map<Object, Object> hashObjects = hashOps.entries(WORKDONE_MAP);
        for (Object key : hashObjects.keySet()) {
            allWorkdoneId.add(key);
        }

        List<String> workdoneIdList = allWorkdoneId.stream()
                .map(wd -> String.class.cast(wd))
                .collect(Collectors.toList());
        return workdoneIdList;
    }

    public void deleteWorkdone(final String workdoneId) {
        redisTemplate.opsForHash().delete(WORKDONE_MAP, workdoneId);
    }

}
