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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

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

        
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        // Call Flask API using RestTemplate
        RestTemplate restTemplate = new RestTemplate();
        String flaskUrl = "http://127.0.0.1:5000/predict"; 

        try {
            ResponseEntity<String> response = restTemplate.exchange(flaskUrl, HttpMethod.POST, entity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody(); // Return successful response
            } else {
                return "Error: Flask API responded with status code " + response.getStatusCode();
            }

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            // Handle client and server errors separately
            return "Error calling Flask API: " + e.getStatusCode() + " " + e.getResponseBodyAsString();
        } catch (Exception e) {
            // Handle other errors such as timeouts
            return "Error calling Flask API: " + e.getMessage();
        }
    }
}

