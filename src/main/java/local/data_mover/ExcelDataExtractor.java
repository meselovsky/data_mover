package local.data_mover;

import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExcelDataExtractor implements DataExtractor<ExcelData>
{
    private final Workbook book;
    private final String fileName;
    private final DataMapper<ExcelData, Row> dataMapper;

    public ExcelDataExtractor(Path path, DataMapper<ExcelData, Row> dataMapper)
            throws IOException
    {
        this.book = WorkbookFactory.create(
                new FileInputStream(path.toString()));
        this.dataMapper = dataMapper;
        this.fileName = path.getFileName().toString();
    }

    @Override
    public List<ExcelData> extract()
    {
        List<ExcelData> result = new ArrayList<>();
        Pattern pattern = Pattern.compile("(.*)_(.*)\\.xlsx?", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(fileName);

        String ter = "";
        String data = "";
        if (matcher.find()) {
            ter = matcher.group(1);
            data = matcher.group(2);
        }

        for (Sheet sheet : book) {
            int startRowNum = getStartRowNum(sheet);
            if (startRowNum == -1) continue;

            int rowLastIndex = sheet.getLastRowNum();
            for (int i = startRowNum; i <= rowLastIndex; i++) {
                Row row = sheet.getRow(i);

                if(!isValidRow(row))
                    break;

                ExcelData excelData = dataMapper.map(row);
                if (excelData.fielda.equals("0") ||
                        excelData.fieldv.equals("0"))
                    continue;
                excelData.ter = ter;
                excelData.data = data;
                result.add(excelData);
            }
        }

        return result;
    }

    @Override
    public void close() throws IOException
    {
        if (book != null)
            book.close();
    }

    private boolean isValidRow(Row row)
    {
        if (row == null)
            return false;

        int cellNum = row.getLastCellNum() - row.getFirstCellNum() + 1;

        if (cellNum < 28 || row.getFirstCellNum() != 0 )
            return false;
        return true;
    }

    private int getStartRowNum(Sheet sheet)
    {
        int index = -1;
        int rowFirstIndex = sheet.getFirstRowNum();
        int rowLastIndex = sheet.getLastRowNum();

        for (int i = rowFirstIndex; i <= rowLastIndex; i++) {
            Row currentRow = sheet.getRow(i);
            if(!isValidRow(currentRow))
                continue;
            if (currentRow.getCell(0)
                    .getStringCellValue()
                    .trim()
                    .equals("А"))
                return i + 1;
        }

        return index;
    }
}
