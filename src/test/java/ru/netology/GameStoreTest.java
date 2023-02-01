package ru.netology;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameStoreTest {
    GameStore emptyStore = new GameStore();
    GameStore oneGameStore = new GameStore();
    GameStore severalGamesStore = new GameStore();
    GameStore onePlayerStore = new GameStore();

    //создаем четыре вида каталога: пустой, с одной игрой, с несколькими играми, с одним игроком (игрок с 0h игры)
    @BeforeEach
    public void setup() {
        oneGameStore.publishGame("Нетология Баттл Онлайн", "Аркады");
        severalGamesStore.publishGame("Герои 3", "Стратегия");
        severalGamesStore.publishGame("Ведьмак", "Экшен");
        severalGamesStore.publishGame("Хитман", "Шутер");
        severalGamesStore.publishGame("Мортал Комбат", "Файтинг");
        severalGamesStore.publishGame("Обитель зла", "Выживание");
        severalGamesStore.publishGame("Дарк Соулс", "Соулслайк");

        onePlayerStore.addPlayTime("Мощные Штаны", 0);
        oneGameStore.addPlayTime("Мощные Штаны", 15);
        severalGamesStore.addPlayTime("Агент00ПОбеда", 26);
        severalGamesStore.addPlayTime("Старожил", 57);
        severalGamesStore.addPlayTime("Кратос", 17);
        severalGamesStore.addPlayTime("Мощные Штаны", 1);
        severalGamesStore.addPlayTime("Разрушитель3000", 31);
        severalGamesStore.addPlayTime("Дима2004", 0);
    }

    // проверяем метод добавления игр в каталоги: пустой, с одной игрой, с несколькими играми
    @Test
    public void shouldAddNewGameInEmptyStore() {

        Game game = emptyStore.publishGame("Нетология Баттл Онлайн", "Аркады");

        assertTrue(emptyStore.containsGame(game));
    }

    @Test
    public void shouldAddNewGameInOneGameStore() {

        Game game = oneGameStore.publishGame("Герои 3", "Стратегия");

        assertTrue(oneGameStore.containsGame(game));
    }

    @Test
    public void shouldAddNewGameInSeveralGameStore() {
        Game game = severalGamesStore.publishGame("Портал 2", "Аркады");

        assertTrue(severalGamesStore.containsGame(game));
    }

    // Проверяем добавление уже существующей игры в каталог. Должна выдаваться либо одна игра с тестируемым
    //названием, либо ошибка
    @Test
    public void shouldThrowAlreadyExistsException() {

        Assertions.assertThrows(AlreadyExistsException.class, () -> {
            severalGamesStore.publishGame("Мортал Комбат", "Файтинг");
        });
    }

    //Тестируем метод проверки наличия игры в каталоге
    @Test
    public void shouldTrueIfGameExist() {
        Game game1 = new Game("Нетология Баттл Онлайн", "Аркады", oneGameStore);

        assertTrue(oneGameStore.containsGame(game1));
    }

    @Test
    public void shouldFalseIfNonexistentGame() {
        Game game1 = new Game("Портал 2", "Аркады", severalGamesStore);

        assertFalse(severalGamesStore.containsGame(game1));
    }

    //Тесты на количество игрового времени

    //Проверяем зачисление времени игры для игрока. 1-й сценарий - не играл в игру, в store - один игрок
    //Actual невозможно получить без get для метода addPlayTime. Возможно, тесты надо переделать, если
    //переписываем метод под return
    @Test
    public void shouldAccrueHoursIf0HPlayed() {

        int expected = 5;
        int actual = onePlayerStore.addPlayTime("Мощные Штаны", 5);

        Assertions.assertEquals(expected, actual);
    }

    // 2-й сценарий - играл в игру 1 час (граничное значение), в store - несколько игроков
    @Test
    public void shouldAccrueHoursIf1HPlayed() {

        int expected = 6;
        int actual = severalGamesStore.addPlayTime("Мощные Штаны", 5);

        Assertions.assertEquals(expected, actual);
    }

    //3-й сценарий - играл в игру больше 1-го часа, в store - несколько игроков
    @Test
    public void shouldAccrueHours() {

        int expected = 70;
        int actual = severalGamesStore.addPlayTime("Старожил", 13);

        Assertions.assertEquals(expected, actual);
    }

    //Проверка зачисления времени в store для нового игрока (нет игроков в store)
    @Test
    public void shouldAccrueHoursNewPlayerEmptyPlayerStore() {

        int expected = 2;
        int actual = emptyStore.addPlayTime("Старожил", 2);

        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void shouldCatchRunTimeException() {

        Assertions.assertThrows(RuntimeException.class, () -> {
            emptyStore.addPlayTime("Старожил", -1);
        });
    }

    //Проверка зачисления времени новому в store игроку (один игрок в store)
    @Test
    public void shouldAccrueHoursNewPlayerOnePlayerStore() {

        int expected = 5;
        int actual = onePlayerStore.addPlayTime("Разрушитель3000", 5);

        Assertions.assertEquals(expected, actual);
    }

    //Проверка зачисления времени новому в store игроку (несколько игроков в store)
    @Test
    public void shouldAccrueHoursNewPlayerSeveralPlayerStore() {

        int expected = 10;
        int actual = severalGamesStore.addPlayTime("Легенда", 10);

        Assertions.assertEquals(expected, actual);
    }

    //Проверка метода getMostPlayer.
    //Тест на магазин, где нет игроков
    @Test
    public void shouldNotFindBestPlayerInEmptyStore() {

        String expected = null;
        String actual = emptyStore.getMostPlayer();

        Assertions.assertEquals(expected, actual);
    }

    //Тест на магазин с одним игроком
    @Test
    public void shouldFindBestPlayerInOnePlayerStore() {

        String expected = "Мощные Штаны";
        String actual = oneGameStore.getMostPlayer();

        Assertions.assertEquals(expected, actual);
    }

    //Тест на магазин с несколькими игроками
    @Test
    public void shouldFindBestPlayerInSeveralPlayersStore() {

        String expected = "Старожил";
        String actual = severalGamesStore.getMostPlayer();

        Assertions.assertEquals(expected, actual);
    }

    //Метод addPlayTime не суммирует время наигранных часов

    //Из ТЗ не ясно, какой игрок из равенства часов должен возвращаться
    //По нынещней реализации метода - это игрок, который первым зафиксирован по количеству часов
    @Test
    public void shouldFindBestPlayerAmongEqualHours() {
        severalGamesStore.addPlayTime("Кратос", 40);

        String expected = "Старожил";
        String actual = severalGamesStore.getMostPlayer();

        Assertions.assertEquals(expected, actual);
    }

    //Тесты на проверку метода getSumPlayedTime
    //Тест на проверку пустого store
    @Test
    public void shouldNotCountSumInEmptyStore() {

        int expected = 0;
        int actual = emptyStore.getSumPlayedTime();

        Assertions.assertEquals(expected, actual);
    }

    //Тест на проверку суммы часов в store из одного игрока
    @Test
    public void shouldCountSumInOnePlayerStore() {

        int expected = 15;
        int actual = oneGameStore.getSumPlayedTime();

        Assertions.assertEquals(expected, actual);
    }

    //Тест на проверку суммы часов в store из нескольких игроков
    @Test
    public void shouldCountSumInSeveralPlayersStore() {

        int expected = 132;
        int actual = severalGamesStore.getSumPlayedTime();

        Assertions.assertEquals(expected, actual);
    }
}
