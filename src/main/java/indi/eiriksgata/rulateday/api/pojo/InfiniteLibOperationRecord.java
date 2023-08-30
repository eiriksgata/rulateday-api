package indi.eiriksgata.rulateday.api.pojo;

import lombok.Data;

@Data
public class InfiniteLibOperationRecord {

    private Long id;
    private String createdAt;
    private String type;
    private String qqid;
    private String name;
    private String describe;
}
