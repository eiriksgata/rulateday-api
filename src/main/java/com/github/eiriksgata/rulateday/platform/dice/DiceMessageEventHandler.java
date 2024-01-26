package com.github.eiriksgata.rulateday.platform.dice;

import com.github.eiriksgata.rulateday.platform.dice.dto.DiceMessageDTO;
import org.jetbrains.annotations.NotNull;

public interface DiceMessageEventHandler {
    void onBotGroupRequest(DiceMessageDTO diceMessageDTO);

    void onFriendRequest(DiceMessageDTO diceMessageDTO);

    void OnDiceMessage(DiceMessageDTO event);

    //EventHandler可以指定多个属性，包括处理方式、优先级、是否忽略已取消的事件
    //其默认值请见EventHandler注解类
    //因为默认处理的类型为Listener.ConcurrencyKind.CONCURRENT
    //需要考虑并发安全
    void onGroupMessage(DiceMessageDTO event);

    //处理在处理事件中发生的未捕获异常
    void handleException(@NotNull Throwable exception);
}
