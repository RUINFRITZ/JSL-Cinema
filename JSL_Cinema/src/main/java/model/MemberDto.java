package model;

public class MemberDto {

//	CREATE TABLE member_cinema (
	private String userid; // VARCHAR2(50) PRIMARY KEY,
	private String password; // VARCHAR2(400) NOT NULL,
	private String name; // VARCHAR(20) NOT NULL,
	private int point; // NUMBER DEFAULT 0,
	private int mgrade; // NUMBER DEFAULT 1, 
	// 1, 'Member' / 2, 'VIP' / 3, 'VVIP' / 4, 'MVP' / 0, 'Admin'
	private String phone;
	private String email;
	private String deldate;  // [추가] 탈퇴일
	
//	CREATE TABLE point_history (
	private int pno; // NUMBER PRIMARY KEY,
//	private String userid; // VARCHAR2(50) NOT NULL,
	private int amount; // NUMBER NOT NULL,           -- 금액 (충전은 +, 사용은 -)
	private String description; // VARCHAR2(100),     -- 내용 (ex: 포인트 충전, 영화 예매)
	private String regdate; // DATE DEFAULT SYSDATE,
//FOREIGN KEY (userid) REFERENCES member(userid)
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public int getMgrade() {
		return mgrade;
	}
	public void setMgrade(int mgrade) {
		this.mgrade = mgrade;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getPno() {
		return pno;
	}
	public void setPno(int pno) {
		this.pno = pno;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	public String getDeldate() { return deldate; }
    public void setDeldate(String deldate) { this.deldate = deldate; }
}

