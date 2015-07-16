package org.github.fit.grizzly;

import org.glassfish.grizzly.Interceptor;
import org.jboss.weld.bean.interceptor.CdiInterceptorFactory;
import org.jboss.weld.context.bound.BoundRequestContext;

import javax.enterprise.inject.spi.CDI;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import java.util.HashMap;
import java.util.Map;

public class CDIRequestInterceptor implements Interceptor<CdiInterceptorFactory> {

    public static final String REQUEST_CONTEXT = "requestContext";
    public static final String REQUEST_MAP = "requestMap";

   /* @SuppressWarnings("unchecked")
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
        BoundRequestContext requestContext = (BoundRequestContext)servletRequestEvent.getServletRequest().getAttribute(REQUEST_CONTEXT);
        Map<String,Object> requestMap = (Map<String,Object>)servletRequestEvent.getServletRequest().getAttribute(REQUEST_MAP);
        requestContext.invalidate();
        requestContext.deactivate();
        requestContext.dissociate(requestMap);
        servletRequestEvent.getServletRequest().setAttribute(REQUEST_CONTEXT, null);
        servletRequestEvent.getServletRequest().setAttribute(REQUEST_MAP, null);
    }


    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        BoundRequestContext requestContext = CDI.current().select(BoundRequestContext.class).get();
        Map<String,Object> requestMap = new HashMap<>();
        requestContext.associate(requestMap);
        requestContext.activate();
        servletRequestEvent.getServletRequest().setAttribute(REQUEST_CONTEXT, requestContext);
        servletRequestEvent.getServletRequest().setAttribute(REQUEST_MAP, requestMap);
    }*/

    @Override
    public int intercept(int i, Object o, CdiInterceptorFactory cdiInterceptorFactory) {
        return 0;
    }
}
