package jp.co.axa.apidemo.filters;

import jp.co.axa.apidemo.util.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

import static jp.co.axa.apidemo.util.Constants.REQUEST_ATTRIBUTE_REQUEST_ID;

@Component
@Order(1)
public class LoggingFilter implements Filter {

    private static final Logger logger = new Logger(LoggingFilter.class);

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        String uuid = UUID.randomUUID().toString();
        request.setAttribute(REQUEST_ATTRIBUTE_REQUEST_ID, uuid);

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        logger.info(
                "Begin request {} : {}",
                req.getMethod(),
                req.getRequestURI()
        );
        chain.doFilter(request, response);
        logger.info("End request");
    }

}