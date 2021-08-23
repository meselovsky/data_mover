package local.data_mover;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

public class ExcelFileProvider implements FileProvider {
    private final DirectoryStream<Path > directoryStream;
    private final Iterator<Path> directoryIterator;
    private Path nextEntry = null;

    public ExcelFileProvider (Path directory)
            throws IOException
    {
        directoryStream = Files.newDirectoryStream(directory);
        directoryIterator = directoryStream.iterator();
    }

    @Override
    public boolean hasNext()
    {
        if (directoryIterator == null)
            return false;

        while (directoryIterator.hasNext()) {
            Path path = directoryIterator.next();
            String pathString = path.toString();

            if (!Files.isDirectory(path) && ( pathString.endsWith(".xls") ||
                    pathString.endsWith(".xlsx")))
                nextEntry = path;
            return true;
        }

        nextEntry = null;
        return false;
    }

    @Override
    public Path next() {
        return nextEntry;
    }

    @Override
    public void close() throws IOException
    {
        if (directoryStream != null)
            directoryStream.close();
    }
}
