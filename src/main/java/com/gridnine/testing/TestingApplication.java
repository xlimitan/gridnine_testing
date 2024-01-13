package com.gridnine.testing;

import com.sun.tools.javac.Main;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Logger;



@SpringBootApplication
public class TestingApplication {
	private static Logger log = Logger.getLogger(Main.class.getName());

	public static void main(String[] args) {
		SpringApplication.run(TestingApplication.class, args);



		Filter filter = new Filter(FlightBuilder.createFlights());

		log.info("Original flight list:\n" + filter.getFlightList() + "\n");
		filter.departNotInPast().lessHrsWait(2).arrNotEarlierDept();
	}
}
