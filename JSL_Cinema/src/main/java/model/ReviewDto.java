package model;

import java.sql.Date;

public class ReviewDto {
    private int rno;
    private int mno;
    private String userid;
    private String content;
    private int rating;
    private Date regdate;

    public ReviewDto() {}

    public int getRno() { return rno; }
    public void setRno(int rno) { this.rno = rno; }
    public int getMno() { return mno; }
    public void setMno(int mno) { this.mno = mno; }
    public String getUserid() { return userid; }
    public void setUserid(String userid) { this.userid = userid; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }
    public Date getRegdate() { return regdate; }
    public void setRegdate(Date regdate) { this.regdate = regdate; }
}