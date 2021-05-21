package indi.eiriksgata.rulateday.pojo;

import lombok.Data;

@Data
public class DiceException {

    private Long id;
    private String title;
    private String content;
    private long createdTimestamp;
    private long qqId;

}
