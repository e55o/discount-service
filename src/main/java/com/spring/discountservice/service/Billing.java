package com.spring.discountservice.service;

import com.spring.discountservice.model.item.Item;
import com.spring.discountservice.model.bill.Bill;
import com.spring.discountservice.model.item.ItemCat;

import java.util.List;

public class Billing {

    /**
     * This is the main function of the program, it takes a bill Object and returns the total
     * amount that should be paid after discounts
     * @param bill
     * @return double
     */
    public Double billCalculator(Bill bill) {
        double totalSum, discount;
        double totalGroceries = 0;
        double totalOthers = 0;

        List<Item> items = bill.getItems();

        for(Item item: items) {
            if (item.getType() == ItemCat.GROCERIES) {
                totalGroceries += item.getPrice();
            } else {
                totalOthers += item.getPrice();
            }
        }

        discount = Bill.percentageDiscount(totalOthers, bill);
        totalSum = totalGroceries + discount;

        return totalSum - Bill.calculateAmount(totalSum);

    }
}
