package org.example;

import com.codeborne.pdftest.PDF;
import com.codeborne.selenide.*;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import java.io.File;
import java.io.IOException;
import static com.codeborne.selenide.FileDownloadMode.PROXY;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoginTest {
    @BeforeClass
    public static void setUp() {
        closeWebDriver();
        Configuration.proxyEnabled = true;
        Configuration.proxyHost = "0.0.0.0";
        Configuration.fileDownload = PROXY;
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));
        Configuration.downloadsFolder = "/Users/salavatamingaliev/Documents/5semestr/TEST/Selenide2/Downloads";
    }
    @Test
    public void LoginTest() throws IOException {
        open("https://passport.yandex.ru");
        // Вход в Яндекс Паспорт
        $(By.name("login")).setValue("salavatamingaliev@yandex.ru").pressEnter();
        $(By.name("passwd")).val("220300sa").pressEnter();
    }
    @Test
    public void downloadTest() throws IOException {
        $(By.xpath("//*[contains(@class, 'user-pic__image')]")).click();
        $(byText("Мой диск")).click();
        Wait().until(titleIs("Яндекс.Диск"));
        // Скачивание файла
        ElementsCollection elements =  elements(By.xpath("//*[contains(@class, 'clamped-text')]"));
        elements.first().contextClick();
        sleep(1000);
        $(By.xpath("//*[contains(@class, 'Menu-Item Menu-Item_type_menuitem resources-actions-popup__action resources-actions-popup__action_type_download')]")).shouldBe(visible).click();
        sleep(10000);
        // Проверка скачанного файла
        PDF pdf = new PDF(new File("/Users/salavatamingaliev/Documents/5semestr/TEST/Selenide2/Downloads/1634317318029_1128_1/Lab6.pdf"));
        assertThat(pdf, PDF.containsText("Напишите консольное приложение C#, в котором"));
        assertThat(pdf, PDF.containsText("Задание 2."));
        assertThat(pdf, PDF.containsText("Средствами Entity Framework,"));
        assertThat(pdf, PDF.containsText("В программе выведите на экран"));
    }
    @Test
    public void uploadTest() throws IOException {
        File file = $(byClassName("upload-button__attach")).uploadFile(new File("/Users/salavatamingaliev/Documents/5semestr/TEST/Selenide2/Amingaliev1.xlsx"));
        sleep(1000);
        $(By.xpath("//*[contains(@class, 'Button2 Button2_theme_raised Button2_view_action Button2_size_m confirmation-dialog__button confirmation-dialog__button_submit ')]")).click();
        sleep(10000);
        $(By.xpath("//*[contains(@class, 'Button2 Button2_view_clear-inverse Button2_size_m uploader-progress__close-button')]")).click();
        $(By.xpath("//*[contains(@class, 'user-pic__image')]")).click();
        $(byText("Выйти")).click();
    }

}