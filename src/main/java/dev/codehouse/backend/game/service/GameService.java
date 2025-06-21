package dev.codehouse.backend.game.service;

public interface GameService <T>{
    void bet(T requestDto);
    void calculateResult();
}
