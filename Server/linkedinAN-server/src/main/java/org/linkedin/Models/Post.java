package org.linkedin.Models;

import org.linkedin.DB.UniqueID;

public abstract class Post {
    protected long id;
    protected long userID;

    public Post(User user) {
        this.id = UniqueID.generateUniqueID();
    }

    public Post(long id, int likes, long userID) {
        this.id = id;
        this.userID = userID;
    }

    public Post() {

    }

    public String getId() {
        return String.valueOf(id);
    }

    public long getUserID() {
        return userID;
    }


    public void setId(long id) {
        this.id = id;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }
}
