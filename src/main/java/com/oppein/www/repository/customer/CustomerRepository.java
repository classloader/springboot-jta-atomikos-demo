package com.oppein.www.repository.customer;

import com.oppein.www.domain.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    List<Customer> findByName(String name);
}
