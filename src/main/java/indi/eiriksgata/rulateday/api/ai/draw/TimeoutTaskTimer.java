package indi.eiriksgata.rulateday.api.ai.draw;

import indi.eiriksgata.rulateday.api.mapper.AiTextDrawMapper;
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
