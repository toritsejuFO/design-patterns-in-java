package co.learn.designpatterns.patterns.singleton.seven;

import org.apache.commons.collections4.ListUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

interface Database
{
    int getPopulation(String name);
}

class SingletonDatabase implements Database {
    private static final SingletonDatabase INSTANCE = new SingletonDatabase();
    private static int instanceCount = 0;
    private Dictionary<String, Integer> capitals = new Hashtable<>();

    private SingletonDatabase() {
        instanceCount++;
        System.out.println("Initializing database");

        try {
            File f = new File(
                    Demo.class.getProtectionDomain()
                            .getCodeSource().getLocation().getPath()
            );
            Path fullPath = Paths.get(f.getPath(), "capitals.txt");
            List<String> lines = Files.readAllLines(fullPath);

            ListUtils.partition(lines, 2)
                    .forEach(kv -> capitals.put(
                            kv.get(0).trim(),
                            Integer.parseInt(kv.get(1))
                    ));
        } catch (Exception e) {
            // handle it!
            System.err.println(e);
        }
    }

    public static int getCount() {
        return instanceCount;
    }

    public static SingletonDatabase getInstance() {
        return INSTANCE;
    }

    public int getPopulation(String name) {
        return capitals.get(name);
    }
}

class SingletonRecordFinder {
    public int getTotalPopulation(List<String> names) {
        int result = 0;
        for (String name : names)
            result += SingletonDatabase.getInstance().getPopulation(name);
        return result;
    }
}

class DummyDatabase implements Database
{
    private Dictionary<String, Integer> data = new Hashtable<>();

    public DummyDatabase() {
        data.put("alpha", 1);
        data.put("beta", 2);
        data.put("gamma", 3);
    }

    @Override
    public int getPopulation(String name)
    {
        return data.get(name);
    }
}

class ConfigurableRecordFinder
{
    private Database database;

    public ConfigurableRecordFinder(Database database) {
        this.database = database;
    }

    public int getTotalPopulation(List<String> names)
    {
        int result = 0;
        for (String name : names)
            result += database.getPopulation(name);
        return result;
    }
}

public class Demo {
    @Test
    public void singletonTotalPopulationTest() {
        // testing on a live database
        SingletonRecordFinder rf = new SingletonRecordFinder();
        List<String> names = List.of("Lagos", "Asaba");
        int tp = rf.getTotalPopulation(names);
        Assertions.assertEquals(200 + 90, tp);
    }

    @Test
    public void dependentPopulationTest()
    {
        DummyDatabase db = new DummyDatabase();
        ConfigurableRecordFinder rf = new ConfigurableRecordFinder(db);
        Assertions.assertEquals(4, rf.getTotalPopulation(
                List.of("alpha", "gamma")
        ));
    }
}
