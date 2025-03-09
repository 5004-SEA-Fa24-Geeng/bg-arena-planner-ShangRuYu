package student;

/**
 * The Filter class provides methods to apply filtering logic.
 */
public final class Filter {
    private Filter() { }

    /**
     * Filter games based on a specific attribute, operation, and value.
     * @param game The game to be filtered
     * @param column The attribute of the game to filter by
     * @param op The operation to apply fot filtering
     * @param value The value to compare with
     * @return True if the game meets the filter criteria
     */
    public static boolean filter(BoardGame game, GameData column, Operations op, String value) {
        switch (column) {
            case NAME:
                //filter the name
                return filterString(game.getName(), op, value);
            case RATING:
                return filterNum(game.getRating(), op, value);
            case DIFFICULTY:
                return filterNum(game.getDifficulty(), op, value);
            case RANK:
                return filterNum(game.getRank(), op, value);
            case MIN_PLAYERS:
                return filterNum(game.getMinPlayers(), op, value);
            case MAX_PLAYERS:
                return filterNum(game.getMaxPlayers(), op, value);
            case MIN_TIME:
                return filterNum(game.getMinPlayTime(), op, value);
            case MAX_TIME:
                return filterNum(game.getMaxPlayTime(), op, value);
            case YEAR:
                return filterNum(game.getYearPublished(), op, value);
            default:
                return false;
        }
    }

    /**
     * Filters a string attribute based on a given operation.
     *
     * @param gameData The string data to filter
     * @param op       The operation to apply
     * @param value    The value to compare against
     * @return True if the string matches the filter criteria
     */
    public static boolean filterString(String gameData, Operations op, String value) {

        switch (op) {
            case EQUALS:
                return gameData.equalsIgnoreCase(value);
            case NOT_EQUALS:
                return !gameData.equalsIgnoreCase(value);
            case CONTAINS:
                return gameData.toLowerCase().contains(value.toLowerCase());
            default:
                return false;
        }
    }

    /**
     * Filters a numeric attribute based on a given operation.
     *
     * @param gameData The numeric data to filter
     * @param op       The operation to apply
     * @param value    The value to compare against
     * @return True if the numeric data meets the filter criteria, false otherwise
     */
    public static boolean filterNum(double gameData, Operations op, String value) {
        int valueInt = Integer.parseInt(value);

        switch (op) {
            case EQUALS:
                return gameData == valueInt;
            case NOT_EQUALS:
                return gameData != valueInt;
            case GREATER_THAN:
                return gameData > valueInt;
            case LESS_THAN:
                return gameData < valueInt;
            case GREATER_THAN_EQUALS:
                return gameData >= valueInt;
            case LESS_THAN_EQUALS:
                return gameData <= valueInt;
            default:
                return false;
        }
    }    
}
