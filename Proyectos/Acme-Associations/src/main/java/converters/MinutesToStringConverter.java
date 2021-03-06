
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Minutes;

@Component
@Transactional
public class MinutesToStringConverter implements Converter<Minutes, String> {

	@Override
	public String convert(final Minutes minutes) {
		String result;

		if (minutes == null)
			result = null;
		else
			result = String.valueOf(minutes.getId());

		return result;
	}

}
