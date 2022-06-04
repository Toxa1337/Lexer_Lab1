package DSL.token;

public record Token(String type, String value, int position, int line) {
    //

    //
    @Override
    public String toString() { // МОЖНО ПОМЕНЯТЬ

        return "" + position +
                ".  '" + type + '\'' +
                " -> '" + value + '\'' +
                "";
    }

    public String insteadWas() {
        return "'" + type + '\'' +
                " -> '" + value + '\'' +
                "";
    }
}
