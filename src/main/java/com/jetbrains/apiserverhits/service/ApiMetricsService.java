package com.jetbrains.apiserverhits.service;


import com.jetbrains.apiserverhits.model.ApiMetrics;
import com.jetbrains.apiserverhits.repository.ApiMetricsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiMetricsService {

    @Autowired
    private ApiMetricsRepository repository;

    public List<ApiMetrics> getAllMetrics() {
        return repository.findAll();
    }

    public ApiMetrics getMetricsByApiName(String apiName) {
        return repository.findById(apiName).orElse(null);
    }

    public void updateMetrics(ApiMetrics metrics) {
        repository.save(metrics);
    }

    public Integer getTotalHits(String apiName) {
        return repository.findTotalHitsByApiName(apiName);
    }

    public Integer getSuccessfulHits(String apiName) {
        return repository.findSuccessfulHitsByApiName(apiName);
    }

    public Integer getFailedHits(String apiName) {
        return repository.findFailedHitsByApiName(apiName);
    }
}
