package com.spring.adaimdb.imdb;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReadCsv {

    private List<List<String>> readFile(String fileLocation) throws IOException {
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileLocation))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                records.add(Arrays.asList(values));
            }
            return records;
        }
    }

    public List<String> getImdbIds(int numberOfFilms) {
        Set<Integer> randomIds = generateRandomIds(numberOfFilms);
        List<List<String>> listFilms = null;
        Map<Integer, String> allImdbIds = new HashMap<>();
        try {
            listFilms = this.readFile("src/main/resources/imdb_data.csv");
            // first line = head
            for (int i = 1; i <= 3348; i++) {
                allImdbIds.put(i, listFilms.get(i).get(0));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Map<Integer, String> filteredMap = allImdbIds.entrySet()
                .stream()
                .filter(entry -> randomIds.contains(entry.getKey()))
                .collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));

        return filteredMap.values().stream().toList();
    }

    private Set<Integer> generateRandomIds(int numberOfFilms) {
        Set<Integer> randomIds = new HashSet<>();
        Random random = new Random();

        while (randomIds.size() < numberOfFilms) {
            int num = random.nextInt(3348) + 1;
            randomIds.add(num);
        }
        return randomIds;
    }
}
