package student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

class FilterTest {
    static Set<BoardGame> games;

    @BeforeEach
    public void setup() {
        games = new HashSet<>();
        games.add(new BoardGame("Chess", 1, 2, 2, 10, 20, 10.0, 700, 10.0, 2006));
        games.add(new BoardGame("Go", 2, 2, 5, 30, 30, 8.0, 100, 7.5, 2000));
        games.add(new BoardGame("Monopoly", 3, 6, 10, 20, 1000, 1.0, 800, 5.0, 2007));
    }

    @Test
    public void testFilterByName() {
        BoardGame game = games.stream()
                .filter(g -> Filter.filter(g, GameData.NAME, Operations.EQUALS, "Go"))
                .findFirst()
                .orElse(null);
        assertNotNull(game);
        assertEquals("Go", game.getName());
    }

    @Test
    public void testFilterByRatingGreaterThan() {
        BoardGame game = games.stream()
                .filter(g -> Filter.filter(g, GameData.RATING, Operations.GREATER_THAN, "5"))
                .findFirst()
                .orElse(null);
        assertNotNull(game);
        assertTrue(game.getRating() > 5);
    }

    @Test
    public void testFilterByMinPlayers() {
        BoardGame game = games.stream()
                .filter(g -> Filter.filter(g, GameData.MIN_PLAYERS, Operations.EQUALS, "2"))
                .findFirst()
                .orElse(null);
        assertNotNull(game);
        assertEquals(2, game.getMinPlayers());
    }

}