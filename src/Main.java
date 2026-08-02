import java.util.*;

public class Main {

    static final String RESET = "\u001B[0m";
    static final String BOLD = "\u001B[1m";

    static final Scanner scanner = new Scanner(System.in);
    static final Random random = new Random();

    public static void main(String[] args) {
        String username = welcome();
        boolean playing = true;

        while (playing) {
            Theme theme = showDashboard(username);
            playTheme(username, theme);
            playing = askPlayAgain();
        }

        System.out.println(BOLD + "\nThanks for playing, " + username + "! See you next time.\n" + RESET);
        scanner.close();
    }

    static String welcome() {
        System.out.println(BOLD + "=======================================");
        System.out.println("        WELCOME TO THEMED MADLIBS");
        System.out.println("=======================================" + RESET);
        System.out.print("Enter your username to begin: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) name = "Player";
        System.out.println("\nHi " + name + "! Let's pick a story theme.\n");
        return name;
    }

    static Theme showDashboard(String username) {
        Theme[] themes = Theme.values();
        System.out.println(BOLD + "----------- MADLIBS DASHBOARD -----------" + RESET);
        for (int i = 0; i < themes.length; i++) {
            System.out.println(themes[i].color + " " + (i + 1) + ". " + themes[i].displayName + " " + RESET);
        }
        System.out.println(BOLD + "------------------------------------------" + RESET);

        int choice = -1;
        while (choice < 1 || choice > themes.length) {
            System.out.print(username + ", pick a theme (1-" + themes.length + "): ");
            String input = scanner.nextLine().trim();
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                choice = -1;
            }
            if (choice < 1 || choice > themes.length) {
                System.out.println("Please enter a number between 1 and " + themes.length + ".");
            }
        }
        return themes[choice - 1];
    }

    static void playTheme(String username, Theme theme) {
        Template template = theme.templates[random.nextInt(theme.templates.length)];

        System.out.println(theme.background + theme.color + BOLD);
        System.out.println(theme.banner);
        System.out.println(RESET + theme.color + theme.tagline + RESET);
        System.out.println();

        String[] answers = new String[template.prompts.length];
        for (int i = 0; i < template.prompts.length; i++) {
            System.out.print(theme.color + template.prompts[i] + ": " + RESET);
            answers[i] = scanner.nextLine();
        }

        String story = String.format(template.storyFormat, (Object[]) answers);

        System.out.println();
        System.out.println(theme.background + theme.color + BOLD + " YOUR STORY " + RESET);
        System.out.println(theme.color + story + RESET);
        System.out.println();
    }

    static boolean askPlayAgain() {
        System.out.print("Play again with a different theme? (y/n): ");
        String answer = scanner.nextLine().trim().toLowerCase();
        return answer.startsWith("y");
    }

    enum Theme {
        F1(
            "F1 Racing",
            "\u001B[41m", "\u001B[97m",
            "  _____ _ \n |  ___/ |\n | |_  | |\n |  _| | |\n |_|   |_|\n",
            "Lights out and away we go!",
            new Template[] {
                new Template(
                    new String[] {"Enter an adjective", "Enter a noun", "Enter an adjective", "Enter a verb ending in -ing", "Enter an adjective", "Enter a driver's name"},
                    "\nThe %s Scuderia Ferrari %s looked %s as %s\n%s through the %s streets of Monza."
                ),
                new Template(
                    new String[] {"Enter a number", "Enter an adjective", "Enter a noun", "Enter a verb ending in -ing", "Enter an exclamation"},
                    "\nWith %s laps to go, the %s %s came flying past the pit wall,\n%s toward the checkered flag. \"%s!\" screamed the crowd."
                )
            }
        ),
        HARRY_POTTER(
            "Harry Potter",
            "\u001B[40m", "\u001B[33m",
            "  _  _ ____\n | || |  _ \\\n | __ | |_) |\n |_||_|  __/\n         |_|\n",
            "The wand chooses the wizard...",
            new Template[] {
                new Template(
                    new String[] {"Enter an adjective", "Enter a noun", "Enter a spell (made up word)", "Enter an adjective", "Enter a creature"},
                    "\nInside the %s halls of Hogwarts, a %s glowed faintly.\n\"%s!\" shouted the young wizard, as a %s %s appeared from the shadows."
                ),
                new Template(
                    new String[] {"Enter a name", "Enter an adjective", "Enter a noun", "Enter a verb ending in -ing", "Enter a place in Hogwarts"},
                    "\n%s crept through the %s corridor, clutching a %s tightly.\n%s quietly, they made their way to the %s."
                )
            }
        ),
        MARVEL(
            "Marvel Universe",
            "\u001B[44m", "\u001B[91m",
            "  __  __                     _\n |  \\/  | __ ___   _____| |\n | |\\/| |/ _` \\ \\ / / _ \\ |\n | |  | | (_| |\\ V /  __/ |\n |_|  |_|\\__,_| \\_/ \\___|_|\n",
            "Heroes assemble!",
            new Template[] {
                new Template(
                    new String[] {"Enter a superhero name", "Enter an adjective", "Enter a noun", "Enter a verb ending in -ing", "Enter an exclamation"},
                    "\n%s stood atop the %s building, gripping a %s.\n%s into the sky, they shouted, \"%s!\" as the battle began."
                ),
                new Template(
                    new String[] {"Enter an adjective", "Enter a villain name", "Enter a noun", "Enter a place", "Enter a verb ending in -ing"},
                    "\nThe %s %s unleashed a %s upon %s.\nOnly the Avengers, %s toward danger, could stop it now."
                )
            }
        );

        final String displayName;
        final String background;
        final String color;
        final String banner;
        final String tagline;
        final Template[] templates;

        Theme(String displayName, String background, String color, String banner, String tagline, Template[] templates) {
            this.displayName = displayName;
            this.background = background;
            this.color = color;
            this.banner = banner;
            this.tagline = tagline;
            this.templates = templates;
        }
    }

    static class Template {
        final String[] prompts;
        final String storyFormat;

        Template(String[] prompts, String storyFormat) {
            this.prompts = prompts;
            this.storyFormat = storyFormat;
        }
    }
}
