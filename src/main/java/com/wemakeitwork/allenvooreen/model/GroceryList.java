package com.wemakeitwork.allenvooreen.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@JsonIgnoreProperties({ "groceryListId", "team", "allItemsOnGroceryList", "hibernateLazyInitializer", "allMedicationOnGroceryList"})
public class GroceryList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Integer groceryListId;

    //TODO Team needs to be added including mapping

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "groceryList")
    private List<Medication> allMedicationOnGroceryList;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "groceryList")
    private List<GroceryItem> allItemsOnGroceryList;

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
