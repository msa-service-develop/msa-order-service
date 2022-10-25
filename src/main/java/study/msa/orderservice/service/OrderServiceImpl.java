package study.msa.orderservice.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import study.msa.orderservice.domain.OrderEntity;
import study.msa.orderservice.dto.OrderDto;
import study.msa.orderservice.repository.OrderRepository;

import java.util.UUID;

@Data
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public ResponseEntity<OrderDto> createOrder(OrderDto orderDto) {
        orderDto.setUserId(UUID.randomUUID().toString());
        orderDto.setTotalPrice(orderDto.getQty() * orderDto.getUnitPrice());

        ModelMapper mp = new ModelMapper();
        mp.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        OrderEntity orderEntity = mp.map(orderDto, OrderEntity.class);
        orderRepository.save(orderEntity);

        OrderDto returnVal = mp.map(orderEntity, OrderDto.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(returnVal);
    }

    @Override
    public OrderDto getOrderByOrderId(String orderId) {
        OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
        OrderDto orderDto = new ModelMapper().map(orderEntity, OrderDto.class);

        return orderDto;
    }

    @Override
    public Iterable<OrderEntity> getOrderByUserId(String userId) {
        return orderRepository.findByUserId(userId);
    }
}
