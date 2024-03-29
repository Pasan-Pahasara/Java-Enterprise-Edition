package servlet;

import dto.CustomerDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

/**
 * @author : Pasan Pahasara
 * @time : 6:53 PM
 * @date : 12/3/2022
 * @since : 0.1.0
 **/
@WebServlet(urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/JEPOS", "root", "1234");
            PreparedStatement pstm = connection.prepareStatement("select * from Customer");
            ResultSet rst = pstm.executeQuery();
            String jsonArray="[";
            while (rst.next()) {
                String id = rst.getString("id");
                String name = rst.getString("name");
                String address = rst.getString("address");
                double salary = rst.getDouble("salary");
                jsonArray+="{\"id\":\""+id+"\",\"name\":\""+name+"\",\"address\":\""+address+"\",\"salary\":"+salary+"},";
            }
            String finalArray = jsonArray.substring(0, jsonArray.length() - 1);
            finalArray+="]";
            resp.addHeader("Content-Type","application/json");
            resp.getWriter().write(finalArray);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String address = req.getParameter("address");
        String option = req.getParameter("option");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/JEPOS", "root", "1234");
            if (option.equals("add")) {
                double salary = Double.parseDouble(req.getParameter("salary"));
                PreparedStatement pstm = connection.prepareStatement("insert into Customer values(?,?,?,?)");
                pstm.setObject(1, id);
                pstm.setObject(2, name);
                pstm.setObject(3, address);
                pstm.setObject(4, salary);
                boolean b = pstm.executeUpdate() > 0;
                resp.setStatus(201);// data created
            } else if (option.equals("remove")) {
                PreparedStatement pstm = connection.prepareStatement("DELETE FROM customer WHERE id = ?");
                pstm.setObject(1, id);
                boolean b = pstm.executeUpdate() > 0;
            } else if (option.equals("update")) {
                double salary = Double.parseDouble(req.getParameter("salary"));
                PreparedStatement pstm = connection.prepareStatement("update Customer set name=?,address=?,salary=? where id=?");
                pstm.setObject(4, id);
                pstm.setObject(1, name);
                pstm.setObject(2, address);
                pstm.setObject(3, salary);
                boolean b = pstm.executeUpdate() > 0;
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
