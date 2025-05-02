package com.example.springboot.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Controller
public class HomeController {

    @Value("${fashion.assistant.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public HomeController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("products", getSampleProducts());
        return "home";
    }

    @PostMapping("/send")
    public String send(@RequestParam String message, @RequestParam String selectedProduct, Model model) {
        Map<String, String> request = new HashMap<>();
        request.put("user_message", message);
        request.put("product_name", selectedProduct);
        request.put("product_description", "Sample description for " + selectedProduct);

        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, request, String.class);
        model.addAttribute("response", response.getBody());
        model.addAttribute("products", getSampleProducts());
        return "home";
    }

    private Map<Integer, String> getSampleProducts() {
        Map<Integer, String> products = new HashMap<>();
        products.put(1, "Navy Single-Breasted Slim Fit Formal Blazer");
        products.put(2, "White & Navy Blue Slim Fit Printed Casual Shirt");
        products.put(3, "Red Slim Fit Checked Casual Shirt");
        products.put(4, "Navy Blue Washed Denim Jacket");
        return products;
    }
}