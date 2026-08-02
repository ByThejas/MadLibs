import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        String adjective1;
        String noun1;
        String adjective2;
        String verb1;
        String adjective3;

        System.out.print("Enter an Adjective: ");
        adjective1 = scanner.nextLine();
        System.out.print("Enter an Noun: ");
        noun1 = scanner.nextLine();
        System.out.print("Enter an Adjective: ");
        adjective2 = scanner.nextLine();
        System.out.print("Enter an Verb: ");
        verb1 = scanner.nextLine();
        System.out.print("Enter an Adjective: ");
        adjective3 = scanner.nextLine();

        System.out.println("\nThe " + adjective1 + " Scuderia Ferrari");
        System.out.println(noun1 + " looked " + adjective2 + " as Charles Leclerc");
        System.out.println(verb1 + " through the");
        System.out.println(adjective3 + " streets of Monza.");

        scanner.close();
    }
}
