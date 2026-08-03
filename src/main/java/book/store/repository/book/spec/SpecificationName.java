package book.store.repository.book.spec;

public enum SpecificationName {
    TITLE("title"),
    AUTHOR("author"),
    DESCRIPTION("description"),
    ISBN("isbn");

    private String value;
    SpecificationName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
