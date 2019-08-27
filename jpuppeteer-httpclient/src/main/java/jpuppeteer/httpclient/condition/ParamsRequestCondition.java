package jpuppeteer.httpclient.condition;

import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.HttpGet;

import java.util.Collection;
import java.util.Map;

import static jpuppeteer.httpclient.constant.Constants.EMPTY_STRING;

public class ParamsRequestCondition extends AbstractRequestCondition<ParamsRequestCondition> {

    private static final String EQUAL = "=";

    private final Multimap<String, String> matches;

    public ParamsRequestCondition(String... matches) {
        Multimap<String, String> multimap = LinkedHashMultimap.create();
        for(String match : matches) {
            int offset = match.indexOf(EQUAL);
            String name;
            String value;
            if (offset == -1) {
                name = StringUtils.trim(match);
                value = EMPTY_STRING;
            } else {
                name = StringUtils.trim(match.substring(0, offset));
                value = match.substring(offset + 1);
            }
            if (StringUtils.isEmpty(name)) {
                continue;
            }
            multimap.put(name, value);
        }
        this.matches = ImmutableSetMultimap.copyOf(multimap);
    }

    private ParamsRequestCondition(Multimap<String, String> matches) {
        this.matches = ImmutableSetMultimap.copyOf(matches);
    }

    @Override
    protected Collection<?> getContent() {
        return this.matches.entries();
    }

    @Override
    protected String getToStringInfix() {
        return " && ";
    }

    @Override
    public ParamsRequestCondition combine(ParamsRequestCondition other) {
        Multimap<String, String> multimap = LinkedHashMultimap.create(matches);
        multimap.putAll(other.matches);
        return new ParamsRequestCondition(multimap);
    }

    @Override
    public ParamsRequestCondition getMatchingCondition(HttpRequestInfo request) {
        Map<String, Collection<String>> map = this.matches.asMap();
        OUTER:
        for(Map.Entry<String, Collection<String>> entry : map.entrySet()) {
            if (!request.getQuery().containsKey(entry.getKey())) {
                return null;
            }
            for(String value : entry.getValue()) {
                if (request.getQuery().containsEntry(entry.getKey(), value)) {
                    //只要发现一个匹配的, 就进入下一个的判断
                    continue OUTER;
                }
                return null;
            }
        }
        return this;
    }

    @Override
    public int compareTo(ParamsRequestCondition other, HttpRequestInfo request) {
        //限定参数个数多的>限定参数个数少的
        return this.matches.size() - other.matches.size();
    }
}