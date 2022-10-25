package indi.eiriksgata.rulateday.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_robot_token")
public class RobotToken {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Date createdAt;

    private String name;
    private String description;
    private String machineCode;

    private Date updatedAt;
    private Date expirationAt;

}
