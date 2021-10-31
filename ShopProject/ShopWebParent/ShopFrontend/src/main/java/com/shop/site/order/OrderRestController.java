package com.shop.site.order;

import com.shop.site.Utility;
import com.shop.site.common.entity.Customer;
import com.shop.site.common.exception.CustomerNotFoundException;
import com.shop.site.common.exception.OrderNotFoundException;
import com.shop.site.customer.CustomerService;
import com.shop.site.shoppingcart.ShoppingCartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class OrderRestController {
    @Autowired
    private OrderService orderService;
    @Autowired
    CustomerService customerService;

    @PostMapping("/orders/return")
    public ResponseEntity<?> handleOrderReturnRequest(@RequestBody OrderReturnRequest returnRequest, HttpServletRequest servletRequest) {
        System.out.println("Order ID: "+ returnRequest.getOrderId());
        System.out.println("Reason: "+ returnRequest.getReason());
        System.out.println("Note: "+ returnRequest.getNote());

        Customer customer = null;
        try {
            customer = getAuthenticatedCustomer(servletRequest);
        } catch (CustomerNotFoundException e) {
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
        }
        try {
            orderService.setOrderReturnRequested(returnRequest, customer);
        } catch (OrderNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new OrderReturnResponse(returnRequest.getOrderId()), HttpStatus.OK);
    }

    private Customer getAuthenticatedCustomer(HttpServletRequest request) throws CustomerNotFoundException {
        String email = Utility.getEmailOfAuthenticatedCustomer(request);
        if (email == null) {
            throw new CustomerNotFoundException("No authenticated customer!");
        }
        return customerService.getCustomerByEmail(email);
    }
}
