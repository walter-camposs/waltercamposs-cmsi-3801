import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Predicate;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Exercises {
    static Map<Integer, Long> change(long amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        var counts = new HashMap<Integer, Long>();
        for (var denomination : List.of(25, 10, 5, 1)) {
            counts.put(denomination, amount / denomination);
            amount %= denomination;
        }
        return counts;
    }

    public static Optional<String> firstThenLowerCase(List<String> strings, java.util.function.Predicate<String> predicate) {
        return strings.stream()
                .filter(predicate)
                .map(String::toLowerCase)
                .findFirst();
    }
    
    public static Say say() {
        return new Say("");
    }

    public static Say say(String initial) {
        return new Say(initial);
    }

    public static class Say {
        private StringBuilder phrase;
        public Say(String initial) {
            this.phrase = new StringBuilder(initial);
        }

        public Say and(String nextWord) {
            Say newSay = new Say(this.phrase.toString());
            if (newSay.phrase.length() > 0 || nextWord.isEmpty()) {
                newSay.phrase.append(" ");
            }
            newSay.phrase.append(nextWord); 
            return newSay; 
        }

        public String phrase() {
            return phrase.toString();
        }
    }

    public static long meaningfulLineCount(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            return reader.lines()
                    .filter(line -> !line.trim().isEmpty() && !line.trim().startsWith("#"))
                    .count();
        }
    }

}

record Quaternion(double a, double b, double c, double d) {

    public static final Quaternion ZERO = new Quaternion(0.0, 0.0, 0.0, 0.0);
    public static final Quaternion I = new Quaternion(0.0, 1.0, 0.0, 0.0);
    public static final Quaternion J = new Quaternion(0.0, 0.0, 1.0, 0.0);
    public static final Quaternion K = new Quaternion(0.0, 0.0, 0.0, 1.0);

    private static final double EPSILON = 1e-6; 

    public Quaternion {
    if (Double.isNaN(a) || Double.isNaN(b) || Double.isNaN(c) || Double.isNaN(d)) {
        System.out.println("Detected NaN value, throwing exception");
        throw new IllegalArgumentException("Coefficients cannot be NaN");
    }
}
    public Quaternion plus(Quaternion other) {
        return new Quaternion(
            this.a + other.a,
            this.b + other.b,
            this.c + other.c,
            this.d + other.d
        );
    }

    public Quaternion times(Quaternion other) {
        return new Quaternion(
            this.a * other.a - this.b * other.b - this.c * other.c - this.d * other.d,
            this.a * other.b + this.b * other.a + this.c * other.d - this.d * other.c,
            this.a * other.c - this.b * other.d + this.c * other.a + this.d * other.b,
            this.a * other.d + this.b * other.c - this.c * other.b + this.d * other.a
        );
    }


    public List<Double> coefficients() {
        return List.of(a, b, c, d);
    }

    public Quaternion conjugate() {
        return new Quaternion(a, -b, -c, -d);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Quaternion)) return false;
        Quaternion other = (Quaternion) o;
        return Math.abs(this.a - other.a) < EPSILON &&
            Math.abs(this.b - other.b) < EPSILON &&
            Math.abs(this.c - other.c) < EPSILON &&
            Math.abs(this.d - other.d) < EPSILON;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (Math.abs(a) > EPSILON) {
            sb.append(String.format("%.1f", a));
        }

        if (Math.abs(b) > EPSILON) {
            if (sb.length() > 0 && b > 0) sb.append("+"); 
            sb.append(String.format("%.1f", b)).append("i");
        }

        if (Math.abs(c) > EPSILON) {
            if (sb.length() > 0 && c > 0) sb.append("+");
            sb.append(String.format("%.1f", c)).append("j");
        }

        if (Math.abs(d) > EPSILON) {
            if (sb.length() > 0 && d > 0) sb.append("+"); 
            sb.append(String.format("%.1f", d)).append("k");
        }

        if (sb.length() == 0) {
            return "0";
        }

        return sb.toString();
    }
}

sealed interface BinarySearchTree permits Empty, Node {
    BinarySearchTree insert(String value);
    boolean contains(String value);
    int size();
    @Override
    String toString();
}

final class Empty implements BinarySearchTree {
    @Override
    public BinarySearchTree insert(String value) {
        return new Node(value, this, this);
    }

    @Override
    public boolean contains(String value) {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public String toString() {
        return "()";
    }
}

final class Node implements BinarySearchTree {
    private final String value;
    private final BinarySearchTree left;
    private final BinarySearchTree right;

    public Node(String value, BinarySearchTree left, BinarySearchTree right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    @Override
    public BinarySearchTree insert(String newValue) {
        int cmp = newValue.compareTo(value);
        if (cmp < 0) {
            return new Node(value, left.insert(newValue), right);
        } else if (cmp > 0) {
            return new Node(value, left, right.insert(newValue));
        } else {
            return this;
        }
    }

    @Override
    public boolean contains(String searchValue) {
        int cmp = searchValue.compareTo(value);
        if (cmp < 0) {
            return left.contains(searchValue);
        } else if (cmp > 0) {
            return right.contains(searchValue);
        } else {
            return true;
        }
    }

    @Override
    public int size() {
        return 1 + left.size() + right.size();
    }

    @Override
    public String toString() {
        String leftStr = left instanceof Empty ? "" : left.toString();
        String rightStr = right instanceof Empty ? "" : right.toString();
        return "(" + leftStr + value + rightStr + ")";
    }
}
