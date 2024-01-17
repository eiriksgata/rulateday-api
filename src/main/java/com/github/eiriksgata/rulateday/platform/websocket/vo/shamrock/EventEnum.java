package com.github.eiriksgata.rulateday.platform.websocket.vo.shamrock;

public class EventEnum {


    public enum PostType {
        MESSAGE("message", "收到消息"),
        MESSAGE_SENT("message_sent", "发送消息"),
        NOTICE("notice", "通知"),
        REQUEST("request", "请求");

        private final String name;
        private final String describe;

        PostType(String name, String describe) {
            this.name = name;
            this.describe = describe;
        }

        public String getName() {
            return name;
        }

        public String getDescribe() {
            return describe;
        }
    }


    public enum MessageType {

        PRIVATE("private", "私聊消息"),
        GROUP("group", "群消息");

        private final String name;
        private final String describe;

        MessageType(String name, String describe) {
            this.name = name;
            this.describe = describe;
        }

        public String getName() {
            return name;
        }

        public String getDescribe() {
            return describe;
        }

    }

    public enum MessageSubType {
        FRIEND("friend", "好友消息"),
        NORMAL("normal", "群消息"),
        GROUP("group", "群临时消息"),
        GROUP_SELF("group_self", "群消息(自身操作)"),
        NOTICE("notice", "系统提示");

        private final String name;
        private final String describe;

        MessageSubType(String name, String describe) {
            this.name = name;
            this.describe = describe;
        }

        public String getName() {
            return name;
        }

        public String getDescribe() {
            return describe;
        }
    }


    public enum NoticeType {
        group_upload("group_upload", "群文件上传"),
        group_admin("group_admin", "群管理员变动"),
        group_decrease("group_decrease", "群成员减少"),
        group_increase("group_increase", "群成员增加"),
        group_ban("group_ban", "群禁言"),
        group_recall("group_recall", "群消息撤回"),
        group_card("group_card", "群成员名片变动"),
        friend_add("friend_add", "好友添加"),
        friend_recall("friend_recall", "好友撤回"),
        offline_file("offline_file", "接收到离线文件包"),
        client_status("client_status", "客户端状态"),
        essence("essence", "精华消息"),
        notify("notify", "系统通知");

        private final String name;
        private final String describe;

        NoticeType(String name, String describe) {
            this.name = name;
            this.describe = describe;
        }

        public String getName() {
            return name;
        }

        public String getDescribe() {
            return describe;
        }
    }


    public enum NoticeNotifySubType {
        honor("honor", "群荣誉变更"),
        poke("poke", "戳一戳"),
        lucky_king("lucky_king", "运气王"),
        title("title", "群头衔变更")

        ;

        private final String name;
        private final String describe;

        NoticeNotifySubType(String name, String describe) {
            this.name = name;
            this.describe = describe;
        }

        public String getName() {
            return name;
        }

        public String getDescribe() {
            return describe;
        }
    }


    public enum RequestType {
        friend("friend", "好友请求"),
        group("group", "群请求")

        ;

        private final String name;
        private final String describe;

        RequestType(String name, String describe) {
            this.name = name;
            this.describe = describe;
        }

        public String getName() {
            return name;
        }

        public String getDescribe() {
            return describe;
        }
    }

}
