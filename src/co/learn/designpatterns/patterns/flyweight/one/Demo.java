package co.learn.designpatterns.patterns.flyweight.one;

import co.learn.log.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

class User {
    private String fullName;

    public User(String fullName) {
        this.fullName = fullName;
    }
}

class User2
{
    static List<String> strings = new ArrayList<>();
    private int[] names;

    public User2(String fullName)
    {
        Function<String, Integer> getOrAdd = (String s) -> {
            int idx = strings.indexOf(s);
            if (idx != -1) return idx;
            else {
                strings.add(s);
                return strings.size() - 1;
            }
        };

        names = Arrays.stream(fullName.split(" "))
                .mapToInt(getOrAdd::apply).toArray();

        strings.forEach(s -> Log.logLine(s));
        Log.logLine();

        for (int name : names) {
            Log.logLine(name);
        }
    }

    public String getFullName()
    {
        return Arrays.stream(names).mapToObj(i -> strings.get(i))
                .collect(Collectors.joining(","));
    }
}

public class Demo {
    public static void main(String[] args) {
        User2 user = new User2("John Smith");
        User2 user1 = new User2("Jane Smith");

        Log.logLine(user.getFullName());
    }
}
