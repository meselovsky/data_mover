package local.data_mover;

import java.io.Closeable;
import java.nio.file.Path;
import java.util.Iterator;

public interface FileProvider
        extends Iterator<Path>, Closeable
{
}
