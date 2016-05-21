import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CSVParserV1 {

    private Matcher getMail;
    private static  final Pattern PATTERN_MAIL = Pattern.compile("([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}");
    private String  filePath= "/file.csv";
    private File parsedData = new File("newDoc.text");
    private List<CSVRecord> temporaryRecordsList = new ArrayList<>();
    private int temporaryListCounter=1;

    public static void main(String[] args)  {

        CSVParserV1 parser=new CSVParserV1();
        try {
            Reader in = new FileReader(parser.filePath);
            FileWriter fileWriter = new FileWriter(parser.parsedData.getAbsoluteFile());
            BufferedWriter bufWriter = new BufferedWriter(fileWriter);
            Iterable<CSVRecord> records = CSVFormat.MYSQL.parse(in);

            for (CSVRecord record : records) {
                parser.temporaryRecordsList.add(record);
                if (parser.temporaryRecordsList.size() == 5) {
                    System.out.println("List" + parser.temporaryListCounter + " is processing");
                    for (int i = 0; i < parser.temporaryRecordsList.size(); i++) {
                        parser.getMail = PATTERN_MAIL.matcher(parser.temporaryRecordsList.get(i).toString());
                        while (parser.getMail.find()) {
                            bufWriter.write(parser.temporaryRecordsList.get(i).toString().substring(parser.getMail.start(), parser.getMail.end()) + ";");
                        }
                    }
                    parser.temporaryRecordsList.clear();
                    parser.temporaryListCounter++;
                }
            }
            bufWriter.close();
            System.out.println("Buffer was closed");
        }
        catch(Exception e){
            System.out.println("Error during processing the file.csv");
        }
    }
}
