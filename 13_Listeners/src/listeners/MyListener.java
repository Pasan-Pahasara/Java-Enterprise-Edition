package listeners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author : ShEnUx
 * @time : 10:35 AM
 * @date : 12/23/2022
 * @since : 0.1.0
 **/
@WebListener
public class MyListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("Servlet Context Created");
        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.setAttribute("name","Pasan");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("Servlet Context Destroyed");
    }
}
