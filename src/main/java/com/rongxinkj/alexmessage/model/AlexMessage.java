package com.rongxinkj.alexmessage.model;

import net.sf.json.JSONObject;

public class AlexMessage {
    private String touser;
    private String template_id;
    private String url;
    private AlexMessageData data;
    private JSONObject jsonObject;

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public AlexMessageData getData() {
        return data;
    }

    public void setData(AlexMessageData data) {
        this.data = data;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
