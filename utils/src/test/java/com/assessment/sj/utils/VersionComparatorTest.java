package com.assessment.sj.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class VersionComparatorTest {

    VersionComparator objectUnderTest = new VersionComparator();

    @ParameterizedTest(name = "Version {0}, {1} are same.")
    @CsvSource ({"1.1,1.1", "0.1,0.1", "1.2.9.9.9.9,1.2.9.9.9.9"})
    void whenVersionsAreSame_thenComparatorReturnsCorrectValue(String version1, String version2) {
        Assertions.assertEquals(0,objectUnderTest.compare(version1,version2));
    }

    @ParameterizedTest(name = "Version {0} is greater then {1}.")
    @CsvSource ({"1.2,1.1", "2.1,1.0", "1.0,1", "1,0"})
    void whenFirstVersionIsGreater_thenComparatorReturnsCorrectValue(String version1, String version2) {
        Assertions.assertEquals(1,objectUnderTest.compare(version1,version2));
    }

    @ParameterizedTest(name = "Version {0} is smaller then {1}.")
    @CsvSource ({"1.0,1.1", "0.9,1.0", "1,1.0", "0,1", "1,1.0", "1.0, 1.0.0"})
    void whenSecondVersionIsGreater_thenComparatorReturnsCorrectValue(String version1, String version2) {
        Assertions.assertEquals(-1,objectUnderTest.compare(version1,version2));
    }
}