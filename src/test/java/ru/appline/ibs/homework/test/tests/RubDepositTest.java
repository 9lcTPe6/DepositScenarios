package ru.appline.ibs.homework.test.tests;

import org.junit.jupiter.api.Test;
import ru.appline.ibs.homework.test.basetestclass.BaseTestClass;

public class RubDepositTest extends BaseTestClass {

    @Test
    public void begin() {

        page.getStartPage()
                .checkStartPage()
                .getNextPage("Вклады")
                .checkDepositsPage()
                .chooseCurrency("RUB")
                .chooseDepositSum(300000L)
                .chooseDepositPeriod(6)
                .monthlyPayment(50000L)
                .choosePercentage(1)
                .checkPercentagesGets()
                .checkTotalMonthlyPayments()
                .checkWhatWeGetInTheEnd();

    }

}
