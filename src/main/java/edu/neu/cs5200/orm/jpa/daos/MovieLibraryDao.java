package edu.neu.cs5200.orm.jpa.daos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.neu.cs5200.orm.jpa.entities.Actor;
import edu.neu.cs5200.orm.jpa.entities.Director;
import edu.neu.cs5200.orm.jpa.entities.Movie;
import edu.neu.cs5200.orm.jpa.entities.MovieLibrary;
import edu.neu.cs5200.orm.jpa.repositories.ActorRepository;
import edu.neu.cs5200.orm.jpa.repositories.DirectorRepository;
import edu.neu.cs5200.orm.jpa.repositories.MovieLibraryRepository;
import edu.neu.cs5200.orm.jpa.repositories.MovieRepository;

@Component
public class MovieLibraryDao {

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
	
	public void deleteAllLibrary() {
		List<MovieLibrary> mlibs = (List<MovieLibrary>) movielibraryRepo.findAll();
		for(MovieLibrary mlib: mlibs) {
			List<Movie> libraryMovies = (List<Movie>) movieRepository.findAll();
			for(Movie m: libraryMovies) {
				if(m.getLibrary() != null) {
					if(m.getLibrary().getId() == mlib.getId()) {
						MovieLibrary temp = null;
						m.setLibrary(temp);
						movieRepository.save(m);
					}
				}
			}
		}
		movielibraryRepo.deleteAll();
	}
	
	public MovieLibrary createMovieLibrary(MovieLibrary mLib) {
		if(movielibraryRepo.findMovieLibraryByName(mLib.getName()) == null)
			movielibraryRepo.save(mLib);
		return movielibraryRepo.findMovieLibraryByName(mLib.getName()); 
	}
	
	public Optional<MovieLibrary> getMovieLibraryById(int id) {
		return movielibraryRepo.findById(id);
	}
	
	public void addMovieToLibrary(MovieLibrary mlib, Movie m1) {
		MovieLibrary movielib =  this.getMovieLibraryById(mlib.getId()).get();
		Movie movie =  movieRepository.findById(m1.getId()).get();
		movielib.getMovies().add(movie);
		movie.setLibrary(movielib);
		movielibraryRepo.save(movielib);
		movieRepository.save(movie);
	}
	
	public MovieLibrary findMovieLibraryByName(String name) {
		return movielibraryRepo.findMovieLibraryByName(name);	
	}
	
	
	public Movie processMovie(Movie m) {
		Movie movie = m;
		return m;
	}
	
	public MovieLibrary createMovieLibraryByName(String name) {
		// TODO Auto-generated method stub
		MovieLibrary created = new MovieLibrary();
		created.setName(name);
		return this.createMovieLibrary(created);
	}

	
	public void test() {
		this.deleteAllLibrary();
		
		Movie m1 = new Movie();
		m1.setTitle("Star Wars A New Hope");
		m1 = movieDao.createMovie(m1);
		
		Actor a1 = new Actor();
		a1.setFirstName("Mark");
		a1.setLastName("Hamill");
		a1 = actorDao.createActor(a1);
		movieDao.addActorToMovie(a1, m1);
		
		
		Actor a2 = new Actor();
		a2.setFirstName("Carrie");
		a2.setLastName("Fisher");
		a2 = actorDao.createActor(a2);
		movieDao.addActorToMovie(a2, m1);
		
		Director d1 = new Director();
		d1.setFirstName("George");
		d1.setLastName("Lucas");
		d1 = directorDao.createDirector(d1);
		movieDao.addDirectorToMovie(d1, m1);
		
		MovieLibrary mlib = new MovieLibrary();
		mlib.setName("Favorite Movies");
		
		this.createMovieLibrary(mlib);
		
		addMovieToLibrary(mlib,m1);
		
		
		Movie m2 = new Movie();
		m2.setTitle("The Revanant");
		m2 = movieDao.createMovie(m2);
		
		Actor a3 = new Actor();
		a3.setFirstName("Leonardo");
		a3.setLastName("DiCaprio");
		a3 = actorDao.createActor(a3);
		movieDao.addActorToMovie(a3, m2);
		
		
		Actor a4 = new Actor();
		a4.setFirstName("Tom");
		a4.setLastName("Hardy");
		a4 = actorDao.createActor(a4);
		movieDao.addActorToMovie(a4, m2);
		
		Director d2 = new Director();
		d2.setFirstName("Alejandro");
		d2.setLastName("Inarritu");
		d2 = directorDao.createDirector(d2);
		movieDao.addDirectorToMovie(d2, m2);
		
		addMovieToLibrary(mlib,m2);
		MovieLibrary retrieved = findMovieLibraryByName("Favorite Movies");
		System.out.println(retrieved.getName());
        for (Movie movie:retrieved.getMovies()) {
            System.out.println(movie.getTitle());
            for (Actor actor:movie.getMovieActor()) {
                System.out.println(actor.getFirstName()+" "+actor.getLastName());
            }
            for (Director director : movie.getMovieDirector()) {
                System.out.println(director.getFirstName()+" "+director.getLastName());
            }
        }
        
	}

	

	
}
