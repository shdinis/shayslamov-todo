package ru.javarush.todo.exception;

public class InvalidIdException extends RuntimeException{
    public InvalidIdException(String message) {
        super(message);
    }
}
