package application.model;

@FunctionalInterface
public interface BIMoreFunction <F, S, T, R> {
    R apply(F first, S second,T third);
}
