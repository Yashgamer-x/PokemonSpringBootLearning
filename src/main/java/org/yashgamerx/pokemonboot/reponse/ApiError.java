package org.yashgamerx.pokemonboot.reponse;

import org.springframework.http.HttpStatusCode;

public record ApiError (
        HttpStatusCode httpStatusCode,
        String message
){ }