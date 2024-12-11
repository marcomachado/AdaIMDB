package com.spring.adaimdb.monitor;

import java.util.*;
import java.util.stream.Collectors;

public class MonitorProgressUsers {
    private static Map<String, List<RoundProgress>> monitorProgressUsers ;

    public static synchronized Map<String, List<RoundProgress>> getInstance() {
        if (monitorProgressUsers == null) {
            monitorProgressUsers = new HashMap<>();
        }
        return monitorProgressUsers;
    }

    public static void add(String username, RoundProgress rp) {
        if (!monitorProgressUsers.containsKey(username)) {
            monitorProgressUsers.put(username, new ArrayList<>());
        }

        monitorProgressUsers.get(username).add(rp);
    }

    public static List<RoundProgress> getRoundsFromUser(String username) {
        return monitorProgressUsers.getOrDefault(username, Collections.emptyList());
    }

    public static Optional<RoundProgress> getRoundFromUserAndUuid(String username, UUID uuid) {
        List<RoundProgress> listRounds = monitorProgressUsers.getOrDefault(username, Collections.emptyList());
        Optional<RoundProgress> first = listRounds.stream()
                .filter(rp -> rp.getUuid().equals(uuid) && rp.getUserAnwser() == -1).findFirst();
        return first;

    }

    public static List<RoundProgress> getUnansweredRound(String username) {
        List<RoundProgress> roundProgress = getRoundsFromUser(username);
        if (roundProgress.isEmpty()) return Collections.emptyList();
        return roundProgress.stream()
                .filter(rp -> rp.getUserAnwser() == -1)
                .collect(Collectors.toList());
    }

    public static int countWrongAnwsers(String username) {
        List<RoundProgress> roundProgress = getRoundsFromUser(username);
        if (roundProgress == null) return 0;
        return (int) roundProgress.stream()
                .filter(rp -> rp.getPoints() == 0)
                .count();
    }

    public static void clearUser(String username) {
        monitorProgressUsers.remove(username);
    }

    public static RoundProgress updateRoundProgress(RoundProgress round, int userAnwser) {
        round.setPoints(round.getPoints());
        round.setUserAnwser(userAnwser);

        return round;
    }
}
