package com.eCommerce.MyECommerce.Services;

import com.eCommerce.MyECommerce.Domain.Constants.OrderStatus;
import com.eCommerce.MyECommerce.Domain.Entities.OrderEntity;
import com.eCommerce.MyECommerce.Domain.Models.MetricsModel;
import com.eCommerce.MyECommerce.Domain.Models.OrderModel;
import com.eCommerce.MyECommerce.Exceptions.NotEmptyException;
import com.eCommerce.MyECommerce.Exceptions.UserIdException;
import com.eCommerce.MyECommerce.Exceptions.InvalidOrderAmountException;
import com.eCommerce.MyECommerce.Mappers.OrderMapper;
import com.eCommerce.MyECommerce.Repositories.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProcessingService processingService;
    private final OrderMapper orderMapper = Mappers.getMapper(OrderMapper.class);

    @Autowired
    public OrderService(OrderRepository orderRepository, ProcessingService processingService) {

        this.orderRepository = orderRepository;
        this.processingService = processingService;
        processingService.processOrder();
        processingService.completeOrder();
    }

    public OrderModel placeOrder(OrderModel orderModel) {

        validateOrder(orderModel);
        OrderEntity orderEntity = orderMapper.mapToOrderEntity(orderModel);
        orderEntity.setCreatedTime(OffsetDateTime.now());
        orderEntity.setStatus(OrderStatus.PENDING.toString());
        orderEntity = orderRepository.save(orderEntity);
        processingService.addPendingOrder(orderEntity);
        return orderMapper.mapToOrderModel(orderEntity);
    }

    public String getOrderStatus(UUID orderId) {

        OrderEntity orderEntity = orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new NotEmptyException(String.format("No Order found with this order id: [%s]", orderId.toString())));
        return orderEntity.getStatus();
    }

    public MetricsModel getMetrics() {

        AtomicInteger totalProcessingTime = new AtomicInteger();
        List<OrderEntity> completedOrders = orderRepository.getAllOrdersByStatus(OrderStatus.COMPLETED.toString());
        completedOrders.forEach(orderEntity -> totalProcessingTime.addAndGet((int) Duration.between(orderEntity.getCreatedTime(), orderEntity.getCompletionTime()).getSeconds()));
        return MetricsModel.builder()
                .totalOrdersProcessed(completedOrders.size())
                .averageProcessingTime(!completedOrders.isEmpty() ? (double) totalProcessingTime.get() / completedOrders.size() : null)
                .pendingOrders(processingService.getPendingOrders())
                .processingOrders(processingService.getProcessingOrders())
                .completedOrders(completedOrders.size())
                .build();
    }

    private void validateOrder(OrderModel orderModel) {

        if (orderModel.getUserId() == null) {
            throw new UserIdException("userId must be a valid UUID");
        }
        if (orderModel.getItemIds().isEmpty()) {
            throw new NotEmptyException("Items list can not be empty");
        }
        if (orderModel.getTotalAmount() <= 0.0) {
            throw new InvalidOrderAmountException("Order amount must be a positive number");
        }
    }
}
