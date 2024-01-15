package com.example.sql;

import com.example.Administrator;
import com.example.Profesor;
import com.example.Student;
import com.example.SuperAdministrator;

import java.util.Random;

public class RandomPopulation {
    public static void populateSuperAdministrators(int howMany) {
        for (int i = 0; i < howMany; i++) {
            SuperAdministrator.insert(randomSuperAdministrator());
        }
    }

    public static void populateAdministrators(int howMany) {
        for (int i = 0; i < howMany; i++) {
            Administrator.insert(randomAdministrator());
        }
    }

    public static void populateProfesors(int howMany) {
        for (int i = 0; i < howMany; i++) {
            Profesor.insert(randomProfesor());
        }
    }

    public static void populateStudents(int howMany) {
        for (int i = 0; i < howMany; i++) {
            Student.insert(randomStudent());
        }
    }

    public static SuperAdministrator randomSuperAdministrator() {
        String cnp = randomCnp();
        String nume = randomName();
        String adresa = randomAddress();
        String nrTelefon = randomPhoneNumber();
        String username = randomUsernameFrom(nume);
        String email = randomEmailFrom(username);
        return new SuperAdministrator(0, cnp, nume, adresa, nrTelefon, email, username, DEFAULT_PASSWORD, SuperAdministrator.ID_ROL);
    }

    public static Administrator randomAdministrator() {
        String cnp = randomCnp();
        String nume = randomName();
        String adresa = randomAddress();
        String nrTelefon = randomPhoneNumber();
        String username = randomUsernameFrom(nume);
        String email = randomEmailFrom(username);
        return new Administrator(0, cnp, nume, adresa, nrTelefon, email, username, DEFAULT_PASSWORD, Administrator.ID_ROL);
    }

    public static Profesor randomProfesor() {
        String cnp = randomCnp();
        String nume = randomName();
        String departament = randomDepartment();
        int nrMinOre = randomNrMinOre();
        int nrMaxOre = randomNrMaxOreWithMin(nrMinOre);
        String adresa = randomAddress();
        String nrTelefon = randomPhoneNumber();
        String username = randomUsernameFrom(nume);
        String email = randomEmailFrom(username);
        return new Profesor(0, cnp, nume, departament, nrMinOre, nrMaxOre, adresa, nrTelefon, email, username, DEFAULT_PASSWORD, Profesor.ID_ROL);
    }

    public static Student randomStudent() {
        String cnp = randomCnp();
        String nume = randomName();
        int anStudiu = randomAnStudiu();
        int numarOre = randomNumarOre();
        String adresa = randomAddress();
        String nrTelefon = randomPhoneNumber();
        String username = randomUsernameFrom(nume);
        String email = randomEmailFrom(username);
        return new Student(0, cnp, nume, anStudiu, numarOre, adresa, nrTelefon, email, username, DEFAULT_PASSWORD, Student.ID_ROL);
    }

    private static final String[] firstNames = {"John", "Emma", "Michael", "Sophia", "William", "Olivia", "James", "Ava"};
    private static final String[] lastNames = {"Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson"};
    private static final String[] streetNames = {"Fulford", "Robinson", "Seafield", "Rosemount", "Anchorage", "Greenfinch", "Clyde", "Backhouse"};
    private static final String[] streetSuffixes = {"Street", "Lane", "Road", "Avenue"};
    private static final String[] emailDomains = {"gmail.com", "yahoo.com", "hotmail.com", "aol.com", "msn.com", "live.com"};
    private static final String[] departments = {"Business", "Health", "Education", "Psychology", "Computer Science"};

    private static final Random random = new Random();

    // password is hardcoded for now (so we can easily log into these users)
    private static final String DEFAULT_PASSWORD = "parola";
    private static final int MAX_AN_STUDIU = 6;
    private static final int MAX_NUMAR_ORE_STUDENT = 15;
    private static final int MAX_NUMAR_MIN_ORE_PROFESOR = 15;
    private static final int MAX_NUMAR_MAX_ORE_PROFESOR = 30;
    private static final int MAX_STREET_NUMBER = 100;

    private static int randomIntInclusive(int bound) {
        return random.nextInt(1, bound + 1);
    }

    private static String randomCnp() {
        StringBuilder cnp = new StringBuilder();
        while (cnp.length() < 13) {
            int digit = random.nextInt(10);
            cnp.append(digit);
        }
        return cnp.toString();
    }

    private static String randomName() {
        String firstName = firstNames[random.nextInt(firstNames.length)];
        String lastName = lastNames[random.nextInt(lastNames.length)];
        return firstName + " " + lastName;
    }

    private static String randomDepartment() {
        return departments[random.nextInt(departments.length)];
    }

    private static int randomNrMinOre() {
        return randomIntInclusive(MAX_NUMAR_MIN_ORE_PROFESOR);
    }

    private static int randomNrMaxOreWithMin(int nrMinOre) {
        return random.nextInt(nrMinOre + 1, MAX_NUMAR_MAX_ORE_PROFESOR + 1);
    }

    private static int randomAnStudiu() {
        return randomIntInclusive(MAX_AN_STUDIU);
    }

    private static int randomNumarOre() {
        return randomIntInclusive(MAX_NUMAR_ORE_STUDENT);
    }

    private static String randomAddress() {
        String streetName = streetNames[random.nextInt(streetNames.length)];
        String suffix = streetSuffixes[random.nextInt(streetSuffixes.length)];
        return randomIntInclusive(MAX_STREET_NUMBER) + " " + streetName + " " + suffix;
    }

    private static String randomPhoneNumber() {
        String prefix = "07";
        StringBuilder suffix = new StringBuilder();
        while (suffix.length() < 8) {
            int digit = random.nextInt(10);
            suffix.append(digit);
        }
        return prefix + suffix;
    }

    private static String randomUsernameFrom(String name) {
        int coolNumber = random.nextInt(0, 1000);
        return name.replace(" ", "_").toLowerCase() + coolNumber;
    }

    private static String randomEmailFrom(String name) {
        String domain = emailDomains[random.nextInt(emailDomains.length)];
        return name.replace(" ", "") + "@" + domain;
    }
}
