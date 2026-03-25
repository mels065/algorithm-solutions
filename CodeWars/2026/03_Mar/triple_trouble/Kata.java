
public class Kata {

    public static String tripleTrouble(String one, String two, String three) {
        // Solution
        int strSize = one.length(); // Since strings are all same size, use the size of one of them.
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < strSize; i++) {
            result
                    .append(one.charAt(i))
                    .append(two.charAt(i))
                    .append(three.charAt(i));
        }

        return result.toString();
    }
}
