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
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
            boolean haveAnyFile = false;
            boolean fileFound = false;
            while ((entry = zis.getNextEntry()) != null) {
                haveAnyFile = true;
                String foundName = entry.getName();
                if (foundName.equals(entryToFind)) {
                    fileFound = true;
                    PDF pdf = new PDF(zis);
                    Assertions.assertTrue(pdf.text.contains("Белый Владимир Михайлович"), "Текст в pdf не совпадает");
                    break;
                }
            }
            Assertions.assertTrue(haveAnyFile, "ZIP-архив пуст");
            Assertions.assertTrue(fileFound, "Файл " + entryToFind + " не найден в архиве");
        }

    }

    @Test
    @DisplayName("В zip архиве должен содержаться csv файл с заданным содержимым")
    void csvFileInZipContainsString() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(cl.getResourceAsStream("qa_guru_aos.zip"))) {
            String entryToFind = "username.csv";
            ZipEntry entry;
            boolean haveAnyFile = false;
            boolean fileFound = false;
            while ((entry = zis.getNextEntry()) != null) {
                haveAnyFile = true;
                String foundName = entry.getName();
                if (foundName.equals(entryToFind)) {
                    fileFound = true;
                    CSVReader csvReader = new CSVReader(new InputStreamReader(zis));
                    List<String[]> data = csvReader.readAll();
                    Assertions.assertEquals(7, data.size());
                    Assertions.assertArrayEquals(
                            new String[]{"Username", "Identifier", "First name", "Last name"},
                            data.get(0), "Текст в csv не совпадает"
                    );
                    Assertions.assertArrayEquals(
                            new String[]{"booker12", "9012", "Rachel", "Booker"},
                            data.get(1), "Текст в csv не совпадает"
                    );
                }
            }
            Assertions.assertTrue(haveAnyFile, "ZIP-архив пуст");
            Assertions.assertTrue(fileFound, "Файл " + entryToFind + " не найден в архиве");
        }
    }

    @Test
    @DisplayName("В zip архиве должен содержаться xls файл с заданным содержимым")
    void xlsFileInZipContainsString() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(cl.getResourceAsStream("qa_guru_aos.zip"))) {
            String entryToFind = "teachers.xls";
            ZipEntry entry;
            boolean haveAnyFile = false;
            boolean fileFound = false;
            while ((entry = zis.getNextEntry()) != null) {
                haveAnyFile = true;
                String foundName = entry.getName();
                if (foundName.equals(entryToFind)) {
                    fileFound = true;
                    XLS xls = new XLS(zis);
                    String actualValue = xls.excel.getSheetAt(2).getRow(4).getCell(1).getStringCellValue();
                    assertTrue(actualValue.contains("Белый Владимир Михайлович"), "Текст в xls не совпадает");
                }
            }
            Assertions.assertTrue(haveAnyFile, "ZIP-архив пуст");
            Assertions.assertTrue(fileFound, "Файл " + entryToFind + " не найден в архиве");
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
            assertTrue(actual.isHaveEgg());
            Assertions.assertEquals("Iceball", actual.getAbilities().getPrimaryAbility());
            Assertions.assertEquals("Ice Spike", actual.getAbilities().getSecondaryAbility());
            Assertions.assertEquals(expectedList, actual.getStats());
            Assertions.assertEquals(null, actual.getEnergy());


        }
    }
}





