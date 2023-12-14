import java.util.Random;

public class RandomNameGenerator {

    private static final String[] firstNames = {"John", "Emma", "Michael", "Sophia", "William", "Olivia", "James", "Ava"};
    private static final String[] lastNames = {"Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson"};

    public static void main(String[] args) {
        String randomName = generateRandomName();
        System.out.println("Random Name: " + randomName);
    }

    public static String generateRandomName() {
        Random random = new Random();
        String firstName = firstNames[random.nextInt(firstNames.length)];
        String lastName = lastNames[random.nextInt(lastNames.length)];
        return firstName + " " + lastName;
    }

    public static String generateRandomCnp(){
        Random random = new Random();
        return String.valueOf(random.nextInt(1000000000));
    }
}
