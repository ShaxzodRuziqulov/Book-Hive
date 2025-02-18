package com.example.bookhive.web.rest;

import com.example.bookhive.service.OrderService;
import com.example.bookhive.service.dto.OrderDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/order")
@Validated
public class OrderResource {
    private final OrderService orderService;

    public OrderResource(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "Yangi buyurtma yaratish")
    @PostMapping("/create")
    public ResponseEntity<OrderDto> create(@Valid @RequestBody OrderDto orderDto) throws URISyntaxException {
        OrderDto result = orderService.create(orderDto);
        return ResponseEntity.created(new URI("/api/order/" + result.getId())).body(result);
    }

    @Operation(summary = "Buyurtmani yangilash")
    @PutMapping("/update/{id}")
    public ResponseEntity<OrderDto> update(@Valid @RequestBody OrderDto orderDto, @PathVariable Long id) {
        if (orderDto.getId() != null && !orderDto.getId().equals(id)) {
            return ResponseEntity.badRequest().body(null);
        }
        OrderDto result = orderService.update(orderDto);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Barcha buyurtmalarni olish")
    @GetMapping("/all")
    public ResponseEntity<List<OrderDto>> findAll() {
        List<OrderDto> orders = orderService.findAllOrders();
        return ResponseEntity.ok(orders);
    }

    @Operation(summary = "Buyurtmani ID bo‘yicha olish")
    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> findById(@PathVariable Long id) {
        OrderDto result = orderService.findById(id);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Buyurtmani o‘chirish")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        orderService.delete(id);
        return ResponseEntity.ok("Buyurtma muvaffaqiyatli o‘chirildi: " + id);
    }
}
