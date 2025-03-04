package com.eCommerce.MyECommerce.Controllers;

import com.eCommerce.MyECommerce.Domain.Constants.OrderStatus;
import com.eCommerce.MyECommerce.Domain.Models.MetricsModel;
import com.eCommerce.MyECommerce.Domain.Models.OrderModel;
import com.eCommerce.MyECommerce.Services.OrderService;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/myECommerce")
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {

        this.orderService = orderService;
    }

    @PostMapping("/order")
    public ResponseEntity<OrderModel> createOrder(@RequestBody OrderModel orderModel) {

        return ResponseEntity.ok().body(orderService.placeOrder(orderModel));
    }

    @GetMapping("/orderStatus/{orderId}")
    public ResponseEntity<String> getOrderStatus(@PathVariable String orderId) {

        log.info("Order id: {}", orderId);
        return ResponseEntity.ok().body(orderService.getOrderStatus(UUID.fromString(orderId)));
    }

    @GetMapping("/metrics")
    public ResponseEntity<MetricsModel> getMetrics() {

        return ResponseEntity.ok().body(orderService.getMetrics());
    }
}
