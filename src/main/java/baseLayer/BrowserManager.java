package baseLayer;

import org.apache.logging.log4j.ThreadContext;

public class BrowserManager {

    private static ThreadLocal<String> browser = new ThreadLocal<>();

    public static void setBrowser(String browserName) {
        String formatted =
                browserName.substring(0,1).toUpperCase() +
                browserName.substring(1).toLowerCase();

        browser.set(formatted);
        ThreadContext.put("browser", formatted); // logs
    }

    public static String getBrowser() {
        return browser.get();
    }

    public static void clear() {
        browser.remove();
        ThreadContext.remove("browser");
    }
}