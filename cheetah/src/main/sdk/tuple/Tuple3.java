package com.netease.music.video.common.tuple;

import java.io.Serializable;

/**
 * Created by pandechuan on 2018/2/6.
 */
public final class Tuple3<A, B, C> extends Tuple implements Serializable {

    private static final long serialVersionUID = 8514682757928741565L;
    private final A a;
    private final B b;
    private final C c;

    private Tuple3(A a, B b, C c) {
        super(a, b, c);

        this.a = a;
        this.b = b;
        this.c = c;
    }

    /**
     * Creates a new instance of Tuple3 with the specified values.
     */
    public static <A, B, C> Tuple3<A, B, C> of(A a, B b, C c) {
        return new Tuple3<>(a, b, c);
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

    /**
     * Returns the third value.
     */
    public C third() {
        return c;
    }
}
