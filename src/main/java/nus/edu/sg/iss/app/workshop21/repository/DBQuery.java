package nus.edu.sg.iss.app.workshop21.repository;

public class DBQuery {
    public static String SELECT_ALL_CUSTOMERS = "select id, company, last_name, first_name from Customer limit ?, ?";
    public static String SELECT_CUSTOMER_BY_ID = "select id, company, last_name, first_name from Customer where id=?";
    public static String SELECT_ORDERS_FOR_CUSTOMERS = "select c.id as customer_id, c.company, o.id as order_id, o.ship_name, o.shipping_fee from customers c, orders o where c.id = o.customer_id and customer_id = ?";
}
