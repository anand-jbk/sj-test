package com.assessment.sj.utils;

import java.util.Comparator;

public class VersionComparator implements Comparator<String> {

    public static final String PARTS_SEPARATOR = "\\.";

    @Override
    public int compare(String firstVersion, String secondVersion) {
        String[] FirstVersionParts = firstVersion.split(PARTS_SEPARATOR);
        String[] secondVersionParts = secondVersion.split(PARTS_SEPARATOR);

        int numberOfPartsInShorterVersion = Math.min(FirstVersionParts.length, secondVersionParts.length);

        int i = 0;
        int partialResult = 0;
        while (i < numberOfPartsInShorterVersion){
            partialResult = Integer.compare(Integer.parseInt(FirstVersionParts[i]), Integer.parseInt(secondVersionParts[i]));
            if(partialResult != 0){
                break;
            }
            i++;
        }

        return partialResult != 0 ? partialResult : Integer.compare(FirstVersionParts.length, secondVersionParts.length);
    }
}
