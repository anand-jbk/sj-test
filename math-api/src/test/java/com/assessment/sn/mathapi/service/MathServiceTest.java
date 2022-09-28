package com.assessment.sn.mathapi.service;

import com.assessment.sn.mathapi.Constant.ErrorMessage;
import com.assessment.sn.mathapi.Exception.UnprocessableRequestException;
import com.assessment.sn.mathapi.model.NumbersProcessingRequest;
import com.assessment.sn.mathapi.model.NumbersQuantifierRequest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static java.lang.Double.NaN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class MathServiceTest {

    private static final List<Double> LIST_OF_ONLY_VALID_NUMBERS = Collections.unmodifiableList(Arrays.asList(2d, 1d, 3d, 0.3, -0.2));
    private static final List<Double> LIST_OF_ONLY_INVALID_NUMBERS = Collections.unmodifiableList(Arrays.asList(NaN, null, NaN, null, null));

    @Autowired
    MathService objectUnderTest;

    @Nested
    class FindMinNumbersTestCases {
        @Test
        public void minNumbersCountIsSameAsQuantifier_whenQuantifierIsLessThenOrEqualToNumbersCount() {

            List<Double> numbers = LIST_OF_ONLY_VALID_NUMBERS;
            int quantifier = LIST_OF_ONLY_VALID_NUMBERS.size() - 1;
            NumbersQuantifierRequest request = new NumbersQuantifierRequest(numbers, quantifier);
            List<Double> minNumbers = objectUnderTest.findMinNumbers(numbers,quantifier);
            assertThat(minNumbers).hasSize(quantifier);
        }

        @Test
        public void minNumbersCountIsSameAsNumbersCount_whenQuantifierIsGreaterThenNumbersCount() {
            List<Double> numbers = LIST_OF_ONLY_VALID_NUMBERS;
            int quantifier = LIST_OF_ONLY_VALID_NUMBERS.size() + 100;
            NumbersQuantifierRequest request = new NumbersQuantifierRequest(numbers, quantifier);
            List<Double> minNumbers = objectUnderTest.findMinNumbers(numbers, quantifier);
            assertThat(minNumbers).hasSize(numbers.size());
        }

        @Test
        public void countOfMinNumbersIsSameAsValidNumbersCount_whenValidNumbersCountsIsLessThenQuantifier() {

            List<Double> number = new ArrayList<>(LIST_OF_ONLY_VALID_NUMBERS);
            number.addAll(LIST_OF_ONLY_INVALID_NUMBERS);
            int quantifier = LIST_OF_ONLY_VALID_NUMBERS.size() + 100;
            List<Double> minNumbers = objectUnderTest.findMinNumbers(number, quantifier);
            assertThat(minNumbers).hasSize(LIST_OF_ONLY_VALID_NUMBERS.size());
        }

        @Test
        public void minNumbersAreCorrect_whenValidNumbersArePassed() {

            List<Double> number = new ArrayList<>(LIST_OF_ONLY_VALID_NUMBERS);
            int quantifier = 2;
            List<Double> minNumbers = objectUnderTest.findMinNumbers(number, quantifier);
            assertThat(minNumbers).containsAll(Arrays.asList(-0.2, 0.3));
        }

        @Test
        public void minNumbersAreCorrect_whenValidAndInvalidNumbersArePassed() {

            List<Double> number = new ArrayList<>(LIST_OF_ONLY_VALID_NUMBERS);
            number.addAll(LIST_OF_ONLY_INVALID_NUMBERS);
            List<Double> minNumbers = objectUnderTest.findMinNumbers(number, number.size());
            assertThat(minNumbers).containsAll(LIST_OF_ONLY_VALID_NUMBERS);
        }

        @Test
        public void invalidNumberAreNotReturned_whenValidInvalidNumbersArePassed() {

            List<Double> number = new ArrayList<>(LIST_OF_ONLY_VALID_NUMBERS);
            number.addAll(LIST_OF_ONLY_INVALID_NUMBERS);
            List<Double> minNumbers = objectUnderTest.findMinNumbers(number, number.size());
            assertThat(minNumbers).noneMatch(aDouble -> aDouble.isNaN());
            assertThat(minNumbers).noneMatch(Objects::isNull);
        }

        @Test
        public void emptyListIsReturned_whenThereIsNoValidNumber() {

            List<Double> number = new ArrayList<>(LIST_OF_ONLY_INVALID_NUMBERS);
            List<Double> minNumbers = objectUnderTest.findMinNumbers(number, number.size());
            assertThat(minNumbers).isEmpty();
        }
    }

    @Nested
    class CalculateAverageTestCases {
        @Test
        public void averageIsCalculatedCorrectlyUsingOnlyValidValues_whenValidAndInvalidNumbersArePassed() {
            List<Double> numbers = new ArrayList<>(LIST_OF_ONLY_VALID_NUMBERS);
            numbers.addAll(LIST_OF_ONLY_INVALID_NUMBERS);
            Collections.shuffle(numbers);
            Double actualAverage = objectUnderTest.calculateAverage(numbers);
            assertThat(actualAverage).isEqualTo(1.22);
        }

        @Test()
        public void exceptionIsThrown_whenOnlyInvalidNumbersArePassed() {

            UnprocessableRequestException exception = assertThrows(UnprocessableRequestException.class, () -> {
                List<Double> numbers = new ArrayList<>(LIST_OF_ONLY_INVALID_NUMBERS);
                objectUnderTest.calculateAverage(numbers);
            });
            assertThat(exception.getMessage()).isEqualTo(ErrorMessage.AVERAGE_CAN_NOT_BE_CALCULATED);
            assertThat(exception.getDetails()).containsOnly(ErrorMessage.MINIMUM_ONE_VALID_NON_NULL_NUMBER_IS_REQUIRED);
        }
    }
}