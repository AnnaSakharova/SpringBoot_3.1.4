package com.example.springboot.springboot_314.resthttp;

import com.example.springboot.springboot_314.resthttp.model.User;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

public class SpringRestClient {
    private static final String GET_USERS_URL = "http://91.241.64.178:7081/api/users";
    private static final String GET_USER_UPDATE_BY_ID_URL = "http://91.241.64.178:7081/api/users";
    private static final String GET_USER_DELETE_BY_ID_URL = "http://91.241.64.178:7081/api/users/{id}";
    private static final String GET_USERS_CREATE_URL = "http://91.241.64.178:7081/api/users";


    private static final RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) {
        SpringRestClient springRestClient = new SpringRestClient();
        springRestClient.getUsers();
    }

    private void getUsers() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> user = new HttpEntity<>("parameters", headers);
        //GET
        ResponseEntity<String> result = restTemplate.exchange(GET_USERS_URL, HttpMethod.GET, user, String.class);

        List<String> cookie = result.getHeaders().get("Set-Cookie");
        System.out.println(result);

        // POST
        if (cookie != null) {
            headers.set("Cookie", String.join(";", cookie));
        }

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        User newUser = new User(3L, "James", "Brown", (byte) 50);
        HttpEntity<User> requestBody = new HttpEntity<>(newUser, headers);

        ResponseEntity<String> resultAddUser = restTemplate.postForEntity(GET_USERS_CREATE_URL, requestBody, String.class);
        System.out.println(resultAddUser.getBody());

        //PUT
        if (cookie != null) {
            headers.set("Cookie", String.join(";", cookie));
        }
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        Map<String, Integer> params = new HashMap<String, Integer>();
        params.put("id", 3);
        User updateUser = new User(3L, "Tomas", "Shelby", (byte) 30);
        HttpEntity<User> updateHttp = new HttpEntity<>(updateUser, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(GET_USER_UPDATE_BY_ID_URL, HttpMethod.PUT, updateHttp, String.class, params);
        System.out.println(responseEntity.getBody());

        //DELETE
        if (cookie != null) {
            headers.set("Cookie", String.join(";", cookie));
        }

        Map<String, Integer> param = new HashMap<>();
        param.put("id", 3);
        HttpEntity<User> deleteHttp = new HttpEntity<>(updateUser, headers);
        ResponseEntity<String> deleteUserResult = restTemplate.exchange(GET_USER_DELETE_BY_ID_URL, HttpMethod.DELETE, deleteHttp, String.class, param);
        System.out.println(deleteUserResult.getBody());
    }
}
