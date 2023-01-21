package co.learn.designpatterns.patterns.prototype.three;

import co.learn.log.Log;
import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;

class Foo implements Serializable {
    public int stuff;
    public String whatever;

    public Foo(int stuff, String whatever) {
        this.stuff = stuff;
        this.whatever = whatever;
    }

    @Override
    public String toString() {
        return "Foo{" +
                "stuff=" + stuff +
                ", whatever='" + whatever + '\'' +
                '}';
    }
}

public class Demo {
    public static void main(String[] args) {
        Foo foo = new Foo(42, "whatever");
        Foo foo2 = SerializationUtils.roundtrip(foo);

        foo2.whatever = "whatever2";

        Log.log(foo);
        Log.log(foo2);
    }
}
