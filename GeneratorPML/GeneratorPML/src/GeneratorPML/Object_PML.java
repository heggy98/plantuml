package GeneratorPML;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Object_PML {
    public String Name;
    public String Body;
    public Access Access;
    public ArrayList<Method_PML> Methods = new ArrayList<>();
    public ArrayList<Attribute_PML> Attributes = new ArrayList<>();

    public void setBody(String body) {
        Body = body;
    }

    public Object_PML(String name, Access access) {
        Name = name;
        Access = access;
    }

    @Override
    public String toString() {
        if(Access == GeneratorPML.Access.DEFAULT){
            return Access.toString().toLowerCase() + Name;
        }
        return Access.toString().toLowerCase() + " " + Name;
    }

    public String attributessAndMethodsToString(){
        String stringToReturn = "";
        if(!Attributes.isEmpty()){
            stringToReturn = stringToReturn + "\n\t\t\tAttributes: " + Attributes.toString();
        }
        if(!Methods.isEmpty()){
            stringToReturn = stringToReturn + "\n\t\t\tMethods: " + Methods.toString();
        }
        return stringToReturn;
    }

    public void getBodyFromCurlyBraces(String input){
        Pattern bodyPattern = Pattern.compile("(?<=\\{)[\\s\\S]*?(?=\\})");
        Matcher bodyMatcher = bodyPattern.matcher(input);
        boolean bodyBoolean = bodyMatcher.find();
        if(bodyBoolean){
            this.Body = bodyMatcher.group();
        }


    }
}
