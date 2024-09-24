package com.example.Churn.Controller;

import com.example.Churn.Entity.CustomerData;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController

public class PredictionController {

    @PostMapping("/api/predict")
    public String getPrediction(@RequestBody CustomerData data) {
        // Prepare the request body to send to Flask
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("MonthlyCharge", data.getMonthlyCharge());
        requestBody.put("AccountWeeks", data.getAccountWeeks());
        requestBody.put("ContractRenewal", data.getContractRenewal());
        requestBody.put("DataPlan", data.getDataPlan());
        requestBody.put("DataUsage", data.getDataUsage());
        requestBody.put("CustServCalls", data.getCustServCalls());
        requestBody.put("DayMins", data.getDayMins());
        requestBody.put("DayCalls", data.getDayCalls());
        requestBody.put("OverageFee", data.getOverageFee());
        requestBody.put("RoamMins", data.getRoamMins());

        // Prepare HTTP headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        // Create the entity with headers and body
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        // Call Flask API using RestTemplate
        RestTemplate restTemplate = new RestTemplate();
        String flaskUrl = "http://127.0.0.1:5000/predict"; // Flask API URL

        ResponseEntity<String> response;
        try {
            response = restTemplate.exchange(flaskUrl, HttpMethod.POST, entity, String.class);
        } catch (Exception e) {
            return "Error calling Flask API: " + e.getMessage();
        }

        // Return the response from Flask
        return response.getBody();
    }
}
// ----------------------------------------------------------------------------
// code with adding the confing

