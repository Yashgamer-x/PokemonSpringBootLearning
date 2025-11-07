package org.yashgamerx.pokemonboot.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class PokemonAspect {

    @Pointcut("execution(* org.yashgamerx.pokemonboot.restcontroller.PokemonController.*(..))")
    private void getPokemonRequest(){}

    @Around("getPokemonRequest()")
    public Object logGetPokemon(
            ProceedingJoinPoint proceedingJoinPoint
    ) throws Throwable {
        Object methodName = proceedingJoinPoint.getSignature().getName();
        log.info("Method Started called: {}()", methodName);
        Object stud = proceedingJoinPoint.proceed();
        log.info("Method Ended called: {}()", methodName);
        return stud;
    }

}
