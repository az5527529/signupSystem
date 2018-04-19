package com.Constant;

public enum DocumentNoEnum {
	SignupNumber("W", 5),OrderNo("DD",24),IdentifyNo("ID",5);

	String noPrefix;
	int bit;

	DocumentNoEnum(String noPrefix, int bit) {
		this.noPrefix = noPrefix;
		this.bit = bit;
	}

	public String getNoPrefix() {
		return noPrefix;
	}

	public int getBit() {
		return bit;
	}
}
