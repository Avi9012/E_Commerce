package com.eCommerce.MyECommerce.Domain.Models;

import com.eCommerce.MyECommerce.Domain.Constants.OrderStatus;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OrderModel {

    private UUID orderId;
    private UUID userId;
    private double totalAmount;
    private String status;
    private List<String> itemIds;
    private OffsetDateTime createdTime;
    private OffsetDateTime completionTime;
}
