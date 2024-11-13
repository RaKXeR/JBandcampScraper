module rakxer.bandcamp {
    exports rakxer.bandcamp.util;
    exports rakxer.bandcamp.model;

    opens rakxer.bandcamp.dto to com.fasterxml.jackson.databind;

    requires org.jsoup;
    requires java.net.http;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires static lombok;
}
