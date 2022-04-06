

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Vector;

public class Handler extends DefaultListModel {
    Vector<Restaurant> restaurants;

    public Handler() {}

    public void createExamles() {
        Vector<Restaurant> map = new Vector<>();
        for (int i = 0; i < 6; i++) {
            Restaurant example = randomRestaurant();
            map.add(example);
        }
        this.restaurants = map;
    }

    public void addRestaurant(Restaurant restaurant) {
        this.restaurants.add(restaurant);
        fireIntervalAdded(this, 0, restaurants.size());
    }

    public void removeRestaurant(int index) {
        this.restaurants.remove(index);
        fireIntervalRemoved(this, 0, restaurants.size());
    }

    public Vector<Restaurant> getRestaurants() {
        return this.restaurants;
    }

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

    public void writeJSON(Vector<Restaurant> restaurants) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            String json = mapper.writeValueAsString(restaurants);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public Vector<Restaurant> readJSON() {
        Vector<Restaurant> restaurants = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File file = new File("/src/main/java/restaurant.json");
            System.out.println(new File(".").getAbsolutePath());
           restaurants = objectMapper.readValue(file, Vector.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return restaurants;
    }
}

