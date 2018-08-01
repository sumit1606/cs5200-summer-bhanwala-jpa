package edu.neu.cs5200;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import edu.neu.cs5200.orm.jpa.daos.ActorDao;
import edu.neu.cs5200.orm.jpa.daos.DirectorDao;
import edu.neu.cs5200.orm.jpa.daos.MovieDao;
import edu.neu.cs5200.orm.jpa.daos.MovieLibraryDao;
import edu.neu.cs5200.orm.jpa.daos.TestDao;

@SpringBootApplication
public class Cs5200Summer2018BhanwalaJpaApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(Cs5200Summer2018BhanwalaJpaApplication.class, args);
	}
	
	@Autowired
	ActorDao actorDao;
	
	@Autowired
	DirectorDao directorDao;
	
	@Autowired
	MovieDao movieDao;
	
	@Autowired
	MovieLibraryDao movieLibraryDao;
	
	@Autowired
	TestDao testDao;
	
	@Override
	public void run(String... args) throws Exception {
		testDao.test();
	}
}
