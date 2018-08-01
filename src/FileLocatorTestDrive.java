import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
/*The class for test. It has positive tests and negative tests. Just run it and you will see a result.*/
public class FileLocatorTestDrive {
    private static List<String[]> listOfPositive = new ArrayList<String[]>();
    private static Path path;
    static {
        path = Paths.get(System.getProperty("user.dir"));
        String[] pTest1 = {path + "\\tests\\Ptest1", "xlsx", path + "\\tests\\Ptest1ExistXML.xml"};
        String[] pTest2 = {path + "\\tests\\Ptest2", "xlsx", path + "\\tests\\Ptest2\\Ptest1.xml"};
        String[] pTest3 = {path + "\\tests\\Ptest2", path + "\\tests\\Ptest3", path + "\\tests\\Ptest4", "xlsx", path + "\\tests\\Ptest3.xml"};
        String[] nTest1 = {path + "\\tests\\Ptest1", "xlsx"};
        String[] nTest2 = {"xlsx"};
        String[] nTest3 = {path + "\\tests\\Ptest1"};
        String[] nTest4 = {};
        String[] nTest5 = {path + "\\tests\\Ptest2", path + "\\tests\\Ptest3", path + "\\tests\\Ptest4", "xlsx", path + "\\tests\\Ptest4.xml"};
        String[] nTest6 = {path + "\\tests\\Ptest2", path + "\\tests\\Ptest3", path + "\\tests\\Ptest4", "xlsx", path + "\\tests\\Ptest3.mp3"};
        String[] nTest7 = {path + "\\tests\\Ptest2", path + "\\tests\\Ptest3", path + "\\tests\\Ptest4", path + "\\tests\\Ptest3.xml"};
        String[] nTest8 = {path + "\\tests\\Ptest2\\dirNotExist", path + "\\tests\\Ptest3", path + "\\tests\\Ptest4", path + "\\tests\\Ptest3.xml"};
        String[] nTest9 = {path + "\\tests\\Ptest2", path + "\\tests", path + "\\tests\\Ptest4", "xlsx",  path + "\\tests\\Ptest3.xml"};
        listOfPositive.add(pTest1);
        listOfPositive.add(pTest2);
        listOfPositive.add(pTest3);
        listOfPositive.add(nTest1);
        listOfPositive.add(nTest2);
        listOfPositive.add(nTest3);
        listOfPositive.add(nTest4);
        listOfPositive.add(nTest5);
        listOfPositive.add(nTest6);
        listOfPositive.add(nTest7);
        listOfPositive.add(nTest8);
        listOfPositive.add(nTest9);
    }

    public static void main(String[] args) throws IOException {
        int i = 0;
        for (String[] test : listOfPositive) {
            System.out.println("Test №" + ++i);
            FileLocator.main(test);
            System.out.println("________________________________________");
        }
    }
}
