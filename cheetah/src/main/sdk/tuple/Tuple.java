package com.netease.music.video.common.tuple;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Created by pandechuan on 2018/2/6.
 */
public abstract class Tuple implements Serializable {

    private static final long serialVersionUID = 4251937663189756777L;
    private final List<Object> values;

    protected Tuple(Object... values) {
        this.values = Collections.unmodifiableList(Arrays.asList(values));
    }

    public List<Object> toList() {
        return values;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tuple)) {
            return false;
        }
        Tuple tuple = (Tuple) o;
        return Objects.equals(values, tuple.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(values);
    }

    @Override
    public String toString() {
        return "(" + values.stream().reduce((t, u) -> t + "," + u).get() + ")";
    }
}
