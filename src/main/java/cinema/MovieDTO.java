package cinema;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDTO {
    @Schema(description = "name of movie", example = "Titanic")
    private String title;
    private LocalDateTime date;
    private int freeSpaces;

}
