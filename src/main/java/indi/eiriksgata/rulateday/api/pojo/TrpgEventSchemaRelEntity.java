package indi.eiriksgata.rulateday.api.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@TableName(value = "trpg_event_schema_rel")
public class TrpgEventSchemaRelEntity {

    private Integer id;
    private String createdAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

    private Integer eventId;
    private Integer nextEventId;

}
