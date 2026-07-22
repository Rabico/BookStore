package book.store.repository.book.spec;

public enum SpecificationName {
    TITTLE("tittle"),
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
