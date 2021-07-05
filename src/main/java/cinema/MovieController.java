package cinema;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cinema")
public class MovieController {
    private MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovieDTO createInstrument(@Valid @RequestBody CreateMovieCommand command) {
        return movieService.createMovie(command);
    }

    @GetMapping
    public List<MovieDTO> listMovies(@RequestParam Optional<String> title) {
        return movieService.listMovies(title);
    }

    @GetMapping("/{id}")
    public MovieDTO findMovieById(@PathVariable("id") long id){
        return movieService.findMovieById(id);
    }

    @PutMapping("/{id}")
    public MovieDTO updateMovie(@PathVariable("id") long id, @Valid @RequestBody UpdateDateCommand command){
        return movieService.updateDate(id,command);
    }

    @PostMapping("/{id}/reserve")
    public MovieDTO reserveMovie(@PathVariable("id") long id, @Valid @RequestBody CreateReservationCommand command){
        return movieService.reserveMovie(id,command);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAll(){
        movieService.deleteAll();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Problem> argHandleNotFound(IllegalArgumentException iae) {
        Problem problem = Problem.builder()
                .withType(URI.create("cinema/not-found"))
                .withTitle("Not found")
                .withStatus(Status.NOT_FOUND)
                .withDetail(iae.getMessage())
                .build();
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(problem);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Problem> statHandleNotFound(IllegalStateException ise) {
        Problem problem = Problem.builder()
                .withType(URI.create("cinema/bad-reservation"))
                .withTitle("Bad request")
                .withStatus(Status.BAD_REQUEST)
                .withDetail(ise.getMessage())
                .build();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(problem);
    }
}
