import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : ShEnUx
 * @time : 9:56 AM
 * @date : 12/23/2022
 * @since : 0.1.0
 **/
@WebServlet(urlPatterns = "/b")
public class ServletB extends HttpServlet {
    public ServletB(){
        System.out.println("Servlet B Instantiated");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Servlet B doGet Method Invoked");
    }

    @Override
    public void init() throws ServletException {
        System.out.println("Servlet B init invoked");
    }

    @Override
    public void destroy() {
        System.out.println("Servlet B destroy invoked");
    }
}
