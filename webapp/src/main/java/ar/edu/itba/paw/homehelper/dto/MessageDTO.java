package ar.edu.itba.paw.homehelper.dto;

public class MessageDTO {
    private String from;
    private String text;

    public MessageDTO() {
    }

    public MessageDTO(String from, String text) {
        this.from = from;
        this.text = text;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
