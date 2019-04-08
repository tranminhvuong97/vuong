package dich.luong.dong.com.call.model;

public class MessageModel {
    private String phone;
    private String time;
    private int id;
    public MessageModel() {
    }

    public MessageModel(String phone, String time) {
        this.phone = phone;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MessageModel(String phone, String time, int id) {
        this.phone = phone;
        this.time = time;
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
