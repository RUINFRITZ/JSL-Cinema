package model;

public class ScheduleDto {

//	CREATE TABLE schedule (
	private int sno; // NUMBER PRIMARY KEY,
	private int mno; // NUMBER NOT NULL,
	private int tno; // NUMBER NOT NULL,
	private String sdate; // DATE NOT NULL
//	private int total_seats; // NUMBER DEFAULT 60,
//  CONSTRAINT movie_fk FOREIGN KEY (mno) REFERENCES movie(mno) ON DELETE CASCADE,
//	CONSTRAINT theater_fk FOREIGN KEY (tno) REFERENCES theater(tno)
	
//	CREATE TABLE theater (
//  tno NUMBER PRIMARY KEY,
	private String tname; //VARCHAR2(50) NOT NULL,
	private int trow; // NUMBER, -- 행
	private int tcol; // NUMBER, -- 열
	private int total_seats; // NUMBER -- trow * tcol 자동계산 혹은 직접입력
	
//	CREATE TABLE reservation (
	private int rno; // NUMBER PRIMARY KEY,
//		    sno NUMBER NOT NULL,
	private String userid; // VARCHAR2(50) NOT NULL,
	private String seat_info; // VARCHAR2(100) NOT NULL,
	private int price; // NUMBER NOT NULL,
	private String rdate; // DATE DEFAULT SYSDATE, -- 예매 일시
//  CONSTRAINT sch_fk FOREIGN KEY (sno) REFERENCES schedule(sno) ON DELETE CASCADE,
//	CONSTRAINT mem_fk FOREIGN KEY (userid) REFERENCES member_cinema(userid) ON DELETE CASCADE
	
	private String title; // VARCHAR2(100) NOT NULL,
	private String poster; // VARCHAR2(500) DEFAULT 'default.jpg',
	
	private int booked_cnt; // 예약된 좌석 수
	
	public ScheduleDto() {}
	
	public ScheduleDto(int sno, int mno, int tno, String sdate) {
		this.sno = sno;
		this.mno = mno;
		this.tno = tno;
		this.sdate = sdate;
	}
	
	public int getSno() {
		return sno;
	}
	public void setSno(int sno) {
		this.sno = sno;
	}
	public int getMno() {
		return mno;
	}
	public void setMno(int mno) {
		this.mno = mno;
	}
	public int getTno() {
		return tno;
	}
	public void setTno(int tno) {
		this.tno = tno;
	}
	public String getSdate() {
		return sdate;
	}
	public void setSdate(String sdate) {
		this.sdate = sdate;
	}
	public String getTname() {
		return tname;
	}
	public void setTname(String tname) {
		this.tname = tname;
	}
	public int getTrow() {
		return trow;
	}
	public void setTrow(int trow) {
		this.trow = trow;
	}
	public int getTcol() {
		return tcol;
	}
	public void setTcol(int tcol) {
		this.tcol = tcol;
	}
	public int getTotal_seats() {
		return total_seats;
	}
	public void setTotal_seats(int total_seats) {
		this.total_seats = total_seats;
	}
	public int getRno() {
		return rno;
	}
	public void setRno(int rno) {
		this.rno = rno;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getSeat_info() {
		return seat_info;
	}
	public void setSeat_info(String seat_info) {
		this.seat_info = seat_info;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getRdate() {
		return rdate;
	}
	public void setRdate(String rdate) {
		this.rdate = rdate;
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
	public int getBooked_cnt() {
		return booked_cnt;
	}
	public void setBooked_cnt(int booked_cnt) {
		this.booked_cnt = booked_cnt;
	}
	
}
