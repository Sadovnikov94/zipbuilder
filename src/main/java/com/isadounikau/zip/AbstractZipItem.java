package com.isadounikau.zip;

/**
 * @author isadovnikov
 */
public abstract class AbstractZipItem {

  private String pathInArchive;
  private String fileNameInArchive;

  public String getPathInArchive() {
    return pathInArchive;
  }

  public void setPathInArchive(String pathInArchive) {
    this.pathInArchive = pathInArchive;
  }

  public String getFileNameInArchive() {
    return fileNameInArchive;
  }

  public void setFileNameInArchive(String fileNameInArchive) {
    this.fileNameInArchive = fileNameInArchive;
  }

}
