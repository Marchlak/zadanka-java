// src/main/java/org/example/model/Comment.java
package org.example.model;

import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
public class Comment {
    private long id;
    private long messageId;
    private String text;
    private String author;
    private Date created;

    public Comment() {
    }

    public Comment(long id, long messageId, String text, String author) {
        this.id = id;
        this.messageId = messageId;
        this.text = text;
        this.created = new Date();
        this.author = author;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
