package com.mycom.word;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class WordCRUD implements ICRUD{
	ArrayList<Word> list;
	Scanner s; 
	final String fname = "Dictionary.txt"; //저장&불러올 파일 
	
	WordCRUD(Scanner s) { //WordManager에서 Scanner변수를 넘겨줌 
		list = new ArrayList<>(); //객체화
		this.s = s;
	}
	
	@Override
	public Object add() { //단어추가 
		System.out.print("\n=> 난이도(1,2,3) & 새 단어 입력 : ");
		int level = s.nextInt();
		String word = s.nextLine(); //s.next로 할경우 \n버퍼 발생 -> nextLine

		System.out.print("뜻 입력 : ");
		String meaning = s.nextLine();

		return new Word(0, level, word, meaning); //새로 입력한 단어를 넘겨줌 
	}
	
	public void addItem() {
		Word one = (Word) add(); //add는 Object 타입
		list.add(one); // list배열에 단어 추가 
		System.out.println("\n새 단어가 단어장에 추가되었습니다. \n");
	}

	@Override
	public int update(Object obj) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Object obj) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void selectOne(int id) {
		// TODO Auto-generated method stub
		
	}
	
	public void listAll() { //list에 있는 단어 꺼내옴 
		System.out.println("\n-----------------------------");
		for(int i = 0; i < list.size(); i++) {
			System.out.print((i+1) + " ");
			System.out.println(list.get(i).toString());
		}
		System.out.println("-----------------------------\n");
	}
	
	public ArrayList<Integer> listAll(String keyword) { //overloading
		
		ArrayList<Integer> idlist = new ArrayList<>(); //반환할 arrayList 생성 
		int j = 0;
		System.out.println("\n-----------------------------");
		for(int i = 0; i < list.size(); i++) {
			String word = list.get(i).getWord();
			if(!word.contains(keyword)) continue;// keyword에 해당하는 단어가 포함되어 있다면 true
			System.out.print((j+1) + " ");
			System.out.println(list.get(i).toString());
			idlist.add(i); //새로만든 list에 keyword에 해당하는 단어의 index(int)를 추가 
			j++;
		}
		System.out.println("-----------------------------\n");
		return idlist;
	}
	
	public void listAll(int level) { //overloading
		int j = 0;
		System.out.println("\n-----------------------------");
		for(int i = 0; i < list.size(); i++) {
			int ilevel = list.get(i).getLevel();
			if(ilevel != level) continue; //level 비교 
			System.out.print((j+1) + " ");
			System.out.println(list.get(i).toString());
			j++;
		}
		System.out.println("-----------------------------\n");
	}

	public void updateItem() {
		System.out.print("=> 수정할 단어 검색 : ");
		String keyword = s.next();
		ArrayList<Integer> idlist = this.listAll(keyword);  //keyword에 해당하는 단어list 반환 
		System.out.print("=> 수정할 번호 선택 : ");
		int id = s.nextInt();
		s.nextLine();//id뒤에있는 enter를 없애기 위해, enter는 여기로 들어감 
		
		System.out.print("=> 뜻 입력 : ");
		String meaning = s.nextLine();
		System.out.println("id"+idlist.get(id-1));
		Word word = list.get(idlist.get(id-1)); // idlist에서 먼저 id를 찾고 그 id를 list에서 다시 찾는방법 
		word.setMeaning(meaning); //새로 작성한 뜻을 setter함수를 이용해서 넣어주기 
		System.out.println("단어가 수정되었습니다. ");
	}

	public void deleteItem() {
		System.out.print("=> 삭제할 단어 검색 : ");
		String keyword = s.next();
		ArrayList<Integer> idlist = this.listAll(keyword);
		System.out.print("=> 삭제할 번호 선택 : ");
		int id = s.nextInt();
		s.nextLine();//id뒤에있는 enter를 없애기 위해, enter는 여기로 들어감 
		
		System.out.print("=> 정말로 삭제하실래요?(Y/n) ");
		String answer = s.next();
		if(answer.equalsIgnoreCase("y")) {//대문자 소문자 상관x
			list.remove((int) idlist.get(id-1)); 
			//Unlikely argument type Integer for remove(Object) on a Collection<Word>
			//idlist.get(id-1) Integer라는 객체로 되어있어서 삭제가 안됨 -> 정수형 casting 
			//Integer객체와 int는 다른가 ?
			System.out.println("단어가 삭제되었습니다. ");
		} else {
			System.out.println("취소되었습니다. ");
		}
	}
	
	public void loadFile() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(fname));
			String line;
			int count = 0;
			
			while(true) {
				line = br.readLine(); // 한줄씩 불러옴
				if(line == null) break; //파일의 끝 
				
				String data[] = line.split("\\|"); //쪼개기: 문자로 인식하지 못함 \\추가 
				int level = Integer.parseInt(data[0]); // string -> int 
				String word = data[1];
				String meaning = data[2];
				list.add(new Word(0, level, word, meaning)); //한줄씩 list 추가 
				count++;
			}
			br.close();//파일을 열었으면 닫아줘야함 
			System.out.println("==> " + count + "개 데이터 로딩 완료!!!");
		} catch (IOException e) {//파일이 없을때 에러 처리 
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void saveFile() {
		try {
			PrintWriter pr = new PrintWriter(new FileWriter(fname));
			for(Word one : list) {//list에서 데이터를 하나씩 가져와서 파일에 작성 
				pr.write(one.toFileString() + "\n"); //write는 enter가 없음 
			}
			pr.close();
			System.out.println("==> 데이터 저장 완료 !!!");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void searchLevel() {
		System.out.print("=> 원하는 레벨은> (1~3) ");
		int level = s.nextInt();
		listAll(level);
	}

	public void searhWord() {
		System.out.print("=> 원하는 단어는? ");
		String keyword = s.next();
		listAll(keyword);
	}
}
