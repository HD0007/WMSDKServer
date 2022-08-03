package com.rory.receiver.services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MessageReceiverService {

    public static ResponseEntity<String> wiseManEcho(JSONObject jsonObject) {
        // When message is received, responds with the same message content prepended with 'a wise man once said'

        System.out.println("Echoing as wise man...");
        try {
            JSONArray messages = jsonObject.getJSONArray("entry").getJSONObject(0).getJSONArray("changes").getJSONObject(0).getJSONObject("value").getJSONArray("messages");

            for (int i = 0; i < messages.length(); i++) {
                JSONObject message = messages.getJSONObject(i);
                JSONObject messageTextObject = message.getJSONObject("text");
                String content = messageTextObject.getString("body");
                System.out.println(content);
                MessageSenderService.sendMessage("A wise man once said: " + content);
            }
            return new ResponseEntity<>("Message Received", HttpStatus.OK);
        } catch (JSONException | IOException e) {
            return new ResponseEntity<>("Request body JSON improperly formed", HttpStatus.BAD_REQUEST);
        }
    }
}

