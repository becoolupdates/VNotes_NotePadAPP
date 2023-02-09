package com.vikas.notebookpro;

import com.google.firebase.Timestamp;


public class Notes {
  private   String title;
  private   String content;
  private Timestamp timestamp;

   //generat getters and setters


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
