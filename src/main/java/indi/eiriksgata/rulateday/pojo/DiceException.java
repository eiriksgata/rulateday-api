package indi.eiriksgata.rulateday.pojo;


public class DiceException {

  private long id;
  private String title;
  private String content;
  private long createdTimestamp;
  private long qqId;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }


  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }


  public long getCreatedTimestamp() {
    return createdTimestamp;
  }

  public void setCreatedTimestamp(long createdTimestamp) {
    this.createdTimestamp = createdTimestamp;
  }


  public long getQqId() {
    return qqId;
  }

  public void setQqId(long qqId) {
    this.qqId = qqId;
  }

}
