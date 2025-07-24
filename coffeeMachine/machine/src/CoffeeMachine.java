package machine;

import java.util.Scanner;

public class CoffeeMachine {
    private int cups;
    private int balance;
    private int milk;
    private int water;
    private int coffeeBeans;
    private int coffeesBeforeCleaning = 0;

    private static Scanner scanner = new Scanner(System.in);

    public CoffeeMachine(int water, int milk, int coffeeBeans, int cups, int balance) {
        this.water = water;
        this.milk = milk;
        this.coffeeBeans = coffeeBeans;
        this.cups = cups;
        this.balance = balance;
    }

    public static void main(String[] args) {
        CoffeeMachine machine = new CoffeeMachine(400, 540, 120, 9, 550);
        while (true) {
            machine.choose();
        }
    }

    private void choose() {
        System.out.println("Write action (buy, fill, take, clean, remaining, exit): ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "buy" -> buy();
            case "fill" -> fill();
            case "take" -> take();
            case "clean" -> clean();
            case "remaining" -> printState();
            case "exit" -> System.exit(0);
            default -> choose();
        }
    }

    private void clean() {
        System.out.println("I have been cleaned!");
        coffeesBeforeCleaning = 0;
    }

    private boolean checkIfClean() {
        if (coffeesBeforeCleaning == 10) {
            System.out.println("I need cleaning!");
            return (false);
        }
        return (true);
    }

    private void buy() {
        if (!checkIfClean())
            return;
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: ");
        String input = scanner.nextLine();

        switch (input) {
            case "1" -> makeCoffee(CoffeeType.ESPRESSO);
            case "2" -> makeCoffee(CoffeeType.LATTE);
            case "3" -> makeCoffee(CoffeeType.CAPPUCCINO);
            case "back" -> {
                return;
            }
            default -> buy();
        }
    }

    private void makeCoffee(CoffeeType type) {
        if (!checkResources(type.water, type.milk, type.beans)) {
            return;
        }

        this.water -= type.water;
        this.milk -= type.milk;
        this.coffeeBeans -= type.beans;
        this.balance += type.price;
        this.cups--;
        coffeesBeforeCleaning++;

        System.out.println("I have enough resources, making you a coffee!");
    }

    private boolean checkResources(int changeWater, int changeMilk, int changeBeans) {
        if (water < changeWater) {
            System.out.println("Sorry, not enough water!");
            return false;
        }
        if (milk < changeMilk) {
            System.out.println("Sorry, not enough milk!");
            return false;
        }
        if (coffeeBeans < changeBeans) {
            System.out.println("Sorry, not enough coffee beans!");
            return false;
        }
        if (cups < 1) {
            System.out.println("Sorry, not enough cups!");
            return false;
        }
        return true;
    }

    private void fill() {
        System.out.println("Write how many ml of water you want to add: ");
        int addWater = Integer.parseInt(scanner.nextLine());
        this.water += addWater;

        System.out.println("Write how many ml of milk you want to add: ");
        int addMilk = Integer.parseInt(scanner.nextLine());
        this.milk += addMilk;

        System.out.println("Write how many grams of coffee beans you want to add: ");
        int addCoffeeBeans = Integer.parseInt(scanner.nextLine());
        this.coffeeBeans += addCoffeeBeans;

        System.out.println("Write how many disposable cups you want to add: ");
        int addCups = Integer.parseInt(scanner.nextLine());
        this.cups += addCups;
    }

    private void take() {
        System.out.printf("I gave you $%d\n", balance);
        this.balance = 0;
    }

    private void printState() {
        System.out.println("The coffee machine has:");
        System.out.printf("%d ml of water\n", water);
        System.out.printf("%d ml of milk\n", milk);
        System.out.printf("%d g of coffee beans\n", coffeeBeans);
        System.out.printf("%d disposable cups\n", cups);
        System.out.printf("$%d of money\n", balance);
    }
}

enum CoffeeType {
    ESPRESSO(250, 0, 16, 4),
    LATTE(350, 75, 20, 7),
    CAPPUCCINO(200, 100, 12, 6);

    final int water, milk, beans, price;

    CoffeeType(int water, int milk, int beans, int price) {
        this.water = water;
        this.milk = milk;
        this.beans = beans;
        this.price = price;
    }
}
