package local.data_mover;

import java.sql.SQLException;
import java.util.List;

public interface Repository<T>
{
    void insertAll(List<T> o) throws SQLException;
    void insert(T o) throws SQLException;
}
