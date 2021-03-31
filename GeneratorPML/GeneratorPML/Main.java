package GeneratorPML;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        Path path = Paths.get(System.getProperty("user.dir") + "\\GeneratorPML\\GeneratorPML\\src\\GeneratorPML\\input.txt");

        try {
            var lines = Files.readAllLines(path).toString();

            Pattern packageNamePattern = Pattern.compile("(?<=package\\s).*?(?=\\;)");
            Matcher packageNameMatcher = packageNamePattern.matcher(lines);
            boolean packageMatchFound = packageNameMatcher.find();
            if(packageMatchFound) {
                System.out.println("PACKAGE NAME: " + packageNameMatcher.group());
                Package_PML package_pml = new Package_PML(packageNameMatcher.group());
                package_pml.getBodyFromCurlyBraces(lines);
                System.out.println("PACKAGE BODY: " + package_pml.Body);

                Pattern interfaceNamePattern = Pattern.compile("(?<=interface\\s).*?(?=\\s\\{)");
                Matcher interfaceNameMatcher = interfaceNamePattern.matcher(lines);
                boolean interfaceNameMatchFound = interfaceNameMatcher.find();
                if(interfaceNameMatchFound){
                    Interface_PML interface_pml = new Interface_PML(interfaceNameMatcher.group());
                    package_pml.inter
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
