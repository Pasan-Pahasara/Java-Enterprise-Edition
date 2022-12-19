import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : ShEnUx
 * @time : 9:46 AM
 * @date : 12/19/2022
 * @since : 0.1.0
 **/
@WebServlet(urlPatterns = "/a")
public class A extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Servlet A Get method Invoked");
    }
}
