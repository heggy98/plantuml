package GeneratorPML;

public class Interface_PML extends Object_PML{
    public Interface_PML(String name, Access access) {
        super(name, access);
    }

    @Override
    public String toString() {
        String stringToReturn = "\n\t\t" + super.toString();
        stringToReturn = stringToReturn + super.attributessAndMethodsToString();
        return  stringToReturn;
    }
}
