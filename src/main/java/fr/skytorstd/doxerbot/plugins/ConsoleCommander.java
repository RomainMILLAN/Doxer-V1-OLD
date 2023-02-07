package fr.skytorstd.doxerbot.plugins;

import fr.skytorstd.doxerbot.App;
import fr.skytorstd.doxerbot.manager.Console;
import fr.skytorstd.doxerbot.messages.ConsoleCommanderMessages;
import fr.skytorstd.doxerbot.states.ConsoleState;

import java.util.Scanner;

public class ConsoleCommander {

    public static void ligarConsoleCommander() throws InterruptedException {
        while(true){
            Console.getInstance().toConsole(ConsoleCommanderMessages.CC_INFO_STOP.getMessage(), ConsoleState.CONSOLE);
            Console.getInstance().toConsole(ConsoleCommanderMessages.CC_INFO_RELOAD.getMessage(), ConsoleState.CONSOLE);
            try (Scanner scanner = new Scanner(System.in)) {
                String consoleCommand = scanner.nextLine();


                if(consoleCommand.equals("!stop")){
                    App.getJda().shutdown();
                    Console.getInstance().toConsole(ConsoleCommanderMessages.CC_STOP.getMessage(), ConsoleState.CONSOLE);
                    System.exit(1);
                } else if(consoleCommand.equals("!reload")){
                    App.getJda().shutdown();
                    Console.getInstance().toConsole(ConsoleCommanderMessages.CC_RELOAD.getMessage(), ConsoleState.CONSOLE);
                    App.run();
                } else
                    Console.getInstance().toConsole(ConsoleCommanderMessages.CC_ERROR_COMMAND.getMessage(), ConsoleState.ERROR);
            }
        }
    }

}
