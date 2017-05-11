/*
 * AbstractTest.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import security.LoginService;

public abstract class AbstractTest {

	// Supporting services --------------------------------

	@Autowired
	private LoginService		loginService;

	private static Properties	prop;


	// Set up and tear down -------------------------------

	@BeforeClass
	public static void setUpClass() {
		AbstractTest.prop = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream("src/main/resources/populate.properties");

			// load a properties file
			AbstractTest.prop.load(input);

		} catch (final IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null)
				try {
					input.close();
				} catch (final IOException e) {
					e.printStackTrace();
				}
		}
	}

	@Before
	public void setUp() {

	}

	@After
	public void tearDown() {
	}

	// Supporting methods ---------------------------------

	public void authenticate(final String username) {
		UserDetails userDetails;
		TestingAuthenticationToken authenticationToken;
		SecurityContext context;

		if (username == null)
			authenticationToken = null;
		else {
			userDetails = this.loginService.loadUserByUsername(username);
			authenticationToken = new TestingAuthenticationToken(userDetails, null);
		}

		context = SecurityContextHolder.getContext();
		context.setAuthentication(authenticationToken);
	}

	public void unauthenticate() {
		this.authenticate(null);
	}

	public void checkExceptions(final Class<?> expected, final Class<?> caught) {
		if (expected != null && caught == null)
			throw new RuntimeException(expected.getName() + " was expected");
		else if (expected == null && caught != null)
			throw new RuntimeException(caught.getName() + " was unexpected");
		else if (expected != null && caught != null && !expected.equals(caught))
			throw new RuntimeException(expected.getName() + " was expected, but " + caught.getName() + " was thrown");
	}

	public int extract(final String beanName) {
		int result;

		result = Integer.valueOf(AbstractTest.prop.getProperty(beanName));

		return result;
	}

}
