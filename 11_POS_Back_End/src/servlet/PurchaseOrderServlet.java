package servlet;

import javax.annotation.Resource;
import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author : ShEnUx
 * @time : 3:54 PM
 * @date : 12/23/2022
 * @since : 0.1.0
 **/
@WebServlet(urlPatterns = "/purchase")
public class PurchaseOrderServlet extends HttpServlet {

    @Resource(name = "java:comp/env/jdbc/pool")
    DataSource dataSource;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//        generateOrderID();
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        String oid = jsonObject.getString("oid");
        String cusId = jsonObject.getString("cusID");
        String date = jsonObject.getString("date");

        try (Connection connection = dataSource.getConnection()) {

            connection.setAutoCommit(false);

            PreparedStatement pstm = connection.prepareStatement("insert into Orders values(?,?,?)");
            pstm.setObject(1, oid);
            pstm.setObject(2, date);
            pstm.setObject(3, cusId);
            if (!(pstm.executeUpdate() > 0)) {
                connection.rollback();
                connection.setAutoCommit(true);
                throw new RuntimeException("Order Issue");
            } else {
                JsonArray orderDetails = jsonObject.getJsonArray("orderDetails");
                for (JsonValue orderDetail : orderDetails) {
                    String code = orderDetail.asJsonObject().getString("code");
                    String qty = orderDetail.asJsonObject().getString("qty");
                    String price = orderDetail.asJsonObject().getString("price");

                    PreparedStatement pstms = connection.prepareStatement("insert into OrderDetails values(?,?,?,?)");
                    pstms.setObject(1, oid);
                    pstms.setObject(2, code);
                    pstms.setObject(3, qty);
                    pstms.setObject(4, price);
                    if (!(pstms.executeUpdate() > 0)) {
                        connection.rollback();
                        connection.setAutoCommit(true);
                        throw new RuntimeException("Order Details Issue");
                    }
                }

                connection.commit();
                connection.setAutoCommit(true);

                JsonObjectBuilder error = Json.createObjectBuilder();
                error.add("state", "Success");
                error.add("message", "Order Successfully Purchased..!");
                error.add("data", "");
                resp.getWriter().print(error.build());

            }

        } catch (SQLException | RuntimeException e) {
            JsonObjectBuilder error = Json.createObjectBuilder();
            error.add("state", "Error");
            error.add("message", e.getLocalizedMessage());
            error.add("data", "");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().print(error.build());
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String option = req.getParameter("option");

        switch (option) {
            case "GenerateOrderID":
                try (Connection connection = dataSource.getConnection()) {
                    JsonObjectBuilder ordID = Json.createObjectBuilder();
                    PreparedStatement pstm = connection.prepareStatement("SELECT oid FROM Orders ORDER BY oid DESC LIMIT 1");

                    ResultSet rst = pstm.executeQuery();
                    System.out.println(rst);
                    while (rst.next()) {
                        ordID.add("orderId", rst.getString(1));
                    }
                    resp.getWriter().print(ordID.build());

                } catch (SQLException e) {

                    JsonObjectBuilder rjo = Json.createObjectBuilder();
                    rjo.add("state", "Error");
                    rjo.add("message", e.getLocalizedMessage());
                    rjo.add("data", "");
                    resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    resp.getWriter().print(rjo.build());
                }
                break;
            case "LoadOrders":
                try (Connection connection = dataSource.getConnection()) {
                    JsonObjectBuilder ordID = Json.createObjectBuilder();
                    PreparedStatement pstm = connection.prepareStatement("SELECT * FROM Orders");

                    ResultSet rst = pstm.executeQuery();
                    System.out.println(rst);
                    while (rst.next()) {
                        ordID.add("orderId", rst.getString(1));
                        ordID.add("orderId", rst.getString(2));
                        ordID.add("orderId", rst.getString(3));
                        ordID.add("orderId", rst.getString(4));
                    }
                    resp.getWriter().print(ordID.build());

                } catch (SQLException e) {

                    JsonObjectBuilder rjo = Json.createObjectBuilder();
                    rjo.add("state", "Error");
                    rjo.add("message", e.getLocalizedMessage());
                    rjo.add("data", "");
                    resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    resp.getWriter().print(rjo.build());
                }
                break;
            case "LoadOrderDetails":
                try (Connection connection = dataSource.getConnection()) {
                    JsonObjectBuilder ordID = Json.createObjectBuilder();
                    PreparedStatement pstm = connection.prepareStatement("SELECT * FROM OrderDetails");

                    ResultSet rst = pstm.executeQuery();
                    System.out.println(rst);
                    while (rst.next()) {
                        ordID.add("orderId", rst.getString(1));
                        ordID.add("orderId", rst.getString(2));
                        ordID.add("orderId", rst.getString(3));
                        ordID.add("orderId", rst.getString(4));
                    }
                    resp.getWriter().print(ordID.build());

                } catch (SQLException e) {

                    JsonObjectBuilder rjo = Json.createObjectBuilder();
                    rjo.add("state", "Error");
                    rjo.add("message", e.getLocalizedMessage());
                    rjo.add("data", "");
                    resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    resp.getWriter().print(rjo.build());
                }
                break;
        }
    }
}
