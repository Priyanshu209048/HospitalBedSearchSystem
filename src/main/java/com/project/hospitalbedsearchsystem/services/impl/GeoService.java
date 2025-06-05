package com.project.hospitalbedsearchsystem.services.impl;

import com.project.hospitalbedsearchsystem.entities.Location;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class GeoService {

    private static final Logger log = LoggerFactory.getLogger(GeoService.class);
    @Value("${locationiq.api.key}")
    private String apiKey;

    public Location getCoordinatesFromAddress(String address) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            String encodedAddress = address.replace(" ", "+");
            /*String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8);*/

            log.info("Encoded Address : {} ", encodedAddress);
            log.info("API Key: {} ", apiKey);

            String url = "https://us1.locationiq.com/v1/search?key=" + apiKey +
                    "&q=" + encodedAddress + "&format=json";

            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            JSONArray array = new JSONArray(response.getBody());
            JSONObject obj = array.getJSONObject(0);

            Location location = new Location();
            location.setLatitude(obj.getDouble("lat"));
            location.setLongitude(obj.getDouble("lon"));

            return location;
        } catch (HttpClientErrorException e) {
            System.err.println("API Error: " + e.getResponseBodyAsString());
            throw new RuntimeException("API Error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error during geocoding: " + e.getMessage(), e);
        }
    }
}
