package com.github.eiriksgata.rulateday.dice;

import com.github.eiriksgata.rulateday.dice.dto.DiceMessageDTO;

public interface DiceMessageEventHandler {
    void onBotGroupRequest(DiceMessageDTO diceMessageDTO);

    void onFriendRequest(DiceMessageDTO diceMessageDTO);

    void OnDiceMessage(DiceMessageDTO event);
}
