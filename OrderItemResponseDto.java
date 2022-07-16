package com.freshlms.core.model.business;

import java.util.ArrayList;
import java.util.List;

import com.freshlms.core.entity.ItemEntity;

import lombok.Data;

@Data
public class OrderItemResponseDto
{

    private Long id = 0l;

    private Long orderDate;

    private String orderStatus;

    private List<ItemEntity> items = new ArrayList<ItemEntity>();
}
