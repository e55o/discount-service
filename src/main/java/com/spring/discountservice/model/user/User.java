package com.spring.discountservice.model.user;

import com.spring.discountservice.utils.YearUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class User {
    private String id;
    private String name;
    private UserType type;
    private Date createdAt;

    /**
     * @param name
     * @param type
     * @param createdAt
     */
    public User(String name, UserType type, Date createdAt) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.type = type;
        this.createdAt = createdAt;
    }

    /**
     * This method is used to check if the customer is old, if so, he will get 5% discount on his bill
     * @param createdAt
     * @param price
     * @return double
     */
    public static Double checkOldCustomer(Date createdAt, Double price) {
        int yearDiff = YearUtil.yearDifference(createdAt);
        if(yearDiff >= 2) {
            price = price * 0.05;
            return price;
        } else {
            return 0.0;
        }
    }
}

