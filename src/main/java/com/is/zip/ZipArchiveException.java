package com.is.zip;

/**
 * @author isadovnikov
 */
public class ZipArchiveException extends RuntimeException {

  public ZipArchiveException() {
  }

  public ZipArchiveException(String message) {
    super(message);
  }

  public ZipArchiveException(String message, Throwable cause) {
    super(message, cause);
  }

  public ZipArchiveException(Throwable cause) {
    super(cause);
  }

  public ZipArchiveException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
