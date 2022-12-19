package filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author : ShEnUx
 * @time : 12:04 PM
 * @date : 12/19/2022
 * @since : 0.1.0
 **/
@WebFilter(urlPatterns = "/*")
public class DefaultFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //a?name=ijse //true -> dispatch to a
        //b?name=ijse //true -> dispatch to b
        System.out.println("Default Filter Invoked");
        String name = servletRequest.getParameter("name");
        //cast ServletRequest to HttpServletRequest
        HttpServletRequest req = (HttpServletRequest) servletRequest;//ServletRequest කියන්නේ super type එක නිසා ඒක cast කරගන්නවා HttpServletRequest sub type එකට. මොකද getServletPath() method එක super type එකේ නෑ.
        String servletPath = req.getServletPath(); //getServletPath() method එකෙන් තමයි path එක ගන්නේ.
        System.out.println(name + " " + servletPath);

        if (name.equals("ijse") && servletPath.equals("/a")) {//name එකයි path එකයි equals ද කියලා බලනවා.
            //proceed to servlet
            filterChain.doFilter(servletRequest, servletResponse);
        } else if (name.equals("ijse") && servletPath.equals("/b")) {
            //proceed to servlet
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            //refuse
            servletResponse.getWriter().write("<h1>Invalid Request</h1>");
        }
    }

    @Override
    public void destroy() {

    }
}
