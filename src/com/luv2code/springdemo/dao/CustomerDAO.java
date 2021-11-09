package com.luv2code.springdemo.dao;

import com.luv2code.springdemo.entity.Customer;

import java.util.List;

public interface CustomerDAO {

    public List<Customer> getCustomers(int theSortField);

    public void saveCustomer(Customer theCustomer);

    Customer getCustomer(int theId);

    public void deleteCustomer(int theId);

    List<Customer> searchCustomers(String theSearchName);
}
