package com.eCommerce.MyECommerce.Domain.Entities;

import com.eCommerce.MyECommerce.Domain.Constants.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID orderId;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "status")
    private String status;

    @Column(name = "item_ids")
    private String itemIds;

    @Column(name = "created_time")
    private OffsetDateTime createdTime;

    @Column(name = "completion_time")
    private OffsetDateTime completionTime;
}
