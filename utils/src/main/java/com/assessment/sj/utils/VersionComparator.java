package com.assessment.sj.utils;

import java.util.Comparator;

public class VersionComparator implements Comparator<String> {

    public static final String PARTS_SEPARATOR = "\\.";

    @Override
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
