package ru.netology.web.tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.web.data.DataGenerator;
import ru.netology.web.data.LocalDateService;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;

public class CardDeliverySelenideTest {

    private static Faker faker;

    @BeforeEach
    void setup() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
    }

    @Test
    void selenideTest() {

        LocalDateService localDate = new LocalDateService();
        String planningDate = localDate.generateDate(4);
        String replanningDate = localDate.generateDate(6);

        var validUser = DataGenerator.Registration.generateInfo("ru");

        $("[data-test-id='city'] input").val(validUser.getCity());
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").val(validUser.getName());
        $("[data-test-id='phone'] input").val(validUser.getPhone());
        $("[data-test-id='agreement'] span[role]").click();
        $x(".//*[text()='Запланировать']/parent::span/parent::button").click();
        $x(".//*[text()='Успешно!']/following-sibling::div")
                .shouldHave(Condition.text("Встреча успешно запланирована на " + planningDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        $("[data-test-id='date'] input").setValue(replanningDate);
        $x(".//*[text()='Запланировать']/parent::span/parent::button").click();
        $x(".//*[text()='Перепланировать']/parent::span/parent::button").click();
        $("[data-test-id='success-notification']")
                .shouldHave(Condition.text("Встреча успешно запланирована на " + replanningDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);


    }
}
