package model;

public class MovieDto {

//	CREATE TABLE movie (
	private int mno; // NUMBER PRIMARY KEY,
	private String title; // VARCHAR2(100) NOT NULL,
	private String catchphrase; // VARCHAR2(500);
	private String poster; // VARCHAR2(500) DEFAULT 'default.jpg',
	private String content; // VARCHAR2(4000),
	private int runtime; // NUMBER,
	private String opendate; // DATE,
	private String regdate; // DATE DEFAULT SYSDATE
	
	private double score;
	
	public MovieDto() {}

    public MovieDto(int mno, String title, String catchphrase, String poster, String content, int runtime, String opendate, String regdate) {
        this.mno = mno;
        this.title = title;
        this.catchphrase = catchphrase;
        this.poster = poster;
        this.content = content;
        this.runtime = runtime;
        this.opendate = opendate;
        this.regdate = regdate;
    }
	public String getCatchphrase() {
		return catchphrase;
	}
	public void setCatchphrase(String catchphrase) {
		this.catchphrase = catchphrase;
	}
	public int getMno() {
		return mno;
	}
	public void setMno(int mno) {
		this.mno = mno;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPoster() {
		return poster;
	}
	public void setPoster(String poster) {
		this.poster = poster;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getRuntime() {
		return runtime;
	}
	public void setRuntime(int runtime) {
		this.runtime = runtime;
	}
	public String getOpendate() {
		return opendate;
	}
	public void setOpendate(String opendate) {
		this.opendate = opendate;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	public double getScore() { return score; }
    public void setScore(double score) { this.score = score; }
}
