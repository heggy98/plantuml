package GeneratorPML;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        Path path = Paths.get("src/GeneratorPML/input.txt");

        try {
            var lines = Files.readAllLines(path).toString();

            Wrapper_PML wrapper_pml = null;

            Pattern umlPattern = Pattern.compile("(?<=@startUml)[\\s\\S]*(?=@enduml)", Pattern.CASE_INSENSITIVE);
            Matcher umlMatcher = umlPattern.matcher(lines);
            boolean umlMatchFound = umlMatcher.find();
            if(umlMatchFound) {
                System.out.println("UML CODE: " + umlMatcher.group());
                wrapper_pml = new Wrapper_PML(umlMatcher.group());
                Pattern packageNamePattern = Pattern.compile("(?<=package\\s).*?(?=\\{)");
                Matcher packageNameMatcher = packageNamePattern.matcher(umlMatcher.group());
                boolean packageMatchFound = packageNameMatcher.find();
                if(packageMatchFound) {
                    System.out.println("PACKAGE NAME: " + packageNameMatcher.group());
                    Package_PML pack = new Package_PML(packageNameMatcher.group());
                    wrapper_pml.Packages.add(pack);
                    pack.getBodyFromCurlyBraces(umlMatcher.group());
                    System.out.println("PACKAGE BODY: " + pack.Body);
                }
            }



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
