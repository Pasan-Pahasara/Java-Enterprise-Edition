package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : ShEnUx
 * @time : 3:14 PM
 * @date : 12/19/2022
 * @since : 0.1.0
 **/
@WebFilter(urlPatterns = "/*")
public class CORSFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(servletRequest,servletResponse);
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        resp.addHeader("Access-Control-Allow-Origin", "*"); //Access-Control-Allow-Origin Header එක add කරනවා
        resp.addHeader("Access-Control-Allow-Methods", "DELETE,PUT"); //Access-Control-Allow-Methods Header එක add කරනවා type එක DELETE,PUT(Update) කියලා
        resp.addHeader("Access-Control-Allow-Headers", "Content-Type"); //Access-Control-Allow-Headers Header එක add කරනවා type එක Content-Type කියලා (Update එකට use කරනවා).
        resp.setContentType("application/json");
    }

    @Override
    public void destroy() {

    }
}
