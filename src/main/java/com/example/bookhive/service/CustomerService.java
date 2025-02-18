package com.example.bookhive.service;

import org.springframework.stereotype.Service;

import com.example.bookhive.entity.Customer;
import com.example.bookhive.repository.CustomerRepository;
import com.example.bookhive.service.dto.CustomerDto;
import com.example.bookhive.service.mapper.CustomerMapper;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    public CustomerDto create(CustomerDto customerDto) {
        Customer customer = customerMapper.toEntity(customerDto);
        customer = customerRepository.save(customer);
        return customerMapper.toDto(customer);
    }

    public CustomerDto update(CustomerDto customerDto) {
        if (customerDto.getId() == null || !customerRepository.existsById(customerDto.getId())) {
            throw new EntityNotFoundException("Customer not found: " + customerDto.getId());
        }
        Customer customer = customerMapper.toEntity(customerDto);
        customer = customerRepository.save(customer);
        return customerMapper.toDto(customer);
    }

    public List<CustomerDto> findAllCustomers() {
        return customerRepository
                .findAll()
                .stream()
                .map(customerMapper::toDto)
                .collect(Collectors.toList());
    }

    public CustomerDto findById(Long id) {
        Customer customer = customerRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found: " + id));
        return customerMapper.toDto(customer);
    }

    public void delete(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found: " + id));
        customerRepository.delete(customer);
    }
}
