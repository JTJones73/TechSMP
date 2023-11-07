package tech.techsmp.core.commands;

import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.util.EulerAngle;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import tech.techsmp.core.Packet.SpecPacketBlocker;
import utils.ConfigMessage;
import utils.InvSave;

import java.io.ObjectInputFilter;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
public class Devfeature implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
			if(sender.isOp()) {

                if(args[0].equalsIgnoreCase("text")){
                    //ConfigMessage.getMessage("&%#1F3839%&lHello %0%, %1% is here", new String[]{" "});
                    ConfigMessage.loadMessages();
                }
                if(args[0].equalsIgnoreCase("save")){
                    Player p = (Player) sender;
                    InvSave.pushInv(p);
                }
                if(args[0].equalsIgnoreCase("load")){
                    Player p = (Player) sender;
                    InvSave.popAndSetPlayerInv(p);
                }
                if(args[0].equalsIgnoreCase("sk")){
                    Player p = (Player) sender;
                    InvSave.saveInv(p, args[1]);
                }
                if(args[0].equalsIgnoreCase("lk")){
                    Player p = (Player) sender;
                    InvSave.loadInv(p, args[1]);
                }
                if(args[0].equalsIgnoreCase("dk")){
                    InvSave.delInvKey(args[1]);
                }
                if(args[0].equalsIgnoreCase("addPlayer")){

                    //SpecPacketBlocker.addPlayer();
                }
                if(args[0].equalsIgnoreCase("api")){
                    HttpClient httpClient = HttpClientBuilder.create().build();
                    try {
                        HttpPost request = new HttpPost("https://discord.com/api/webhooks/1156248858003767436/lR39_hH7pNW8W8Rmg06IY_eBwM3k95VgHk6Okgj7z1Yl7k9E9m3chIb3VHRy5oZkXqei");

                        // Set the headers (content type)
                        request.addHeader("Content-Type", "application/json");

                        // Define the JSON payload
                        String jsonPayload = "{\n" +
                                "  \"username\": \"TTU MC Server\",\n" +
                                "  \"avatar_url\": \"https://i.imgur.com/9h8p1az.png\",\n" +
                                "  \"content\": \"Test of Discord Webhook\",\n" +
                                "  \"embeds\": [\n" +
                                "    {\n" +
                                "      \"title\": \"Test\",\n" +
                                "      \"color\": 15258703,\n" +
                                "      \"fields\": [\n" +
                                "        {\n" +
                                "          \"name\": \"This was sent from the TechSMP Plugin\",\n" +
                                "          \"value\": \"Test\"\n" +
                                "        }\n" +
                                "      ],\n" +
                                "      \"footer\": {\n" +
                                "        \"text\": \"Coming Soon...\"\n" +
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

        return true;  
    }
}