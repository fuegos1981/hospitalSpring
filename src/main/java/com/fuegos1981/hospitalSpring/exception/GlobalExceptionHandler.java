package com.fuegos1981.hospitalSpring.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {
    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(DBException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ModelAndView nullEntityReferenceExceptionHandler(HttpServletRequest request, DBException exception) {
        logger.info("_________nullEntityReferenceExceptionHandler_"+exception.getMessage());
        return getModelAndView(request, HttpStatus.BAD_REQUEST, exception);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    public ModelAndView entityNotFoundExceptionHandler(HttpServletRequest request, EntityNotFoundException exception) {
        logger.info("_________________entityNotFoundExceptionHandler"+exception.getMessage());
        return getModelAndView(request, HttpStatus.NOT_FOUND, exception);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(value= HttpStatus.FORBIDDEN)
    public ModelAndView accessDeniedExceptionHandler(HttpServletRequest request, AccessDeniedException exception) {
        logger.info("_________________accessDeniedExceptionHandler"+exception.getMessage());
        return getModelAndView(request, HttpStatus.FORBIDDEN, exception);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView internalServerErrorHandler(HttpServletRequest request, Exception exception) {
        logger.info("_________________internalServerErrorHandler"+exception.getMessage());
        return getModelAndView(request, HttpStatus.INTERNAL_SERVER_ERROR, exception);
    }

    private ModelAndView getModelAndView(HttpServletRequest request, HttpStatus httpStatus, Exception exception) {
        logger.error("Exception raised = {} :: URL = {}", exception.getMessage(), request.getRequestURL());
        ModelAndView modelAndView = new ModelAndView("error");
        //modelAndView.addObject("code", httpStatus.value() + " / " + httpStatus.getReasonPhrase());
        modelAndView.addObject("message", exception.getMessage());
        return modelAndView;
    }
}
