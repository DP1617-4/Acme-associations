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

public class MaskString {

	//	public static void main(final String[] args) {
	//
	//		//		final String split[] = StringUtils.split(in);
	//		//
	//		//		final Lispublic static void main(final String[] args) throws IOException {
	//		ConsoleReader reader;
	//		String line;
	//		String out;
	//
	//		try {
	//			System.out.printf("Mask String 1.9%n");
	//			System.out.printf("----------------%n%n");
	//
	//			reader = new ConsoleReader();
	//			line = reader.readLine();
	//			while (!line.equals("quit")) {
	//				out = MaskString.mask(line);
	//				System.out.println(out);
	//				line = reader.readLine();
	//			}
	//		} catch (final Throwable oops) {
	//			System.out.flush();
	//			System.err.printf("%n%s%n", oops.getLocalizedMessage());
	//			//oops.printStackTrace(System.out);			
	//		}
	//	}

	public static String mask(final String in) {

		String out;
		//
		//		for (int i = 0; i < items.size(); i++)
		//			if (items.get(i).matches("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$"))
		//				items.add(i, "***");
		out = in.replaceAll("([^.@\\s]+)(\\.[^.@\\s]+)*@([^.@\\s]+\\.)+([^.@\\s]+)", "***");
		out = out.replaceAll("([+](9[976]\\d|8[987530]\\d|6[987]\\d|5[90]\\d|42\\d|3[875]\\d|2[98654321]\\d|9[8543210]|8[6421]|6[6543210]|5[87654321]|4[987654310]|3[9643210]|2[70]|7|1))?(\\s\\d){3,14}", "***");

		return out;
	}

}
