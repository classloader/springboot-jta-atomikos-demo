package com.at.mul;

import javax.transaction.Transactional;

import com.oppein.www.domain.order.Order;
import com.oppein.www.repository.order.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.oppein.www.MainConfig;
import com.oppein.www.domain.customer.Customer;
import com.oppein.www.repository.customer.CustomerRepository;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MainConfig.class)
//@Transactional
@TransactionConfiguration(transactionManager = "transactionManager")
public class CustomerRepositoryTest {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private OrderRepository orderRepository;


	@Test
	public void testQuery(){
		List li = customerRepository.findAll();
		int i = li.size();
		List<Customer> li1 =  customerRepository.findByName("test-name");

		for (Customer co:li1) {
			System.out.println(co);
		}
	}

	@Test
	@Transactional
	@Rollback(false)
	public void save() {
		Customer c = new Customer();
		c.setName("test-name");
		c.setAge(30000);
		Customer cust = customerRepository.save(c);


		Order o = new Order();
		o.setCode(51);
		o.setQuantity(101);
		Order or = orderRepository.save(o);


	}

}
