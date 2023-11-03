package com.example.prac4_client.DTO;
import com.example.prac4_client.model.Order;

import java.util.List;

public class OrderListWrapper {
    private List<Order> orders;
    public List<Order> getOrders(){
        return orders;
    }
    public void setOrders(List<Order> orders){
        this.orders = orders;
    }
}
