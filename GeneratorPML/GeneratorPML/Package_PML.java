package GeneratorPML;

import java.util.ArrayList;

public class Package_PML extends Object_PML {
    public String PackageName;
    public String PackageCode;
    public ArrayList<Interface_PML> Interfaces;
    public ArrayList<Class_PML> Classes;


    public Package_PML(String packageName) {
        PackageName = packageName;
    }
}