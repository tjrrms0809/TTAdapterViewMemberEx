package com.kitesoft.adapterviewmemberex;

public class Member {
	
	String name;
	String nation;
	int gender;
	int flagId;
	String date;
	
	public Member(String name, String nation, int gender, int flagId, String date) {
		this.name=name;
		this.nation= nation;
		this.gender=gender;
		this.flagId= flagId;
		this.date= date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public int getFlagId() {
		return flagId;
	}

	public void setFlagId(int flagId) {
		this.flagId = flagId;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
