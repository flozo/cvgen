package de.flozo.common.content;

public class LetterContent {

    private int id;
    private String name;
    private Address sender;
    private Address receiver;
    private String subject;
    private String date;
    private String bodyText;

    public LetterContent(int id, String name, Address sender, Address receiver, String subject, String date, String bodyText) {
        this.id = id;
        this.name = name;
        this.sender = sender;
        this.receiver = receiver;
        this.subject = subject;
        this.date = date;
        this.bodyText = bodyText;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getSender() {
        return sender;
    }

    public void setSender(Address sender) {
        this.sender = sender;
    }

    public Address getReceiver() {
        return receiver;
    }

    public void setReceiver(Address receiver) {
        this.receiver = receiver;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBodyText() {
        return bodyText;
    }

    public void setBodyText(String bodyText) {
        this.bodyText = bodyText;
    }

    @Override
    public String toString() {
        return "LetterContent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sender=" + sender +
                ", receiver=" + receiver +
                ", subject='" + subject + '\'' +
                ", date='" + date + '\'' +
                ", bodyText='" + bodyText + '\'' +
                '}';
    }
}
