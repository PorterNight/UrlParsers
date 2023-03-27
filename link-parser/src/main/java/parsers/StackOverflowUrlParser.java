package parsers;

import parsers.parsedUrl.ParsedUrl;
import parsers.parsedUrl.StackOverflowParsedUrl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StackOverflowUrlParser implements UrlParser {

    private final UrlParser nextParser;
    private final Pattern pattern;

    public StackOverflowUrlParser(UrlParser nextParser) {
        this.nextParser = nextParser;
        this.pattern = Pattern.compile("https://stackoverflow.com/questions/(\\d+)/.*");
    }

    public ParsedUrl parseLink(String url) {
    // сheck if the URL matches the StackOverflow pattern

        Matcher matcher = pattern.matcher(url);

        if (matcher.matches()) {
            int id = Integer.parseInt(matcher.group(1));
            return new StackOverflowParsedUrl(id);
        }

        // if no match, go to the next parser
        if (nextParser != null) {
            return nextParser.parseLink(url);
        }

        return null;
    }
}
