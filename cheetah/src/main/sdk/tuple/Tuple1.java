package com.netease.music.video.common.tuple;

import java.io.Serializable;

/**
 * Created by pandechuan on 2018/2/6.
 */
public final class Tuple1<A> extends Tuple implements Serializable {

    private static final long serialVersionUID = -3542274821371629971L;
    private final A a;

    private Tuple1(A a) {
        super(a);

        this.a = a;
    }

    /**
     * Creates a new instance of Tuple1 with the specified value.
     */
    public static <A> Tuple1<A> of(A a) {
        return new Tuple1<>(a);
    }

    /**
     * Returns the first and only value.
     */
    public A first() {
        return a;
    }
}