package com.github.eiriksgata.rulateday.platform.ai.draw;

import com.github.eiriksgata.rulateday.platform.mapper.AiTextDrawMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class TimeoutTaskTimer {

    @Autowired
    AiTextDrawMapper aiTextDrawMapper;

    @Scheduled(cron = "0 0/3 * * * ?")
    public void scheduled() {
        aiTextDrawMapper.updateTimeoutTask(System.currentTimeMillis());
    }

}
