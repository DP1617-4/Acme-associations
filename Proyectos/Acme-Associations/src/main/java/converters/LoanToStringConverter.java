
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Loan;

@Component
@Transactional
public class LoanToStringConverter implements Converter<Loan, String> {

	@Override
	public String convert(final Loan loan) {
		String result;

		if (loan == null)
			result = null;
		else
			result = String.valueOf(loan.getId());

		return result;
	}

}
