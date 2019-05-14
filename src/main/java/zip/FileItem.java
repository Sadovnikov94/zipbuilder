package zip;

import java.io.File;

/**
 * @author isadovnikov
 */
public class FileItem extends AbstractZipItem {

  private File file;

  public File getFile() {
    return file;
  }

  public void setFile(File file) {
    this.file = file;
  }

  @Override
  public String toString() {
    return super.getPath() + super.getFileName();
  }
}
