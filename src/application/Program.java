package application;

import model.services.UserInteractionService;

import java.io.IOException;

public class Program {
    public static void main(String[] args) throws IOException {
        UserInteractionService.start();
    }

}
