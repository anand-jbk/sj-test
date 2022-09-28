package com.assessment.sn.mathapi.service;

import com.assessment.sn.mathapi.Exception.UnprocessableRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.assessment.sn.mathapi.Constant.ErrorMessage.*;

@Service
public class MathService {

    private static Logger LOGGER = LoggerFactory.getLogger(MathService.class);


    /**
     * To get min numbers from {@link List} of numbers. This method only consider non-null and non-NaN numbers,
     * which may cause count of min-numbers returned to be less than quantifier passed.
     * Size of min-numbers list will be min of 'count of valid numbers' and quantifier.
     * @param numbers List to get min-numbers from
     * @param quantifier Count of min-numbers to be returned
     * @return {@link List} of min numbers
     */
    public List<Double> findMinNumbers(List<Double> numbers, Integer quantifier) {
        return numbers.parallelStream()
                .filter(Objects::nonNull)
                .filter(value -> !value.isNaN())
                .sorted()
                .limit(quantifier).collect(Collectors.toList());
    }

    /**
     * To get max numbers from {@link List} of numbers. This method only consider non-null and non-NaN numbers,
     * which may cause count of min-numbers returned to be less than quantifier passed.
     * Size of max-numbers list will be min of 'count of valid numbers' and quantifier.
     * @param numbers List to get max-numbers from
     * @param quantifier Count of max-numbers to be returned
     * @return {@link List} of max numbers
     */
    public List<Double> findMaxNumbers(List<Double> numbers, Integer quantifier) {
        return numbers.parallelStream()
                .filter(Objects::nonNull)
                .filter(value -> !value.isNaN())
                .sorted(Comparator.reverseOrder())
                .limit(quantifier)
                .collect(Collectors.toList());
    }

    /**
     * To calculate average of {@link List} of numbers. This method only consider non-null and non-NaN numbers for
     * average calculation.
     * @param numbers List to get average of
     * @return Average of given list of numbers
     */
    public Double calculateAverage(List<Double> numbers) {
        OptionalDouble average = numbers.parallelStream()
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

    /**
     * To calculate median {@link List} of numbers. This method only consider non-null and non-NaN numbers for
     * median calculation. If there is no non-null and non-NaN number in list {@link UnprocessableRequestException} exception is thrown.
     * @param numbers List to get median of
     * @return Median of given list of numbers
     */
    public double calculateMedian(List<Double> numbers) {
        List<Double> cleanedNumbers = numbers.parallelStream()
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

    /**
     * To calculate qth percentile {@link List} of numbers. This method only consider non-null and non-NaN numbers for
     * percentile calculation. If there is no non-null and non-NaN number in list {@link UnprocessableRequestException} exception is thrown.
     * @param numbers List to get percentile of
     * @param quantifier qth percentile
     * @return qth percentile of given list of numbers
     */
    public Double calculatePercentile(List<Double> numbers, Integer quantifier) {
        List<Double> cleanedNumbers = numbers.parallelStream()
                .filter(Objects::nonNull)
                .filter(value -> !value.isNaN())
                .sorted()
                .collect(Collectors.toList());

        if(cleanedNumbers.isEmpty()){
            LOGGER.error("Percentile can not be calculated as there is no valid value in numbers list.");
            throw new UnprocessableRequestException("Percentile Can not be calculated",
                    Collections.singletonList(MINIMUM_ONE_VALID_NON_NULL_NUMBER_IS_REQUIRED));
        }

        int rank = calculateRank(quantifier, cleanedNumbers);
        return cleanedNumbers.get(rank);
    }

    private static int calculateRank(Integer quantifier, List<Double> cleanedNumbers) {
        long numerator = (long) quantifier * cleanedNumbers.size();
        return numerator % 100 == 0 ? (int) ((numerator / 100) - 1) : Double.valueOf((float)numerator/100).intValue();
    }
}
