package utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.bukkit.Bukkit;

public class DiscordWebhook {
    public static void sendDiscordMsg(String intro, String header, String name, String content){
        HttpClient httpClient = HttpClientBuilder.create().build();
        try {
            HttpPost request = new HttpPost("https://discord.com/api/webhooks/1156248858003767436/lR39_hH7pNW8W8Rmg06IY_eBwM3k95VgHk6Okgj7z1Yl7k9E9m3chIb3VHRy5oZkXqei");

            // Set the headers (content type)
            request.addHeader("Content-Type", "application/json");

            // Define the JSON payload
            String jsonPayload = "{\n" +
                    "  \"username\": \"TTU MC Server\",\n" +
                    "  \"avatar_url\": \"https://i.imgur.com/9h8p1az.png\",\n" +
                    "  \"content\": \"" + intro + "\",\n" +
                    "  \"embeds\": [\n" +
                    "    {\n" +
                    "      \"title\": \"" + header + "\",\n" +
                    "      \"color\": 15258703,\n" +
                    "      \"fields\": [\n" +
                    "        {\n" +
                    "          \"name\": \"" + name + "\",\n" +
                    "          \"value\": \"" + content + "\"\n" +
                    "        }\n" +
                    "      ],\n" +
                    "      \"footer\": {\n" +
                    "        \"text\": \"\"\n" +
                    "      }\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}";

            // Set the JSON payload
            StringEntity params = new StringEntity(jsonPayload);
            request.setEntity(params);

            // Execute the request
            HttpResponse response = httpClient.execute(request);

            // Read and print the response
            int statusCode = response.getStatusLine().getStatusCode();
            if(statusCode != 204)
                Bukkit.getConsoleSender().sendMessage("Error with discord response Status Code: " + statusCode);

            // Close the response
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
