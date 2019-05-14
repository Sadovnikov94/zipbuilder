package zip;

import java.io.File;
import java.util.UUID;
import org.junit.Test;

public class ZipArchiveBuilderTest
{

    @Test(expected = ZipArchiveException.class)
    public void addUrlItem_malformedUrl_throwZipArchiveException()
    {
        ZipArchiveBuilder testSource = new ZipArchiveBuilder();
        testSource.addUrlItem(File.separator, UUID.randomUUID().toString(), UUID.randomUUID().toString());
    }


    @Test
    public void addUrlItem_normalUrl_itemAdded()
    {
        ZipArchiveBuilder testSource = new ZipArchiveBuilder();
        testSource.addUrlItem(File.separator, UUID.randomUUID().toString(), "https://github.com/Sadovnikov94/zipbuilder");
    }


    @Test
    public void addFileItem()
    {
        ZipArchiveBuilder testSource = new ZipArchiveBuilder();
        testSource.addFileItem(File.separator, UUID.randomUUID().toString(), new File("test.zip"));
    }


    @Test
    public void buildTempArchive()
    {
        ZipArchiveBuilder testSource = new ZipArchiveBuilder();
        testSource.addUrlItem(File.separator, UUID.randomUUID().toString(), "https://github.com/Sadovnikov94/zipbuilder");
        File file = testSource.buildTempArchive();
        file.deleteOnExit();
    }
}
