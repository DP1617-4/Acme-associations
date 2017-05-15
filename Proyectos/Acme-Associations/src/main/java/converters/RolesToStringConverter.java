
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Roles;

@Component
@Transactional
public class RolesToStringConverter implements Converter<Roles, String> {

	@Override
	public String convert(final Roles roles) {
		String result;

		if (roles == null)
			result = null;
		else
			result = String.valueOf(roles.getId());

		return result;
	}

}
