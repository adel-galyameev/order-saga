package ru.galuameev.order_saga.saga.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReserveProductsEvent {

    private UUID userId;

    private UUID orderId;

    private UUID productId;

    private Integer productQuantity;

    private boolean isSuccesses;
}
