package Core;

public class Term {
    private String word;
    private int position;

    public Term(String word, int position) {
        this.word = word;
        this.position = position;
    }

    public String getWord() {
        return word;
    }

    public int getPosition() {
        return position;
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;

        Term term = (Term) object;

        if (position != term.position) return false;
        if (word != null ? !word.equals(term.word) : term.word != null) return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (word != null ? word.hashCode() : 0);
        result = 31 * result + position;
        return result;
    }
}