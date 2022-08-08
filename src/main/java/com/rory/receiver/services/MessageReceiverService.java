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
            System.out.println(messages);

            for (int i = 0; i < messages.length(); i++) {
                JSONObject message = messages.getJSONObject(i);
                String fromNumber = message.getString("from");
                JSONObject messageTextObject = message.getJSONObject("text");
                String content = messageTextObject.getString("body");
                System.out.println(content);
//                 MessageSenderService.sendMessage("A wise man once said: " + content,fromNumber);
                                if(content.startsWith("LEAP INFYAR")) {
                	MessageSenderService.sendMessage("Dear Requestor,\n"
                			+ "\n"
                			+ "Thanks for contacting PTC team. \n"
                			+ "\n"
                			+ "We will address your query at the earliest for CaseId:AR:00000070\n"
                			+ "\n"
                			+ "Your patience and co-operation is appreciated.\n"
                			+ "\n"
                			+ "Regards,\n"
                			+ "PTC Team  \n"
                			+ " ",fromNumber);
                	System.out.println("CASE CREATED WITH :"+content);
                }
                else if(content.startsWith("LEAP INFYVR")) {
                	MessageSenderService.sendMessage("Dear Requestor,\n"
                			+ "\n"
                			+ "Thanks for contacting PTC team. \n"
                			+ "\n"
                			+ "We will address your query at the earliest for CaseId:VR:00000070\n"
                			+ "\n"
                			+ "Your patience and co-operation is appreciated.\n"
                			+ "\n"
                			+ "Regards,\n"
                			+ "PTC Team  \n"
                			+ " ",fromNumber);
                	System.out.println("CASE CREATED WITH :"+content);
                }
                else {
                	MessageSenderService.sendMessage("THANK YOU FOR CONTACTING, WE CAN'T PROCESS YOUR REQUEST AT THIS TIME",fromNumber);
                	System.out.println("NO PROCESS FOUND"+content);
                }
            }
            return new ResponseEntity<>("Message Received", HttpStatus.OK);
        } catch (JSONException | IOException e) {
            return new ResponseEntity<>("Request body JSON improperly formed", HttpStatus.BAD_REQUEST);
        }
    }
}

