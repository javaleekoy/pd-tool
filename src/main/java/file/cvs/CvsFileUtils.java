package file.cvs;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import exception.PdException;
import exception.PdFileNotFoundException;
import exception.PdIOException;
import exception.PdNullPointerException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author peramdy on 2018/9/17.
 */
public class CvsFileUtils {


    private final static String DEFAULT_NEW_LINE_SEPARATOR = "\n";
    private final static String DEFAULT_NULL_STRING = "";

    /**
     * read csv file
     *
     * @param filePath csv file path
     * @param headers  csv headers
     * @return
     */
    public static JSONArray ReadDefaultCsvFile(String filePath, String[] headers) {
        if (StringUtils.isBlank(filePath)) {
            throw new PdFileNotFoundException("filePath is null!");
        }
        if (headers == null || headers.length == 0) {
            throw new PdException("headers is null!");
        }
        JSONArray jsonArray = null;
        Reader reader = null;
        CSVParser parser = null;
        try {
            reader = new FileReader(filePath);
            parser = CSVFormat.DEFAULT
                    .withHeader(headers)
                    .withIgnoreEmptyLines()
                    .withNullString(DEFAULT_NULL_STRING)
                    .parse(reader);
            List<CSVRecord> list = parser.getRecords();
            if (list == null || list.size() == 0) {
                return jsonArray;
            }
            jsonArray = new JSONArray();
            for (CSVRecord record : list) {
                JSONObject jsonObject = new JSONObject();
                for (String header : headers) {
                    String value = record.get(header);
                    jsonObject.put(header, value);
                }
                jsonArray.add(jsonObject);
            }
        } catch (FileNotFoundException e) {
            throw new PdFileNotFoundException(e);
        } catch (IOException e) {
            throw new PdIOException(e);
        } finally {
            close(reader, parser, null, null);
        }
        return jsonArray;
    }


    /**
     * read csv file
     * map<key,value> key:column value:value
     *
     * @param filePath file path
     * @return
     */
    public static List<Map<String, String>> ReadDefaultCsvFile(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            throw new PdFileNotFoundException("filePath is null!");
        }
        List<Map<String, String>> reList;
        Reader reader = null;
        CSVParser parser = null;
        try {
            reader = new FileReader(filePath);
            parser = CSVFormat.RFC4180
                    .withFirstRecordAsHeader()
                    .withIgnoreEmptyLines()
                    .withNullString(DEFAULT_NULL_STRING)
                    .parse(reader);
            List<CSVRecord> list = parser.getRecords();
            if (list == null || list.size() == 0) {
                return null;
            }
            reList = new ArrayList<>();
            for (CSVRecord record : list) {
                reList.add(record.toMap());
            }
        } catch (FileNotFoundException e) {
            throw new PdFileNotFoundException(e);
        } catch (IOException e) {
            throw new PdIOException(e);
        } finally {
            close(reader, parser, null, null);
        }
        return reList;
    }


    /**
     * write csv file
     *
     * @param filePath csv file path
     * @param headers  csv headers
     * @param data     csv data
     */
    public static void writeCsvFile(String filePath, String[] headers, List<String[]> data) {
        if (StringUtils.isBlank(filePath)) {
            throw new PdNullPointerException("filePath is null !");
        }
        if (data == null || data.size() == 0) {
            throw new PdNullPointerException("data is null !");
        }
        FileWriter writer = null;
        CSVPrinter printer = null;
        try {
            writer = new FileWriter(filePath);
            CSVFormat format = CSVFormat.RFC4180
                    .withRecordSeparator(DEFAULT_NEW_LINE_SEPARATOR)
                    .withIgnoreEmptyLines()
                    .withNullString(DEFAULT_NULL_STRING)
                    .withHeader(headers);
            if (headers == null || headers.length == 0) {
                format.withFirstRecordAsHeader();
            }
            printer = format.print(writer);
            for (String[] record : data) {
                try {
                    printer.printRecord(record);
                } catch (IOException e) {
                    throw new PdIOException(e);
                }
            }
            printer.flush();
        } catch (IOException e) {
            throw new PdIOException(e);
        } finally {
            close(null, null, writer, printer);
        }
    }

    /**
     * write csv file
     *
     * @param filePath file path
     * @param data     data(include columnNames)
     */
    public static void writeCsvFile(String filePath, List<String[]> data) {
        writeCsvFile(filePath, null, data);
    }


    /**
     * close stream
     *
     * @param reader  reader
     * @param parser  parser
     * @param writer  writer
     * @param printer printer
     */
    private static void close(Reader reader, CSVParser parser, Writer writer, CSVPrinter printer) {
        try {
            if (reader != null) {
                reader.close();
            }
            if (parser != null) {
                if (!parser.isClosed()) {
                    parser.close();
                }
            }
            if (writer != null) {
                writer.close();
            }
            if (printer != null) {
                printer.close();
            }
        } catch (IOException e) {
            throw new PdIOException(e);
        }
    }

//    public static void main(String[] args) {
//        String[] headers = new String[]{"name", "password", "address"};
//        /*CvsFileUtils.ReadDefaultCsvFile("D:\\test.csv", headers);*/
//        String[] data1 = new String[]{"peramdy", "987654", "sh"};
//        String[] data2 = new String[]{"jay", "123456", "bj"};
//        List<String[]> list = new ArrayList<>();
//        list.add(headers);
//        list.add(data1);
//        list.add(data2);
//        writeCsvFile("D:\\tmp.csv", list);
//
//    }


}
