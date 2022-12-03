import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author : Pasan Pahasara
 * @time : ${TIME}
 * @date : ${DATE}
 * @since : 0.1.0
 **/
@WebServlet(urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Customer Saved");
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String address = req.getParameter("address");
        String salary = req.getParameter("salary");
        System.out.println(id+" "+name+" "+address+" "+salary);

        PrintWriter writer = resp.getWriter();
        writer.write("<h1>Saved Customer</h1>");
        writer.write("<table border='1' border-color='#000000' width='50%'>");
        writer.write("<thead bgcolor='#A67D7D'>");
        writer.write("<tr><th>ID</th><th>Name</th><th>Age</th><th>Address</th></tr>");
        writer.write("</thead>");
        writer.write("<tbody>");
        writer.write("<tr><td>"+id+"</td><td>"+name+"</td><td>"+address+"</td><td>"+salary+"</td></tr>");
        writer.write("</tbody>");
        writer.write("</table>");
    }
}