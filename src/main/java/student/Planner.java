package student;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.List;

/**
 * The planner class use for filtering and sorting board games.
 * Includes criteria such as rating, difficulty, and players.
 */
public class Planner implements IPlanner {
    /**
     * The complete set of all board games available to the planner.
     */
    private final Set<BoardGame> allGames;

    /**
     * The current set of board games after applying filters.
     */
    private Set<BoardGame> filteredGames;

    /**
     * The most recent list of filtered board games, stored for quick access.
     */
    private List<BoardGame> lastFilteredList;

    /**
     * Constructors for the planner with set of board games.
     * @param games The set of board games
     */
    public Planner(Set<BoardGame> games) {
        this.allGames = games;
        this.filteredGames = games;
        this.lastFilteredList = new ArrayList<>();
    }

    /**
     * Filter the game based on provided filter strings.
     * @param filter The filter to apply to the board games
     * @return A stream of filtered games
     */
    @Override
    public Stream<BoardGame> filter(String filter) {
        return applyFilter(filter, GameData.NAME, true);
    }

    /**
     * Filter and sort games based on provided criteria.
     * @param filter The filter to apply to the board games.
     * @param sortOn The column to sort the results on.
     * @return A stream of filtered and sorted games
     */
    @Override
    public Stream<BoardGame> filter(String filter, GameData sortOn) {
        return applyFilter(filter, sortOn, true);
    }

    /**
     * Filter and sort games based on provided criteria and sorting order.
     * @param filter The filter to apply to the board games.
     * @param sortOn The column to sort the results on.
     * @param ascending Whether to sort the results in ascending order or descending order.
     * @return A stream of filtered and sorted games
     */
    @Override
    public Stream<BoardGame> filter(String filter, GameData sortOn, boolean ascending) {
        return applyFilter(filter, sortOn, ascending);
    }

    /**
     * Reset the list.
     */
    @Override
    public void reset() {
        this.filteredGames = allGames;
        this.lastFilteredList.clear();
    }

    /**
     * Applies sorting and filtering on board games.
     * @param filter The filter to apply to the board games.
     * @param sortOn The column to sort the results on.
     * @param ascending Whether to sort the results in ascending order or descending order.
     * @return A stream of filtered and sorted games
     */
    private Stream<BoardGame> applyFilter(String filter, GameData sortOn, boolean ascending) {
        Stream<BoardGame> stream = filteredGames.stream();

        if (!filter.isEmpty()) {
            String[] conditions = filter.split(",");
            for (String condition : conditions) {
                stream = stream.filter(game -> Filter
                        .filter(game, GameData.fromString(condition.split(Operations.getOperatorFromStr(condition)
                        .getOperator())[0].trim()), Operations.getOperatorFromStr(condition),
                                condition.split(Operations.getOperatorFromStr(condition)
                        .getOperator())[1].trim()));
            }
        }

        Stream<BoardGame> sortedStream = stream.sorted((g1, g2) -> {
            int comparison;
            switch (sortOn){
                case RATING:
                    comparison = Double.compare(g1.getRating(), g2.getRating());
                    break;
                case DIFFICULTY:
                    comparison = Double.compare(g1.getDifficulty(), g2.getDifficulty());
                    break;
                case RANK:
                    comparison = Integer.compare(g1.getRank(), g2.getRank());
                    break;
                case MIN_PLAYERS:
                    comparison = Integer.compare(g1.getMinPlayers(), g2.getMinPlayers());
                    break;
                case MAX_PLAYERS:
                    comparison = Integer.compare(g1.getMaxPlayers(), g2.getMaxPlayers());
                    break;
                case MIN_TIME:
                    comparison = Integer.compare(g1.getMinPlayTime(), g2.getMinPlayTime());
                    break;
                case MAX_TIME:
                    comparison = Integer.compare(g1.getMaxPlayTime(), g2.getMaxPlayTime());
                    break;
                case YEAR:
                    comparison = Integer.compare(g1.getYearPublished(), g2.getYearPublished());
                    break;
                default:
                    comparison = g1.getName().compareToIgnoreCase(g2.getName());
            }
            return ascending ? comparison : -comparison;
        });
        this.lastFilteredList = sortedStream.collect(Collectors.toList());
        return this.lastFilteredList.stream();
    }

}
