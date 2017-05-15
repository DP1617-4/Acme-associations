
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.RolesRepository;
import domain.Roles;

@Component
@Transactional
public class StringToRolesConverter implements Converter<String, Roles> {

	@Autowired
	RolesRepository	rolesRepository;


	@Override
	public Roles convert(final String text) {
		Roles result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.rolesRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
