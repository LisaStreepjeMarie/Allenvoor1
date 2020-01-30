package com.wemakeitwork.allenvooreen.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties({ "groceryItemId", "groceryName", "bought", "groceryList"})
public class GroceryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Integer groceryItemId;

    String groceryName;


    Boolean bought;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "grocerylistId", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    GroceryList groceryList;

    public Integer getGroceryItemId() {
        return groceryItemId;
    }

    public void setGroceryItemId(Integer groceryItemId) {
        this.groceryItemId = groceryItemId;
    }

    public String getGroceryName() {
        return groceryName;
    }

    public void setGroceryName(String groceryName) {
        this.groceryName = groceryName;
    }

    public Boolean getBought() {
        return bought;
    }

    public void setBought(Boolean bought) {
        this.bought = bought;
    }

    public GroceryList getGroceryList() {
        return groceryList;
    }

    public void setGroceryList(GroceryList groceryList) {
        this.groceryList = groceryList;
    }

}
