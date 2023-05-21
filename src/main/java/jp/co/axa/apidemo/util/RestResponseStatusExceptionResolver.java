package jp.co.axa.apidemo.util;

import jp.co.axa.apidemo.filters.LoggingFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class defines the behavior of the application when an exception occurs.
 *
 * Exceptions generated inside controllers will be handled here and converted to
 * the appropriate http error.
 */
@Component
public class RestResponseStatusExceptionResolver extends AbstractHandlerExceptionResolver {

    private static final Logger logger = new Logger(RestResponseStatusExceptionResolver.class);


    @Override
    protected ModelAndView doResolveException(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception exception
    ) {
        try {
            if (exception instanceof IllegalArgumentException) {
                logger.warn("Invalid argument exception: {}", exception.getMessage());
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            } else {
                logger.warn("Unknown error: {}", exception.getMessage());
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            logger.error("Unable to handle the error: {}", exception.getMessage());
        }
        ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
        mav.addObject("error", exception.getMessage());
        return mav;
    }
}
