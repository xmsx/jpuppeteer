package jpuppeteer.httpclient.method.support;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class HandlerMethod {

    public final Object handler;

    public final Method method;

    public final Parameter[] parameters;

    public HandlerMethod(Method method) {
        this.handler = new Object();
        this.method = method;
        this.parameters = new Parameter[]{};
    }
}