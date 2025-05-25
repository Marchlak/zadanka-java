package org.example.model;

import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
@XmlRootElement
public class Message {
    private long id;
    private String message;
    private Date created;
    private String author;
    private List<Link> links = new ArrayList<>();
    public Message() {}
    public Message(long id, String message, String author) {
        this.id = id;
        this.message = message;
        this.created = new Date();
        this.author = author;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Date getCreated() {
        return created;
    }
    public void setCreated(Date created) {
        this.created = created;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public List<Link> getLinks() {
        return links;
    }
    public void setLinks(List<Link> links) {
        this.links = links;
    }
    public void addLink(String link, String rel) {
        this.links.add(new Link(link, rel));
    }
}
