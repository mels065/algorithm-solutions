
public class PerfectPower {

    public static int[] isPerfectPower(int n) {
        for (double i = 2; i <= Math.log(n) * 2; i++) {
            double rootValue = Math.round(Math.pow(n, 1 / i));

            if (Math.pow(rootValue, i) == n) {
                int[] result = {(int) rootValue, (int) i};
                return result;
            }
        }

        return null;
    }
}
