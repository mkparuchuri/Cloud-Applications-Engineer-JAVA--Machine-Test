package com.freshlms.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "item")
public class ItemEntity
{

    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long itemId = 0l;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity orderEntity;

    @Column(name = "order_id", insertable = false, updatable = false)
    private Long orderId;

    @Column(name = "item_name")
    private String itemName = "";

    @Column(name = "item_unit_price")
    private Long itemUnitPrice;

    @Column(name = "item_quantity")
    private int itemQuantity;
}
