package ru.appline.ibs.homework.test.tests;

import org.junit.jupiter.api.Test;
import ru.appline.ibs.homework.test.basetestclass.BaseTestClass;

public class UsdDepositTest extends BaseTestClass {

    @Test
    public void begin() {

        page.getStartPage()
                .checkStartPage()
                .getNextPage("Вклады")
                .checkDepositsPage()
                .chooseCurrency("USD")
                .chooseDepositSum(500000L)
                .chooseDepositPeriod(9)
                .monthlyPayment(70000L)
                .choosePercentage(1)
                .checkPercentagesGets()
                .checkTotalMonthlyPayments()
                .checkWhatWeGetInTheEnd();

    }

}
