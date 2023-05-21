package jp.co.axa.apidemo.util;

import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static jp.co.axa.apidemo.util.Constants.REQUEST_ATTRIBUTE_REQUEST_ID;

/**
 * This logger proxy prepend the request id to the log message.
 * Use this pattern to trace your request when the system is executing multiple requests.
 */
public class Logger {

    private final org.slf4j.Logger logger;


    public Logger(Class clazz) {
        logger = LoggerFactory.getLogger(clazz);
    }


    public void trace(String pattern, Object... args) {
        String newPattern = String.format("[%s] %s", getRequestUUID(), pattern);
        logger.trace(newPattern, args);
    }


    public void info(String pattern, Object... args) {
        String newPattern = String.format("[%s] %s", getRequestUUID(), pattern);
        logger.info(newPattern, args);
    }


    public void warn(String pattern, Object... args) {
        String newPattern = String.format("[%s] %s", getRequestUUID(), pattern);
        logger.warn(newPattern, args);
    }


    public void error(String pattern, Object... args) {
        String newPattern = String.format("[%s] %s", getRequestUUID(), pattern);
        logger.error(newPattern, args);
    }


    private String getRequestUUID() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        if (attr == null) return "";
        HttpServletRequest request = attr.getRequest();
        return (String) request.getAttribute(REQUEST_ATTRIBUTE_REQUEST_ID);
    }
}
