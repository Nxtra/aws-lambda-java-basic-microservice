package io.theclouddeveloper.tips.model;


public class Tip {
    private String creator;
    private String message;

    public Tip(String creator, String message) {
        this.creator = creator;
        this.message = message;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Tip{" +
                "creator='" + creator + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
