package zip;

/**
 * @author isadovnikov
 */
public abstract class AbstractZipItem {

  private String path;
  private String fileName;

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

}
