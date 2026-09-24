package baseLayer;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import configLayer.ConfigReader;
import configLayer.Log;

public class DriverFactory {

    public static WebDriver initDriver(String browser) {

        /*
         * =========================
         * BROWSER
         * =========================
         *
         * Priority:
         *
         * Jenkins / Maven:
         * -Dbrowser=edge
         *
         * then:
         *
         * config.properties
         *
         * then:
         *
         * method parameter
         *
         * finally:
         *
         * chrome
         */

        String configuredBrowser = ConfigReader.get("browser");

        if (configuredBrowser == null
                || configuredBrowser.trim().isEmpty()) {

            configuredBrowser = browser;
        }

        if (configuredBrowser == null
                || configuredBrowser.trim().isEmpty()) {

            configuredBrowser = "chrome";
        }

        configuredBrowser =
                configuredBrowser.trim().toLowerCase();


        /*
         * =========================
         * EXECUTION MODE
         * =========================
         *
         * local  = Local browser
         * remote = Selenium Grid / Remote machine
         */

        String executionMode =
                ConfigReader.get(
                        "executionMode",
                        "local"
                ).toLowerCase();


        /*
         * =========================
         * HEADLESS
         * =========================
         */

        boolean headless =
                ConfigReader.getBoolean(
                        "headless",
                        false
                );


        /*
         * =========================
         * INCOGNITO
         * =========================
         */

        boolean incognito =
                ConfigReader.getBoolean(
                        "incognito",
                        false
                );


        /*
         * =========================
         * TIMEOUTS
         * =========================
         */

        int implicitWait =
                ConfigReader.getInt(
                        "implicitWait",
                        10
                );

        int pageLoadTimeout =
                ConfigReader.getInt(
                        "pageLoadTimeout",
                        30
                );

        int scriptTimeout =
                ConfigReader.getInt(
                        "scriptTimeout",
                        30
                );


        /*
         * =========================
         * LOG CONFIGURATION
         * =========================
         */

        Log.info("==========================================");
        Log.info("Browser        : " + configuredBrowser);
        Log.info("Execution Mode : " + executionMode);
        Log.info("Headless       : " + headless);
        Log.info("Incognito      : " + incognito);
        Log.info("==========================================");


        WebDriver driver;


        /*
         * =========================
         * LOCAL EXECUTION
         * =========================
         */

        if ("local".equalsIgnoreCase(executionMode)) {

            driver =
                    createLocalDriver(
                            configuredBrowser,
                            headless,
                            incognito
                    );
        }


        /*
         * =========================
         * REMOTE EXECUTION
         * =========================
         */

        else if ("remote".equalsIgnoreCase(executionMode)) {

            driver =
                    createRemoteDriver(
                            configuredBrowser,
                            headless,
                            incognito
                    );
        }


        /*
         * =========================
         * INVALID EXECUTION MODE
         * =========================
         */

        else {

            throw new RuntimeException(
                    "Unsupported executionMode: "
                            + executionMode
                            + ". Use local or remote."
            );
        }


        /*
         * =========================
         * SELENIUM TIMEOUTS
         * =========================
         */

        driver.manage()
                .timeouts()
                .implicitlyWait(
                        Duration.ofSeconds(
                                implicitWait
                        )
                );

        driver.manage()
                .timeouts()
                .pageLoadTimeout(
                        Duration.ofSeconds(
                                pageLoadTimeout
                        )
                );

        driver.manage()
                .timeouts()
                .scriptTimeout(
                        Duration.ofSeconds(
                                scriptTimeout
                        )
                );


        /*
         * =========================
         * WINDOW
         * =========================
         *
         * Safari may not support all
         * window-management operations
         * in every environment.
         */

        try {

            driver.manage()
                    .window()
                    .maximize();

        } catch (Exception e) {

            Log.info(
                    "Window maximize skipped: "
                            + e.getMessage()
            );
        }


        /*
         * =========================
         * THREAD LOCAL
         * =========================
         */

        BaseClass.setDriver(driver);
        BaseClass.setBrowser(configuredBrowser);


        Log.info(
                "Browser launched successfully: "
                        + configuredBrowser
                        + " | Mode: "
                        + executionMode
        );


        return driver;
    }


    // =========================================================
    // LOCAL DRIVER
    // =========================================================

    private static WebDriver createLocalDriver(
            String browser,
            boolean headless,
            boolean incognito) {


        switch (browser) {


            // =================================================
            // CHROME
            // =================================================

            case "chrome":

                ChromeOptions chromeOptions =
                        new ChromeOptions();

                if (headless) {

                    chromeOptions.addArguments(
                            "--headless=new"
                    );
                }

                if (incognito) {

                    chromeOptions.addArguments(
                            "--incognito"
                    );
                }

                chromeOptions.addArguments(
                        "--disable-notifications"
                );

                chromeOptions.addArguments(
                        "--disable-popup-blocking"
                );

                chromeOptions.addArguments(
                        "--start-maximized"
                );

                return new ChromeDriver(
                        chromeOptions
                );


            // =================================================
            // FIREFOX
            // =================================================

            case "firefox":

                FirefoxOptions firefoxOptions =
                        new FirefoxOptions();

                if (headless) {

                    firefoxOptions.addArguments(
                            "-headless"
                    );
                }

                /*
                 * Firefox does not use a normal
                 * "--incognito" argument.
                 *
                 * Private browsing is enabled
                 * using this preference.
                 */

                if (incognito) {

                    firefoxOptions.addPreference(
                            "browser.privatebrowsing.autostart",
                            true
                    );
                }

                return new FirefoxDriver(
                        firefoxOptions
                );


            // =================================================
            // EDGE
            // =================================================

            case "edge":

                EdgeOptions edgeOptions =
                        new EdgeOptions();

                if (headless) {

                    edgeOptions.addArguments(
                            "--headless=new"
                    );
                }

                if (incognito) {

                    edgeOptions.addArguments(
                            "--inprivate"
                    );
                }

                edgeOptions.addArguments(
                        "--disable-notifications"
                );

                edgeOptions.addArguments(
                        "--disable-popup-blocking"
                );

                edgeOptions.addArguments(
                        "--start-maximized"
                );

                return new EdgeDriver(
                        edgeOptions
                );


            // =================================================
            // SAFARI
            // =================================================

            case "safari":

                if (headless) {

                    Log.info(
                            "Safari does not support standard "
                                    + "headless execution. "
                                    + "Ignoring headless=true."
                    );
                }

                if (incognito) {

                    Log.info(
                            "Safari private browsing cannot be "
                                    + "configured through standard "
                                    + "Selenium SafariOptions. "
                                    + "Ignoring incognito=true."
                    );
                }

                SafariOptions safariOptions =
                        new SafariOptions();

                return new SafariDriver(
                        safariOptions
                );


            // =================================================
            // INVALID BROWSER
            // =================================================

            default:

                throw new RuntimeException(
                        "Unsupported browser: "
                                + browser
                                + ". Supported browsers: "
                                + "chrome, firefox, edge, safari"
                );
        }
    }


    // =========================================================
    // REMOTE DRIVER
    // =========================================================

    private static WebDriver createRemoteDriver(
            String browser,
            boolean headless,
            boolean incognito) {


        String remoteUrl =
                ConfigReader.get(
                        "remoteUrl",
                        "http://localhost:4444"
                );


        String platform =
                ConfigReader.get(
                        "platform",
                        ""
                );


        String browserVersion =
                ConfigReader.get(
                        "browserVersion",
                        ""
                );


        Log.info(
                "Remote Selenium Grid URL: "
                        + remoteUrl
        );

        Log.info(
                "Remote Platform: "
                        + platform
        );

        Log.info(
                "Remote Browser Version: "
                        + browserVersion
        );


        MutableCapabilities options;


        /*
         * =====================================================
         * CHROME
         * =====================================================
         */

        if ("chrome".equalsIgnoreCase(browser)) {

            ChromeOptions chromeOptions =
                    new ChromeOptions();

            if (headless) {

                chromeOptions.addArguments(
                        "--headless=new"
                );
            }

            if (incognito) {

                chromeOptions.addArguments(
                        "--incognito"
                );
            }

            chromeOptions.addArguments(
                    "--disable-notifications"
            );

            options = chromeOptions;
        }


        /*
         * =====================================================
         * FIREFOX
         * =====================================================
         */

        else if ("firefox".equalsIgnoreCase(browser)) {

            FirefoxOptions firefoxOptions =
                    new FirefoxOptions();

            if (headless) {

                firefoxOptions.addArguments(
                        "-headless"
                );
            }

            if (incognito) {

                firefoxOptions.addPreference(
                        "browser.privatebrowsing.autostart",
                        true
                );
            }

            options = firefoxOptions;
        }


        /*
         * =====================================================
         * EDGE
         * =====================================================
         */

        else if ("edge".equalsIgnoreCase(browser)) {

            EdgeOptions edgeOptions =
                    new EdgeOptions();

            if (headless) {

                edgeOptions.addArguments(
                        "--headless=new"
                );
            }

            if (incognito) {

                edgeOptions.addArguments(
                        "--inprivate"
                );
            }

            edgeOptions.addArguments(
                    "--disable-notifications"
            );

            options = edgeOptions;
        }


        /*
         * =====================================================
         * SAFARI
         * =====================================================
         */

        else if ("safari".equalsIgnoreCase(browser)) {

            SafariOptions safariOptions =
                    new SafariOptions();

            if (headless) {

                Log.info(
                        "Safari does not support standard "
                                + "headless execution."
                );
            }

            if (incognito) {

                Log.info(
                        "Safari private browsing is not "
                                + "configured through standard "
                                + "Selenium capabilities."
                );
            }

            options = safariOptions;
        }


        /*
         * =====================================================
         * INVALID BROWSER
         * ===================================================== */

        else {

            throw new RuntimeException(
                    "Unsupported browser for remote execution: "
                            + browser
            );
        }


        /*
         * =====================================================
         * PLATFORM
         * =====================================================
         *
         * Only set platform when supplied.
         */

        if (platform != null
                && !platform.trim().isEmpty()) {

            try {

                Platform seleniumPlatform =
                        Platform.fromString(
                                platform
                        );

                options.setCapability(
                        "platformName",
                        seleniumPlatform
                                .toString()
                );

            } catch (Exception e) {

                Log.info(
                        "Invalid platform value: "
                                + platform
                                + ". Platform capability skipped."
                );
            }
        }


        /*
         * =====================================================
         * BROWSER VERSION
         * =====================================================
         */

        if (browserVersion != null
                && !browserVersion.trim().isEmpty()) {

            options.setCapability(
                    "browserVersion",
                    browserVersion
            );
        }


        /*
         * =====================================================
         * CREATE REMOTE DRIVER
         * =====================================================
         */

        try {

            URL gridUrl =
                    URI.create(remoteUrl)
                            .toURL();

            return new RemoteWebDriver(
                    gridUrl,
                    options
            );

        } catch (MalformedURLException e) {

            throw new RuntimeException(
                    "Invalid Selenium Grid URL: "
                            + remoteUrl,
                    e
            );
        }
    }
}

