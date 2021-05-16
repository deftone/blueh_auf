package de.deftone.blueh_auf.biotopVernetzung;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class GeoHelper {
    //https://developer.here.com/blog/how-to-use-geocoding-in-java-with-the-here-geocoding-search-api
    //https://developer.here.com/projects
    //https://developer.here.com/projects/PROD-cbaf4c8b-c59b-49b2-aa6a-2a7fe7dcee90
    private static final String GEOCODING_RESOURCE = "https://geocode.search.hereapi.com/v1/geocode";
    private static final String API_KEY = "5PCHGMonfvLs7mc7GwJ6C-5b3GpcslnO2rbxmlnTlXw";
    private static final HttpClient httpClient = HttpClient.newHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper();

    public GeoLocation getCoordinatesFromAddress(String query) throws IOException, InterruptedException {

        String encodedQuery = URLEncoder.encode(query, "UTF-8");
        String requestUri = GEOCODING_RESOURCE + "?apiKey=" + API_KEY + "&q=" + encodedQuery;

        HttpRequest geocodingRequest = HttpRequest.newBuilder().GET().uri(URI.create(requestUri))
                .timeout(Duration.ofMillis(2000)).build();

        HttpResponse<String> stringHttpResponse = httpClient.send(geocodingRequest,
                HttpResponse.BodyHandlers.ofString());

        return extractCoordinates(stringHttpResponse.body());
    }

    private GeoLocation extractCoordinates(String response) throws JsonProcessingException {

        GeoLocation geoLocation = new GeoLocation();

        JsonNode responseJsonNode = mapper.readTree(response);
        JsonNode items = responseJsonNode.get("items");

        for (JsonNode item : items) {
            //erst pruefen ob die adresse und hausnummer gefunden wurde
            JsonNode address = item.get("address");
            JsonNode street = address.get("street");
            if (street == null) {
                return null;
            }
            JsonNode houseNumber = address.get("houseNumber");
            if (houseNumber == null) {
                return null;
            }
            //wenn beide gefuellt sind, dann ist alles ok
            JsonNode position = item.get("position");
            geoLocation.setLatitude(position.get("lat").asDouble());
            geoLocation.setLongitude(position.get("lng").asDouble());
        }
        return geoLocation;
    }

}
