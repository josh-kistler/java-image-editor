import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherForecast {

    public static void main(String[] args) throws IOException{
        String latitude = "39.168804";
        String longitude = "-86.536659";
        String unit2 = "F";
        String unit = "fahrenheit";
        String v = "EST";
        for (int i = 0; i < args.length - 1; i++) {
            if (args[i].equals("--latitude")) {
                latitude = args[i + 1];
            } else if (args[i].equals("--longitude")) {
                longitude = args[i + 1];
            } else if (args[i].equals("--unit")) {
                unit2 = args[i + 1];
                if (unit2.equals("C")) {
                    unit = "celsius";
                } else if (unit2.equals("F")) {
                    unit = "fahrenheit";
                }
            }
        }
        String urlString = "https://api.open-meteo.com/v1/forecast"
                + "?latitude=" + latitude
                + "&longitude=" + longitude
                + "&hourly=temperature_2m"
                + "&temperature_unit=" + unit
                + "&timezone=" + v;

        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        if (responseCode != 200) {
            throw new IOException("HTTP GET Request Failed with Error Code: " + responseCode);
        }
        StringBuilder response = new StringBuilder();
        try (BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = input.readLine()) != null){
                response.append(line);
            }
            JsonElement result = JsonParser.parseString(response.toString());
            JsonArray times = result.getAsJsonObject().getAsJsonObject("hourly").getAsJsonArray("time");
            JsonArray temps = result.getAsJsonObject().getAsJsonObject("hourly").getAsJsonArray("temperature_2m");

            String u = "";
            if (unit.equals("fahrenheit")){
                u = "Fahrenheit";
            }
            else {
                u = "Celcius";
            }
            System.out.println("7-Day Forecast in " + u + " :");
            String curr = "";
            for (int i = 0; i < times.size(); i += 3){
                String clock = times.get(i).getAsString();
                String[] seperate = clock.split("T");
                String date = seperate[0];
                String time = seperate[1];
                if (!date.equals(curr)){
                    curr = date;
                    System.out.println("Forecast for " + date + ":");
                }
                double temp = temps.get(i).getAsDouble();
                System.out.println(time + ": " + temp + "°F");
            }
        }
    }
}
