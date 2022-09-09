package com.mycom.word;

public class Word {
	
	private int id; //번호 
	private int level; //*의 갯수로 표시 
	private String word; //단어 
	private String meaning; //뜻 
	
	Word(){}//기본형은 자동으로 만들어지지 않음 
	Word(int id, int level, String word, String meaning){
		this.id = id;
		this.level = level;
		this.word = word;
		this.meaning = meaning;
	}
	//데이터 접근을 위한 getter setter 함
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getMeaning() {
		return meaning;
	}
	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}
	
	
	// "*        driveway 차고 진입로" 이런식으로 한줄로 표 
	@Override
	public String toString() {
		
		String slevel = "";
		for(int i = 0 ; i < level ; i++) slevel += "*";
		String str = String.format("%-3s", slevel) //level 왼쪽 정렬 3개 공백  
				+ String.format("%15s", word) + "  " + meaning;
		return str;
	}
	
	public String toFileString() { //파일에 데이터를 출력할 때 어떤 포맷으로 쓸지 
		return this.level + "|" + this.word + "|" + this.meaning; //"|" 분리자 사
	}
	
	
	
	
}
