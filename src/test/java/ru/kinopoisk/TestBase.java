package ru.kinopoisk;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {
    @BeforeAll
    static void beforeAll() {
        Configuration.browserSize = "1920x1080";
        Configuration.browser = "Chrome";
        Configuration.timeout = 5000;
        Configuration.holdBrowserOpen = true;
        Configuration.pageLoadStrategy="eager";
    }
}
