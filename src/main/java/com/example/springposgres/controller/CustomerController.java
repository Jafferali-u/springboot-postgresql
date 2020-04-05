package com.example.springposgres.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.springposgres.model.Customer;
import com.example.springposgres.model.CustomerUI;
import com.example.springposgres.repository.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
 
@RestController
public class CustomerController {
	@Autowired
	CustomerRepository repository;
	
	@GetMapping("/bulkcreate")
	public String bulkcreate(){
		// save a single Customer
		repository.save(new Customer("AA", "AAA"));
		
		// save a list of Customers
        repository.saveAll(Arrays.asList(new Customer("BBBB", "CCC")
                       , new Customer("Jaffer", "Ali")
		       , new Customer("A123", "B1234")
		       , new Customer("A456", "B")
			, new Customer("A4567", "B")
			, new Customer("IT WORKING", "WORKING")
                       , new Customer("jaffer", "Jaffer")));
		
		return "Customers are created";
	}
	
	@GetMapping("/test")
	public String test(){
		
		
		return "Welcome to my project version 37\n This is autoscale Testing  ";
	}
	
	@PostMapping("/create")
	public String create(@RequestBody CustomerUI customer){
		// save a single Customer
		repository.save(new Customer(customer.getFirstName(), customer.getLastName()));

		return "Customer is created";
	}
	@GetMapping("/findall")
	public List<CustomerUI> findAll(){

		List<Customer> customers = repository.findAll();
		List<CustomerUI> customerUI = new ArrayList<>();
		
		for (Customer customer : customers) {
			customerUI.add(new CustomerUI(customer.getFirstName(),customer.getLastName()));
		}

		return customerUI;
	}
	
	@RequestMapping("/search/{id}")
	public String search(@PathVariable long id){
		String customer = "";
		customer = repository.findById(id).toString();
		return customer;
	}
	
	@RequestMapping("/searchbyfirstname/{firstname}")
	public List<CustomerUI> fetchDataByLastName(@PathVariable String firstname){
	
		List<Customer> customers = repository.findByFirstName(firstname);
		List<CustomerUI> customerUI = new ArrayList<>();
		
		for (Customer customer : customers) {
			customerUI.add(new CustomerUI(customer.getFirstName(),customer.getLastName()));
		}

		return customerUI;
	}
}
