/*
 * HashPassword.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import utilities.internal.ConsoleReader;

public class Tests {

	public static void main(final String[] args) throws IOException {
		ConsoleReader reader;
		String line;
		final Collection<String> attach = new ArrayList<String>();

		try {

			reader = new ConsoleReader();

			line = reader.readLine();
			while (!line.equals("quit")) {

				attach.add(line);
				System.out.println(attach);

				line = reader.readLine();
			}
		} catch (final Throwable oops) {
			System.out.flush();
			System.err.printf("%n%s%n", oops.getLocalizedMessage());
			//oops.printStackTrace(System.out);			
		}
	}

}
