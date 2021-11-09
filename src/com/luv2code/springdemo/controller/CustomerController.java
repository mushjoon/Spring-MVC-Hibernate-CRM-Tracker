package com.luv2code.springdemo.controller;

import com.luv2code.springdemo.dao.CustomerDAO;
import com.luv2code.springdemo.entity.Customer;
import com.luv2code.springdemo.service.CustomerService;
import com.luv2code.springdemo.util.SortUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/customer")
public class CustomerController {

    // need to inject our customer service
    @Autowired
    private CustomerService customerService;

    @GetMapping(value = "/list")
    public String listCustomers(Model theModel, @RequestParam(required = false) String sort) {

        // get customers from the service
        List<Customer> theCustomers = null;

        // check for sort field
        if (sort != null) {
            int theSortField = Integer.parseInt(sort);
            theCustomers = customerService.getCustomers(theSortField);
        } else {
            // no sort field provided ... default to sorting by last name
            theCustomers = customerService.getCustomers(SortUtils.LAST_NAME);
        }

        // add the customers to the model
        theModel.addAttribute("customers", theCustomers);

        return "list-customers";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel) {

        // create model attribute to bind form data
        Customer theCustomer = new Customer();

        theModel.addAttribute("customer", theCustomer);

        return "customer-form";
    }

    @PostMapping("/saveCustomer")
    public String saveCustomer(@ModelAttribute("customer") Customer theCustomer) {

        // save the customer using our service
        customerService.saveCustomer(theCustomer);

        return "redirect:/customer/list";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("customerId") int theId, Model theModel) {

        // get the customer from the DB
        Customer theCustomer = customerService.getCustomer(theId);
        // set customer as a model attribute to pre-populate the form
        theModel.addAttribute("customer", theCustomer);
        // send over to our form
        return "customer-form";
    }

    @GetMapping ("/delete")
    public String delete(@RequestParam("customerId") int theId) {

        // delete the customer
        customerService.deleteCustomer(theId);

        return "redirect:/customer/list";
    }

    @GetMapping("/search")
    public String search(@RequestParam("theSearchName") String theSearchName, Model theModel) {
        // search customers from the service
        List<Customer> theCustomers = customerService.searchCustomers(theSearchName);
        // add the customers to the model
        theModel.addAttribute("customers", theCustomers);

        return "list-customers";
    }
}