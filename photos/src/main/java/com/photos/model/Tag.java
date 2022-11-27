package com.photos.model;

public class Tag {
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
