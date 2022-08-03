package com.rory.receiver;

import com.rory.receiver.services.MessageReceiverService;
import com.rory.receiver.services.MessageSenderService;
import com.rory.receiver.services.VerificationService;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;

@RestController
public class GeneralController {

    @GetMapping("/")
    public String mainPage(){
        return "Main page displayed successfully!";
    }

    @GetMapping("/webhook")
    public ResponseEntity<String> verification(@RequestParam("hub.mode") String mode,
                                               @RequestParam("hub.verify_token") String verify_token,
                                               @RequestParam("hub.challenge") String challenge){
        return VerificationService.verify(mode, verify_token, challenge);
    }

    @PostMapping(value = "/webhook", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> receiveMessage(@RequestBody String jsonString){
        System.out.println("Message received.");
        try {
            JSONObject messageJSON = new JSONObject(jsonString);
            // For now as a test, call the service to echo the message.
            return MessageReceiverService.wiseManEcho(messageJSON);
        } catch (JSONException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/sendText", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> sendMessage(@RequestBody String messageJSONString){
        // Convert body to JSON and extract message
        String message;

        try {
            JSONObject messageJSON = new JSONObject(messageJSONString);
            message = messageJSON.getString("text");
        } catch (JSONException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        System.out.println("Message extracted properly...");

        try {
            return MessageSenderService.sendMessage(message);
        } catch (MalformedURLException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value="/sendTemplate")
    public ResponseEntity<String> sendTemplate(@RequestParam("template_name") String template_name){
        try {
            return MessageSenderService.sendTemplate(template_name);
        } catch (JSONException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (IOException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}