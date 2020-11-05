package edu.challangetwo.orderapi.api.dto;

import edu.challangetwo.orderapi.model.Item;

import java.util.List;

public class CreateOrderDto {

    private Long id;
    private List<ItemDto> items;
}