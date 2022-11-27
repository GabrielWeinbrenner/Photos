package com.photos.model;

import java.io.Serializable;

public class Tag implements Serializable{
    public String tagName = "";
    public String tagValue = "";
    public Tag(String string, String string2) {
        tagName = string;
        tagValue = string2;
    }
    public String getTagName() {
        return tagName;
    }
    public String getTagValue() {
        return tagValue;
    }
    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
    public void setTagValue(String tagValue) {
        this.tagValue = tagValue;
    }

}
