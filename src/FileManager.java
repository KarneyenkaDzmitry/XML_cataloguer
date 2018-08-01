/**
 * Created by Дмитрий on 17.02.2018.
 */

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.TreeSet;

public class FileManager {
    private Path rootPath;
    private Set<Path> fileList;
    private String suffix;

    public FileManager(Path rootPath, String suffix) throws IOException {
        this.rootPath = rootPath;
        fileList = new TreeSet<Path>();
        collectFileList(rootPath, suffix);
        this.suffix = suffix;
    }

    public Set<Path> getFileList() {
        return fileList;
    }

    private void collectFileList(Path path, String suffix) throws IOException {
        if (Files.isRegularFile(path)) {
            if (path.toString().endsWith(suffix)) {
                fileList.add(path.toAbsolutePath());
            }
        }
        if (Files.isDirectory(path)) {
            DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path);
            for (Path file : directoryStream) {
                collectFileList(file, suffix);
            }
        }
    }
}


