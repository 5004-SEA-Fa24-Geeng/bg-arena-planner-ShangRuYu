package student;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The GameList class manages a list of board games, allowing addition, removal,
 * retrieval, and saving of game lists.
 */
public class GameList implements IGameList {
    /**
     * The list that stores all board games added to this GameList.
     */
    private final List<BoardGame> games;
    /**
     * Constructor for the GameList.
     */
    public GameList() {
        this.games = new ArrayList<>();
    }

    /**
     * Retrieves the names of all games in the list.
     *
     * @return A list of game names.
     */
    @Override
    public List<String> getGameNames() {
        return games.stream()
                .map(BoardGame::getName)
                .collect(Collectors.toList());
    }

    /**
     * The GameList class manages a list of board games, allowing addition, removal,
     * retrieval, and saving of game lists.
     */
    @Override
    public void clear() {
        games.clear();
    }

    /**
     * Returns the count of games in the list.
     *
     * @return The number of games in the list.
     */
    @Override
    public int count() {
        return games.size();
    }

    /**
     * Saves the list of game names to a file.
     *
     * @param filename The name of the file where game names are saved.
     */
    @Override
    public void saveGame(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String name : getGameNames()) {
                writer.write(name);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error saving game list", e);
        }
    }

    /**
     * Adds games to the list based on a given string input.
     * The input can be  game name, index, range.
     *
     * @param str      The input determining which games to add.
     * @param filtered The stream of filtered games.
     * @throws IllegalArgumentException if the input does not match a valid game.
     */
    @Override
    public void addToList(String str, Stream<BoardGame> filtered) throws IllegalArgumentException {
        List<BoardGame> filteredList = filtered.collect(Collectors.toList());

        if (str.equalsIgnoreCase(ADD_ALL)) {
            Set<BoardGame> uniqueGames = new LinkedHashSet<>(filteredList);
            for (BoardGame game : uniqueGames) {
                if (!games.contains(game)) {
                    games.add(game);
                }
            }
        } else if (str.matches("\\d+-\\d+")) {
            String[] range = str.split("-");
            int start = Integer.parseInt(range[0]) - 1;
            int end = Integer.parseInt(range[1]) - 1;
            if (start < 0 || end >= filteredList.size() || start > end) {
                throw new IllegalArgumentException("Invalid range");
            }
            for (int i = start; i <= end; i++) {
                BoardGame gameToAdd = filteredList.get(i);
                if (!games.contains(gameToAdd)) {
                    games.add(gameToAdd);
                }
            }
        } else {
            try {
                int index = Integer.parseInt(str) - 1;
                if (index < 0 || index >= filteredList.size()) {
                    throw new IllegalArgumentException("Invalid Index");
                }
                BoardGame gameToAdd = filteredList.get(index);
                if (!games.contains(gameToAdd)) {
                    games.add(gameToAdd);
                }
            } catch (NumberFormatException e) {
                filteredList.stream()
                        .filter(game -> game.getName().equalsIgnoreCase(str))
                        .findFirst()
                        .ifPresentOrElse(games::add, () -> {
                            throw new IllegalArgumentException("Game not found");
                        });
            }
        }
    }

    /**
     * Removes a game from the list based on a given string input.
     * The input can be a specific game name or an index.
     *
     * @param str The input determining which game to remove.
     * @throws IllegalArgumentException if the input does not match a valid game.
     */
    @Override
    public void removeFromList(String str) throws IllegalArgumentException {
        if (str.equalsIgnoreCase(ADD_ALL)) {
            clear();
            return;
        }
        try {
            List<String> sortedGameNames = getGameNames();
            int index = Integer.parseInt(str) - 1;
            if (index < 0 || index >= sortedGameNames.size()) {
                throw new IllegalArgumentException("Invalid index");
            }
            String gameToRemove = sortedGameNames.get(index);
            games.removeIf(game -> game.getName().equalsIgnoreCase(gameToRemove));
        } catch (NumberFormatException e) {
            if (!games.removeIf(game -> game.getName().equalsIgnoreCase(str))) {
                throw new IllegalArgumentException("Game not found");
            }
        }
    }
}
