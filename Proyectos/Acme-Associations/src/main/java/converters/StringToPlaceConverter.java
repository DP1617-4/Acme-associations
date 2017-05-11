/*
 * StringToActorConverter.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.PlaceRepository;
import domain.Place;

@Component
@Transactional
public class StringToPlaceConverter implements Converter<String, Place> {

	@Autowired
	PlaceRepository	placeRepository;


	@Override
	public Place convert(final String text) {
		Place result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.placeRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
