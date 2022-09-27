package com.assessment.sn.mathapi.service;

import com.assessment.sn.mathapi.Exception.UnprocessableRequestException;
import com.assessment.sn.mathapi.model.NumbersProcessingRequest;
import com.assessment.sn.mathapi.model.NumbersQuantifierRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.assessment.sn.mathapi.Constant.ErrorMessage.*;

@Service
public class MathService {

    private static Logger LOGGER = LoggerFactory.getLogger(MathService.class);

    public List<Double> findMinNumbers(NumbersQuantifierRequest request) {
        return request.getNumbers().parallelStream()
                .filter(Objects::nonNull)
                .filter(value -> !value.isNaN())
                .sorted()
                .limit(request.getQuantifier()).collect(Collectors.toList());
    }

    public List<Double> findMaxNumbers(NumbersQuantifierRequest request) {
        return request.getNumbers().parallelStream()
                .filter(Objects::nonNull)
                .filter(value -> !value.isNaN())
                .sorted(Comparator.reverseOrder())
                .limit(request.getQuantifier())
                .collect(Collectors.toList());
    }

    public Double calculateAverage(NumbersProcessingRequest request) {
        OptionalDouble average = request.getNumbers().parallelStream()
                .filter(Objects::nonNull)
                .filter(value -> !value.isNaN())
                .mapToDouble(value -> value)
                .average();

        return average.orElseThrow(() -> {
            LOGGER.error("Average can not be calculated as there is no valid value in numbers list.");
            throw new UnprocessableRequestException(AVERAGE_CAN_NOT_BE_CALCULATED,
                    Collections.singletonList(MINIMUM_ONE_VALID_NON_NULL_NUMBER_IS_REQUIRED));
        });
    }

    public double calculateMedian(NumbersProcessingRequest request) {
        List<Double> cleanedNumbers = request.getNumbers().parallelStream()
                .filter(Objects::nonNull)
                .filter(value -> !value.isNaN())
                .sorted()
                .collect(Collectors.toList());

        if(cleanedNumbers.isEmpty()){
            LOGGER.error("Median can not be calculated as there is no valid value in numbers list.");
            throw new UnprocessableRequestException(MEDIAN_CAN_NOT_BE_CALCULATED,
                    Collections.singletonList(MINIMUM_ONE_VALID_NON_NULL_NUMBER_IS_REQUIRED));
        }

        int midWayIndex = cleanedNumbers.size() / 2;
        return cleanedNumbers.size() % 2 == 1 ?
                cleanedNumbers.get(midWayIndex) :
                (cleanedNumbers.get(midWayIndex - 1) + cleanedNumbers.get(midWayIndex)) / 2 ;
    }

    public Double calculatePercentile(NumbersQuantifierRequest request) {
        List<Double> cleanedNumbers = request.getNumbers().parallelStream()
                .filter(Objects::nonNull)
                .filter(value -> !value.isNaN())
                .sorted()
                .collect(Collectors.toList());

        if(cleanedNumbers.isEmpty()){
            LOGGER.error("Percentile can not be calculated as there is no valid value in numbers list.");
            throw new UnprocessableRequestException("Percentile Can not be calculated",
                    Collections.singletonList(MINIMUM_ONE_VALID_NON_NULL_NUMBER_IS_REQUIRED));
        }

        int rank = getRank(request, cleanedNumbers);
        return cleanedNumbers.get(rank);
    }

    private static int getRank(NumbersQuantifierRequest request, List<Double> cleanedNumbers) {
        long numerator = (long) request.getQuantifier() * cleanedNumbers.size();
        int rank = numerator % 100 == 0 ? (int) ((numerator / 100) - 1) : Double.valueOf((float)numerator/100).intValue();
        return rank;
    }
}
