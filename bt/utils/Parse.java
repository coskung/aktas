package bt.utils;

public class Parse {
    private String DELIM = ";";
    private String row;

    public Parse(final String row, final String delim) {
        this.row = row;
        this.DELIM = delim;
    }

    public String nextToken() {
        if (row == null) {
            return null;
        }
        int token = row.indexOf(DELIM);
        if (token < 0) {
            String tmp = row;
            row = null;
            return tmp;
        }
        String value = row.substring(0, token);
        row = row.substring(token + 1);
        return value;
    }
}