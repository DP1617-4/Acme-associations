
package validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MomentsValidator implements ConstraintValidator<Moments, domain.Activity> {

	@Override
	public void initialize(final Moments constraintAnnotation) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isValid(final domain.Activity value, final ConstraintValidatorContext context) {
		if (value.getStartMoment().after(value.getEndMoment()))
			return false;
		return true;
	}

}
