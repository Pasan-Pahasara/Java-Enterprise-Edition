package servlet;

import javax.annotation.Resource;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
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
 * @author : Pasan Pahasara
 * @time : 6:53 PM
 * @date : 12/3/2022
 * @since : 0.1.0
 **/
@WebServlet(urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {

    @Resource(name = "java:comp/env/jdbc/pool")
    DataSource dataSource;

    //Query String
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = dataSource.getConnection()){
//        try (Connection connection = ((BasicDataSource) getServletContext().getAttribute("dbcp")).getConnection()){//listener එකෙන් passed කරන value එකට අදාල key එක getServletContext().getAttribute() method එකට pass කරනවා. ඒ value එක cast කරනවා BasicDataSource type එකට.
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
            JsonObjectBuilder load = Json.createObjectBuilder();
            load.add("state", "Ok");
            load.add("message", "Successfully Loaded..!");
            load.add("data", allCustomers.build());
            resp.getWriter().print(load.build());
        } catch (SQLException e) {
            JsonObjectBuilder rjoError = Json.createObjectBuilder();
            rjoError.add("state", "Error");
            rjoError.add("message", e.getLocalizedMessage());
            rjoError.add("data", "");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().print(rjoError.build());
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
        String salary = req.getParameter("salary");

        try (Connection connection = dataSource.getConnection()){
//        try (Connection connection = ((BasicDataSource) getServletContext().getAttribute("dbcp")).getConnection()){
            PreparedStatement pstm = connection.prepareStatement("insert into Customer values(?,?,?,?)");
            pstm.setObject(1, id);
            pstm.setObject(2, name);
            pstm.setObject(3, address);
            pstm.setObject(4, salary);
            boolean b = pstm.executeUpdate() > 0;

            if (b) {
                JsonObjectBuilder responseObject = Json.createObjectBuilder();
                responseObject.add("state", "Ok");
                responseObject.add("message", "Successfully Added..!");
                responseObject.add("data", "");//මේක පාවිච්චි කරන්නෑ හැබැයි convention එකක් විදිහට යවනවා.
                resp.getWriter().print(responseObject.build());//response Object එකේ තියෙන writer එකේ තියෙන print කියන method JsonObject එක build කරලා දානවා.
            }
//            resp.setStatus(201);// data created
        } catch (SQLException e) {
            JsonObjectBuilder error = Json.createObjectBuilder();
            error.add("state", "Error");
            error.add("message", e.getLocalizedMessage());//Exception එකෙන් එන message එක ගන්නවා (getMessage() එකෙන් ගන්නත් පුලුවන්)
            error.add("data", "");
//            resp.setStatus(400);//status code එක සෙට් කරලා යවනවා (Client error)
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);//status code 400 alternative
            resp.getWriter().print(error.build());
        }
    }

    //Query String
    //JSON
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        System.out.println(id);

        try (Connection connection = dataSource.getConnection()){
//        try (Connection connection = ((BasicDataSource) getServletContext().getAttribute("dbcp")).getConnection()){
            PreparedStatement pstm = connection.prepareStatement("delete from Customer where id=?");
            pstm.setObject(1, id);
            boolean b = pstm.executeUpdate() > 0;
            if (b) {
                JsonObjectBuilder rjo = Json.createObjectBuilder();
                rjo.add("state", "Ok");
                rjo.add("message", "Successfully Deleted..!");
                rjo.add("data", "");//මේක පාවිච්චි කරන්නෑ හැබැයි convention එකක් විදිහට යවනවා.
                resp.getWriter().print(rjo.build());//response Object එකේ තියෙන writer එකේ තියෙන print කියන method JsonObject එක build කරලා දානවා.
            } else {
                throw new RuntimeException("There is no such customer for that ID..!");//Customized දෙන්න ඕන exception එක හදලා throw කරනවා.
            }
        } catch (RuntimeException e) {//Throw කරපු exception එක අල්ලගන්නවා.
            JsonObjectBuilder rjoError = Json.createObjectBuilder();
            rjoError.add("state", "Error");
            rjoError.add("message", e.getLocalizedMessage());//අල්ලගත්ත Exception එකෙන් එන message එක ගන්නවා (getMessage() එකෙන් ගන්නත් පුලුවන්)
            rjoError.add("data", "");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);//status code 400 alternative
            resp.getWriter().print(rjoError.build());
        } catch (SQLException e) {
            JsonObjectBuilder rjoError = Json.createObjectBuilder();
            rjoError.add("state", "Error");
            rjoError.add("message", e.getLocalizedMessage());//Exception එකෙන් එන message එක ගන්නවා (getMessage() එකෙන් ගන්නත් පුලුවන්)
            rjoError.add("data", "");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);//status code 500 alternative
            resp.getWriter().print(rjoError.build());
        }
    }

    //Query String
    //JSON
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObject customer = Json.createReader(req.getReader()).readObject();

        try (Connection connection = dataSource.getConnection()){
//        try (Connection connection = ((BasicDataSource) getServletContext().getAttribute("dbcp")).getConnection()){
            PreparedStatement pstm = connection.prepareStatement("update Customer set name=?,address=?,salary=? where id=?");
            pstm.setObject(4, customer.getString("id"));
            pstm.setObject(1, customer.getString("name"));
            pstm.setObject(2, customer.getString("address"));
            pstm.setObject(3, customer.getString("salary"));
            boolean b = pstm.executeUpdate() > 0;
            if (b) {
                JsonObjectBuilder rjo = Json.createObjectBuilder();
                rjo.add("state", "Ok");
                rjo.add("message", "Successfully Updated..!");
                rjo.add("data", "");//මේක පාවිච්චි කරන්නෑ හැබැයි convention එකක් විදිහට යවනවා.
                resp.getWriter().print(rjo.build());//response Object එකේ තියෙන writer එකේ තියෙන print කියන method JsonObject එක build කරලා දානවා.
            } else {
                throw new RuntimeException("Wrong ID, Please Check The ID..!");
            }
        } catch (RuntimeException e) {//Throw කරපු exception එක අල්ලගන්නවා.
            JsonObjectBuilder rjoError = Json.createObjectBuilder();
            rjoError.add("state", "Error");
            rjoError.add("message", e.getLocalizedMessage());//අල්ලගත්ත Exception එකෙන් එන message එක ගන්නවා (getMessage() එකෙන් ගන්නත් පුලුවන්)
            rjoError.add("data", "");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);//status code 500 alternative
            resp.getWriter().print(rjoError.build());
        } catch (SQLException e) {
            JsonObjectBuilder rjoError = Json.createObjectBuilder();
            rjoError.add("state", "Error");
            rjoError.add("message", e.getLocalizedMessage());//Exception එකෙන් එන message එක ගන්නවා (getMessage() එකෙන් ගන්නත් පුලුවන්)
            rjoError.add("data", "");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);//status code 500 alternative
            resp.getWriter().print(rjoError.build());
        }
    }
}
