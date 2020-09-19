package com.codegym.controller;

import com.codegym.model.Customer;
import com.codegym.model.CustomerForm;
import com.codegym.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.List;


@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private ICustomerService<Customer> customerService;

    @Autowired
    private Environment environment;

    @GetMapping()
    public ModelAndView listCustomers(){
        List<Customer> customerList = customerService.findAll();
        ModelAndView modelAndView = new ModelAndView("/customer/list");
        modelAndView.addObject("customers", customerList);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showCreate(){
        ModelAndView modelAndView = new ModelAndView("/customer/create");
        modelAndView.addObject("customerForm", new CustomerForm());
        return modelAndView;
    }

    @PostMapping("/create")
    public String saveCustomer( CustomerForm customerForm) {
        MultipartFile multipartFile = customerForm.getAvatar();
        String fileName = multipartFile.getOriginalFilename();
        String folderUpload = environment.getProperty("file_upload");
        String path = folderUpload + fileName;

        try {
            FileCopyUtils.copy(multipartFile.getBytes(), new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Customer customer = new Customer(customerForm.getFirstName(), customerForm.getLastName(), fileName);
        customerService.save(customer);
        return "redirect:/customers";
    }

}
