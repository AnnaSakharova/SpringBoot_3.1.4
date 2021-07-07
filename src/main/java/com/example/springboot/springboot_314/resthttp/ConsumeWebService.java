package com.example.springboot.springboot_314.resthttp;

import com.example.springboot.springboot_314.resthttp.model.User;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;


public class ConsumeWebService {

    static String JSESSIONID;
    static String finalCode;
    static final String URL_USERS = "http://91.241.64.178:7081/api/users";

    public static void main(String[] args) {

        User newUser = new User(3L, "James", "Brown", (byte) 33);
        RestTemplate restTemplate = new RestTemplate();

        /*GET USERS*/
        ResponseEntity<User[]> response = restTemplate.getForEntity(URL_USERS, User[].class);
        User[] users = response.getBody();
        HttpHeaders getHeaders = response.getHeaders();
        JSESSIONID = getHeaders.getFirst(HttpHeaders.SET_COOKIE);

        /*POST USER*/
        HttpHeaders headers = new HttpHeaders();
        //headers.add(HttpHeaders.SET_COOKIE, JSESSIONID);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Cookie", JSESSIONID);
        HttpEntity<User> requestBody = new HttpEntity<>(newUser, headers);
        ResponseEntity<String> result = restTemplate.exchange(URL_USERS, HttpMethod.POST, requestBody, String.class);
        finalCode = (String) result.getBody();

        /*PUT USER*/
        newUser.setName("Thomas");
        newUser.setLastName("Shelby");
        ResponseEntity<String> responsePut = restTemplate.exchange(URL_USERS, HttpMethod.PUT, requestBody, String.class);
        finalCode += (String) responsePut.getBody();

        /*DELETE USER*/
        ResponseEntity<String> responseDel = restTemplate.exchange(URL_USERS + "/3", HttpMethod.DELETE, requestBody, String.class);
        finalCode += (String) responseDel.getBody();

        /*RESULT*/
        System.out.println(finalCode);

    }
}
