package ru.netology.web.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.*;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999/");
    }

    @Test
    @DisplayName("Transfer to the first card")
        // Начальный запуск
    void shouldTransferMoneyFrom1to2() {
        var loginPage = new LoginPage();
        var autoInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(autoInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(autoInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        // Баланс карты
        var firstCardBalance = dashboardPage.getCardBalance(DataHelper.getCardFirst());
        var secondCardBalance = dashboardPage.getCardBalance(DataHelper.getCardSecond());
        dashboardPage.FirstCardTransfer();
        var transferMoneyPage = new TransferMoneyPage();
        var transferSum = DataHelper.AmountValue(500);
        var topUpFirstCard = transferMoneyPage.transfer(transferSum, DataHelper.getCardSecond().getCardNumber());
        var expectedFirstCardBalance = firstCardBalance + transferSum;
        var expectedSecondCardBalance = secondCardBalance - transferSum;
        //        Получаем фактический результат на дашборде
        var actualFirstCardBalance = dashboardPage.getCardBalance(DataHelper.getCardFirst());
        var actualSecondCardBalance = dashboardPage.getCardBalance(DataHelper.getCardSecond());
//        Сравниваем ожидаемый и фактический результат
        assertEquals(expectedFirstCardBalance, actualFirstCardBalance);
        assertEquals(expectedSecondCardBalance, actualSecondCardBalance);

    }

    @Test
    @DisplayName("Transfer to the Second card")
        // Начальный запуск
    void shouldTransferMoneyFrom2to1() {
        var loginPage = new LoginPage();
        var autoInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(autoInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(autoInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        // Баланс карты
        var firstCardBalance = dashboardPage.getCardBalance(DataHelper.getCardFirst());
        var secondCardBalance = dashboardPage.getCardBalance(DataHelper.getCardSecond());
        dashboardPage.SecondCardTransfer();
        var transferMoneyPage = new TransferMoneyPage();
        var transferSum = DataHelper.AmountValue(500);
        // Ввод суммы на кариу
        var topUpSecondCard = transferMoneyPage.transfer(transferSum, DataHelper.getCardFirst().getCardNumber());
        var expectedFirstCardBalance = firstCardBalance - transferSum;
        var expectedSecondCardBalance = secondCardBalance + transferSum;
        //        Получаем фактический результат на дашборде
        var actualFirstCardBalance = dashboardPage.getCardBalance(DataHelper.getCardFirst());
        var actualSecondCardBalance = dashboardPage.getCardBalance(DataHelper.getCardSecond());
//        Сравниваем ожидаемый и фактический результат
        assertEquals(expectedFirstCardBalance, actualFirstCardBalance);
        assertEquals(expectedSecondCardBalance, actualSecondCardBalance);
    }

    @Test
    @DisplayName("Transfer more than sum in to card balance")
        // Начальный запуск
    void shouldTransferMoreMoneyFrom1to2() {
        var loginPage = new LoginPage();
        var autoInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(autoInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(autoInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        // Баланс карты
        var firstCardBalance = dashboardPage.getCardBalance(DataHelper.getCardFirst());
        var secondCardBalance = dashboardPage.getCardBalance(DataHelper.getCardSecond());
        dashboardPage.FirstCardTransfer();
        var transferMoneyPage = new TransferMoneyPage();
        var transferSum = DataHelper.AmountValue(15500);
        var topUpFirstCard = transferMoneyPage.transfer(transferSum, DataHelper.getCardSecond().getCardNumber());
        transferMoneyPage.errorMessage();

    }

}
