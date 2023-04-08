package ru.netology.web;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.*;

class RegistrationTest {

    public String date(int plusDay, String pattern) {
        return LocalDate.now().plusDays(plusDay).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldRegisterByCard() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Казань");
        $("[data-test-id=date] input.input__control").doubleClick().sendKeys("BACKSPACE");
        String dateOfMeeting = date(6, "dd.MM.yyyy");
        $("[data-test-id=date] input.input__control").sendKeys(dateOfMeeting);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+71234567890");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $x("//div[contains(text(), 'Успешно!')]").shouldBe(visible, Duration.ofSeconds(15));
    }
}