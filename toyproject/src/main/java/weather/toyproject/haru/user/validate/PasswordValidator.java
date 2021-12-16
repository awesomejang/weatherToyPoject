package weather.toyproject.haru.user.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import weather.toyproject.haru.user.domain.UserVO;

public class PasswordValidator implements ConstraintValidator<Password, UserVO>{

	@Override
	public boolean isValid(UserVO value, ConstraintValidatorContext context) {
		context.disableDefaultConstraintViolation();
		//valid 로직 작성 
		if(!value.getPassword().equals(value.getSecondPassword())) {
			context.buildConstraintViolationWithTemplate("비밀번호가 동일하지 않습니다.")
				   .addConstraintViolation();
		    return false;
		}
		return true;
	}
	
}
