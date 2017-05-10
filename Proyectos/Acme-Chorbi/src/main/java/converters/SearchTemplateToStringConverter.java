
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.SearchTemplate;

@Component
@Transactional
public class SearchTemplateToStringConverter implements Converter<SearchTemplate, String> {

	@Override
	public String convert(final SearchTemplate st) {
		String result;

		if (st == null)
			result = null;
		else
			result = String.valueOf(st.getId());

		return result;
	}

}
