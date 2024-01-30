package com.github.eiriksgata.rulateday.platform.dice.service;

public interface UserTempDataService {

    void updateUserAttribute(Long id, String attribute);

    void updateUserDiceFace(Long id, int diceFace);

    void addUserTempData(Long id);

    Integer getUserDiceFace(Long id);

    String getUserAttribute(Long id);

}
