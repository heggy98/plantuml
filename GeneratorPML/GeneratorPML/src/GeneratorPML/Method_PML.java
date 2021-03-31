package GeneratorPML;

import java.util.ArrayList;

public class Method_PML extends Object_PML {
    public ArrayList<String> Parameters;
    public String type;

    public Method_PML(String name, GeneratorPML.Access access, ArrayList<String> parameters, String type) {
        super(name, access);
        Parameters = parameters;
        this.type = type;
    }

    @Override
    public String toString() {
        return this.Name + " : " + type;
    }
}
