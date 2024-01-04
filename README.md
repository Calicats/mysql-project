# CSP-11: Functions for reading all the tables

## Description

You should make a function for all the tabels that returns a `List<TableName>` with all the entries. Like this:

`List<TableName> getEntriesTableName()`

Also, we might need a second function like this: 

`TableName getEntriesTableName(int id)`

So you should also make a class with data for all the tabels, for example a class Student which holds data for a student.

## Implementation

I have created a class for each table. The names are identical, so for example `class SuperAdministrator` in **Java** would correspond to the `SuperAdministrator` table in **MySQL**.

Each class contains:

- A constructor that takes an `id` as a parameter. The instantiated class will have the data of the entry with the given `id`.
- A static method `getTable()` that returns an `ArrayList` containing all of the entries in the corresponding table.
- Getter methods for each value in the table.
- `toString()` implemented for easy debugging.

## TODO

- Actually test the methods.
    - Maybe use Random-Database-Population to populate the tables?
- Proper error handling for instantiating table classes with an invalid `id`.
