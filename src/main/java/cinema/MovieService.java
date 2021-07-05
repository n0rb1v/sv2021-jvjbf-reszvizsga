package cinema;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class MovieService {
    private ModelMapper modelMapper;
    private AtomicLong id = new AtomicLong();
    private List<Movie> movies = new ArrayList<>();

    public MovieService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public MovieDTO createMovie(CreateMovieCommand command) {
        Movie movie = new Movie(
                id.incrementAndGet(),command.getTitle(), command.getTime(),command.getMaxSlot());
        movies.add(movie);
        return modelMapper.map(movie,MovieDTO.class);
    }

    public List<MovieDTO> listMovies(Optional<String> title) {
        return movies.stream()
                .filter(m -> title.isEmpty() || m.getTitle().equalsIgnoreCase(title.get()))
                .map(m -> modelMapper.map(m,MovieDTO.class))
                .collect(Collectors.toList());
    }

    public MovieDTO findMovieById(long id) {
        return modelMapper.map(movies.stream()
                .filter(m -> m.getId() == id)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Not found: " + id)), MovieDTO.class);
    }

    public MovieDTO updateDate(long id, UpdateDateCommand command) {
        Movie movie = findMovie(id);
        movie.setDate(command.getDate());
        return modelMapper.map(movie, MovieDTO.class);
    }

    private Movie findMovie(long id) {
        Movie movie = movies.stream()
                .filter(m -> m.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Not found: " + id));
        return movie;
    }

    public void deleteAll() {
        id = new AtomicLong();
        movies.clear();
    }

    public MovieDTO reserveMovie(long id, CreateReservationCommand command) {
        Movie movie = findMovie(id);
        movie.foglal(command.getReserveSpaces());
        return modelMapper.map(movie, MovieDTO.class);
    }
}
