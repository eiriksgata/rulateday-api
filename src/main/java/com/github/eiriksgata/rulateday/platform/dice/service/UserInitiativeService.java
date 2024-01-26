package com.github.eiriksgata.rulateday.platform.dice.service;

public interface UserInitiativeService {
    boolean diceLimit(String groupId);

    void addInitiativeDice(String groupId, Long userId, String name, int value);

    void deleteDice(String groupId, Long userId, String name);

    void clearGroupDice(String groupId);

    String showInitiativeList(String groupId);
}
