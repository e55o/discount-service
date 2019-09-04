package com.spring.discountservice.utils;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class YearUtil {

    /**
     * This method is used to calculate the year difference
     * @param createdAt
     * @return integer
     */
    public static final Integer yearDifference(Date createdAt) {
        LocalDate currentDate = LocalDate.now();
        LocalDate userAddedDate = createdAt.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        int yearDiff = Period.between(userAddedDate, currentDate).getYears();
        return yearDiff;
    }
}
