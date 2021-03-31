package GeneratorPML;

import java.util.ArrayList;

public class Class_PML extends Object_PML {
    public ArrayList<String> Extenders;
    public ArrayList<String> Implements;

    public Class_PML(String name, Access access) {
        super(name, access);
        this.Extenders = new ArrayList<>();
        this.Implements = new ArrayList<>();
    }

    @Override
    public String toString() {
        String stringToReturn = "\n\t\t" + super.toString();
        if(!Extenders.isEmpty()){
            stringToReturn = stringToReturn + "\n\t\t\tExtend: " + Extenders.toString();
        }
        if(!Implements.isEmpty()){
            stringToReturn = stringToReturn + "\n\t\t\tImplements: " + Implements.toString();
        }
        stringToReturn = stringToReturn + super.attributessAndMethodsToString();
        return  stringToReturn;
    }
}
