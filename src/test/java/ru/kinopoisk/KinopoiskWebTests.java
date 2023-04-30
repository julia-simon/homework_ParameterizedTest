package ru.kinopoisk;

import com.codeborne.selenide.CollectionCondition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.kinopoisk.domain.Locale;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;


@DisplayName("WEB - тесты для демонстрации возможностей JUnit")

public class KinopoiskWebTests extends TestBase {
    @BeforeEach
    void openPage() {
        open("https://www.kinopoisk.ru/");
    }
    static Stream<Arguments> chooseSection() {
        return Stream.of(
                Arguments.of(Locale.Фильмы, List.of("250 лучших фильмов", "Лучшие фильмы-2022 по версии редакции", "500 лучших фильмов")),
                Arguments.of(Locale.Сериалы, List.of("250 лучших сериалов", "Шедевры HBO", "Лучшие сериалы-2022 по версии редакции"))
        );
    }
    @MethodSource
    @ParameterizedTest
    @DisplayName("Проверка корректности перехода в раздел")
    void chooseSection(Locale siteLocale, List<String> expectedList) {
        $(".styles_sidebarContainer__dxNPY").$(byText(String.valueOf(siteLocale))).click();
        $$(".styles_name__G_1mq").shouldHave(CollectionCondition.containExactTextsCaseSensitive(expectedList));
    }
    @CsvSource({
            "hfhfjjkkk, Такого аккаунта нет",
            "попопол, Такой логин не подойдет"
    })
    @ParameterizedTest
    @DisplayName("Проверка ошибок авторизации")
    void wrongAuthorization(String login, String expectedError) {
        $("button.styles_loginButton__LWZQp").click();
        $(".Button2_view_default").click();
        $(".Textinput-Control").setValue(login).pressEnter();
        $(".Textinput-Hint_state_error").shouldHave(text(expectedError));
    }
    @ValueSource(strings = {
            "Назад в будущее",
            "Матрица"
    })
    @ParameterizedTest(name = "Проверка выдачи при поиске")
    void movieSearch(String filmName) {
        $("[placeholder='Фильмы, сериалы, персоны']").setValue(filmName).pressEnter();
        $("div.most_wanted p.name").shouldHave(text(filmName));
    }
};

