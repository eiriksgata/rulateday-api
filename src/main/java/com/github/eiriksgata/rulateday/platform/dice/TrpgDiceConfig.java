package com.github.eiriksgata.rulateday.platform.dice;

import com.github.eiriksgata.rulateday.platform.dice.instruction.QueryInstruct;
import com.github.eiriksgata.trpg.dice.message.handle.InstructHandle;
import com.github.eiriksgata.trpg.dice.operation.DiceSet;
import com.github.eiriksgata.trpg.dice.operation.RollBasics;
import com.github.eiriksgata.trpg.dice.operation.RollRole;
import com.github.eiriksgata.trpg.dice.operation.impl.RollBasicsImpl;
import com.github.eiriksgata.trpg.dice.operation.impl.RollRoleImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TrpgDiceConfig {

    @Bean
    public InstructHandle createdTrpgDiceInstruct() {
        return new InstructHandle();
    }

    @Bean
    public DiceSet createDiceSet() {
        return new DiceSet();
    }

    @Bean
    public RollBasics createRollBasics() {
        return new RollBasicsImpl();
    }

    @Bean
    public RollRole createRollRole() {
        return new RollRoleImpl();
    }


}
