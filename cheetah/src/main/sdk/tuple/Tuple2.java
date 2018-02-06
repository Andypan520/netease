package com.netease.music.video.common.tuple;

import java.io.Serializable;

/**
 * Created by pandechuan on 2018/2/6.
 */
public final class Tuple2<A, B> extends Tuple implements Serializable {

    private static final long serialVersionUID = 3787953943275384262L;
    private final A a;
    private final B b;

    private Tuple2(A a, B b) {
        super(a, b);

        this.a = a;
        this.b = b;
    }

    /**
     * Creates a new instance of Tuple2 with the specified values.
     */
    public static <A, B> Tuple2<A, B> of(A a, B b) {
        return new Tuple2<>(a, b);
    }

    /**
     * Returns the first value.
     */
    public A first() {
        return a;
    }

    /**
     * Returns the second value.
     */
    public B second() {
        return b;
    }
}
