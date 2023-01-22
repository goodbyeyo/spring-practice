package com.study.spring.di.controller;

import com.study.spring.di.annotation.Controller;
import com.study.spring.di.annotation.Inject;
import com.study.spring.di.service.ItemService;

@Controller
public class ItemController {

    private final ItemService itemService;

    @Inject
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    public ItemService getItemService() {
        return itemService;
    }
}
