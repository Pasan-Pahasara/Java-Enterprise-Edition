import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : Pasan Pahasara
 * @time : ${TIME}
 * @date : ${DATE}
 * @since : 0.1.0
 **/
@WebServlet(urlPatterns = "/customer")
public class MyClass extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Reserved Request");
    }
}