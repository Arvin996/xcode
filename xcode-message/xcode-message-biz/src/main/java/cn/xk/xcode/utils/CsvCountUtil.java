package cn.xk.xcode.utils;

import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvRowHandler;
import cn.xk.xcode.handler.csv.CsvRowCountHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * @Author xuk
 * @Date 2025/3/11 10:15
 * @Version 1.0.0
 * @Description CsvCountUtil
 **/
@Slf4j
public class CsvCountUtil {

    public static long countCsvRow(String path, CsvRowCountHandler csvRowCountHandler) {
        try (CsvReader reader = new CsvReader(new File(path), null)) {
            reader.read(csvRowCountHandler);
        } catch (Exception e) {
            log.error("CsvCountUtil#countCsvRow fail!{}", e.getMessage());
        }
        return csvRowCountHandler.getRowSize();
    }

    public static void handleCsv(String path, CsvRowHandler rowHandler){
        try (CsvReader reader = new CsvReader(new File(path), null)) {
            reader.read(rowHandler);
        }catch (Exception e){
            log.error("CsvCountUtil#handleCsv fail!{}", e.getMessage());
        }
    }
}
