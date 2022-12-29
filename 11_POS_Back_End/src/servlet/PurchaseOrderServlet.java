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
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        String oid = jsonObject.getString("oid");
        String cusId = jsonObject.getString("cusID");
        String date = jsonObject.getString("date");
        String subTotal = jsonObject.getString("subTotal");
        String discount = jsonObject.getString("discount");

        String name = jsonObject.getString("name");
        String description = jsonObject.getString("description");

        try (Connection connection = dataSource.getConnection()){

            connection.setAutoCommit(false);

            PreparedStatement pstm = connection.prepareStatement("insert into Orders values(?,?,?,?,?)");
            pstm.setString(1,oid);
            pstm.setString(2,date);
            pstm.setString(3,cusId);
            pstm.setDouble(4, Double.parseDouble(subTotal));
            pstm.setInt(5, Integer.parseInt(discount));
            if (!(pstm.executeUpdate()>0)) {
                connection.rollback();
                connection.setAutoCommit(true);
                throw new RuntimeException("Order Issue");
            }else{
                JsonArray orderDetails = jsonObject.getJsonArray("orderDetails");

                for (JsonValue orderDetail : orderDetails) {
                    String code = orderDetail.asJsonObject().getString("code");
                    String qty = orderDetail.asJsonObject().getString("qty");
                    String price = orderDetail.asJsonObject().getString("price");


                    PreparedStatement pstms = connection.prepareStatement("insert into OrderDetails values(?,?,?,?,?,?,?)");
                    pstms.setObject(1,oid);
                    pstms.setObject(2,cusId);
                    pstms.setObject(3,name);
                    pstms.setObject(4,code);
                    pstms.setObject(5,description);
                    pstms.setObject(6,qty);
                    pstms.setObject(7,price);
                    if (!(pstms.executeUpdate()>0)) {
                        connection.rollback();
                        connection.setAutoCommit(true);
                        throw new RuntimeException("Order Details Issue");
                    }
                }

                connection.commit();
                connection.setAutoCommit(true);

                JsonObjectBuilder error = Json.createObjectBuilder();
                error.add("state","Success");
                error.add("message","Order Successfully Purchased..!");
                error.add("data","");
                resp.getWriter().print(error.build());

            }

        } catch (SQLException | RuntimeException e) {
            JsonObjectBuilder error = Json.createObjectBuilder();
            error.add("state","Error");
            error.add("message",e.getLocalizedMessage());
            error.add("data","");
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
                        ordID.add("oid", rst.getString("oid"));
                    }
//                    resp.getWriter().print(ordID.build());
                    JsonObjectBuilder rjo = Json.createObjectBuilder();
                    rjo.add("state", "Ok");
                    rjo.add("message", "Successfully Generate Order ID");
                    rjo.add("data", ordID.build());
                    resp.setStatus(HttpServletResponse.SC_OK);
                    resp.getWriter().print(rjo.build());

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
                    PreparedStatement pstm = connection.prepareStatement("SELECT * FROM Orders");

                    ResultSet rst = pstm.executeQuery();
//                    System.out.println(rst);
                    JsonArrayBuilder allOrders = Json.createArrayBuilder();
                    while (rst.next()) {
                        JsonObjectBuilder orders = Json.createObjectBuilder();
                        orders.add("oid", rst.getString("oid"));
                        orders.add("date", rst.getString("date"));
                        orders.add("cusId", rst.getString("cusId"));
                        orders.add("subTotal", rst.getDouble("subTotal"));
                        orders.add("discount", rst.getString("discount"));
                        allOrders.add(orders.build());
                    }
//                    resp.getWriter().print(orders.build());
                    JsonObjectBuilder rjo = Json.createObjectBuilder();
                    rjo.add("state", "Ok");
                    rjo.add("message", "Successfully Data Loaded");
                    rjo.add("data", allOrders.build());
                    resp.setStatus(HttpServletResponse.SC_OK);
                    resp.getWriter().print(rjo.build());

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
                    PreparedStatement pstm = connection.prepareStatement("SELECT * FROM OrderDetails");

                    ResultSet rst = pstm.executeQuery();
                    System.out.println(rst);
                    JsonObjectBuilder orderDetails = Json.createObjectBuilder();
                    JsonArrayBuilder allOrderDetails = Json.createArrayBuilder();
                    while (rst.next()) {
                        orderDetails.add("oid", rst.getString("oid"));
                        orderDetails.add("id", rst.getString("id"));
                        orderDetails.add("name", rst.getString("name"));
                        orderDetails.add("code", rst.getString("code"));
                        orderDetails.add("description", rst.getString("description"));
                        orderDetails.add("qty", rst.getString("qty"));
                        orderDetails.add("price", rst.getString("price"));
                        allOrderDetails.add(orderDetails.build());
                    }
//                    resp.getWriter().print(orderDetails.build());
                    JsonObjectBuilder rjo = Json.createObjectBuilder();
                    rjo.add("state", "Ok");
                    rjo.add("message", "Successfully Data Loaded");
                    rjo.add("data", allOrderDetails.build());
                    resp.setStatus(HttpServletResponse.SC_OK);
                    resp.getWriter().print(rjo.build());

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
