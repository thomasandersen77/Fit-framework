package com.github.fit;

import org.jboss.weld.environment.ContainerContext;
import org.jboss.weld.environment.tomcat.TomcatContainer;

import javax.servlet.ServletException;

public class EmbeddedTomcat {
    //http://docs.jboss.org/weld/reference/latest/en-US/html_single/#_jetty

    public static void main(String[] args) throws ServletException, LifecycleException {
        ContainerContext containerContext


        TomcatContainer tomcat = new TomcatContainer();
        tomcat.initialize();
        Context ctx = tomcat.addContext("/", new File("src/main/resources").getAbsolutePath());


        Tomcat.addServlet(ctx, "hello", HelloWorldServlet.class.getName());

        ctx.addServletMapping("/*", "hello");


        // ctx.addApplicationListener(Listener.class.getName()); 1


        tomcat.start();

        tomcat.getServer().await();

    }


    public static class HelloWorldServlet extends HttpServlet {


        @Inject

        private BeanManager manager;


        @Override

        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

            resp.setContentType("text/plain");

            resp.getWriter().append("Hello from " + manager);

        }

    }
}
