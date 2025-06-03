package com.project.hospitalbedsearchsystem.services.impl;

import com.project.hospitalbedsearchsystem.dao.LocationDao;
import com.project.hospitalbedsearchsystem.entities.Location;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class GeoService {

    private final LocationDao locationDao;

    public Location saveCoordinatesFromAddress(String address) {
        RestTemplate restTemplate = new RestTemplate();
        String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8);
        String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + encodedAddress + "&key=YOUR_API_KEY";

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        JSONObject json = new JSONObject(response.getBody());

        if (!json.getJSONArray("results").isEmpty()) {
            JSONObject location = json.getJSONArray("results")
                    .getJSONObject(0)
                    .getJSONObject("geometry")
                    .getJSONObject("location");

            double lat = location.getDouble("lat");
            double lng = location.getDouble("lng");

            Location loc = new Location();
            loc.setLatitude(lat);
            loc.setLongitude(lng);
            return locationDao.save(loc);
        }

        throw new RuntimeException("Unable to geocode address");
    }
}
