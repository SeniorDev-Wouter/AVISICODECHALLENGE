package nl.lpp.uncenshow.exceptionhandling.exceptions

import nl.lpp.uncenshow.exceptionhandling.BaseException
import org.springframework.http.HttpStatus

class UsernameNotValidException : BaseException("Username does not meet criteria.") {
    override fun getHttpStatus(): HttpStatus {
        return HttpStatus.BAD_REQUEST;
    }
}