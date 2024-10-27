# JBandcampScraper

JBandcampScraper is a java program that accepts album and track URLs from bandcamp and outputs the title, streaming link, and artwork URL.

## Using as a Library

This project is not published in the Maven repository, but you can include it in your own Maven Project using `jitpack`:

```
    <repositories>
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
    </repositories>

    <dependencies>
        <dependency>
            <groupId>com.github.RaKXeR</groupId>
            <artifactId>JBandcampScraper</artifactId>
            <version>master-SNAPSHOT</version>
        </dependency>
    </dependencies>
```

You can then import `BandcampFetcher` For an easy way to get `Song`'s based on Bandcamp URLs.
