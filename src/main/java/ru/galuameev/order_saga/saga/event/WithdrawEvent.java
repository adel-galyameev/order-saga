package ru.galuameev.order_saga.saga.event;

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
public class WithdrawEvent {

    private UUID userId;

    private UUID orderId;

    private BigDecimal price;

    private boolean isSuccesses;
}
