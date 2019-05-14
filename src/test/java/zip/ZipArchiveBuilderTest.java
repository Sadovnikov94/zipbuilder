package zip;

import java.io.File;
import java.util.UUID;
import org.junit.Test;

public class ZipArchiveBuilderTest
{

    private ZipArchiveBuilder testSource = new ZipArchiveBuilder();


    @Test(expected = ZipArchiveException.class)
    public void addUrlItem()
    {
        testSource.addUrlItem(UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString());
    }


    @Test
    public void addFileItem()
    {
        testSource.addFileItem("test.zip", "test.zip", new File("test.zip"));
    }


    @Test
    public void buildTempArchive()
    {
        testSource.buildTempArchive();
    }
}
