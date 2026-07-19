# Saga Orchestrator — Схема взаимодействия

## Используемый паттерн: Orchestrator Saga (Сага-оркестратор)

В отличие от хореографии, где сервисы общаются напрямую друг с другом, здесь выделен **центральный координатор** — `OrderSagaOrchestrator` в сервисе `order-saga`. Он управляет всеми шагами распределённой транзакции: получает события от участников и принимает решение о следующем шаге или запуске компенсирующих транзакций при сбое.

Команды и события передаются через **Apache Kafka** (асинхронное сообщение).

---

## Схема взаимодействия (Sequence Diagram)
![Схема взаимодействия](./SequenceDiagram.png)


---

## Статусы заказа (Order Status Flow)

```
Успешный сценарий:
  CREATED → PROCESSED → PAID → COMPLETED

Сценарии с ошибкой:
  CREATED → PROCESSED → PAID → REJECT          (не хватило товара / курьера → возврат средств → REFUNDED)
  CREATED → PROCESSED → REJECT                  (не хватило денег)
  CREATED → REJECT                               (ошибка отправки в Kafka)
```

---

## Kafka Topics

| Топик | Тип | Назначение |
|-------|-----|------------|
| `order-created` | Command | Оркестратор → Billing: команда на списание |
| `billing-event` | Event | Billing → Оркестратор: результат списания |
| `product-command` | Command | Оркестратор → Warehouse: команда на резервацию |
| `product-event` | Event | Warehouse → Оркестратор: результат резервации |
| `reserve-courier-command` | Command | Оркестратор → Delivery: команда на поиск курьера |
| `reserve-courier-event` | Event | Delivery → Оркестратор: результат бронирования |
| `unreserve-product-command` | Command | Оркестратор → Warehouse: команда отмены резервации (компенсация) |
| `unreserve-product-event` | Event | Warehouse → Оркестратор: результат отмены резервации |
| `cancel-order-command` | Command | Оркестратор → Billing: команда на возврат средств (компенсация) |
| `cancel-order-event` | Event | Billing → Оркестратор: результат возврата |

---

## Участники

| Сервис | Порт | БД | Ответственность |
|--------|------|----|-----------------|
| **order-saga** | 8080 | `orders` | Оркестратор, управление заказами, хранение истории статусов |
| **billing-saga** | 8081 | `billing` | Списание и возврат денежных средств пользователя |
| **warehouse-saga** | 8082 | `warehouse` | Резервация и отмена резервации товаров на складе |
| **delivery-saga** | 8084 | `delivery` | Поиск и бронирование свободного курьера |

---

## Компенсирующие транзакции (Compensating Transactions)

При отказе на любом шаге оркестратор запускает цепочку отката:

| Шаг отказа | Компенсация |
|-----------|-------------|
| Резервация товара (`warehouse`) | `CancelOrderCommand` → возврат денег (`billing`) |
| Бронирование курьера (`delivery`) | `UnreserveProductsCommand` → разрезервация товара (`warehouse`) → `CancelOrderCommand` → возврат денег (`billing`) |
