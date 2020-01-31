package com.wemakeitwork.allenvooreen.repository;

import com.wemakeitwork.allenvooreen.model.GroceryItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroceryItemRepository extends JpaRepository <GroceryItem, Integer> {
}
