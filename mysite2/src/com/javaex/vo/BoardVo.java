package com.javaex.vo;

public class BoardVo {
	private int no;
	private int user_no;
	private int hit;
	private String reg_date;
	private String title;
	private String content;
	private String name;
	
	
	public BoardVo() {}
	
	public BoardVo(int no, int user_no, String title, int hit, String reg_date,String content, String name) {
		this.no = no;
		this.user_no = user_no;
		this.title =title;
		this.hit = hit;
		this.reg_date = reg_date;
		this.content = content;
		this.name = name;
	}

	
	public BoardVo(int user_no, String title, String content, String name) {
		this.user_no = user_no;
		this.title = title;
		this.content = content;
		this.name = name;
	}
	
	
	public BoardVo(int no, int hit, String title, String content) {
		this.no  = no;
		this.hit = hit;
		this.title =title;
		this.content = content;
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public int getUser_no() {
		return user_no;
	}

	public void setUser_no(int user_no) {
		this.user_no = user_no;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public String getReg_date() {
		return reg_date;
	}

	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}

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

	@Override
	public String toString() {
		return "BoardVo [no=" + no + ", user_no=" + user_no + ", hit=" + hit + ", reg_date=" + reg_date + ", title="
				+ title + ", content=" + content + ", name=" + name + "]";
	}



	
	
}
