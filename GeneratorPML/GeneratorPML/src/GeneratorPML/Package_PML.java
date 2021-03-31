package GeneratorPML;

import java.util.ArrayList;

public class Package_PML extends Object_PML {
    public ArrayList<Interface_PML> interfaces;
    public ArrayList<Class_PML> classes;

    public Package_PML(String name, Access access) {
        super(name, access);
        interfaces = new ArrayList<>();
        classes = new ArrayList<>();
    }

    @Override
    public String toString() {
        String stringToReturn = super.toString() + " {\n\tInterfaces: " + interfaces.toString() + "\n\tClasses: " + classes.toString() + "\n}";
        return stringToReturn;
    }

    public String writeUml(){
        StringBuilder sb = new StringBuilder();
        sb.append("@startuml\n");

        sb.append("package " + Name + "{\n");

        for (var i: interfaces) {
            sb.append("interface " + i.Name + "{\n");
            for (var a: i.Attributes) {
                if(a.Access == GeneratorPML.Access.PUBLIC){
                    sb.append("+ " + a.Name + " : " + a.type + "\n");
                }
                else if(a.Access == GeneratorPML.Access.PRIVATE){
                    sb.append("- " + a.Name + " : " + a.type + "\n");
                }
                else{
                    sb.append(a.Name + " : " + a.type + "\n");
                }
            }
            for (var m: i.Methods) {
                if(m.Access == GeneratorPML.Access.PUBLIC){
                    sb.append("+ " + m.Name + " : " + m.type + "\n");
                }
                else if(m.Access == GeneratorPML.Access.PRIVATE){
                    sb.append("- " + m.Name + " : " + m.type + "\n");
                }
                else{
                    sb.append(m.Name + " : " + m.type + "\n");
                }
            }
        }

        for (var c: classes) {
            sb.append("class " + c.Name + "{");
            for (var a: c.Attributes) {
                if(a.Access == GeneratorPML.Access.PUBLIC){
                    sb.append("+ " + a.Name + " : " + a.type + "\n");
                }
                else if(a.Access == GeneratorPML.Access.PRIVATE || a.Access == GeneratorPML.Access.DEFAULT){
                    sb.append("- " + a.Name + " : " + a.type + "\n");
                }
                else{
                    sb.append(a.Name + " : " + a.type + "\n");
                }
            }
            for (var m: c.Methods) {
                if(m.Access == GeneratorPML.Access.PUBLIC){
                    sb.append("+ " + m.Name + " : " + m.type + "\n");
                }
                else if(m.Access == GeneratorPML.Access.PRIVATE || m.Access == GeneratorPML.Access.DEFAULT){
                    sb.append("- " + m.Name + " : " + m.type + "\n");
                }
                else{
                    sb.append(m.Name + " : " + m.type + "\n");
                }
            }
        }

        sb.append("@enduml");
        return sb.toString();
    }
}