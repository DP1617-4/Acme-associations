
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Sanction;

@Component
@Transactional
public class SanctionToStringConverter implements Converter<Sanction, String> {

	@Override
	public String convert(final Sanction sanction) {
		String result;

		if (sanction == null)
			result = null;
		else
			result = String.valueOf(sanction.getId());

		return result;
	}

}
