package com.youdao.dic.comber;

public class Main {

	public static void main(String[] args) {
		IWordComber comber = WordComberCreator.create("source filePath");
		
		comber.start();
		
		System.out.println("Word Comb done~ Please reImport the file");
	}

}
