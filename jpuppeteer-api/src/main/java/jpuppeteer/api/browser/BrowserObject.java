package jpuppeteer.api.browser;

import com.alibaba.fastjson.TypeReference;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public interface BrowserObject extends ExecutionContext {

    ExecutionContext executionContext();

    List<? extends BrowserObject> getProperties() throws Exception;

    BrowserObject getProperty(String name) throws Exception;

    void release() throws Exception;

    /**
     * 原始值
     * @return
     */
    Object value();

    Boolean toBoolean();

    boolean toBooleanValue();

    Short toShort();

    short toShortValue();

    Integer toInteger();

    int toIntValue();

    Long toLong();

    long toLongValue();

    Float toFloat();

    float toFloatValue();

    Double toDouble();

    double toDoubleValue();

    BigDecimal toBigDecimal();

    BigInteger toBigInteger();

    String toStringValue();

    Date toDate();

    <T> T toObject(Class<T> clazz);

    <T> T toObject(TypeReference<T> type);

}
