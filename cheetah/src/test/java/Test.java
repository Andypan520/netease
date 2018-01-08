import java.time.Duration;
import java.time.Instant;
import java.util.stream.IntStream;

/**
 * Created by pandechuan on 2018/1/8.
 */
public class Test {
    public static void main(String[] args){
        Instant inst1 = Instant.now();
        IntStream.range(1,10000000).forEach(e ->System.out.println(e));
        Instant inst2 = Instant.now();
        System.out.println("Inst2 : " + inst2);
        System.out.println("Difference in milliseconds : " + Duration.between(inst1, inst2).getSeconds());
    }

}
