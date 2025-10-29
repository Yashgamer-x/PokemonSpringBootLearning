package org.yashgamerx.pokemonboot.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.yashgamerx.pokemonboot.exception.PokemonNotFoundException;
import org.yashgamerx.pokemonboot.exception.PokemonRegionNotFoundException;
import org.yashgamerx.pokemonboot.reponse.ApiError;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handlePokemonRegionNotFound(PokemonRegionNotFoundException ex) {
        var body = new ApiError(HttpStatus.NOT_FOUND,  ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(PokemonNotFoundException.class)
    public ResponseEntity<Object> handlePokemonNotFound(PokemonNotFoundException ex) {
        var body = new ApiError(HttpStatus.NOT_FOUND,  ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }
}
