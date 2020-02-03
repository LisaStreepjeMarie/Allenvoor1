package com.wemakeitwork.allenvooreen.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@JsonPropertyOrder(value = {"id", "title", "bought"}, alphabetic = true)
public class GroceryItem {

    public GroceryItem() {
        this.bought = false;
    }

    @Id
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    Integer groceryItemId;

    @JsonProperty("title")
    String groceryName;

    @JsonProperty("bought")
    Boolean bought;

    @JsonIgnore
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

    public void setBought() {
        this.bought = !this.bought;
    }

    public GroceryList getGroceryList() {
        return groceryList;
    }

    public void setGroceryList(GroceryList groceryList) {
        this.groceryList = groceryList;
    }

}
