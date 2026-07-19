package ru.galuameev.order_saga.saga.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CancelOrderCommand {

    private UUID userId;

    private UUID orderId;

    private BigDecimal price;
}
