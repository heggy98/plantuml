package GeneratorPML;

public enum Access {
    PUBLIC,
    PRIVATE,
    PROTECTED,
    DEFAULT{
        public String toString() {
            return "";
        }
    };
}

