package cinema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    private long id;
    private String title;
    private LocalDateTime date;
    private int maxSpaces;
    private int freeSpaces;

    public Movie(long id, String title, LocalDateTime date, int maxSpaces) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.maxSpaces = maxSpaces;
        this.freeSpaces = maxSpaces;
    }

    public void foglal(int i) {
        if (i <= freeSpaces) {
            freeSpaces -= i;
        }else {
            throw new IllegalStateException("foglalas hiba");
        }
    }
}
