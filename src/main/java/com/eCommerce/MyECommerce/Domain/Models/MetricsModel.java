package com.eCommerce.MyECommerce.Domain.Models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MetricsModel {

    private Integer totalOrdersProcessed;
    private Double averageProcessingTime;
    private Integer pendingOrders;
    private Integer processingOrders;
    private Integer completedOrders;
}
