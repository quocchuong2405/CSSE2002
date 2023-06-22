package srg.game;

import srg.cli.given.CLIHandler;
import srg.cli.given.Command;
import srg.cli.given.IO;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        IO ioHandler = new IO();
        CLIHandler commandParser = new CLIHandler();

        boolean exit;
        do {
            Command command = commandParser.parse(ioHandler);
            exit = game.update(ioHandler, command);
        } while (!exit);
    }
}