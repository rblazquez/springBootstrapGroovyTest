package hello

import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

import org.springframework.boot.SpringApplication
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate

import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

class HelloControllerSpec extends Specification {

	@Shared
	@AutoCleanup
	ConfigurableApplicationContext context

	void setupSpec() {
		Future future = Executors
				.newSingleThreadExecutor().submit(
				new Callable() {
					@Override
					public ConfigurableApplicationContext call() throws Exception {
						return (ConfigurableApplicationContext) SpringApplication
								.run(Application.class)
					}
				})
		context = future.get(60, TimeUnit.SECONDS)
	}

	void cleanupSpec() {
		if (context != null) {
			context.close()
		}
	}

	void "testing spock works"() {
		expect:
		true;
	}

	void "should return Greetings from Spring Boot!"() {
		when:'I go to the homepage'
		ResponseEntity entity = new RestTemplate().getForEntity("http://localhost:8080", String.class)

		then:'I get a greeting'
		entity.statusCode == HttpStatus.OK
		entity.body == 'Hello World!'
	}

	void "should reverse request!"() {
		when:
		ResponseEntity<String> entity = new RestTemplate().getForEntity(url, String.class)

		then:
		entity.statusCode == HttpStatus.OK
		entity.body == reversedString

		where:
		url                                 || reversedString
		'http://localhost:8080/reverse/uno' || 'onu'
		'http://localhost:8080/reverse/ufc' || 'cfu'
	}
}
