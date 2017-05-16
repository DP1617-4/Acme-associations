
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.LoanRepository;
import domain.Loan;

@Component
@Transactional
public class StringToLoanConverter implements Converter<String, Loan> {

	@Autowired
	LoanRepository	loanRepository;


	@Override
	public Loan convert(final String text) {
		Loan result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.loanRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
