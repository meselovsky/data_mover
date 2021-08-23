package local.data_mover;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ExcelFromRowDataMapper implements DataMapper<ExcelData, Row> {
    @Override
    public ExcelData map(Row row)
    {
        ExcelData result = new ExcelData();
        int cellFirstIndex = row.getFirstCellNum();
        int cellLastIndex = row.getLastCellNum();

        List<String> fields = new ArrayList<>(cellLastIndex - cellFirstIndex + 1);
        for (int i = cellFirstIndex; i < cellLastIndex; ++i) {
            Cell cell = row.getCell(i);
            if (cell == null) continue;

            switch (cell.getCellType()) {
                case NUMERIC:
                    fields.add(Objects.toString
                            (Math.round(cell.getNumericCellValue())));
                    break;
                case STRING:
                    fields.add(Objects.toString(
                            cell.getStringCellValue().trim()));
                    break;
                case BLANK:
                case _NONE:
                    fields.add("0");
                    break;
            }
        }

        result.fielda = fields.get(0);
        result.fieldb = fields.get(1);
        result.fieldv = fields.get(2);
        result.field1 = fields.get(3);
        result.field2 = fields.get(4);
        result.field3 = fields.get(5);
        result.field4 = fields.get(6);
        result.field5 = fields.get(7);
        result.field6 = fields.get(8);
        result.field7 = fields.get(9);
        result.field8 = fields.get(10);
        result.field9 = fields.get(11);
        result.field10 = fields.get(12);
        result.field11 = fields.get(13);
        result.field12 = fields.get(14);
        result.field13 = fields.get(15);
        result.field14 = fields.get(16);
        result.field15 = fields.get(17);
        result.field16 = fields.get(18);
        result.field17 = fields.get(19);
        result.field18 = fields.get(20);
        result.field19 = fields.get(21);
        result.field20 = fields.get(22);
        result.field21 = fields.get(23);
        result.field22 = fields.get(24);
        result.field23 = fields.get(25);
        result.field24 = fields.get(26);
        result.field25 = fields.get(27);

        return result;
    }
}
