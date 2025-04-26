package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.Customer;
import model.CustomerDAO;

import java.io.IOException;

@WebServlet(name = "AddCustomerServlet", value = "/add-customer")
public class AddCustomerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // reading parameters from the request
        String lname = request.getParameter("lname");
        String fname = request.getParameter("fname");
        double balance = Double.parseDouble(request.getParameter("balance"));

        // instantiating the javabean to be given in input to doSave
        Customer customer = new Customer();
        customer.setFirstName(fname);
        customer.setLastName(lname);
        customer.setBalance(balance);

        // instantiating a Model class to interact with the db
        CustomerDAO service = new CustomerDAO();

        // invocating the Model service to store the "customer" in the db
        service.doSave(customer);

        //storing the javabean in the "request" object
        request.setAttribute("customer", customer);


        RequestDispatcher dispatcher =
                request.getRequestDispatcher("WEB-INF/results/db-insert-success.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
