package com.photos.model;

import java.io.Serializable;

/**
 * tag class wtih function
 * 
 * @author Gabe Weinbrenner gcw35
 * @author Zihe Zhang zz475
 */
public class Tag implements Serializable{
    /**
     * tag name
     */
    public String tagName = "";
    /**
     * tag value
     */
    public String tagValue = "";
    /** 
     * constructor
     * 
     */
    public Tag(String string, String string2) {
        tagName = string;
        tagValue = string2;
    }
    
    /** 
     * return tag name
     * @return tag name in String
     */
    public String getTagName() {
        return tagName;
    }
    
    /** 
     * return current tag value
     * @return tag value in String
     */
    public String getTagValue() {
        return tagValue;
    }
    
    /** 
     * change the tag name
     * @param tagName the new tag name
     */
    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
    
    /** 
     * change the tag value
     * @param tagValue the new tag value
     */
    public void setTagValue(String tagValue) {
        this.tagValue = tagValue;
    }

}
