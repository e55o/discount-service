package com.spring.discountservice.model.bill;

import java.util.ArrayList;
import java.util.UUID;

import com.spring.discountservice.model.user.User;
import com.spring.discountservice.model.item.Item;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Bill {
    private ArrayList<Item> items;
    private String id;
    private User user;

    /**
     * @param items
     * @param user
     */
    public Bill(ArrayList<Item> items, User user) {
        this.id = UUID.randomUUID().toString();
        this.items = items;
        this.user = user;
    }

    /**
     * This method calculates the percentage that should be reduced from the bill
     * @param price
     * @param bill
     * @return double
     */
    public static Double percentageDiscount(Double price, Bill bill) {
        switch(bill.getUser().getType()) {
            case CUSTOMER:
                price -= User.checkOldCustomer(bill.getUser().getCreatedAt(), price);
                break;
            case EMPLOYEE:
                price -= price * 0.3;
                break;
            case AFFILIATE:
                price -= price *0.1;
                break;
        }
        return price;
    }

    /**
     * this method returns the amount that should be paid in the end, after all reductions
     * @param total
     * @return double
     */
    public static double calculateAmount(double total) {
        return (int) (total / 100) * 5;
    }

}
