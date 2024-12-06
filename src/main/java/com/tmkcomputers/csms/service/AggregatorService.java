package com.tmkcomputers.csms.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

@Service
public class AggregatorService {

    private static final String API_KEY = ""; // Replace with actual API key
    private static final String BASE_URL = "https://api.openchargemap.io/v3/poi/";

    public JSONArray getNearbyChargingStations(double latitude, double longitude, int radius, Integer[] connectionTypeIds, Integer levelId, Integer statusTypeId, Integer usageTypeId, Integer minPowerKw, int maxResults, int offset) throws Exception {
        StringBuilder urlBuilder = new StringBuilder(String.format("%s?output=json&latitude=%.4f&longitude=%.4f&distance=%d&maxresults=%d&offset=%d&key=%s",
                BASE_URL, latitude, longitude, radius, maxResults, offset, API_KEY));

        // Add connection type filter if provided (comma-separated values)
        if (connectionTypeIds != null && connectionTypeIds.length > 0) {
            String connectionTypeIdsParam = Arrays.toString(connectionTypeIds).replaceAll("\\[|\\]|\\s", ""); // Format as comma-separated list
            urlBuilder.append("&connectiontypeid=").append(connectionTypeIdsParam);
        }

        // Add charging level filter if provided
        if (levelId != null) {
            urlBuilder.append("&levelid=").append(levelId);
        }

        // Add station status filter if provided
        if (statusTypeId != null) {
            urlBuilder.append("&statustypeid=").append(statusTypeId);
        }

        // Add usage type filter if provided
        if (usageTypeId != null) {
            urlBuilder.append("&usagetypeid=").append(usageTypeId);
        }

        // Add minimum power filter if provided
        if (minPowerKw != null) {
            urlBuilder.append("&minpowerkw=").append(minPowerKw);
        }

        return fetchJsonArrayFromAPI(urlBuilder.toString());
    }

    public JSONObject getChargingStationDetails(int stationId) throws Exception {
        String urlString = String.format("%s?output=json&chargepointid=%d&key=%s", BASE_URL, stationId, API_KEY);
        JSONArray jsonArray = fetchJsonArrayFromAPI(urlString);

        if (jsonArray.length() == 0) {
            throw new Exception("Station not found.");
        }
        return jsonArray.getJSONObject(0);
    }

    private JSONArray fetchJsonArrayFromAPI(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder responseBuilder = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            responseBuilder.append(line);
        }
        br.close();

        return new JSONArray(responseBuilder.toString());
    }
}
