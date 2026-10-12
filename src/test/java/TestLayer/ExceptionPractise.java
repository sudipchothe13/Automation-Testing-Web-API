package TestLayer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ExceptionPractise {
	
	public static void main(String[] args) {
		
		
		try {
			 FileReader file = new FileReader("test.txt");
		}catch (FileNotFoundException a){
			System.out.println(a);
		}catch (IOException b) {
			System.out.println(b);
		}
		
	}

}
