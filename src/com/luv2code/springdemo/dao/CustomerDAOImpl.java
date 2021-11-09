package com.luv2code.springdemo.dao;

import com.luv2code.springdemo.entity.Customer;
import com.luv2code.springdemo.util.SortUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Locale;

@Repository
public class CustomerDAOImpl implements CustomerDAO{

    // need to inject the session factory
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Customer> getCustomers(int theSortField) {

        // get the current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        // determine sort field
        String theFieldName = null;

        switch (theSortField) {
            case SortUtils.FIRST_NAME:
                theFieldName = "firstName";
                break;
            case SortUtils.LAST_NAME:
                theFieldName = "lastName";
                break;
            case SortUtils.EMAIL:
                theFieldName = "email";
                break;
            default:
                // if nothing matches the default to sort by lastName
                theFieldName = "lastName";
        }

        // create a query ... sort by last name
        String queryString = "FROM Customer ORDER BY " + theFieldName;
        Query<Customer> theQuery = currentSession.createQuery(queryString, Customer.class);

        // execute query and get result list
        List<Customer> customers = theQuery.getResultList();

        // return the result
        return customers;
    }

    @Override
    public void saveCustomer(Customer theCustomer) {

        // get current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();
        // save the customer
        currentSession.saveOrUpdate(theCustomer);

    }

    @Override
    public Customer getCustomer(int theId) {

        // get the current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        // now retrieve/read from database using the primary key
        Customer theCustomer = currentSession.get(Customer.class, theId);

        return theCustomer;
    }

    @Override
    public void deleteCustomer(int theId) {

        // get the current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        // delete object with primary key
        Query theQuery = currentSession.createQuery("DELETE FROM Customer WHERE id=:customerId");

        theQuery.setParameter("customerId", theId);

        theQuery.executeUpdate();
    }

    @Override
    public List<Customer> searchCustomers(String theSearchName) {

        // get the current session
        Session currentSession = sessionFactory.getCurrentSession();

        Query theQuery = null;

        // only search by name if theSearchName != empty
        if (theSearchName != null && theSearchName.trim().length() > 0) {
            // search for firstName or lastName ... case insensitive
            theQuery = currentSession.createQuery("FROM Customer WHERE LOWER(firstName) LIKE :theName OR LOWER(lastName) LIKE :theName", Customer.class);
            theQuery.setParameter("theName", "%" + theSearchName.toLowerCase() + "%");
        } else {
            // the searchName is empty ... so just get all customers
            theQuery = currentSession.createQuery("FROM Customer", Customer.class);
        }

        // execute query and get result list
        List<Customer> customers = theQuery.getResultList();

        return customers;
    }
}
