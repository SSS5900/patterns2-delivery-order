package ru.netology.patterns2.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.patterns2.data.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.patterns2.data.DataGenerator.Registration.getUser;
import static ru.netology.patterns2.data.DataGenerator.getRandomLogin;
import static ru.netology.patterns2.data.DataGenerator.getRandomPassword;

public class TestMode {


    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("[class='button__text']").click();
        $("[id='root']").shouldBe(exactText("Личный кабинет")).shouldBe(Condition.visible);


    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        var notRegisteredUser = getUser("active");
        $("[data-test-id='login'] input").setValue(notRegisteredUser.getLogin());
        $("[data-test-id='password'] input").setValue(notRegisteredUser.getPassword());
        $("[class='button__text']").click();
        $(".notification__content").shouldBe(exactText("Ошибка! Неверно указан логин или пароль")).shouldBe(Condition.visible);


    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        var blockedUser = getRegisteredUser("blocked");
        $("[data-test-id='login'] input").setValue(blockedUser.getLogin());
        $("[data-test-id='password'] input").setValue(blockedUser.getPassword());
        $("[class='button__text']").click();
        $(".notification__content").shouldBe(text("Пользователь заблокирован")).should(Condition.visible);


    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();
        $("[data-test-id='login'] input").setValue(wrongLogin);
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("[class='button__text']").click();
        $(".notification__content").shouldBe(exactText("Ошибка! Неверно указан логин или пароль")).should(Condition.visible);


    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(wrongPassword);
        $("[class='button__text']").click();
        $(".notification__content").shouldBe(exactText("Ошибка! Неверно указан логин или пароль")).should(Condition.visible);


    }
}

