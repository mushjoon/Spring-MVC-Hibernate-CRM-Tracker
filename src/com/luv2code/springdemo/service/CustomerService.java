package com.luv2code.springdemo.service;

import com.luv2code.springdemo.entity.Customer;

import java.util.List;

public interface CustomerService {

    public List<Customer> getCustomers(int theSortField);

    public void saveCustomer(Customer theCustomer);

    public Customer getCustomer(int theId);

    public void deleteCustomer(int theId);

    List<Customer> searchCustomers(String theSearchName);
}
