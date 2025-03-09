# Board Game Arena Planner Design Document


This document is meant to provide a tool for you to demonstrate the design process. You need to work on this before you code, and after have a finished product. That way you can compare the changes, and changes in design are normal as you work through a project. It is contrary to popular belief, but we are not perfect our first attempt. We need to iterate on our designs to make them better. This document is a tool to help you do that.


## (INITIAL DESIGN): Class Diagram 

Place your class diagrams below. Make sure you check the file in the browser on github.com to make sure it is rendering correctly. If it is not, you will need to fix it. As a reminder, here is a link to tools that can help you create a class diagram: [Class Resources: Class Design Tools](https://github.com/CS5004-khoury-lionelle/Resources?tab=readme-ov-file#uml-design-tools)

### Provided Code

Provide a class diagram for the provided code as you read through it.  For the classes you are adding, you will create them as a separate diagram, so for now, you can just point towards the interfaces for the provided code diagram.

```mermaid
classDiagram
    BGArenaPlanner --> ConsoleApp
    BGArenaPlanner --> GameList
    BGArenaPlanner --> Planner
    BGArenaPlanner --> GameLoader : uses
    
    GameList ..|> IGameList : is-a
    GameList o--BoardGame : has-many
    
    Planner ..|> IPlanner : is-a
    Planner *-- BoardGame : has-many
    Planner --> GameData : uses
    Planner --> Operations : uses
    
    ConsoleApp *-- IPlanner : has-a
    GameLoader --> BoardGame
    GameLoader --> GameData : uses
    
    class IGameList{
        <<interface>>
        +ADD_ALL: String
        +getGameNames() List~String~
        +clear() void
        +count() int
        +saveGame(filename: String) void
        +addToList(str: String, filtered: Stream~BoardGame~) void
        +removeFromList(str: String) void
    }
    
    class IPlanner{
        <<interface>>
        +filter(filter: String) Stream~BoardGame~
        +filter(filter: String, sortOn: GameData) Stream~BoardGame~
        +filter(filter: String, sortOn: GameData, ascending: boolean) Stream~BoardGame~
        +reset() void
    }
    
    class BGArenaPlanner{
        -DEFAULT_COLLECTION: String
        -BGArenaPlanner()
        +main(String[] args): void
    }

    class BoardGame{
        -name: String
        -id: int
        -minPlayers: int
        -maxPlayers: int
        -maxPlayTime: int
        -minPlayTime: int
        -difficulty: duoble
        -rank: int
        -averageRating: double
        -yearPublished: int
        +BoardGame(name, id, minPlayers, maxPlayers, minPlayTime, maxPlayTime, difficulty, rank, averageRating, yearPublished)
        +getName() String
        +getId() int
        +getMinPlayers() int
        +getMaxPlayers() int
        +getMaxPlayTime() int
        +getMinPlayTime() int
        +getDifficulty() double
        +getRank() int
        +getRating() double
        +getYearPublished() int
        +toStringWithInfo(GameData col) String
        +toString() String
        +equals(Object obj) boolean
        +hashCode() int
    }
    
    class ConsoleApp{
        -IN: Scanner
        -DEFAULT_FILENAME: String
        -RND: Random
        -current: Scanner
        -gameList: IGameList
        -planner: IPlanner
        +ConsoleApp(gameList: IGameList, planner: IPlanner)
        +start() void
        -randomNumber() void
        -processHelp() void
        -processFilter() void
        -printFilterStream(games: Stream~BoardGame~ , sortON: GameData) void
        -processListCommands() void
        -printCurrentList() void
        -nextCommand() ConsoleText
        -remainder() String
    }
    
    class GameData{
        <<enumeration>>
        NAME
        ID
        RATING
        DIFFICULTY
        RANK
        MIN_PLAYERS
        MAX_PLAYERS
        MIN_TIME
        MAX_TIME
        YEAR
        -columnName String
        +getColumnName() String
        +fromColumnName(columnName: String) GameData
        +fromString(name: String) GameData
    }
    
    class GameList{
        +GameList()
        +getGameNames() List~String~
        +clear() void
        +count() int
        +saveGame(filename: String) void
        +addToList(str: String, filtered: Stream~BoardGame~) void
        +removeFromList(str: String) void
    }
    
    
    
    class GameLoader{
        -DELIMITER String
        -GamesLoader()
        +loadGamesFile(filename: String) Set~BoardGame~
        -toBoardGame(line: String, columnMap: Map) BoardGame
        -processHeader(header: String) Map
    }
    
    class Operations{
        <<enumeration>>
        EQUALS
        NOT_EQUALS
        GREATER_THAN
        LESS_THAN
        GREATER_THAN_EQUALS
        LESS_THAN_EQUALS
        CONTAINS
        -operator String
        +getOperator() String
        +fromOperator(operator: String) Operations
        +getOperatorFromStr(str: String) Operations
    }
    
    class Planner{
        +Planner(games: Set~BoardGame~)
        +filter(filter: String) Stream~BoardGame~
        +filter(filter: String, sortOn: GameData) Stream~BoardGame~
        +filter(filter: String, sortOn: GameData, ascending: boolean) Stream~BoardGame~
        +reset() void

    }

```

### Your Plans/Design

Create a class diagram for the classes you plan to create. This is your initial design, and it is okay if it changes. Your starting points are the interfaces. 

```mermaid
classDiagram
    BGArenaPlanner --> ConsoleApp
    BGArenaPlanner --> GameList
    BGArenaPlanner --> Planner
    BGArenaPlanner --> GameLoader : uses

    GameList ..|> IGameList : is-a
    GameList o--BoardGame : has-many

    Planner ..|> IPlanner : is-a
    Planner *-- BoardGame : has-many
    Planner --> GameData : uses
    Planner --> Operations : uses

    ConsoleApp *-- IPlanner : has-a
    GameLoader --> BoardGame
    GameLoader --> GameData : uses

    PlayerCountFilter --|> AbstractFilter : is-a
    DifficultyFilter --|> AbstractFilter : is-a
    Planner *-- AbstractFilter : has-many
    AbstractFilter ..> BoardGame

    class IGameList {
        <<interface>>
        +ADD_ALL: String
        +getGameNames() List~String~
        +clear() void
        +count() int
        +saveGame(filename: String) void
        +addToList(str: String, filtered: Stream~BoardGame~) void
        +removeFromList(str: String) void
    }
    class IPlanner {
        <<interface>>
        +filter(filter: String) Stream~BoardGame~
        +filter(filter: String, sortOn: GameData) Stream~BoardGame~
        +filter(filter: String, sortOn: GameData, ascending: boolean) Stream~BoardGame~
        +reset() void
    }
    
    class AbstractFilter {
        <<abstract>>
        #value: String
        #nextFilter: AbstractFilter
        +setNextFilter(filter: AbstractFilter)
        +abstract apply(games: Stream~BoardGame~) Stream~BoardGame~
    }

    class PlayerCountFilter {
        -minPlayers: int
        -maxPlayers: int
        +apply(games: Stream~BoardGame~) Stream~BoardGame~
    }

    class DifficultyFilter {
        -minDifficulty: double
        -maxDifficulty: double
        +apply(games: Stream~BoardGame~) Stream~BoardGame~
    }

    class Planner {
        -filters: List~AbstractFilter~
        -games: Set~BoardGame~
        +filter() Stream~BoardGame~
        +reset() void
    }

    class BGArenaPlanner{
        -DEFAULT_COLLECTION: String
        -BGArenaPlanner()
        +main(String[] args): void
    }

    class BoardGame{
        -name: String
        -id: int
        -minPlayers: int
        -maxPlayers: int
        -maxPlayTime: int
        -minPlayTime: int
        -difficulty: duoble
        -rank: int
        -averageRating: double
        -yearPublished: int
        +BoardGame(name, id, minPlayers, maxPlayers, minPlayTime, maxPlayTime, difficulty, rank, averageRating, yearPublished)
        +getName() String
        +getId() int
        +getMinPlayers() int
        +getMaxPlayers() int
        +getMaxPlayTime() int
        +getMinPlayTime() int
        +getDifficulty() double
        +getRank() int
        +getRating() double
        +getYearPublished() int
        +toStringWithInfo(GameData col) String
        +toString() String
        +equals(Object obj) boolean
        +hashCode() int
    }

    class ConsoleApp{
        -IN: Scanner
        -DEFAULT_FILENAME: String
        -RND: Random
        -current: Scanner
        -gameList: IGameList
        -planner: IPlanner
        +ConsoleApp(gameList: IGameList, planner: IPlanner)
        +start() void
        -randomNumber() void
        -processHelp() void
        -processFilter() void
        -printFilterStream(games: Stream~BoardGame~ , sortON: GameData) void
        -processListCommands() void
        -printCurrentList() void
        -nextCommand() ConsoleText
        -remainder() String
    }

    class GameData{
        <<enumeration>>
        NAME
        ID
        RATING
        DIFFICULTY
        RANK
        MIN_PLAYERS
        MAX_PLAYERS
        MIN_TIME
        MAX_TIME
        YEAR
        -columnName String
        +getColumnName() String
        +fromColumnName(columnName: String) GameData
        +fromString(name: String) GameData
    }

    class GameList{
        +GameList()
        +getGameNames() List~String~
        +clear() void
        +count() int
        +saveGame(filename: String) void
        +addToList(str: String, filtered: Stream~BoardGame~) void
        +removeFromList(str: String) void
    }



    class GameLoader{
        -DELIMITER String
        -GamesLoader()
        +loadGamesFile(filename: String) Set~BoardGame~
        -toBoardGame(line: String, columnMap: Map) BoardGame
        -processHeader(header: String) Map
    }

    class Operations{
        <<enumeration>>
        EQUALS
        NOT_EQUALS
        GREATER_THAN
        LESS_THAN
        GREATER_THAN_EQUALS
        LESS_THAN_EQUALS
        CONTAINS
        -operator String
        +getOperator() String
        +fromOperator(operator: String) Operations
        +getOperatorFromStr(str: String) Operations
    }
```



## (INITIAL DESIGN): Tests to Write - Brainstorm

Write a test (in english) that you can picture for the class diagram you have created. This is the brainstorming stage in the TDD process. 

> [!TIP]
> As a reminder, this is the TDD process we are following:
> 1. Figure out a number of tests by brainstorming (this step)
> 2. Write **one** test
> 3. Write **just enough** code to make that test pass
> 4. Refactor/update  as you go along
> 5. Repeat steps 2-4 until you have all the tests passing/fully built program

You should feel free to number your brainstorm. 

1. Create a BoardGame object, testing getters return correct value.
2. Compare two BoardGame object with same name and ID, both will be tested as equal.
3. Create a BoardGame object, testing with edge cases.
4. Add a game into list, test the count and getGameName.
5. Add a duplicate game, test the return count value.
6. Remove a game, test teh return value.
7. Test minPlayer with random number, should return proper games.
8. Test invalid syntax


## (FINAL DESIGN): Class Diagram

Go through your completed code, and update your class diagram to reflect the final design. Make sure you check the file in the browser on github.com to make sure it is rendering correctly. It is normal that the two diagrams don't match! Rarely (though possible) is your initial design perfect. 

For the final design, you just need to do a single diagram that includes both the original classes and the classes you added. 

> [!WARNING]
> If you resubmit your assignment for manual grading, this is a section that often needs updating. You should double check with every resubmit to make sure it is up to date.

```mermaid
classDiagram
    BGArenaPlanner --> ConsoleApp
    BGArenaPlanner --> GameList
    BGArenaPlanner --> Planner
    BGArenaPlanner --> GameLoader : uses
    
    GameList ..|> IGameList : is-a
    GameList o--BoardGame : has-many
    
    Planner ..|> IPlanner : is-a
    Planner *-- BoardGame : has-many
    Planner --> GameData : uses
    Planner --> Operations : uses
    Planner --> Filter : uses
    
    ConsoleApp *-- IPlanner : has-a
    GameLoader --> BoardGame
    GameLoader --> GameData : uses
    
    class IGameList{
        <<interface>>
        +ADD_ALL: String
        +getGameNames() List~String~
        +clear() void
        +count() int
        +saveGame(filename: String) void
        +addToList(str: String, filtered: Stream~BoardGame~) void
        +removeFromList(str: String) void
    }
    
    class IPlanner{
        <<interface>>
        +filter(filter: String) Stream~BoardGame~
        +filter(filter: String, sortOn: GameData) Stream~BoardGame~
        +filter(filter: String, sortOn: GameData, ascending: boolean) Stream~BoardGame~
        +reset() void
    }
    
    class BGArenaPlanner{
        -DEFAULT_COLLECTION: String
        -BGArenaPlanner()
        +main(String[] args): void
    }

    class BoardGame{
        -name: String
        -id: int
        -minPlayers: int
        -maxPlayers: int
        -maxPlayTime: int
        -minPlayTime: int
        -difficulty: duoble
        -rank: int
        -averageRating: double
        -yearPublished: int
        +BoardGame(name, id, minPlayers, maxPlayers, minPlayTime, maxPlayTime, difficulty, rank, averageRating, yearPublished)
        +getName() String
        +getId() int
        +getMinPlayers() int
        +getMaxPlayers() int
        +getMaxPlayTime() int
        +getMinPlayTime() int
        +getDifficulty() double
        +getRank() int
        +getRating() double
        +getYearPublished() int
        +toStringWithInfo(GameData col) String
        +toString() String
        +equals(Object obj) boolean
        +hashCode() int
    }
    
    class ConsoleApp{
        -IN: Scanner
        -DEFAULT_FILENAME: String
        -RND: Random
        -current: Scanner
        -gameList: IGameList
        -planner: IPlanner
        +ConsoleApp(gameList: IGameList, planner: IPlanner)
        +start() void
        -randomNumber() void
        -processHelp() void
        -processFilter() void
        -printFilterStream(games: Stream~BoardGame~ , sortON: GameData) void
        -processListCommands() void
        -printCurrentList() void
        -nextCommand() ConsoleText
        -remainder() String
    }
    
    class GameData{
        <<enumeration>>
        NAME
        ID
        RATING
        DIFFICULTY
        RANK
        MIN_PLAYERS
        MAX_PLAYERS
        MIN_TIME
        MAX_TIME
        YEAR
        -columnName String
        +getColumnName() String
        +fromColumnName(columnName: String) GameData
        +fromString(name: String) GameData
    }
    
    class GameList{
        +GameList()
        +getGameNames() List~String~
        +clear() void
        +count() int
        +saveGame(filename: String) void
        +addToList(str: String, filtered: Stream~BoardGame~) void
        +removeFromList(str: String) void
    }
    
    
    
    class GameLoader{
        -DELIMITER String
        -GamesLoader()
        +loadGamesFile(filename: String) Set~BoardGame~
        -toBoardGame(line: String, columnMap: Map) BoardGame
        -processHeader(header: String) Map
    }
    
    class Operations{
        <<enumeration>>
        EQUALS
        NOT_EQUALS
        GREATER_THAN
        LESS_THAN
        GREATER_THAN_EQUALS
        LESS_THAN_EQUALS
        CONTAINS
        -operator String
        +getOperator() String
        +fromOperator(operator: String) Operations
        +getOperatorFromStr(str: String) Operations
    }
    
    class Filter{
        +filter(game: BoardGame, column: GameData, op: Operations, value: String) boolean
        +filer(gameData: String, op: Operations, value: String) boolean
        +filter(gameData: double, op: Operations, value: String) boolean
    }
    
    class Planner{
        +Planner(games: Set~BoardGame~)
        +filter(filter: String) Stream~BoardGame~
        +filter(filter: String, sortOn: GameData) Stream~BoardGame~
        +filter(filter: String, sortOn: GameData, ascending: boolean) Stream~BoardGame~
        +reset() void
        -applyFilter(filter: String, sortOn: GameData, ascending: boolean) Stream~BoardGame~
    }

```



## (FINAL DESIGN): Reflection/Retrospective

> [!IMPORTANT]
> The value of reflective writing has been highly researched and documented within computer science, from learning to information to showing higher salaries in the workplace. For this next part, we encourage you to take time, and truly focus on your retrospective.

Take time to reflect on how your design has changed. Write in *prose* (i.e. do not bullet point your answers - it matters in how our brain processes the information). Make sure to include what were some major changes, and why you made them. What did you learn from this process? What would you do differently next time? What was the most challenging part of this process? For most students, it will be a paragraph or two. 

I learned that design is a evolving process from this project. While the initial diagram can help us to understand the structure of a system, the details often shows through the continiusly coding and testing. This iterative process helps me to understand how each class interact with others.

When designing object-oriented systems, one of the challenges I often face is deciding when to use an abstract class instead of a concrete class. Abstract classes are useful when multiple related classes share common behavior. However, distinguishing between situations where an abstract class or concrete class can [sometimes be unclear.

Another thing is I learned the importance of maintainence when using filtering and sorting operations. Java streams are powerful but need to be careful handling when dealing with user inputs, as they are easily referenceable structures once used.

