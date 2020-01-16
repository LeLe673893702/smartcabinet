package com.llc.smartcabinet.data.model;

/**
 * @author newler
 * @what
 * @date 2020/1/12
 */
public class WebSocketData {
    private String action;
    private String json;

    public WebSocketData(String action, String json) {
        this.action = action;
        this.json = json;
    }

    public WebSocketData() {
    }


    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
