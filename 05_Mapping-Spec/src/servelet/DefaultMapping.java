package servelet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : Pasan Pahasara
 * @time : 1:50 PM
 * @date : 11/25/2022
 * @since : 0.1.0
 **/
@WebServlet(urlPatterns = "/") //Default Mapping
public class DefaultMapping extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("Default Mapping Invoked");
    }
}
