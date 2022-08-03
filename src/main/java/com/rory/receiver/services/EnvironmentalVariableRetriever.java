package com.rory.receiver.services;

public class EnvironmentalVariableRetriever {

    public static final String access_token = System.getenv("ACCESS_TOKEN");
    public static final String WAID = System.getenv("WAID");
    public static final String verify_token = System.getenv("VERIFICATION_TOKEN");
}
