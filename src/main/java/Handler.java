import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Vector;

public class Handler extends DefaultListModel {
    Vector<Restaurant> restaurants;

    public Handler() {
        this.restaurants = readJSON();
    }

    /**
     * Function for testi ng
     */
    public void createExamles() {
        Vector<Restaurant> map = new Vector<>();
        for (int i = 0; i < 6; i++) {
            Restaurant example = randomRestaurant();
            map.add(example);
        }
        this.restaurants = map;
    }

    /**
     * Called when a new restaurant is created to save it to the json file and update the DefaultListModel
     * @param restaurant
     */
    public void addRestaurant(Restaurant restaurant) {
        this.restaurants.add(restaurant);
        writeJSON(this.restaurants);
        fireIntervalAdded(this, 0, restaurants.size());
    }

    /**
     * Called when a restaurant is removed to save the update list to the json file and update the DefaultListModel
     * @param index
     */
    public void removeRestaurant(int index) {
        this.restaurants.remove(index);
        writeJSON(this.restaurants);
        fireIntervalRemoved(this, 0, restaurants.size());
    }

    public Vector<Restaurant> getRestaurants() {
        return this.restaurants;
    }

    /**
     * Function used for the createExamples function to create a random restaurant from an API
     * @return
     */
    public static Restaurant randomRestaurant() {

        Restaurant restaurant = null;

        try {

            URL url = new URL("https://random-data-api.com/api/restaurant/random_restaurant");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestProperty("accept", "application/json");

            InputStream responseStream = connection.getInputStream();
            ObjectMapper mapper = new ObjectMapper();

            JsonNode jsonNode = mapper.readTree(responseStream);
            String name = jsonNode.get("name").asText();
            String type = jsonNode.get("type").asText();
            String address = jsonNode.get("address").asText();
            String opens = jsonNode.get("hours").get("monday").get("opens_at").asText();
            String closes = jsonNode.get("hours").get("monday").get("closes_at").asText();
            String desc = jsonNode.get("description").asText();
            restaurant = new Restaurant(name, type, desc, address, opens, closes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return restaurant;
    }

    /**
     * Function that writes the current Vector of restaurants to the JSON-File
     * @param map
     */
    public void writeJSON(Vector<Restaurant> map) {
        try {

            URL url = new URL ("https://api.npoint.io/03f61026c30f01cc67b2");
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);



            String jsonInputString = new ObjectMapper().writeValueAsString(map.toArray());
            jsonInputString = "[" + jsonInputString.substring(1, jsonInputString.length() - 1) + "]";



            try(OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input);
            }

            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Function that reads the restaurants from the JSON-File to a Vector
     * @return
     */
    public Vector<Restaurant> readJSON() {
        Vector<Restaurant> restaurants = new Vector<>();
        try {

            URL url = new URL("https://api.npoint.io/03f61026c30f01cc67b2");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestProperty("accept", "application/json");

            InputStream responseStream = connection.getInputStream();
            ObjectMapper mapper = new ObjectMapper();
            Restaurant[] restaurants1 = mapper.readValue(responseStream, Restaurant[].class);
            restaurants.addAll(Arrays.asList(restaurants1));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return restaurants;
    }
}

