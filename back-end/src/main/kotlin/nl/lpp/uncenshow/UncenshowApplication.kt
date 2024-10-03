package nl.lpp.uncenshow

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@SpringBootApplication
@EnableMongoRepositories
class UncenshowApplication

fun main(args: Array<String>) {
	runApplication<UncenshowApplication>(*args)
}