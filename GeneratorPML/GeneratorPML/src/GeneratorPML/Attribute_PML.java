package GeneratorPML;

public class Attribute_PML extends Object_PML{
    public String type;
    public Attribute_PML(String name, GeneratorPML.Access access, String type) {
        super(name, access);
        this.type = type;
    }

    @Override
    public String toString() {
        return this.Name + " : " + type;
    }
}
