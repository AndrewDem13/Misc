package com.javarush.test.level20.lesson10.bonus03;

import java.util.ArrayList;
import java.util.List;

/* Задание:
1. Дан двумерный массив, который содержит буквы английского алфавита в нижнем регистре.
2. Метод detectAllWords должен найти все слова из words в массиве crossword.
3. Элемент(startX, startY) должен соответствовать первой букве слова, элемент(endX, endY) - последней.
text - это само слово, располагается между начальным и конечным элементами
4. Все слова есть в массиве.
5. Слова могут быть расположены горизонтально, вертикально и по диагонали как в нормальном, так и в обратном порядке.
6. Метод main не участвует в тестировании
*/
public class Solution {
    public static void main(String[] args) {
        int[][] crossword = new int[][]{
                {'f', 'd', 'e', 'r', 'l', 'k'},
                {'u', 's', 'a', 'm', 'e', 'o'},
                {'l', 'n', 'g', 'r', 'o', 'v'},
                {'m', 'n', 'p', 't', 'r', 'h'},
                {'p', 'o', 'e', 'e', 'j', 'j'}
        };
        List<Word> list = detectAllWords(crossword,  "gae", "gml", "gro", "gtj", "gpe", "gnp", "gnl", "gsf", "rmr");
        for (Word word : list)
            System.out.println(word);
        /*
Ожидаемый результат
home - (5, 3) - (2, 0)
same - (1, 1) - (4, 1)
         */
    }

    public static List<Word> detectAllWords(int[][] crossword, String... words) {
        List<Word> result = new ArrayList<>();
        List<String> possWords;
        char ch;
        Word temp;

        // Проходимся по каждой букве в массиве
        for (int y = 0; y < crossword.length; y++) {
            for (int x = 0; x < crossword[y].length; x++) {
                ch = (char)crossword[y][x];
                possWords = getWords(ch, words); // Собираем искомые слова, которые могут начинаться на текущую букву
                for (String word : possWords) // Проходимся по всем этим словам
                {
                    temp = searchWord(word, crossword, y, x, 0, Directions.NULL); // Тут вся магия
                    if (temp != null)
                        result.add(temp);
                }
            }
        }

        /** В следующем блоке ищем отловленные палиндромы, которые у нас в списке дважды в 2 противоположных направлениях.

         Поскольку элементы у нас специфические, своего класса, то варианты удаления дубликатов с помощью Set отпадают,
         так как хоть текст у элементов Word и одинаковый, координаты начала и конца разные.

         В итоге проходимся по списку и копируем палиндромы во вспомогательный список, который потом используем для
         удаления палиндромов из result перед возвратом последнего из метода.

         P.S. Эта функция выходит за рамки задания, для ее реализации пришлось изменить данный изначально класс Word,
         добавив в него геттер текста и импровизированный геттер координат.
          */
        ArrayList<Word> repeated = new ArrayList<>();
        for (int i = 0; i < result.size()-1; i++) {
            for (int j = 1; j < result.size(); j++) {
                if (result.get(i).getText().equals(result.get(j).getText()) &&
                        !result.get(i).getCoordinates().equals(result.get(j).getCoordinates()))
                    repeated.add(result.get(i));
            }
        }
        for (Word w : repeated)
            result.remove(w);

        return result;
    }

    private static List<String> getWords(char ch, String... words) {
        List<String> result = new ArrayList<>();
        for (String word : words) {
            if (word.charAt(0)==ch)
                result.add(word);
        }
        return result;
    }


    /**
    Рекурсивный метод, исполняющий основную работу - поиск слова (word) в массиве (crossword), startY и startX - это
    координаты первой буквы, count - рабочий счетчик рекурсивных вызовов, dir - направление.

    Исходя из условия, слова могут читаться в 8 направлениях (для этого я создал Enum Directions) и не "ломаются".

    Первый проход всегда с направлением NULL, нужен для определения потенциального направления слова.

    case NULL достаточно громоздкая конструкция, которая перебирает каждое направление по очереди и после каждой пробы
    направления в случае его неудачи на n-ом витке рекурсии (например, 3 из 4 букв совпали) переходит к следующему.

    Конструкция try-catch в каждой проверке соседней буквы отлавливает случаи выхода за границы массива.
     */
    private static Word searchWord(String word, int[][] crossword, int startY, int startX, int count, Directions dir) {
        Word result = null;
        char nextChar;
        switch (dir) {
            case NULL:
                try {
                    if ((char) crossword[startY - 1][startX] == word.charAt(count+1))
                        result = searchWord(word, crossword, startY, startX, count, Directions.NORTH);
                } catch (Exception ignored) {}
                if (result != null)
                    return result;
                try {
                    if ((char) crossword[startY + 1][startX] == word.charAt(count+1))
                        result = searchWord(word, crossword, startY, startX, count, Directions.SOUTH);
                } catch (Exception ignored) {}
                if (result != null)
                    return result;
                try {
                    if ((char) crossword[startY][startX - 1] == word.charAt(count+1))
                        result = searchWord(word, crossword, startY, startX, count, Directions.WEST);
                } catch (Exception ignored) {}
                if (result != null)
                    return result;
                try {
                    if ((char) crossword[startY][startX + 1] == word.charAt(count+1))
                        result = searchWord(word, crossword, startY, startX, count, Directions.EAST);
                } catch (Exception ignored) {}
                if (result != null)
                    return result;
                try {
                    if ((char) crossword[startY + 1][startX - 1] == word.charAt(count+1))
                        result = searchWord(word, crossword, startY, startX, count, Directions.SOUTHWEST);
                } catch (Exception ignored) {}
                if (result != null)
                    return result;
                try {
                    if ((char) crossword[startY + 1][startX + 1] == word.charAt(count+1))
                        result = searchWord(word, crossword, startY, startX, count, Directions.SOUTHEAST);
                } catch (Exception ignored) {}
                if (result != null)
                    return result;
                try {
                    if ((char) crossword[startY - 1][startX - 1] == word.charAt(count+1))
                        result = searchWord(word, crossword, startY, startX, count, Directions.NORTHWEST);
                } catch (Exception ignored) {}
                if (result != null)
                    return result;
                try {
                    if ((char) crossword[startY - 1][startX + 1] == word.charAt(count+1))
                        result = searchWord(word, crossword, startY, startX, count, Directions.NORTHEAST);
                } catch (Exception ignored) {}
                if (result != null)
                    return result;
                break;

            case NORTH:
                try {
                    count++;
                    nextChar = (char)crossword[startY-1][startX];
                    if (nextChar == word.charAt(count))
                    {
                        if (word.length() == count+1)
                        {
                            result = new Word(word);
                            result.setStartPoint(startX, startY+count-1);
                            result.setEndPoint(startX, startY-1);
                            return result;
                        }
                        else
                            return searchWord(word, crossword, startY-1, startX, count, Directions.NORTH);
                    }
                } catch (Exception ignored) {}
                break;

            case SOUTH:
                try {
                    count++;
                    nextChar = (char)crossword[startY+1][startX];
                    if (nextChar == word.charAt(count))
                    {
                        if (word.length() == count+1)
                        {
                            result = new Word(word);
                            result.setStartPoint(startX, startY-count+1);
                            result.setEndPoint(startX, startY+1);
                            return result;
                        }
                        else
                            return searchWord(word, crossword, startY+1, startX, count, Directions.SOUTH);
                    }
                } catch (Exception ignored) {}
                break;

            case WEST:
                try {
                    count++;
                    nextChar = (char)crossword[startY][startX-1];
                    if (nextChar == word.charAt(count))
                    {
                        if (word.length() == count+1)
                        {
                            result = new Word(word);
                            result.setStartPoint(startX+count-1, startY);
                            result.setEndPoint(startX-1, startY);
                            return result;
                        }
                        else
                            return searchWord(word, crossword, startY, startX-1, count, Directions.WEST);
                    }
                } catch (Exception ignored) {}
                break;

            case EAST:
                try {
                    count++;
                    nextChar = (char)crossword[startY][startX+1];
                    if (nextChar == word.charAt(count))
                    {
                        if (word.length() == count+1)
                        {
                            result = new Word(word);
                            result.setStartPoint(startX-count+1, startY);
                            result.setEndPoint(startX+1, startY);
                            return result;
                        }
                        else
                            return searchWord(word, crossword, startY, startX+1, count, Directions.EAST);
                    }
                } catch (Exception ignored) {}
                break;

            case NORTHWEST:
                try {
                    count++;
                    nextChar = (char)crossword[startY-1][startX-1];
                    if (nextChar == word.charAt(count))
                    {
                        if (word.length() == count+1)
                        {
                            result = new Word(word);
                            result.setStartPoint(startX+count-1, startY+count-1);
                            result.setEndPoint(startX-1, startY-1);
                            return result;
                        }
                        else
                            return searchWord(word, crossword, startY-1, startX-1, count, Directions.NORTHWEST);
                    }
                } catch (Exception ignored) {}
                break;

            case NORTHEAST:
                try {
                    count++;
                    nextChar = (char)crossword[startY-1][startX+1];
                    if (nextChar == word.charAt(count))
                    {
                        if (word.length() == count+1)
                        {
                            result = new Word(word);
                            result.setStartPoint(startX-count+1, startY+count-1);
                            result.setEndPoint(startX+1, startY-1);
                            return result;
                        }
                        else
                            return searchWord(word, crossword, startY-1, startX+1, count, Directions.NORTHEAST);
                    }
                } catch (Exception ignored) {}
                break;

            case SOUTHWEST:
                try {
                    count++;
                    nextChar = (char)crossword[startY+1][startX-1];
                    if (nextChar == word.charAt(count))
                    {
                        if (word.length() == count+1)
                        {
                            result = new Word(word);
                            result.setStartPoint(startX+count-1, startY-count+1);
                            result.setEndPoint(startX-1, startY+1);
                            return result;
                        }
                        else
                            return searchWord(word, crossword, startY+1, startX-1, count, Directions.SOUTHWEST);
                    }
                } catch (Exception ignored) {}
                break;

            case SOUTHEAST:
                try {
                    count++;
                    nextChar = (char)crossword[startY+1][startX+1];
                    if (nextChar == word.charAt(count))
                    {
                        if (word.length() == count+1)
                        {
                            result = new Word(word);
                            result.setStartPoint(startX-count+1, startY-count+1);
                            result.setEndPoint(startX+1, startY+1);
                            return result;
                        }
                        else
                            return searchWord(word, crossword, startY+1, startX+1, count, Directions.SOUTHEAST);
                    }
                } catch (Exception ignored) {}
                break;
        }
        return result;
    }


    public static class Word {
        private String text;
        private int startX;
        private int startY;
        private int endX;
        private int endY;

        public Word(String text) {
            this.text = text;
        }

        /** Этот метод добавлен к данному в условии классу */
        public String getText() {
            return text;
        }
        /** Этот метод добавлен к данному в условии классу */
        public String getCoordinates() {
            return ""+startX+startY+endX+endY;
        }

        public void setStartPoint(int i, int j) {
            startX = i;
            startY = j;
        }

        public void setEndPoint(int i, int j) {
            endX = i;
            endY = j;
        }

        @Override
        public String toString() {
            return String.format("%s - (%d, %d) - (%d, %d)", text, startX, startY, endX, endY);
        }
    }

    private enum Directions
    {
        NORTH, SOUTH, EAST, WEST, NORTHEAST, NORTHWEST, SOUTHEAST, SOUTHWEST, NULL
    }
}