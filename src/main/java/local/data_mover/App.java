package local.data_mover;

import org.apache.poi.ss.usermodel.Row;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class App
{
    public static void main(String[] args)
            throws IOException, ClassNotFoundException, SQLException
    {

        Path srcDirectory = Paths.get("Nalog/To_Load").toAbsolutePath();
        Path destDirectory = getDestDirectory(srcDirectory);

        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection(
                Constants.DATABASE_URL,
                Constants.DATABASE_LOGIN,
                Constants.DATABASE_PASSWORD);

        FileProvider fileProvider = null;
        try {
            fileProvider =
                    new ExcelFileProvider(srcDirectory);
        } catch (IOException e) {
            connection.close();
            throw e;
        }

        Repository<ExcelData> repository =
                new ExcelDataRepository(connection);
        DataMapper<ExcelData, Row> dataMapper =
                new ExcelFromRowDataMapper();

        while(fileProvider.hasNext()) {
            Path excelFile = fileProvider.next();
            DataExtractor<ExcelData> dataExtractor
                    = new ExcelDataExtractor(excelFile, dataMapper);

            List<ExcelData> objects = dataExtractor.extract();
            if (objects.size() == 0)
                continue;
            repository.insertAll(objects);
            dataExtractor.close();

            Files.move(excelFile, destDirectory
                    .resolve(excelFile.getFileName()));

            System.out.print("[+] Loaded " + objects.size() + " rows from: ");
            System.out.println(excelFile);
        }

        fileProvider.close();
        connection.close();
    }

    private static Path getDestDirectory(Path dir)
            throws IOException
    {
        Path destDirectory = dir.getParent();
        if (destDirectory == null)
            destDirectory = dir;
        destDirectory = destDirectory.resolve(Constants.DIR_LOADED);
        try {
            Files.createDirectory(destDirectory);
        } catch (FileAlreadyExistsException e) {}

        return destDirectory;
    }
}
