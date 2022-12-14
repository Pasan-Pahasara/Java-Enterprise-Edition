package servlet;

import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

/**
 * @author : Pasan Pahasara
 * @time : 6:53 PM
 * @date : 12/3/2022
 * @since : 0.1.0
 **/
@WebServlet(urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {

    //Query String
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/JEPOS", "root", "1234");
            PreparedStatement pstm = connection.prepareStatement("select * from Customer");
            ResultSet rst = pstm.executeQuery();

            JsonArrayBuilder allCustomers = Json.createArrayBuilder();
            while (rst.next()) {
                JsonObjectBuilder customer = Json.createObjectBuilder();
                customer.add("id", rst.getString("id"));
                customer.add("name", rst.getString("name"));
                customer.add("address", rst.getString("address"));
                customer.add("salary", rst.getDouble("salary"));
                allCustomers.add(customer.build());
            }
            resp.addHeader("Content-Type", "application/json");
            resp.getWriter().print(allCustomers.build());
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Query String
    //Form Data (x-www-form-url-encoded)
    //JSON
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String address = req.getParameter("address");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/JEPOS", "root", "1234");
            double salary = Double.parseDouble(req.getParameter("salary"));
            PreparedStatement pstm = connection.prepareStatement("insert into Customer values(?,?,?,?)");
            pstm.setObject(1, id);
            pstm.setObject(2, name);
            pstm.setObject(3, address);
            pstm.setObject(4, salary);
            boolean b = pstm.executeUpdate() > 0;
            resp.setStatus(201);// data created
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Query String
    //JSON
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/JEPOS", "root", "1234");
            PreparedStatement pstm = connection.prepareStatement("DELETE FROM customer WHERE id = ?");
            pstm.setObject(1, id);
            boolean b = pstm.executeUpdate() > 0;
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Query String
    //JSON
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObject customer = Json.createReader(req.getReader()).readObject();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/JEPOS", "root", "1234");
            PreparedStatement pstm = connection.prepareStatement("update Customer set name=?,address=?,salary=? where id=?");
            pstm.setObject(4, customer.getString("id"));
            pstm.setObject(1, customer.getString("name"));
            pstm.setObject(2, customer.getString("address"));
            pstm.setObject(3, customer.getString("salary"));
            boolean b = pstm.executeUpdate() > 0;
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
