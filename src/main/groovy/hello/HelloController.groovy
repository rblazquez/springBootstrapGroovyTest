package hello

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {

	@RequestMapping(value="/", method=RequestMethod.GET)
	String home() {
		return "Hello World!"
	}

	@RequestMapping(value="/reverse/{stringToReverse}", method=RequestMethod.GET)
	@ResponseBody
	String reverse(@PathVariable String stringToReverse) {
		return new StringBuilder(stringToReverse).reverse().toString();
	}
}
