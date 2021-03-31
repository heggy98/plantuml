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
        Path path = Paths.get(System.getProperty("user.dir") + "\\src\\GeneratorPML\\input.txt");

        try {
            String input = Files.readAllLines(path).toString();
            var pac = compilePackage(input);
            System.out.println(pac.toString());

            String fileName = "out.txt";

            List<String> lines = new ArrayList<>();
            java.nio.file.Files.write(Path.of(fileName), lines);

            String oneBigString = pac.writeUml();
            java.nio.file.Files.writeString(Path.of(fileName), oneBigString);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Package_PML compilePackage(String input) {
        Pattern packageNamePattern = Pattern.compile("(?<=package\\s).*?(?=\\;)");
        Matcher packageNameMatcher = packageNamePattern.matcher(input);
        boolean packageMatchFound = packageNameMatcher.find();
        Package_PML package_pml = null;
        if (packageMatchFound) {
            String packageName = packageNameMatcher.group();
            package_pml = new Package_PML(packageName, Access.DEFAULT);
            package_pml.setBody(input);

            compileObjectsOfType(package_pml, ObjectType.INTERFACE);
            compileObjectsOfType(package_pml, ObjectType.CLASS);
        }
        return package_pml;
    }

    private static void compileObjectsOfType(Package_PML pack, ObjectType objectType) {
        for (Access accessType : Access.values()) {
            String access = accessType.toString().toLowerCase() + " ";
            if (accessType == Access.DEFAULT) {
                access = "";
            }

            InheritanceType inheritanceType = null;
            boolean objectFound = false;
            String wholeObject = "";
            do {
                String AccessWithObjectTypeInLowerCase = access + objectType.toString().toLowerCase();

                if (objectFound) {
                    String whatToReplace = AccessWithObjectTypeInLowerCase + " " + wholeObject;
                    pack.Body = pack.Body.replace(whatToReplace, "");
                }


                Pattern pattern = Pattern.compile("(?<=" + AccessWithObjectTypeInLowerCase + "\\s).*?(?=\\s\\{)[\\s\\S]*?(?=})");

                Matcher matcher = pattern.matcher(pack.Body);
                objectFound = matcher.find();

                if (objectFound) {
                    String matcherResult = matcher.group();

                    wholeObject = matcher.group() + "}";
                    Pattern namePattern = Pattern.compile(".*?(?=\\s\\{)");

                    if (objectType == ObjectType.CLASS) {

                        for (InheritanceType it : InheritanceType.values()) {
                            if (it == InheritanceType.ExtendsImplements) {
                                namePattern = Pattern.compile(".*?(?=\\sextends[\\s\\S)]*implements)");
                                Matcher nameMatcher = namePattern.matcher(wholeObject);
                                if (!nameMatcher.find()) {
                                    namePattern = Pattern.compile(".*?(?=\\s\\{)");
                                }
                                else{
                                    break;
                                }
                            }

                            if (it == InheritanceType.Implements) {
                                namePattern = Pattern.compile(".*?(?=\\simplements)");
                                Matcher nameMatcher = namePattern.matcher(wholeObject);
                                if (!nameMatcher.find()) {
                                    namePattern = Pattern.compile(".*?(?=\\s\\{)");
                                }
                                else{
                                    break;
                                }

                            }

                            if (it == InheritanceType.Extends) {
                                namePattern = Pattern.compile(".*?(?=\\sextends)");
                                Matcher nameMatcher = namePattern.matcher(wholeObject);
                                if (!nameMatcher.find()) {
                                    namePattern = Pattern.compile(".*?(?=\\s\\{)");
                                }
                                else{
                                    break;
                                }
                            }
                        }
                    }

                    MatchName(pack, objectType, accessType, wholeObject, namePattern);

                }
            }
            while (objectFound);
        }
    }

    private static Pattern getPattern(Package_PML pack, Access accessType, String wholeObject, Pattern namePattern) {

        return namePattern;
    }

    private static void MatchName(Package_PML pack, ObjectType objectType, Access accessType, String wholeObject, Pattern namePattern) {
        Matcher nameMatcher = namePattern.matcher(wholeObject);
        boolean nameMatchFound = nameMatcher.find();
        if (nameMatchFound) {
            String name = nameMatcher.group();
            Object_PML object_pml = new Object_PML(name, accessType);
            object_pml.getBodyFromCurlyBraces(wholeObject);

            if (objectType == ObjectType.CLASS) {
                CreateClass(pack, accessType, name, object_pml.Body);
            } else {
                CreateInterface(pack, accessType, name, object_pml.Body);
            }

        }
    }

    private static void CreateInterface(Package_PML pack, Access accessType, String name, String body) {
        Interface_PML interface_pml = new Interface_PML(name, accessType);
        interface_pml.setBody(body);

        String bodyOfInterface = interface_pml.Body + "}";

        bodyOfInterface = CreateMethods(interface_pml, bodyOfInterface);
        CreateAttributes(interface_pml, bodyOfInterface);

        pack.interfaces.add(interface_pml);
    }

    private static void CreateClass(Package_PML pack, Access accessType, String name, String body) {
        Class_PML class_pml = new Class_PML(name, accessType);
        class_pml.setBody(body);

        String bodyOfClass = class_pml.Body + "}";

        bodyOfClass = CreateMethods(class_pml, bodyOfClass);

        CreateAttributes(class_pml, bodyOfClass);

        pack.classes.add(class_pml);
    }

    private static void CreateAttributes(Object_PML object_pml, String body) {
        // ATTRIBUTES
        for (Access acc : Access.values()) {
            String access = acc.toString().toLowerCase();
            if (acc == Access.DEFAULT) {
                access = "";
            }

            boolean attributeFound = false;
            String wholeAttribute = "";
            do {
                if (attributeFound) {
                    String whatToReplace = access + " " + wholeAttribute + ";";
                    body = body.replace(whatToReplace, "");
                }


                Pattern pattern = Pattern.compile("(?<=" + access + "\\s).*?(?=\\;)");

                Matcher matcher = pattern.matcher(body);
                attributeFound = matcher.find();

                if (attributeFound) {
                    wholeAttribute = matcher.group();
                    String[] attribute = wholeAttribute.split(" ");
                    String attributeType = attribute[0];
                    String attributeName = attribute[1];
                    object_pml.Attributes.add(new Attribute_PML(attributeName, acc, attributeType));
                }
            }
            while (attributeFound);
        }
    }

    private static String CreateMethods(Object_PML object_pml, String bodyOfClass) {
        // METHODS
        for (Access acc : Access.values()) {
            String access = acc.toString().toLowerCase();
            if (acc == Access.DEFAULT) {
                access = "";
            }

            boolean methodFound = false;
            String wholeMethod = "";
            do {
                if (methodFound) {
                    String whatToReplace = access + " " + wholeMethod + "}";
                    bodyOfClass = bodyOfClass.replace(whatToReplace, "");
                }

                Pattern pattern = Pattern.compile("(?<=" + access + "\\s)[\\s\\S]*(?=})");

                Matcher matcher = pattern.matcher(bodyOfClass);
                methodFound = matcher.find();

                if (methodFound) {
                    wholeMethod = matcher.group();

                    Pattern nameAndTypePattern = Pattern.compile("(?<=" + access + "\\s).*?\\)");

                    Matcher nameAndTypeMatcher = nameAndTypePattern.matcher(bodyOfClass);
                    boolean nameAndTypeFound = nameAndTypeMatcher.find();

                    if (nameAndTypeFound) {
                        String result = nameAndTypeMatcher.group().trim();
                        String[] method = result.split(" ");
                        String methodType = method[0];

                        Pattern methodNamePattern = Pattern.compile(".*?(?=\\()");
                        Matcher methodNameMatcher = methodNamePattern.matcher(method[1]);
                        boolean methodNameFound = methodNameMatcher.find();
                        if(methodNameFound){
                            String methodName = methodNameMatcher.group();

                            Pattern methodParametersPattern = Pattern.compile("(?<=\\().*?(?=\\))");
                            Matcher methodParametersMatcher = methodParametersPattern.matcher(method[1]);
                            boolean methodParametersFound = methodParametersMatcher.find();

                            ArrayList<String> arrayList = new ArrayList<>();

                            if(methodParametersFound){
                                result = methodParametersMatcher.group().trim();
                                String[] parametersArray = result.split(",");
                                for(int i = 0; i < parametersArray.length; i++){
                                    if(!parametersArray[i].isEmpty()){
                                        arrayList.add(parametersArray[i]);
                                    }
                                }
                            }
                            if(arrayList.isEmpty()){
                                methodName = methodName + "()";
                            }
                            Method_PML method_pml = new Method_PML(methodName, acc, arrayList, methodType );
                            object_pml.Methods.add(method_pml);
                        }
                    }
                }
            }
            while (methodFound);
        }
        return bodyOfClass;
    }

//    public static Access setAccessModifier(Access access){
//        switch (access) {
//            case DEFAULT:
//                break;
//            default:
//                return Access.DEFAULT;
//                break;
//            case PUBLIC:
//                return PUBLIC;
//                break;
//            case PRIVATE:
//                return Access.PRIVATE;
//                break;
//            case PROTECTED:
//                return Access.PROTECTED;
//        }
//    }
}
