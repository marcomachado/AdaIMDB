package com.spring.adaimdb.imdb;

import com.google.gson.Gson;
import com.spring.adaimdb.imdb.model.ImdbMovie;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

@Service
public class RequestDataFromOmdb {
    public ImdbMovie getMovieFromOmdb(String imdbId) throws IOException, InterruptedException {
        String url = "https://www.omdbapi.com/?i="+imdbId+"&apikey=e2ff3d0e";
        URI endereco = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            System.err.println("RequestDataFromOmdb");
            System.err.println("Status code "+response.statusCode());
        }
        String body = response.body();
        Gson gson = new Gson();
        ImdbMovie movie = gson.fromJson(body, ImdbMovie.class);
        return movie;
    }
}
