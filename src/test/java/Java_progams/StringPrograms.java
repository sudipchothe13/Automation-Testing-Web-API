package Java_progams;

import org.testng.annotations.Test;

public class StringPrograms {

	@Test
	public void reverseString1() {
		// O/P----->  gnitseT noitamotuA
		String str = "Automation Testing";
		for (int i = str.length() - 1; i >= 0; i--) {
			System.out.print(str.charAt(i));
		}
	}

	@Test
	public void reverseString2() {

		// O/P--->    avaJ muineleS DDB
		String str = "Java Selenium BDD";
		String[] s = str.split(" ");
		for (int i = 0; i < s.length; i++) {
			for (int j = s[i].length() - 1; j >= 0; j--) {
				System.out.print(s[i].charAt(j));
			}
			System.out.print(" ");
		}
	}

	public void reverseString3() {

	}
}
