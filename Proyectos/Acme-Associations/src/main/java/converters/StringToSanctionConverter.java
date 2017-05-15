
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.SanctionRepository;
import domain.Sanction;

@Component
@Transactional
public class StringToSanctionConverter implements Converter<String, Sanction> {

	@Autowired
	SanctionRepository	sanctionRepository;


	@Override
	public Sanction convert(final String text) {
		Sanction result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.sanctionRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
