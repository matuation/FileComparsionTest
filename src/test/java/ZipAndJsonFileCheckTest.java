import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import model.Chicken;
import model.Stats;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipAndJsonFileCheckTest {

    private ClassLoader cl = ZipAndJsonFileCheckTest.class.getClassLoader();
    Chicken chicken = new Chicken();
    ObjectMapper mapper = new ObjectMapper();

    @Test
    @DisplayName("В zip архиве должен содержаться pdf файл с заданным содержимым")
    void pdfFileInZipContainsString() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(cl.getResourceAsStream("qa_guru_aos.zip"))) {
            String entryToFind = "teachers.pdf";
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                String foundName = entry.getName();
                if (foundName.equals(entryToFind)) {
                    PDF pdf = new PDF(zis);
                    Assertions.assertTrue(pdf.text.contains("Белый Владимир Михайлович"));
                }
            }

        }

    }

    @Test
    @DisplayName("В zip архиве должен содержаться csv файл с заданным содержимым")
    void csvFileInZipContainsString() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(cl.getResourceAsStream("qa_guru_aos.zip"))) {
            String entryToFind = "username.csv";
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                String foundName = entry.getName();
                if (foundName.equals(entryToFind)) {
                    CSVReader csvReader = new CSVReader(new InputStreamReader(zis));
                    List<String[]> data = csvReader.readAll();
                    Assertions.assertEquals(7, data.size());
                    Assertions.assertArrayEquals(
                            new String[]{"Username", "Identifier", "First name", "Last name"},
                            data.get(0)
                    );
                    Assertions.assertArrayEquals(
                            new String[]{"booker12", "9012", "Rachel", "Booker"},
                            data.get(1)
                    );
                }
            }

        }
    }

    @Test
    @DisplayName("В zip архиве должен содержаться xls файл с заданным содержимым")
    void xlsFileInZipContainsString() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(cl.getResourceAsStream("qa_guru_aos.zip"))) {
            String entryToFind = "teachers.xls";
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                String foundName = entry.getName();
                if (foundName.equals(entryToFind)) {
                    XLS xls = new XLS(zis);
                    String actualValue = xls.excel.getSheetAt(2).getRow(4).getCell(1).getStringCellValue();
                    Assertions.assertTrue(actualValue.contains("Белый Владимир Михайлович"));
                }
            }

        }

    }

    @Test
    @DisplayName("В возвращаемом json содержатся верные данные")
    void jsonContainsExpectedData() throws Exception {
        try (InputStream is = cl.getResourceAsStream("chicken.json")) {
            Chicken actual = mapper.readValue(is, Chicken.class);
            List<Stats> expectedList = List.of(
                    new Stats("Ability Power", 865),
                    new Stats("Cooldown reduction", 25)
            );
            Assertions.assertEquals("Anivia", actual.getName());
            Assertions.assertEquals(1000, actual.getAge());
            Assertions.assertEquals("Frelyord", actual.getBorn());
            Assertions.assertTrue(actual.isHaveEgg());
            Assertions.assertEquals("Iceball", actual.getAbilities().getPrimaryAbility());
            Assertions.assertEquals("Ice Spike", actual.getAbilities().getSecondaryAbility());
            Assertions.assertEquals(expectedList, actual.getStats());
            Assertions.assertEquals(null, actual.getEnergy());



        }
    }
}





