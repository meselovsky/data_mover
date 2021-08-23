package local.data_mover;

import java.io.Closeable;
import java.util.Iterator;
import java.util.List;

public interface DataExtractor<T> extends Closeable
{
    List<T> extract();
}
