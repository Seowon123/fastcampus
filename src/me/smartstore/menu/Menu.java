package me.smartstore.menu;

import me.smartstore.exception.InputEndException;
import me.smartstore.exception.InputFormatException;
import me.smartstore.exception.InputRangeException;
import me.smartstore.util.Message;

import java.util.Scanner;

public interface Menu {
    Scanner scanner = new Scanner(System.in);

    default String nextLine() {
        String str = scanner.nextLine().toUpperCase();
        String[] strings = str.split("\\s");
        return (strings.length > 1) ? "" : str;
    }

    default String nextLine(String messageForEnd) {
        System.out.println("\n** Press 'end', if you want to exit! **");
        String str = scanner.nextLine().toUpperCase();
        if (str.equals(messageForEnd)) throw new InputEndException();

        String[] strings = str.split("\\s");
        return (strings.length > 1) ? "" : str;
    }

    default int nextInt(String messageForEnd) {
        String str = nextLine();
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            throw new InputFormatException();
        }

    }

    default int chooseMenu(String[] menus) {
        while ( true ) {
            try {
                System.out.println("\n==============================");
                for (int i = 0; i < menus.length; i++) {
                    System.out.printf(" %d. %s\n", i + 1, menus[i]);
                }
                System.out.println("==============================");
                System.out.print("Choose One: ");
                int choice = Integer.parseInt(nextLine());
                if (choice >= 1 && choice <= menus.length) {
                    return choice;
                }
                throw new InputRangeException();
            } catch (NumberFormatException e) {
                System.out.println(Message.ERR_MSG_INVALID_INPUT_FORMAT);
            } catch (InputRangeException e) {
                System.out.println(Message.ERR_MSG_INVALID_INPUT_RANGE);
            }
        }
    }

    void manage(); // 각 서브메뉴들을 관리하는 함수 (각 서브메뉴의 최상위 메뉴)
}
