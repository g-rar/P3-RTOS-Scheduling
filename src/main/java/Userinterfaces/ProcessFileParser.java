package Userinterfaces;

import Model.RTProcess;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.csv.CSVFormat.DEFAULT;

public class ProcessFileParser {

    private enum columns {id,period,execTime,deadLine}

    public static List<RTProcess> parsefile(String path) throws Exception {
        File csvFile = new File(path);
        if(csvFile.exists()){
            CSVParser parser = CSVParser.parse(csvFile, Charset.defaultCharset(), DEFAULT.withHeader("id","period","execTime","deadLine"));
            List<RTProcess> retVal = new ArrayList<>();
            boolean firstIt = true;
            for(CSVRecord record : parser){
                if(firstIt){
                    firstIt = false;
                    continue;
                }
                int period = Integer.parseInt(record.get(columns.period));
                int deadLine = Integer.parseInt(record.get(columns.deadLine));
                int execTime = Integer.parseInt(record.get(columns.execTime));
                int id = Integer.parseInt(record.get(columns.id));
                retVal.add(new RTProcess(period, deadLine, execTime, id));
            }
            return retVal;
        } else {
            throw new Exception("No se encontró el archivo especificado");
        }
    }

    public static void createFileTemplate() throws IOException {
        int count = 0;
        File newfile = new File("newFile.csv");
        while(newfile.exists()){
            newfile = new File("newFile_" + Integer.toString(count++)+".csv");
        }
        newfile.createNewFile();
        FileWriter writer = new FileWriter(newfile);
        writer.write("id,period,execTime,deadLine\n");
        writer.close();
    }
}
