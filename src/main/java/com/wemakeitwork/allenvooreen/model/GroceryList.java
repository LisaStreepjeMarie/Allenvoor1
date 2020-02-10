package com.wemakeitwork.allenvooreen.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIgnoreProperties({"team", "hibernateLazyInitializer"})
public class GroceryList {

    @Id
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Integer groceryListId;

    //TODO Team needs to be added including mapping

    @JsonProperty("medications")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "groceryList")
    private List<Medication> allMedicationOnGroceryList = new ArrayList<>();

    @JsonProperty("groceries")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "groceryList")
    private List<GroceryItem> allItemsOnGroceryList = new ArrayList<>();

    public Integer getGroceryListId() {
        return groceryListId;
    }

    public void setGroceryListId(Integer groceryListId) {
        this.groceryListId = groceryListId;
    }

    public List<GroceryItem> getAllItemsOnGroceryList() {
        return allItemsOnGroceryList;
    }

    public void setAllItemsOnGroceryList(List<GroceryItem> allItemsOnGroceryList) {
        this.allItemsOnGroceryList = allItemsOnGroceryList;
    }

    public List<Medication> getAllMedicationOnGroceryList() {
        return allMedicationOnGroceryList;
    }

    public void setAllMedicationOnGroceryList(List<Medication> allMedicationOnGroceryList) {
        this.allMedicationOnGroceryList = allMedicationOnGroceryList;
    }
}
