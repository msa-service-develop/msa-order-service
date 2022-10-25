package study.msa.orderservice.repository;

import org.springframework.data.repository.CrudRepository;
import study.msa.orderservice.domain.OrderEntity;

public interface OrderRepository extends CrudRepository<OrderEntity, Long> {
    OrderEntity findByOrderId(String orderId);
    Iterable<OrderEntity> findByUserId(String userId);
}
