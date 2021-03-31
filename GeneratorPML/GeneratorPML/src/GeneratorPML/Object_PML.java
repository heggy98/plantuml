package GeneratorPML;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Object_PML {
    public String Body;
    public void getBodyFromCurlyBraces(String input){
        Pattern bodyPattern = Pattern.compile("(?<=\\{).*(?=\\})");
        Matcher bodyMatcher = bodyPattern.matcher(input);
        boolean bodyBoolean = bodyMatcher.find();
        if(bodyBoolean){
            this.Body = bodyMatcher.group();
        }
    }
}
