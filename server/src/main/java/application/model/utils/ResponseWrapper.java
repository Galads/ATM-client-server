package application.model.utils;

import application.exception.ClientNotFoundException;

import java.util.Optional;
import java.util.function.BiFunction;

public class ResponseWrapper {
    public static <F, S, T> Optional<T> wrap(BiFunction<F, S, T> func, F first, S second) {
        try {
            return Optional.of(func.apply(first, second));
        } catch (ClientNotFoundException ex) {
            return Optional.empty();
        }
    }
}
