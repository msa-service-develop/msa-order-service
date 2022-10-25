package study.msa.orderservice.service;


import org.springframework.http.ResponseEntity;
import study.msa.orderservice.domain.OrderEntity;
import study.msa.orderservice.dto.OrderDto;

public interface OrderService {
    ResponseEntity<OrderDto> createOrder(OrderDto orderDetails);
    OrderDto getOrderByOrderId(String orderId);
    Iterable<OrderEntity> getOrderByUserId(String userId);
}
