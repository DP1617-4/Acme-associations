
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Likes;

@Component
@Transactional
public class LikeToStringConverter implements Converter<Likes, String> {

	@Override
	public String convert(final Likes like) {
		String result;

		if (like == null)
			result = null;
		else
			result = String.valueOf(like.getId());

		return result;
	}

}
