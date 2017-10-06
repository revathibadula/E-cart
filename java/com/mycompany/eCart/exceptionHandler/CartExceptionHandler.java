package com.mycompany.eCart.exceptionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class CartExceptionHandler {
	private static final Logger log = LoggerFactory.getLogger(CartExceptionHandler.class);

	@ExceptionHandler(CannotCreateTransactionException.class)
	private ModelAndView dbaseException(Exception e) {
		log.error("Database could not be started.");
		ModelAndView mv = new ModelAndView("error");
		mv.addObject("message",
				"It seems DATABASE is unavailable. Please Contact Administrators or try again after some time.");
		return mv;
	}

	@ExceptionHandler(NullPointerException.class)
	private ModelAndView excp() {
		log.error("Null Pointer in Thankyou. Pressing Back button");
		ModelAndView mv = new ModelAndView("error");
		mv.addObject("error", "Looks like there was a problem accessing this Page.");
		return mv;
	}

	@ExceptionHandler(UnsatisfiedDependencyException.class)
	private ModelAndView exception21(Exception e) {
		log.error("Unsatisfied Dependency...");
		ModelAndView mv = new ModelAndView("error");
		mv.addObject("error", "Error.. Please return to home page.");
		return mv;
	}
}