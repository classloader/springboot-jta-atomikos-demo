package com.oppein.www.service;

import com.oppein.www.domain.customer.Customer;
import com.oppein.www.domain.order.Order;
import com.oppein.www.exception.NoRollbackException;
import com.oppein.www.exception.StoreException;

public interface StoreService {
	
	void store(Customer customer, Order order) throws Exception;
	
	void storeWithStoreException(Customer customer, Order order) throws StoreException;
	
	void storeWithNoRollbackException(Customer customer, Order order) throws NoRollbackException;
	
	void transferWithStoreException() throws StoreException;
	void transferWithNoRollbackException() throws NoRollbackException;
	void transfer();

}
