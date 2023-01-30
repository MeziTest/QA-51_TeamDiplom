package ru.netology;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PlayerTest {

    // Исходя из беглого анализа кода, в двух методах 100% есть ошибки: в методах installGame и mostPlayerByGenre
    // (здесь вообще пустой метод). Методы play и sumGenre вроде работают более-менее.
    // Геттер getName вообще непонятно куда девать, вероятно придется вообще убрать.
    // Также в методе play требуется написать класс-исключение для RunTimeException
    // В методе play также неверно считает сумму проигранных часов

    @Test
    public void shouldInstall() {
        GameStore store = new GameStore();
        Game game = store.publishGame("Нетология Баттл Онлайн", "Аркады");

        Player player = new Player("Vasya");
        player.installGame(game);
        boolean expected = true;
        boolean actual = store.containsGame(game);
        assertEquals(actual,expected);
    }

    @Test
    public void shouldSumGenreIfOneGame() {
        GameStore store = new GameStore();
        Game game = store.publishGame("Нетология Баттл Онлайн", "Аркады");
        Player player = new Player("Petya");
        player.installGame(game);
        player.play(game, 3);

        int expected = 3;
        int actual = player.sumGenre(game.getGenre());
        assertEquals(expected, actual);
    }
    @Test
    public void shouldSumGenreIfTwoGames() {
        GameStore store = new GameStore();
        Game game1 = store.publishGame("Нетология Баттл Онлайн", "Аркады");
        Game game2 = store.publishGame("Doodle jump", "Аркады");

        Player player = new Player("Petya");
        player.installGame(game1);
        player.installGame(game2);
        player.play(game1, 3);
        player.play(game2, 6);

        int expected = 9;
        int actual = player.sumGenre(game1.getGenre() + game2.getGenre());
        assertEquals(expected, actual);
    }
    @Test
    public void shouldNotSumGenreIfZeroGames() {
        GameStore store = new GameStore();
        Game game = store.publishGame(null, null);

        Player player = new Player("Petya");

        int expected = 0;
        int actual = player.sumGenre(game.getGenre());
        assertEquals(expected, actual);
    }
    @Test
    public void shouldFindMostPlayedGenreOfOne() {
        GameStore store = new GameStore();
        Game game = store.publishGame("PUBG Онлайн", "Шутеры");
        Player player = new Player("Kolya");
        player.installGame(game);
        player.play(game, 3);
        String expected = "PUBG Онлайн";
        Game actual = player.mostPlayerByGenre(game.getGenre());
        assertEquals(expected,actual);

    }
    @Test
    public void shouldFindMostPlayedGenreOfTwo() {
        GameStore store = new GameStore();
        Game game = store.publishGame("PUBG Онлайн", "Шутеры");
        Game game2 = store.publishGame("Нетология Баттл Онлайн", "Аркады");
        Player player = new Player("Kolya");
        player.installGame(game);
        player.installGame(game2);
        player.play(game, 3);
        player.play(game2,2);
        String expected = "PUBG Онлайн";
        Game actual = player.mostPlayerByGenre(game.getGenre() + game2.getGenre());
        assertEquals(expected,actual);

    }
    @Test
    public void shouldNotFindMostPlayedGenre() {
        GameStore store = new GameStore();
        Game game = store.publishGame(null, null);
        Player player = new Player("Anya");
        player.installGame(null);
        String expected = null;
        Game actual = player.mostPlayerByGenre(game.getGenre());
        assertEquals(expected,actual);

    }
    @Test
    public void shouldPlay() {
        GameStore store = new GameStore();
        Game game = store.publishGame("PUBG Онлайн", "Шутеры");
        Player player = new Player("Anya");
        player.installGame(game);
        int expected = 3;
        int actual = player.play(game,3);
        assertEquals(expected,actual);
    }
    @Test
    public void shouldPlayIfPlayedTwice() {
        GameStore store = new GameStore();
        Game game = store.publishGame("PUBG Онлайн", "Шутеры");
        Player player = new Player("Anya");
        player.installGame(game);
        int expected = 8;
        int actual = player.play(game,3) + player.play(game, 5);
        assertEquals(expected,actual);
    }
    @Test
    public void shouldCatchRuntimeExceptionInMethodPlay() {
        GameStore store = new GameStore();
        Game game = store.publishGame("Doodle jump", "Аркады");
        Player player = new Player("Petya");

        Assertions.assertThrows(RuntimeException.class, () -> {
            player.play(game,3);
        });
    }

    // другие ваши тесты
}
