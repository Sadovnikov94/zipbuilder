package zip;

import java.net.URL;

/**
 * @author isadovnikov
 */
public class UrlItem extends AbstractZipItem {

  private URL url;

  public URL getUrl() {
    return url;
  }

  public void setUrl(URL url) {
    this.url = url;
  }

}
