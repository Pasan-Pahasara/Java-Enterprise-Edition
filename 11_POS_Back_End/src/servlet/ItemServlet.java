package servlet;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

/**
 * @author : ShEnUx
 * @time : 7:04 PM
 * @date : 12/18/2022
 * @since : 0.1.0
 **/

@WebServlet(urlPatterns = "/item")
public class ItemServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/JEPOS", "root", "1234");
            PreparedStatement pstm = connection.prepareStatement("select * from Item");
            ResultSet rst = pstm.executeQuery();

            JsonArrayBuilder allItems = Json.createArrayBuilder();
            while (rst.next()) {
                JsonObjectBuilder item = Json.createObjectBuilder();
                item.add("code", rst.getString("code"));
                item.add("description", rst.getString("description"));
                item.add("qtyOnHand", rst.getString("qtyOnHand"));
                item.add("unitPrice", rst.getDouble("unitPrice"));
                allItems.add(item.build());
            }
            JsonObjectBuilder load = Json.createObjectBuilder();
            load.add("state", "Ok");
            load.add("message", "Successfully Loaded..!");
            load.add("data", allItems.build());
            resp.getWriter().print(load.build());
        } catch (ClassNotFoundException | SQLException e) {
            JsonObjectBuilder rjoError = Json.createObjectBuilder();
            rjoError.add("state", "Error");
            rjoError.add("message", e.getLocalizedMessage());
            rjoError.add("data", "");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().print(rjoError.build());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/JEPOS", "root", "1234");
            PreparedStatement pstm = connection.prepareStatement("insert into Item values(?,?,?,?)");
            pstm.setObject(1, req.getParameter("code"));
            pstm.setObject(2, req.getParameter("description"));
            pstm.setObject(3, req.getParameter("qtyOnHand"));
            pstm.setObject(4, req.getParameter("unitPrice"));
            boolean b = pstm.executeUpdate() > 0;

            if (b) {
                JsonObjectBuilder responseObject = Json.createObjectBuilder();
                responseObject.add("state", "Ok");
                responseObject.add("message", "Successfully Added..!");
                responseObject.add("data", "");
                resp.getWriter().print(responseObject.build());
            }
        } catch (ClassNotFoundException e) {
            JsonObjectBuilder error = Json.createObjectBuilder();
            error.add("state", "Error");
            error.add("message", e.getLocalizedMessage());
            error.add("data", "");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);//status code 500 alternative
            resp.getWriter().print(error.build());
        } catch (SQLException e) {
            JsonObjectBuilder error = Json.createObjectBuilder();
            error.add("state", "Error");
            error.add("message", e.getLocalizedMessage());
            error.add("data", "");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);//status code 400 alternative
            resp.getWriter().print(error.build());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/JEPOS", "root", "1234");
            PreparedStatement pstm = connection.prepareStatement("delete from Item where code=?");
            pstm.setObject(1, req.getParameter("code"));
            boolean b = pstm.executeUpdate() > 0;
            if (b) {
                JsonObjectBuilder rjo = Json.createObjectBuilder();
                rjo.add("state", "Ok");
                rjo.add("message", "Successfully Deleted..!");
                rjo.add("data", "");
                resp.getWriter().print(rjo.build());
            } else {
                throw new RuntimeException("There is no such item for that ID..!");
            }
        } catch (RuntimeException e) {
            JsonObjectBuilder rjoError = Json.createObjectBuilder();
            rjoError.add("state", "Error");
            rjoError.add("message", e.getLocalizedMessage());
            rjoError.add("data", "");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().print(rjoError.build());
        } catch (ClassNotFoundException | SQLException e) {
            JsonObjectBuilder rjoError = Json.createObjectBuilder();
            rjoError.add("state", "Error");
            rjoError.add("message", e.getLocalizedMessage());
            rjoError.add("data", "");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);//status code 500 alternative
            resp.getWriter().print(rjoError.build());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObject item = Json.createReader(req.getReader()).readObject();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/JEPOS", "root", "1234");
            PreparedStatement pstm = connection.prepareStatement("update Item set description=?,qtyOnHand=?,unitPrice=? where code=?");
            pstm.setObject(4, item.getString("code"));
            pstm.setObject(1, item.getString("description"));
            pstm.setObject(2, item.getString("qtyOnHand"));
            pstm.setObject(3, item.getString("unitPrice"));
            boolean b = pstm.executeUpdate() > 0;
            if (b) {
                JsonObjectBuilder rjo = Json.createObjectBuilder();
                rjo.add("state", "Ok");
                rjo.add("message", "Successfully Updated..!");
                rjo.add("data", "");
                resp.getWriter().print(rjo.build());
            } else {
                throw new RuntimeException("Wrong ID, Please Check The ID..!");
            }
        } catch (RuntimeException e) {
            JsonObjectBuilder rjoError = Json.createObjectBuilder();
            rjoError.add("state", "Error");
            rjoError.add("message", e.getLocalizedMessage());
            rjoError.add("data", "");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);//status code 500 alternative
            resp.getWriter().print(rjoError.build());
        } catch (ClassNotFoundException | SQLException e) {
            JsonObjectBuilder rjoError = Json.createObjectBuilder();
            rjoError.add("state", "Error");
            rjoError.add("message", e.getLocalizedMessage());
            rjoError.add("data", "");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);//status code 500 alternative
            resp.getWriter().print(rjoError.build());
        }
    }
}
