package local.data_mover;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ExcelDataRepository implements Repository<ExcelData>
{
    private final Connection connection;
    private final static String INSERT_QUERY =
            "INSERT INTO \"public\".nalog1nom_Veselovskiy VALUES " +
            "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?," +
            " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?," +
            " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public ExcelDataRepository(Connection connection)
    {
        this.connection = connection;
    }

    @Override
    public void insert(ExcelData excelData)
            throws SQLException
    {
        try (PreparedStatement statement =
                     connection.prepareStatement(INSERT_QUERY))
        {
            statement.setString(1, excelData.fielda);
            statement.setString(2, excelData.fieldb);
            statement.setString(3, excelData.fieldv);
            statement.setString(4, excelData.field1);
            statement.setString(5, excelData.field2);
            statement.setString(6, excelData.field3);
            statement.setString(7, excelData.field4);
            statement.setString(8, excelData.field5);
            statement.setString(9, excelData.field6);
            statement.setString(10, excelData.field7);
            statement.setString(11, excelData.field8);
            statement.setString(12, excelData.field9);
            statement.setString(13, excelData.field10);
            statement.setString(14, excelData.field11);
            statement.setString(15, excelData.field12);
            statement.setString(16, excelData.field13);
            statement.setString(17, excelData.field14);
            statement.setString(18, excelData.field15);
            statement.setString(19, excelData.field16);
            statement.setString(20, excelData.field17);
            statement.setString(21, excelData.field18);
            statement.setString(22, excelData.field19);
            statement.setString(23, excelData.field20);
            statement.setString(24, excelData.field21);
            statement.setString(25, excelData.field22);
            statement.setString(26, excelData.field23);
            statement.setString(27, excelData.field24);
            statement.setString(28, excelData.field25);
            statement.setString(29, excelData.ter);
            statement.setString(30, excelData.data);

            statement.executeUpdate();
        }
    }

    @Override
    public void insertAll(List<ExcelData> objects)
            throws SQLException
    {
        for (ExcelData excelData: objects)
            insert(excelData);
    }
}
