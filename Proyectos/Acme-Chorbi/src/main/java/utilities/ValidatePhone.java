
package utilities;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import utilities.internal.ConsoleReader;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

public class ValidatePhone {

	public static void ValidatorPhone(final String number, final String country) {

		final PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
		boolean isValid = false;
		final Map<String, Locale> map = new HashMap<String, Locale>();
		for (final Locale locale : Locale.getAvailableLocales())
			map.put(locale.getDisplayCountry(), locale);
		//		final Locale[] locales = Locale.getAvailableLocales();
		//		for (int i = 0; i < locales.length; i++)
		//			if (locales[i].getDisplayCountry().equals(country))
		//				code = locales[i].getISO3Country();
		//		
		//		code = code.trim();

		String code = map.get(country).getISO3Country();
		code = code.substring(0, code.length() - 1);
		try {

			final PhoneNumber phoneNumberProto = phoneUtil.parse(number, code);

			isValid = phoneUtil.isValidNumber(phoneNumberProto);

			System.out.println(phoneUtil.format(phoneNumberProto, PhoneNumberFormat.INTERNATIONAL));

			if (isValid)
				System.out.println("valid phone number");
			else
				System.out.println("Not a valid phone number");

		} catch (final NumberParseException e) {
			System.err.println("NumberParseException was thrown: " + e.toString());
		}

	}
	public static void main(final String[] args) {

		ConsoleReader reader;
		String line, country;

		try {
			System.out.printf("Phone Validator 1.0%n");
			System.out.printf("----------------%n%n");
			System.out.printf("Please type your country%n");

			reader = new ConsoleReader();

			line = reader.readLine();
			while (!line.equals("quit")) {
				country = line;
				System.out.printf("Please type your phone number%n");
				line = reader.readLine();

				while (!line.equals("quit")) {

					ValidatePhone.ValidatorPhone(line, country);
					line = reader.readLine();

				}

			}
		} catch (final Throwable oops) {
			System.out.flush();
			System.err.printf("%n%s%n", oops.getLocalizedMessage());
			//oops.printStackTrace(System.out);			
		}

	}
}
