package com.example.feng.otakuspedia.bean;

/**
 * EventBus用到的事件类
  */
public class MessageEvent {
    private String keyword;
    private String target;

    public MessageEvent(String keyword, String target) {
        this.keyword = keyword;
        this.target = target;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
