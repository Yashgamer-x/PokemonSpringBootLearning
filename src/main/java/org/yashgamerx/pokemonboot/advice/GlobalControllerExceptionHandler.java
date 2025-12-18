package org.yashgamerx.pokemonboot.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.springframework.web.servlet.view.RedirectView;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(NoResourceFoundException.class)
    public RedirectView handleNoResourceFound(NoResourceFoundException ex) {
        return new RedirectView("/");
    }
}
