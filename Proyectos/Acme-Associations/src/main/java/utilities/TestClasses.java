
package utilities;

import utilities.internal.ConsoleReader;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

public class TestClasses {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		ConsoleReader reader;
		String line;

		try {
			System.out.printf("Test your method");

			reader = new ConsoleReader();

			line = reader.readLine();
			while (!line.equals("quit")) {

				PhoneNumber checkNum;

				checkNum = new PhoneNumber();
				checkNum.setCountryCode(34);
				checkNum.setNationalNumber(689355643);

				final PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

				final boolean potato = phoneUtil.isValidNumber(checkNum);

				System.out.println(potato);
				line = reader.readLine();

			}
		} catch (final Throwable oops) {
			System.out.flush();
			System.err.printf("%n%s%n", oops.getLocalizedMessage());
			//oops.printStackTrace(System.out);			
		}

	}
}
