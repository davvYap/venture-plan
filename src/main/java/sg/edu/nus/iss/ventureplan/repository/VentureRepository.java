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

import sg.edu.nus.iss.ventureplan.model.Team;

@Repository
public class VentureRepository {
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    private static final String TEAM_LIST = "teamList";
    private static final String TEAM_MAP = "teamMap";

    public Team save(final Team team) {
        // redisTemplate.opsForList().leftPush(TEAM_LIST, team.getTeamName());
        redisTemplate.opsForHash().put(TEAM_MAP, team.getTeamName(), team);
        return findByTeamName(team.getTeamName());
    }

    public Team findByTeamName(final String teamName) {
        Team team = (Team) redisTemplate.opsForHash().get(TEAM_MAP, teamName);
        return team;
    }

    public List<Team> findAllTeam() {
        // List<Object> allTeamNames = redisTemplate.opsForList().range(TEAM_LIST, 0,
        // -1);

        // IMPORTANT Another way to retrieve all workdoneId from Redis Map without using
        // Redis List
        List<Object> allTeamNames = new ArrayList<>();
        HashOperations<String, Object, Object> hashOps = redisTemplate.opsForHash();
        Map<Object, Object> hashObjects = hashOps.entries(TEAM_MAP);
        for (Object key : hashObjects.keySet()) {
            allTeamNames.add(key);
        }

        List<Team> allTeams = redisTemplate.opsForHash()
                .multiGet(TEAM_MAP, allTeamNames)
                .stream()
                .filter(t -> Team.class.isInstance(t))
                .map(t -> Team.class.cast(t))
                .sorted(Comparator.comparing(Team::getName))
                .collect(Collectors.toList());

        return allTeams;
    }

    public List<String> getTeamNames() {
        // List<Object> allTeamNames = redisTemplate.opsForList().range(TEAM_LIST, 0,
        // -1);

        List<Object> allTeamNames = new ArrayList<>();
        HashOperations<String, Object, Object> hashOps = redisTemplate.opsForHash();
        Map<Object, Object> hashObjects = hashOps.entries(TEAM_MAP);
        for (Object key : hashObjects.keySet()) {
            allTeamNames.add(key);
        }

        List<String> teamNameList = allTeamNames.stream()
                .map(s -> String.class.cast(s))
                .collect(Collectors.toList());
        return teamNameList;
    }

    public void deleteTeam(final String teamName) {
        redisTemplate.opsForHash().delete(TEAM_MAP, teamName);
    }

}
