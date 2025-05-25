package org.example.resources;

import org.example.model.Comment;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class CommentResource {
    private static Map<Long, Map<Long, Comment>> comments = new HashMap<>();
    private static AtomicLong nextId = new AtomicLong(1);

    static {
        comments.put(1L, new HashMap<>());
        comments.get(1L).put(1L, new Comment(1, 1, "Pierwszy komentarz", "Anna"));
        comments.get(1L).put(2L, new Comment(2, 1, "Drugi komentarz", "Bartek"));
        nextId.set(3);
    }

    public List<Comment> getCommentsForMessage(long messageId) {
        Map<Long, Comment> map = comments.get(messageId);
        if (map == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(map.values());
    }

    public Comment addComment(long messageId, Comment comment) {
        long id = nextId.getAndIncrement();
        comment.setId(id);
        comment.setMessageId(messageId);
        comment.setCreated(new Date());
        comments.computeIfAbsent(messageId, k -> new HashMap<>()).put(id, comment);
        return comment;
    }
}
