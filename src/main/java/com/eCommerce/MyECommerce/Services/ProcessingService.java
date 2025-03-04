package com.eCommerce.MyECommerce.Services;

import com.eCommerce.MyECommerce.Domain.Constants.OrderStatus;
import com.eCommerce.MyECommerce.Domain.Entities.OrderEntity;
import com.eCommerce.MyECommerce.Repositories.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.time.OffsetDateTime;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

@Service
@Slf4j
public class ProcessingService {

    private final BlockingDeque<OrderEntity> pendingQueue;
    private final BlockingDeque<OrderEntity> processingQueue;
    private final OrderRepository orderRepository;

    public ProcessingService(OrderRepository orderRepository) {

        this.pendingQueue = new LinkedBlockingDeque<>();
        this.processingQueue = new LinkedBlockingDeque<>();
        this.orderRepository = orderRepository;
    }

    public int getProcessingOrders() {
        return processingQueue.size();
    }

    public int getPendingOrders() {
        return pendingQueue.size();
    }

    public void addPendingOrder(OrderEntity orderEntity) {
        this.pendingQueue.add(orderEntity);
    }

    @Async
    public void processOrder() {

        try {
            while(true) {
                OrderEntity orderEntity = pendingQueue.take();
                log.info("ProcessingService::processOrder: Processing order: {}", orderEntity.getOrderId());
                orderEntity.setStatus(OrderStatus.PROCESSING.toString());
                orderEntity = orderRepository.save(orderEntity);
                processingQueue.add(orderEntity);
                int randomDelay = (int) (Math.random() * 10000) + 20000;
                log.info("ProcessingService::processOrder: Sleeping processor thread for [{}] seconds", (double) randomDelay / 1000);
                Thread.sleep(randomDelay);
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            log.error("ProcessingService::processOrder: Exception: {} occurred while processing an order", ex.getMessage());
        }
    }

    @Async
    public void completeOrder() {

        try {
            while(true) {
                OrderEntity orderEntity = processingQueue.take();
                log.info("ProcessingService::completeOrder: Completing order: {}", orderEntity.getOrderId());
                orderEntity.setStatus(OrderStatus.COMPLETED.toString());
                orderEntity.setCompletionTime(OffsetDateTime.now());
                orderRepository.save(orderEntity);
                int randomDelay = (int) (Math.random() * 10000) + 25000;
                log.info("ProcessingService::completeOrder: Sleeping processor thread for [{}] seconds", (double) randomDelay / 1000);
                Thread.sleep(randomDelay);
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            log.error("ProcessingService::completeOrder: Exception: {} occurred while completing an order", ex.getMessage());
        }
    }
}
