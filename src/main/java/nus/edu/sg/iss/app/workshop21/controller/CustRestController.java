package nus.edu.sg.iss.app.workshop21.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import nus.edu.sg.iss.app.workshop21.model.Customer;
import nus.edu.sg.iss.app.workshop21.model.Order;
import nus.edu.sg.iss.app.workshop21.repository.CustomerRepo;

@RestController
@RequestMapping(path="/api/customers", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustRestController {

    @Autowired
    CustomerRepo customerRepo;

    @GetMapping()
    public ResponseEntity<String> getAllCustomer(@RequestParam(required = false) String limit, @RequestParam(required = false) String offset){
        if(Objects.isNull(limit)) limit = "5";
        if(Objects.isNull(offset)) offset = "0";

        List<Customer> customers = customerRepo.getAllCustomer(Integer.parseInt(offset), Integer.parseInt(limit));
        
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for(Customer c : customers) {
            arrayBuilder.add(c.toJson());
        }
        JsonArray result = arrayBuilder.build();
        
        return ResponseEntity.status(HttpStatus.OK)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(result.toString());
    }
    
    @GetMapping(path = "{customerId}")
    public ResponseEntity<String> getCustomerById(@PathVariable Integer customerId){
        
        JsonObject result = null;

        try{        
            Customer customer = customerRepo.findCustomerById(customerId);
            JsonObjectBuilder OjbBuilder = Json.createObjectBuilder();
            OjbBuilder.add("customer", customer.toJson());
            result = OjbBuilder.build();
        } catch(IndexOutOfBoundsException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body("{\"error message\":\"record not found\"}");
        }

        return ResponseEntity.status(HttpStatus.OK)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(result.toString());
    }

    /*
     * Fetch order for customer
     */
    @GetMapping(path="{customer_id}/orders")
    public ResponseEntity<String> getOrderForCustomer(@PathVariable Integer customer_id){
        List<Order> orders = new ArrayList<Order>();
        JsonArray result = null;

        orders = customerRepo.getCustomerOrders(customer_id);
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        for(Order o : orders){
            arrayBuilder.add(o.toJson());
        }

        result = arrayBuilder.build();
        if(result.size() == 0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .body("{\"error message\":\"record not found\"}");
        }
        return ResponseEntity.status(HttpStatus.OK)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(result.toString());
    }
}
