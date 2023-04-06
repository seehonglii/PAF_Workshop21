package nus.edu.sg.iss.app.workshop21.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import nus.edu.sg.iss.app.workshop21.model.Customer;
import nus.edu.sg.iss.app.workshop21.model.Order;

import static nus.edu.sg.iss.app.workshop21.repository.DBQuery.*;

@Repository
public class CustomerRepo {

    @Autowired
    JdbcTemplate jdbcTemplate;

    /*
     * Fetch all customers
     */

    public List<Customer> getAllCustomer(Integer offset, Integer limit){
        List<Customer> csts = new ArrayList<>();
        SqlRowSet rs = jdbcTemplate.queryForRowSet(SELECT_ALL_CUSTOMERS, offset, limit);

        while (rs.next()){
            csts.add(Customer.create(rs));
        }
        return csts;
    }

    /*
     * Fetch customer using their ID
     */

    public Customer findCustomerById(Integer id){
        
        List<Customer> customer = jdbcTemplate.query(SELECT_CUSTOMER_BY_ID, new CustomerRowMapper(), new Object[]{id});
        return customer.get(0);
    }

    /*
     * Fetch order for customer
     */

    public List<Order> getCustomerOrders(Integer id){
        List<Order> orders = new ArrayList<Order>();
        SqlRowSet rs = jdbcTemplate.queryForRowSet(SELECT_ORDERS_FOR_CUSTOMERS, new Object[]{id});

        while(rs.next()){
            orders.add(Order.create(rs));
        }

        return orders;
    }
}
