package study.msa.orderservice.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.msa.orderservice.domain.OrderEntity;
import study.msa.orderservice.dto.OrderDto;
import study.msa.orderservice.service.OrderService;
import study.msa.orderservice.vo.RequestOrder;
import study.msa.orderservice.vo.ResponseOrder;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/order-service")
public class OrderController {
    private final Environment env;
    private final OrderService orderService;

    @GetMapping("/health-check")
    public String status() {
        return String.format("it`s working in Order Service on Port %s"
                , env.getProperty("local.server.port"));
    }

    @PostMapping("/{userId}/orders")
    public ResponseEntity createOrder(@RequestBody RequestOrder requestOrder,
                                     @PathVariable("userId") String userId){

        ModelMapper mp = new ModelMapper();
        mp.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        OrderDto orderDto = mp.map(requestOrder, OrderDto.class);
        orderDto.setUserId(userId);

        return orderService.createOrder(orderDto);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<?> getOrder(@PathVariable("userId") String userId){

        Iterable<OrderEntity> orderEntities = orderService.getOrderByUserId(userId);
        List<ResponseOrder> resultList = new ArrayList<>();
        orderEntities.forEach(v -> {
            resultList.add(new ModelMapper().map(v, ResponseOrder.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(resultList);
    }
}
