package com.dreamers.adapters;

import com.dreamers.entities.CalculationResult;
import com.dreamers.entities.Result;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class CalculationAdapter {

    public List<Result> getResultFromCalculation(List<CalculationResult> calculation) {

        Function<CalculationResult, List<Object>> keyExtractor = cr ->
                Arrays.<Object>asList(cr.getMeasurement().getFacilityId(), cr.getMeasurement().getRoomId(),
                        cr.getStuffId(), cr.getStuffName(), cr.isClean(), cr.getConsumption(), cr.getMeasureName(),
                        cr.getPackConsumption(), cr.getPackName());

        Set<Map.Entry<List<Object>, DoubleSummaryStatistics>> result = calculation.stream()
                .collect(Collectors.groupingBy(keyExtractor, Collectors.summarizingDouble(CalculationResult::getM2)))
                .entrySet();

        return result.stream().map(CalculationAdapter::createResult).collect(Collectors.toList());
    }

    public List<Result> getResultForFacilityFromCalculation(List<CalculationResult> calculation) {

        Function<CalculationResult, List<Object>> keyExtractor = cr ->
                Arrays.<Object>asList(cr.getMeasurement().getFacilityId(), null, cr.getStuffId(), cr.getStuffName(),
                        cr.isClean(), cr.getConsumption(), cr.getMeasureName(), cr.getPackConsumption(), cr.getPackName());

        Set<Map.Entry<List<Object>, DoubleSummaryStatistics>> result = calculation.stream()
                .collect(Collectors.groupingBy(keyExtractor, Collectors.summarizingDouble(CalculationResult::getM2)))
                .entrySet();

        return result.stream().map(CalculationAdapter::createResult).collect(Collectors.toList());
    }

    private static Result createResult(Map.Entry<List<Object>, DoubleSummaryStatistics> result) {
        List<Object> keys = result.getKey();
        int index = 0;
        Long facilityId = (Long) keys.get(index++);
        Long roomId = (Long) keys.get(index++);
        Long stuffId = (Long) keys.get(index++);
        String stuffName = (String) keys.get(index++);
        boolean isClean = (boolean) keys.get(index++);
        double consumption = (double) keys.get(index++);
        String measureName = (String) keys.get(index++);
        double packConsumption = (double) keys.get(index++);
        String packName = (String) keys.get(index++);
        return new Result(facilityId, roomId, stuffId, stuffName, isClean, consumption, measureName, packConsumption,
                packName, result.getValue().getSum());
    }
}
