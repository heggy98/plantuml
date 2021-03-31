package GeneratorPML;

import java.util.ArrayList;

public class Wrapper_PML {
    public String Code;
    public ArrayList<Package_PML> Packages;

    public Wrapper_PML(String code) {
        Code = code;
        this.Packages = new ArrayList<>();
    }
}
