package com.example.bookhive.service;

import com.example.bookhive.entity.Book;
import com.example.bookhive.entity.Customer;
import com.example.bookhive.repository.BookRepository;
import com.example.bookhive.repository.CustomerRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import com.example.bookhive.entity.Order;
import com.example.bookhive.repository.OrderRepository;
import com.example.bookhive.service.dto.OrderDto;
import com.example.bookhive.service.mapper.OrderMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final BookRepository bookRepository;
    private final CustomerRepository customerRepository;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper, BookRepository bookRepository, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.bookRepository = bookRepository;
        this.customerRepository = customerRepository;
    }

    public OrderDto create(OrderDto orderDto) {
        Order order = orderMapper.toEntity(orderDto);

        for (Book book : order.getBooks()) {
            if (book.getQuantityInStock() < 1) {
                throw new RuntimeException("Kitob stokda mavjud emas: " + book.getTitle());
            }
            book.setQuantityInStock(book.getQuantityInStock() - 1);
        }

        Customer customer = order.getCustomer();
        double totalAmount = order.getBooks().stream().mapToDouble(Book::getPrice).sum();
        customer.setTotalPurchasesAmount(customer.getTotalPurchasesAmount() + totalAmount);

        order = orderRepository.save(order);
        return orderMapper.toDto(order);
    }

    public OrderDto update(@Valid OrderDto orderDto) {
        if (orderDto.getId() == null) {
            throw new IllegalArgumentException("Order ID bo‘sh bo‘lishi mumkin emas");
        }

        Order existingOrder = orderRepository.findById(orderDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Buyurtma topilmadi: " + orderDto.getId()));

        Order updatedOrder = orderMapper.toEntity(orderDto);
        updatedOrder.setId(existingOrder.getId());
        updatedOrder = orderRepository.save(updatedOrder);

        return orderMapper.toDto(updatedOrder);
    }

    public List<OrderDto> findAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    public OrderDto findById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Buyurtma topilmadi: " + id));
        return orderMapper.toDto(order);
    }

    public void delete(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Buyurtma topilmadi: " + id));

        Customer customer = order.getCustomer();

        double totalOrderPrice = 0.0;
        for (Book book : order.getBooks()) {
            book.setQuantityInStock(book.getQuantityInStock() + 1);
            totalOrderPrice += book.getPrice();
        }

        customer.setTotalPurchasesAmount(customer.getTotalPurchasesAmount() - totalOrderPrice);

        orderRepository.delete(order);
    }
}


