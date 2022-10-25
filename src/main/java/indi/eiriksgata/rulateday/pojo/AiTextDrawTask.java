package indi.eiriksgata.rulateday.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_ai_text_draw_task")
public class AiTextDrawTask {

    private Integer id;
    private Date createdAt;
    private String machineCode;
    private Date updatedAt;

    private Long groupId;
    private String prompt;
    private String negativePrompt;
    private Integer width;
    private Integer height;
    private Integer samplingSteps;

    private String name;
    private String description;
    private String code;
    private String userId;
    private String clientId;

    private int isEnable;
    private int isComplete;

    private Date completeAt;

    private String leaveWord;


}
