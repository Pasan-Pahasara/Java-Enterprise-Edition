package servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : ShEnUx
 * @time : 9:54 AM
 * @date : 12/23/2022
 * @since : 0.1.0
 **/

@WebServlet(urlPatterns = "/a")
public class ServletA extends HttpServlet {
    public ServletA(){
        System.out.println("Servlet A Instantiated");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Servlet A doGet Method Invoked");
        ServletContext servletContext = getServletContext();
        String name =(String) servletContext.getAttribute("name");
        System.out.println(name);

    }

    @Override
    public void init() throws ServletException {
        System.out.println("Servlet A init invoked");
    }

    @Override
    public void destroy() {
        System.out.println("Servlet A destroy invoked");
    }
}
