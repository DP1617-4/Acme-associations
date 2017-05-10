
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.CreditHolder;

@Component
@Transactional
public class CreditHolderToStringConverter implements Converter<CreditHolder, String> {

	@Override
	public String convert(final CreditHolder creditHolder) {
		String result;

		if (creditHolder == null)
			result = null;
		else
			result = String.valueOf(creditHolder.getId());

		return result;
	}
}
