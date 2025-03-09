# Report

Submitted report to be manually graded. We encourage you to review the report as you read through the provided
code as it is meant to help you understand some of the concepts. 

## Technical Questions

1. What is the difference between == and .equals in java? Provide a code example of each, where they would return different results for an object. Include the code snippet using the hash marks (```) to create a code block.
   ```java
   String str1 = new String("hello");
   String str2 = new String("hello");
   
   System.out.println(str1 == str2); //true
   System.out.println(str1.equals(str2)); //false
   
   public boolean equals(Object obj) {
        if (this == obj) return true; // check reference
        if (obj == null || getClass() != obj.getClass()) return false;
      }
   }
   
   ```
   `==` checks whether two obejects point to the same object in memory. `.equals()` check whether two objects are logically equal. Without overriding `equals()`, the default `equals()` method is the same as `==`


2. Logical sorting can be difficult when talking about case. For example, should "apple" come before "Banana" or after? How would you sort a list of strings in a case-insensitive manner? 

   We can converts each string into lowercase before comparing.



3. In our version of the solution, we had the following code (snippet)
    ```java
    public static Operations getOperatorFromStr(String str) {
        if (str.contains(">=")) {
            return Operations.GREATER_THAN_EQUALS;
        } else if (str.contains("<=")) {
            return Operations.LESS_THAN_EQUALS;
        } else if (str.contains(">")) {
            return Operations.GREATER_THAN;
        } else if (str.contains("<")) {
            return Operations.LESS_THAN;
        } else if (str.contains("=="))...
    ```
    Why would the order in which we checked matter (if it does matter)? Provide examples either way proving your point. 

   The checking order matters because if we check for `>` before `>=`, then any string containing `>=` will be misinterpreted as just `>`.

4. What is the difference between a List and a Set in Java? When would you use one over the other? 

   Use `List` when we need duplicate elements and indexed access. Use `Set` when unique is required.


5. In [GamesLoader.java](src/main/java/student/GamesLoader.java), we use a Map to help figure out the columns. What is a map? Why would we use a Map here? 

   Map allows storing and retrieving values based on key.


6. [GameData.java](src/main/java/student/GameData.java) is actually an `enum` with special properties we added to help with column name mappings. What is an `enum` in Java? Why would we use it for this application?

   `enum` defines a fixed set of constant values. It is used when a variable has already been defined in fixed set. We use enum for operations. Defines filtering operations like `>=` `<=` `==`.







7. Rewrite the following as an if else statement inside the empty code block.
    ```java
    switch (ct) {
                case CMD_QUESTION: // same as help
                case CMD_HELP:
                    processHelp();
                    break;
                case INVALID:
                default:
                    CONSOLE.printf("%s%n", ConsoleText.INVALID);
            }
    ``` 

    ```java
    if (ct == ConsoleText.CMD_QUESTION || ct == ConsoleText.CMD_HELP) {
        processHelp();
    } else if (ct == ConsoleText.INVALID) {
        CONSOLE.printf("%s%n", ConsoleText.INVALID);
    } else {
        CONSOLE.printf("%s%n", ConsoleText.INVALID);
    }

    ```

## Deeper Thinking

ConsoleApp.java uses a .properties file that contains all the strings
that are displayed to the client. This is a common pattern in software development
as it can help localize the application for different languages. You can see this
talked about here on [Java Localization – Formatting Messages](https://www.baeldung.com/java-localization-messages-formatting).

Take time to look through the console.properties file, and change some of the messages to
another language (probably the welcome message is easier). It could even be a made up language and for this - and only this - alright to use a translator. See how the main program changes, but there are still limitations in 
the current layout. 

Post a copy of the run with the updated languages below this. Use three back ticks (```) to create a code block. 

```text
*******歡迎來到桌遊規劃工具！*******

這是一款工具，幫助您計劃在 Board Game Arena 上想要玩的遊戲。
請在下方輸入您的第一個指令，或輸入 ? 或 help 來查看可用指令。
```

Now, thinking about localization - we have the question of why does it matter? The obvious
one is more about market share, but there may be other reasons.  I encourage
you to take time researching localization and the importance of having programs
flexible enough to be localized to different languages and cultures. Maybe pull up data on the
various spoken languages around the world? What about areas with internet access - do they match? Just some ideas to get you started. Another question you are welcome to talk about - what are the dangers of trying to localize your program and doing it wrong? Can you find any examples of that? Business marketing classes love to point out an example of a car name in Mexico that meant something very different in Spanish than it did in English - however [Snopes has shown that is a false tale](https://www.snopes.com/fact-check/chevrolet-nova-name-spanish/).  As a developer, what are some things you can do to reduce 'hick ups' when expanding your program to other languages?


As a reminder, deeper thinking questions are meant to require some research and to be answered in a paragraph for with references. The goal is to open up some of the discussion topics in CS, so you are better informed going into industry. 

The purpose of localization is to expand the market, enabling users from different backgrounds to understand and use the software. By enhancing user experience with displaying content in the user’s native language,  localization promotes accessibility, ensuring that content is culturally appropriate for different user groups.Companies that offer multilingual support can strengthen brand image, demonstrating competitiveness and increasing revenue in international markets[^1].

For instance, Weee!, a well-known North American e-commerce platform. Targeting Asian customers for groceries shopping. Since its majority customers are Asian, Weee! provides Chinese, Korean, and Japanese language support to attract diverse users. However, if Weee! fails to accurately translate product names or promotional messages, users may become confused, reducing their willingness to purchase.  If localization is not handled properly, some user groups may feel overlooked, impacting customer loyalty[^2].

[^1]Al-Tarawneh, A. (2024). Translating User Experience: Localization Strategies for E-Commerce Websites. In Frontiers of Human Centricity in the Artificial Intelligence-Driven Society 5.0 (pp. 1125-1134). Springer, Cham.

[^2]Marcus, A., & Gould, E. W. (2012). Globalization, localization, and cross-cultural user-interface design. The human-computer interaction handbook: Fundamentals, evolving technologies, and emerging applications, 341-366.