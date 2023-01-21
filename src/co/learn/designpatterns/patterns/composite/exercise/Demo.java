package co.learn.designpatterns.patterns.composite.exercise;

import co.learn.log.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

interface ValueContainer extends Iterable<Integer> {
}

class SingleValue implements ValueContainer {
    public int value;

    // please leave this constructor as-is
    public SingleValue(int value) {
        this.value = value;
    }

    @Override
    public Iterator<Integer> iterator() {
        return Collections.singleton(value).iterator();
    }

    @Override
    public void forEach(Consumer<? super Integer> action) {
        action.accept(value);
    }

    @Override
    public Spliterator<Integer> spliterator() {
        return Collections.singleton(value).spliterator();
    }
}

class ManyValues extends ArrayList<Integer> implements ValueContainer {
}


class MyList extends ArrayList<ValueContainer> {
    // please leave this constructor as-is
    public MyList(Collection<? extends ValueContainer> c) {
        super(c);
    }

    public int sum() {
        // todo
        int sum = 0;

        for (ValueContainer vc : this) {
            for (Integer i : vc) {
                sum += i;
            }
        }

        return sum;
    }
}

public class Demo {
    public static void main(String[] args) {
        ManyValues manyValues = new ManyValues();
        manyValues.add(3);
        manyValues.add(6);
        manyValues.add(9);
        manyValues.add(12);
        manyValues.add(15);

        SingleValue singleValue = new SingleValue(4);

        MyList myList = new MyList(Arrays.asList(singleValue, manyValues));
//        MyList myList = new MyList(Collections.singleton(manyValues));
        Log.log(myList.sum());
    }
}
