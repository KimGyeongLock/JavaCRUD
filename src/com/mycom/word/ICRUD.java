package com.mycom.word;

public interface ICRUD { // 추가로 구현할 기능을 위해 interface로 구현 
	public Object add();
	public int update(Object obj);
	public int delete(Object obj);
	public void selectOne(int id);
} 