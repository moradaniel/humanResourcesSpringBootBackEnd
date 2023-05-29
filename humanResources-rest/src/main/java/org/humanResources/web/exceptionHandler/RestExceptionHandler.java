package org.humanResources.web.exceptionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;


/**
 * Catch Exceptions and build a message for the REST client
 *
 */

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(value={
            /*ObjectNotFoundException.class,
            EntityNotFoundException.class,
            EntityExistsException.class,
            DataIntegrityViolationException.class*/
            Exception.class
    })
    public ResponseEntity<Object> handleCustomException(Exception ex, WebRequest request) {

        logger.error(org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace(ex));

        HttpHeaders headers = new HttpHeaders();
        HttpStatus status;

        /*if (ex instanceof ObjectNotFoundException) {
            status = HttpStatus.NOT_FOUND;
        } else */if (ex instanceof EntityNotFoundException) {
            status = HttpStatus.NOT_FOUND;
        } else if (ex instanceof EntityExistsException) {
            status = HttpStatus.CONFLICT;
        } else if (ex instanceof DataIntegrityViolationException) {
            status = HttpStatus.CONFLICT;
        } else {
            //logger.warn("Unknown exception type: " + ex.getClass().getName());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            return handleExceptionInternal(ex, null, headers, status, request);
        }

        return handleExceptionInternal(ex, buildRestError(ex, status), headers, status, request);
    }


    // 404

    /*
    @ExceptionHandler(value = { com.site.product.exception.EntityNotFoundException.class})
    protected ResponseEntity<Object> handleNotFound(final RuntimeException ex, final WebRequest request) {
        String body = "RESOURCE_NOT_FOUND";
        if(!StringUtils.isEmpty(ex.getMessage())){
            body = ex.getMessage();
        }
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }*/

    /*
    @ExceptionHandler(value = { com.site.product.exception.InvalidObjectException.class})
    protected ResponseEntity<Object> handleInvalidObjectException(final InvalidObjectException ex, final WebRequest request) {
        String body = "BAD_REQUEST";
        if(!StringUtils.isEmpty(ex.getErrors())){
            StringBuilder sb = new StringBuilder();
            ex.getErrors().stream().forEach(error->sb.append(error+"\n"));
            body = sb.toString();
        }
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }*/

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<Object> handleAccessDeniedException(Exception exception, WebRequest webRequest)
    {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        //return new ResponseEntity<>("Authentication Failed: Bad credentials", new HttpHeaders(), HttpStatus.UNAUTHORIZED);
        return handleExceptionInternal(exception, buildRestError(exception, HttpStatus.UNAUTHORIZED), new HttpHeaders(), status, webRequest);
    }


    private RestError buildRestError(Exception ex, HttpStatus status) {
        RestError.Builder builder = new RestError.Builder();
        //builder.setCode(status.value()).setStatus(status.getReasonPhrase()).setThrowable(ex);
        builder.setCode(status.value()).setStatus(status.getReasonPhrase()).setDeveloperMessage(org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace(ex));

        return builder.build();
    }

}