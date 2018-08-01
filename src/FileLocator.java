import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileLocator {

    private static String xmlPath;
    private static String typeOfDoc;
    private static Set<Path> paths = new TreeSet<Path>();

    private static String help = "Recall the program and put at least three parameters as arguments via space.\n" +
            "For example: C:/SomePath1/ docx c:/results.xml\n" +
            "You can enter as many directories as you want!\n" +
            "However the last two arguments have to be:\n" +
            "1. Documents' expansion without dot or spaces. For example: docx \n" +
            "2. Path to existing or not existing XML - file. For example: c:/results.xml\n" +
            "I know you smart. You can do it.";
/*
This method checks the arguments for validity. Return tru or false.
true - valid, false - invalid.*/
    private static boolean checkArgs(String[] args) {
        for (int i = 0; i < args.length - 2; i++) {
            Path path = Paths.get(args[i]);
            if ((!Files.exists(path)) && (!Files.isDirectory(path))) {
                System.out.println("The wrong parameter: \"" + path + "\" It isn't directory!");
                return false;
            }
        }
        xmlPath = args[args.length - 1];
        if (!xmlPath.endsWith(".xml")) {
            System.out.println("The last argument isn't an XML file");
            return false;
        }
        if (Files.notExists(Paths.get(xmlPath))) {
            System.out.println("XML file doesn't exist.");
            System.out.println(help);
            return false;
        }
        typeOfDoc = args[args.length - 2];
        Pattern pattern = Pattern.compile("\\W");
        Matcher matcher = pattern.matcher(typeOfDoc);
        if (matcher.find()) {
            System.out.println("Wrong parameter of documents' expansion!\n" + help);
            return false;
        }
        return true;
    }
/*The method return String with results.*/
    private static String results() throws IOException {
        StringBuilder builder = new StringBuilder();
        for (Path path : paths) {
            builder.append(path.toFile().getAbsolutePath() + "\n");
        }
        builder.deleteCharAt(builder.lastIndexOf("\n"));
        return builder.toString();
    }
/*The method write data to XML file*/
    private static void xmlWriter() throws IOException {
        StringBuilder builder = new StringBuilder("<?xml version=\"1.0\" encoding = \"UTF-8\"?>\n<files>");
        for (Path path : paths) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.YYYY HH:MM:s");
            BasicFileAttributes attrs = Files.readAttributes(path.toAbsolutePath(), BasicFileAttributes.class);
            byte[] bytes = Files.readAllBytes(path.toAbsolutePath());
            Date date = new Date(attrs.creationTime().toMillis());
            long size = bytes.length;
            builder.append("<file>\n<name>" + path.getFileName() + "</name>\n<path>" +
                    path.getParent().toAbsolutePath() + "</path>\n" +
                    "<size>" + size + "</size>\n" + "<datetime>" + sdf.format(date) + "</datetime>\n</file>\n");
        }
        builder.append("</files>");
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(Paths.get(xmlPath).toString()));
        bufferedWriter.write(builder.toString());
        bufferedWriter.close();
    }
/*Its a main method.*/
    public static void main(String[] args) throws IOException {
        if (args.length > 2) {
            if (checkArgs(args)) {
                for (int i = 0; i < args.length - 2; i++) {
                    Path path = Paths.get(args[i]);
                    try {
                        FileManager fileManager = new FileManager(path, typeOfDoc);//Special class that return Set of paths
                        paths.addAll(fileManager.getFileList());
                    } catch (IOException e) {
                        System.out.println("Something bad with \"" + path + "\"");
                        e.printStackTrace();
                    }
                }
                System.out.println(results());
                xmlWriter();
                paths.clear();
            }
        } else {
            System.out.println("There aren't enough arguments!\n" + help);
        }
    }
}
