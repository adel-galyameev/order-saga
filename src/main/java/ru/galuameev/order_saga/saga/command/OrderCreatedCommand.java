package ru.galuameev.order_saga.saga.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedCommand {

    private UUID userId;

    private UUID orderId;

    private BigDecimal price;
}
