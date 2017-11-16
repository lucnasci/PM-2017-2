
import java.io.IOException;
import java.util.ArrayList;
public class main {

    public static void main(String[] args) throws IOException {
    	DisciplineRecordReader reader = DisciplineRecordReader.getInstance();
        ArrayList<String> lines = reader.getListOfDisciplines("file.pdf");
        lines.forEach(line -> System.out.println(line));
    }
}