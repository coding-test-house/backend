package dev.codehouse.backend.game.service;

public interface GameService <T>{
    void bet(String username,T requestDto);
    void calculateResult();
}
