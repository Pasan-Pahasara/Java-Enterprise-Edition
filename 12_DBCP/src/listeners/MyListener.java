package listeners;

import org.apache.commons.dbcp2.BasicDataSource;

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
        //How to configure DBCP pool
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/JEPOS");
        dataSource.setUsername("root");
        dataSource.setPassword("1234");
        dataSource.setMaxTotal(2);//how many connection (ඕන connection ගාන දානවා)
        dataSource.setInitialSize(2);//how many connection should be initialized from created connection (දාපු connection ගානින් active කරලා තියන්න ඕන connection ගාන)
        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.setAttribute("dbcp",dataSource);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
