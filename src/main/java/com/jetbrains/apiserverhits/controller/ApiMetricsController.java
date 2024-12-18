package com.jetbrains.apiserverhits.controller;

import com.jetbrains.apiserverhits.model.ApiMetrics;
import com.jetbrains.apiserverhits.service.ApiMetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/metrics")
public class ApiMetricsController {

    @Autowired
    private ApiMetricsService service;

    @GetMapping
    public List<ApiMetrics> getAllMetrics() {
        return service.getAllMetrics();
    }

    @GetMapping("/{apiName}")
    public ApiMetrics getMetricsByApiName(@PathVariable String apiName) {
        return service.getMetricsByApiName(apiName);
    }

    @PostMapping
    public void updateMetrics(@RequestBody ApiMetrics metrics) {
        service.updateMetrics(metrics);
    }

    @GetMapping("/{apiName}/total-hits")
    public Integer getTotalHits(@PathVariable String apiName) {
        return service.getTotalHits(apiName);
    }

    @GetMapping("/{apiName}/successful-hits")
    public Integer getSuccessfulHits(@PathVariable String apiName) {
        return service.getSuccessfulHits(apiName);
    }

    @GetMapping("/{apiName}/failed-hits")
    public Integer getFailedHits(@PathVariable String apiName) {
        return service.getFailedHits(apiName);
    }
}
