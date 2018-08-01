package edu.neu.cs5200.orm.jpa.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.neu.cs5200.orm.jpa.repositories.ActorRepository;
import edu.neu.cs5200.orm.jpa.repositories.DirectorRepository;
import edu.neu.cs5200.orm.jpa.repositories.MovieLibraryRepository;
import edu.neu.cs5200.orm.jpa.repositories.MovieRepository;

@Component
public class TestDao {

	@Autowired
	MovieRepository movieRepository;	
	
	@Autowired
	ActorRepository actorrepo;
	
	@Autowired
	MovieLibraryRepository movielibraryRepo;
	
	@Autowired
	DirectorRepository directorrepo;
	
	@Autowired
	ActorDao actorDao;
	
	@Autowired
	DirectorDao directorDao;
	
	@Autowired
	MovieDao movieDao;
	
	@Autowired
	MovieLibraryDao movielibraryDao;
	
	public void test() {
	
		actorDao.test();
		directorDao.test();
		movieDao.test();
		movielibraryDao.test();
	}

}
