package com.assessment.sj.utils;

import java.util.Comparator;

public class VersionComparator implements Comparator<String> {

    public static final String PARTS_SEPARATOR = "\\.";

    @Override
    /**
     * To compare to version strings. This method assumes that strings are non-empty and contains only numbers and dots.
     * Result of comparison can be summarized as -> 0.1 < 0.2 < 1 < 1.0 < 1.0.0 < 1.0.0.1 < 1.2.9.9.9 < 1.3
     */
    public int compare(String firstVersion, String secondVersion) {
        String[] firstVersionParts = firstVersion.split(PARTS_SEPARATOR);
        String[] secondVersionParts = secondVersion.split(PARTS_SEPARATOR);

        int numberOfPartsInShorterVersion = Math.min(firstVersionParts.length, secondVersionParts.length);

        int i = 0;
        int partialResult = 0;
        while (i < numberOfPartsInShorterVersion){
            partialResult = Integer.compare(Integer.parseInt(firstVersionParts[i]), Integer.parseInt(secondVersionParts[i]));
            if(partialResult != 0){
                break;
            }
            i++;
        }

        return partialResult != 0 ? partialResult : Integer.compare(firstVersionParts.length, secondVersionParts.length);
    }
}
