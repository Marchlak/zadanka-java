package org.example.demo.model;

import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
public class Comment {
    private long id;
    private String content;
    private String author;
    private Date created;

    public Comment() {
        this.created = new Date();
    }

    public Comment(long id, String content, String author) {
        this.id = id;
        this.content = content;
        this.author = author;
        this.created = new Date();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
