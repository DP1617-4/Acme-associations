
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.CreditHolderRepository;
import domain.CreditHolder;

@Component
@Transactional
public class StringToCreditHolderConverter implements Converter<String, CreditHolder> {

	@Autowired
	CreditHolderRepository	creditHolderRepository;


	@Override
	public CreditHolder convert(final String text) {
		CreditHolder result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.creditHolderRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
