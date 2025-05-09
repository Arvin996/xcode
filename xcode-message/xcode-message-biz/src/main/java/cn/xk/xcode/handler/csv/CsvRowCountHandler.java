package cn.xk.xcode.handler.csv;

import cn.hutool.core.text.csv.CsvRow;
import cn.hutool.core.text.csv.CsvRowHandler;
import lombok.Getter;

/**
 * @Author xuk
 * @Date 2025/3/11 9:33
 * @Version 1.0.0
 * @Description CsvRowCountHandler
 **/
@Getter
public class CsvRowCountHandler implements CsvRowHandler {

    private long rowSize;

    @Override
    public void handle(CsvRow csvRow) {
        rowSize++;
    }
}
