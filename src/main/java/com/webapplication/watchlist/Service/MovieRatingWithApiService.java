package com.webapplication.watchlist.Service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieRatingWithApiService {

    String apiUrl = "http://www.omdbapi.com/?apikey=5889e042&t=";

    public String getMovieRating(String title){
        try{
            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<ObjectNode> response = restTemplate.getForEntity(apiUrl + title, ObjectNode.class);
            ObjectNode jsonObject = response.getBody();

            return jsonObject.path("imdbRating").asText();
        }catch (Exception e){
            System.out.println("Something wrong with OMdb API" + e.getMessage());
            return e.getMessage();
        }

    }
}
