package student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

class GameListTest {
    static Set<BoardGame> games;
    static IGameList gameList;

    @BeforeEach
    public void setUp() {
        games = new HashSet<>();
        games.add(new BoardGame("Chess", 1, 2, 2, 10, 20, 10.0, 700, 10.0, 2006));
        games.add(new BoardGame("Go", 2, 2, 5, 30, 30, 8.0, 100, 7.5, 2000));
        games.add(new BoardGame("Monopoly", 3, 6, 10, 20, 1000, 1.0, 800, 5.0, 2007));
        gameList = new GameList();
    }

    @Test
    public void testAddToListByName() {
        gameList.addToList("Go", games.stream());
        List<String> names = gameList.getGameNames();
        assertEquals(1, names.size());
        assertEquals("Go", names.get(0));
    }

    @Test
    public void testAddToListByIndex() {
        gameList.addToList("1", games.stream());
        assertEquals(1, gameList.count());
    }

    @Test
    public void testClearList() {
        gameList.addToList("Go", games.stream());
        gameList.clear();
        assertEquals(0, gameList.count());
    }

    @Test
    public void testRemoveFromList() {
        gameList.addToList("Go", games.stream());
        gameList.removeFromList("Go");
        assertEquals(0, gameList.count());
    }
}