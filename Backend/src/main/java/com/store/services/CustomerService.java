package com.store.services;

import com.store.models.Customer;
import com.store.repos.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository repo;

    @Autowired
    public CustomerService(CustomerRepository repo) {
        this.repo = repo;
    }

    public List<Customer> getAllCustomers() {
        return repo.findAll();
    }

    public Customer getCustomer(Long id) {
        return repo.findById(id).orElse(null);
    }

    public Customer createCustomer(Customer customer) {
        if (customer == null) return null;
        return repo.save(customer);
    }

    public Customer updateCustomer(Customer customer) {
        if (customer == null) return null;
        if (!repo.existsById(customer.getId())) return null;
        return repo.save(customer);
    }

    public boolean deleteCustomer(Long id) {
        Optional<Customer> customer = repo.findById(id);
        if (customer.isEmpty()) return false;
        repo.delete(customer.get());
        return true;
    }
}