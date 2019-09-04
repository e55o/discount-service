package com.spring.discountservice.model.item;

import com.spring.discountservice.model.item.ItemCat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
public class Item {
    private String id;
    private String name;
    private ItemCat type;
    private Double price;

    /**
     * @param name
     * @param type
     * @param price
     */
    public Item(String name, ItemCat type, Double price) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.type = type;
        this.price = price;
    }

}
