
package utilities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class TestClasses {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		

		//		try {
		//			System.out.printf("Test your method");
		//
		//			reader = new ConsoleReader();
		//
		//			line = reader.readLine();
		//			while (!line.equals("quit")) {
		//
		//				PhoneNumber checkNum;
		//
		//				checkNum = new PhoneNumber();
		//				checkNum.setCountryCode(34);
		//				checkNum.setNationalNumber(689355643);
		//
		//				final PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
		//
		//				final boolean potato = phoneUtil.isValidNumber(checkNum);
		//
		//				System.out.println(potato);
		//				line = reader.readLine();
		//
		//			}
		//		} catch (final Throwable oops) {
		//			System.out.flush();
		//			System.err.printf("%n%s%n", oops.getLocalizedMessage());
		//			//oops.printStackTrace(System.out);			
		//		}

		String result;
		final String datePattern = "yyyyMMdd";
		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
		final String moment = simpleDateFormat.format(new Date());
		String code = "";
		code += "-" + TestClasses.randomLetter() + TestClasses.randomLetter() + TestClasses.randomLetter();
		result = moment + code;
		System.out.println(result);

	}

	public static String randomLetter() {
		char result;
		final String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		final Random random = new Random();
		result = alphabet.charAt(random.nextInt(62));
		return Character.toString(result);
	}
}
