import static com.leacox.motif.Motif.match;
import static com.leacox.motif.hamcrest.CaseThatCases.caseEq;

/**
 * Created by pandechuan on 2018/2/2.
 */
public class CaseThatExample {
    /**
     * A main method to run the hamcrest example.
     */
    public static void main(String[] args) {
        new CaseThatExample().run();
    }

    private void run() {
        Object pi = Math.PI;
        String result = match(pi)
                .when(caseEq(42)).get(t -> "a magic no.")
                .when(caseEq("Hello!")).get(t -> "a greet")
                .when(caseEq(Math.PI)).get(t -> "another magic no.")
                .orElse("something else")
                .getMatch();

        System.out.println("Matching Result: " + result);
    }

}
