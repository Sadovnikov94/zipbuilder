package com.is.zip;

import com.google.common.net.UrlEscapers;
import java.util.logging.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

/**
 * @author isadovnikov
 */
public class ZipArchiveBuilder
{

    private static final Logger logger = Logger.getLogger(ZipArchiveBuilder.class.getName());

    private HashMap<String, AbstractZipItem> items;


    public ZipArchiveBuilder()
    {
        items = new HashMap<>();
    }


    public void addUrlItem(String itemUrl)
    {
        this.addUrlItem(File.separator, UUID.randomUUID().toString(), itemUrl);
    }


    public void addFileItem(File file)
    {
        String fileNameInArchive = file.getName();
        if (this.isBlank(fileNameInArchive))
        {
            fileNameInArchive = UUID.randomUUID().toString();
        }
        this.addFileItem(File.separator, fileNameInArchive, file);
    }


    public void addUrlItem(String pathInArchive, String fileNameInArchive, String itemUrl)
    {
        try
        {
            URL url = new URL(itemUrl);

            UrlItem urlItem = new UrlItem();
            urlItem.setPathInArchive(pathInArchive);
            urlItem.setFileNameInArchive(fileNameInArchive);
            urlItem.setUrl(url);

            addItem(urlItem, 0);
        }
        catch (MalformedURLException e)
        {
            throw new ZipArchiveException("Failed to add url " + itemUrl, e);
        }
    }


    public void addFileItem(String pathInArchive, String fileNameInArchive, File file)
    {
        FileItem fileItem = new FileItem();
        fileItem.setPathInArchive(pathInArchive);
        fileItem.setFileNameInArchive(fileNameInArchive);
        fileItem.setFile(file);

        addItem(fileItem, 0);
    }


    public File buildTempArchive()
    {
        File tempZipArchive;
        try
        {
            tempZipArchive = File.createTempFile(UUID.randomUUID().toString(), ".zip");
        }
        catch (IOException e)
        {
            throw new ZipArchiveException("Unexpected item type.", e);
        }
        try (
            FileOutputStream fileOutputStream = new FileOutputStream(tempZipArchive);
            ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream)
        )
        {

            int count = 0;
            for (Map.Entry<String, AbstractZipItem> entry : items.entrySet())
            {
                String zipFilePath = entry.getKey();
                AbstractZipItem item = entry.getValue();

                logger.fine("Archive " + tempZipArchive.getName() + " zipping progress : (" + ++count + "/" + items.size() + ")");
                if (item instanceof FileItem)
                {
                    FileItem fileItem = (FileItem) item;

                    zipFileEntry(zipOutputStream, fileItem.getFile(), zipFilePath);
                }
                else if (item instanceof UrlItem)
                {
                    UrlItem urlItem = (UrlItem) item;

                    File tempFile;
                    try
                    {
                        tempFile = File.createTempFile(UUID.randomUUID().toString(), "");

                        URL url = new URL(UrlEscapers.urlFragmentEscaper().escape(urlItem.getUrl().toString()));
                        FileUtils.copyURLToFile(url, tempFile);
                    }
                    catch (IOException e)
                    {
                        throw new ZipArchiveException("Failed to download file " + urlItem.getUrl(), e);
                    }

                    zipFileEntry(zipOutputStream, tempFile, zipFilePath);

                    tempFile.delete();
                }
                else
                {
                    throw new ZipArchiveException("Unexpected item type.");
                }
            }

            logger.info("Archive " + tempZipArchive.getName() + " ready");
            return tempZipArchive;
        }
        catch (IOException e)
        {
            throw new ZipArchiveException("Failed to build zip Archive", e);
        }
    }


    private static void zipFileEntry(ZipOutputStream zipOutputStream, File fileToZip, String zipFilePath) throws IOException
    {
        try (FileInputStream is = new FileInputStream(fileToZip))
        {
            ZipEntry ze = new ZipEntry(zipFilePath);
            zipOutputStream.putNextEntry(ze);

            IOUtils.copy(is, zipOutputStream);
        }
        finally
        {
            if (fileToZip != null)
            {
                fileToZip.delete();
            }
        }
    }


    private void addItem(AbstractZipItem item, int fileVersion)
    {
        StringBuilder zipFilePath = new StringBuilder();

        logger.fine("item added " + item);

        if (isBlank(item.getPathInArchive()))
        {
            zipFilePath.append(item.getPathInArchive());
            zipFilePath.append(File.separator);
        }
        int extensionFirstChar = item.getFileNameInArchive().lastIndexOf('.');
        if (extensionFirstChar == -1)
        {
            extensionFirstChar = item.getFileNameInArchive().length();
        }
        zipFilePath.append(item.getFileNameInArchive(), 0, extensionFirstChar);
        if (fileVersion > 0)
        {
            zipFilePath.append(" (").append(fileVersion).append(")");
        }
        zipFilePath.append(item.getFileNameInArchive().substring(extensionFirstChar));

        if (items.containsKey(zipFilePath.toString()))
        {
            addItem(item, ++fileVersion);
        }
        else
        {
            items.put(zipFilePath.toString(), item);
        }
    }


    private boolean isBlank(String string)
    {
        return string != null && !string.trim().equals("");
    }
}
