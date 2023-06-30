package com.mockcompany.webapp.service;

import com.mockcompany.webapp.data.ProductItemRepository;
import com.mockcompany.webapp.model.ProductItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class SearchService {

    private final ProductItemRepository productItemRepository;

    @Autowired
    public SearchService(ProductItemRepository productItemRepository) {
        this.productItemRepository = productItemRepository;
    }

    public Collection<ProductItem> search(String query) {
       
        Iterable<ProductItem> allItems = this.productItemRepository.findAll();
        List<ProductItem> itemList = new ArrayList<>();

        boolean exactMatch = false;
        if (query.startsWith("\"") && query.endsWith("\"")) {
            exactMatch = true;
            query = query.substring(1, query.length() - 1);
        } else {
            query = query.toLowerCase();
        }

        for (ProductItem item : allItems) {
            boolean nameMatches;
            boolean descMatches;
            if (exactMatch) {
                nameMatches = query.equals(item.getName());
                descMatches = query.equals(item.getDescription());
            } else {
                
                nameMatches = item.getName().toLowerCase().contains(query);
                descMatches = item.getDescription().toLowerCase().contains(query);
            }

            if (nameMatches || descMatches) {
                itemList.add(item);
            }
        }
        return itemList;
    }
}
