/* Знакомство с тегами
Считать с консоли имя файла, который имеет HTML-формат
Пример содержания файла:
Info about Leela <span xml:lang="en" lang="en"><b><span>Turanga Leela
</span></b></span><span>Super</span><span>girl</span>
Первым параметром в метод main приходит тег. Например, "span"

Вывести на консоль все теги, которые соответствуют заданному тегу
Каждый тег на новой строке, порядок должен соответствовать порядку следования в файле
Файл не содержит тег CDATA, для всех открывающих тегов имеется отдельный закрывающий тег, одиночных тегов нету
Тег может содержать вложенные теги
Пример вывода:
<span xml:lang="en" lang="en"><b><span>Turanga Leela</span></b></span>
<span>Turanga Leela</span>
<span>Super</span>
<span>girl</span>
*/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Solution {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String file = reader.readLine();
        reader.close();
        reader = new BufferedReader(new FileReader(file));
        String s = "";
        while (reader.ready())
            s += reader.readLine();
        reader.close();

        ArrayList<String> result = new ArrayList<>();

        result = search(s, args[0], result);

        while (true)
        {
                String str = result.get(result.size()-1);
                System.out.println(str);
                result.remove(result.size()-1);
                if (str.split(args[0]).length > 3)
                {
                    str = str.substring(5, str.length()-7);
                    result = search(str, args[0], result);
                }
                if (result.size() == 0)
                    break;
        }
    }

    private static ArrayList<String> search(String s, String tag, ArrayList<String> result) {
        int innerCount = 0;
        String begining = "<"+tag;
        String ending = "</"+tag+">";
        while (s.contains(tag)) {
            int end = s.lastIndexOf(ending);
            for (int i = end; i >=ending.length(); i--) {
                String sub = s.substring(i-ending.length(), i);

                if (sub.equals(ending))
                    innerCount++;

                if (sub.contains(begining)) {
                    if (innerCount == 0)
                    {
                        String str = s.substring(i - ending.length(), end + ending.length());
                        result.add(str);
                        s = s.substring(0, i - ending.length());
                        break;
                    }
                    else
                    {
                        innerCount--;
                        i -= 2;
                    }
                }
            }
        }
        return result;
    }
}
