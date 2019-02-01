package evolution.convert;

/**
 * Created by pandechuan on 2018/11/27.
 */
public interface DTOConvert<A, B> {
    A convert(A a);
}
