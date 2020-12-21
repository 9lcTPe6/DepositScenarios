package ru.appline.ibs.homework.framework.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import javax.swing.plaf.TableHeaderUI;
import java.util.List;
import java.util.NoSuchElementException;

import static ru.appline.ibs.homework.framework.managers.WebDriverManager.getDriver;

/**
 * Страница с калькулятором вкладов
 * P.S. Можно было сделать один объект String = "//label[contains(text(), '%s')]", но мне было лень.
 */
public class DepositsPage extends BasePage {

    /**
     * Меню с калькулятором вкладов
     */
    @FindBy(xpath = "//div[@class='calculator js-deposits-calculator']")
    List<WebElement> depositsCalculator;

    /**
     * Поле с выбором, где открыть вклад
     */
    @FindBy(xpath = "//label[contains(text(), 'Открытие вклада')]/..")
    List<WebElement> whereToOpenDeposit;

    /**
     * Поле, содержащее информацию с результатом расчетов
     */
    @FindBy(xpath = "//div[@class='calculator__result-block']")
    List<WebElement> calculatorResultsBlock;

    @FindBy(xpath = ".//select")
    WebElement selectOption;

    /**
     * Переменные, используемые для универсального доступа к пунктам калькулятора
     */
    private String currencyChooser = "//span[contains(text(), '%s')]/../../input";
    private String sWhereToOpenDeposit = "//span[contains(text(), '%s')]/../..//input[@class='calculator__check']";
    private String capitalisationOptions = "//span[contains(text(), '%s')]/../..//input";
    private String calculatorResults = "//span[@class='js-calc-%s']";

    /**
     * Проверка, что данная страница является страницей со вкладами
     *
     * @return DepositsPage getDepositsPage()
     */
    public DepositsPage checkDepositsPage() {

        Assertions.assertEquals("Вклады", getPageTitle(), "Заголовок страницы не соответствует ожидаемому");
        return this;

    }

    /**
     * Выбор валюты вклада
     *
     * @param currencyShortName - универсальное международное обозначение валюты. Доступны только RUB и USD
     * @return DepositsPage getDepositsPage()
     */
    public DepositsPage chooseCurrency(String currencyShortName) {

        switch (currencyShortName) {
            case "RUB":
                currencyChooser = String.format(currencyChooser, "Рубли");
                clickOnElement(currencyChooser);
                checkChooseCurrency("p");
                return this;
            case "USD":
                currencyChooser = String.format(currencyChooser, "Доллары США");
                clickOnElement(currencyChooser);
                checkChooseCurrency("$");
                return this;
            default:
                System.out.println("Упс! Кажется, такой валюты нет.");
        }
        Assertions.fail("Введённая валюта не найдена. Попробуйте ввести RUB или USD.");
        return this;

    }

    private void checkChooseCurrency(String currency) {

        for (int i = 0; i < 5; i++) {
            if (getDriver().findElement(By.xpath(".//span[@class='js-calc-currency']")).getText().equals(currency)) {
                return;
            }
            waitALittle(500L);
        }
        Assertions.fail("Элемент со знаком валюты не найден");

    }

    /**
     * Выбор места открытия вклада
     *
     * @param whereOpen - логическая переменная. "true" - если нужно открыть в отделении банка
     *                  или "false" - для открытия в интернет-банке
     * @return DepositsPage getDepositsPage()
     */
    public DepositsPage whereToOpen(boolean whereOpen) {

        if (whereOpen) {
            sWhereToOpenDeposit = String.format(sWhereToOpenDeposit, "В отделении банка");
        } else {
            sWhereToOpenDeposit = String.format(sWhereToOpenDeposit, "В интернет-банке");
        }
        clickOnElement(whereToOpenDeposit, sWhereToOpenDeposit);
        return this;

    }

    /**
     * Выбор суммы депозита
     *
     * @param depositAmount - сумма депозита типа Long. Использовать L в конце числа, либо каст (long)
     *                      для корректной работы метода
     * @return DepositsPage getDepositsPage()
     */
    public DepositsPage chooseDepositSum(Long depositAmount) {

        String depositSum = "//label[contains(text(), 'Сумма вклада')]/..//input";
        fillInputFrame(depositAmount.toString(), depositSum);
        return this;

    }

    /**
     * Выбор периода вклада
     *
     * @param period - период, на который выбирается вклад. На текущий момент доступно 3, 6, 9, 12, 13 или 18 месяце.
     *               Для корректной работы необходимо использовать каст (short)
     * @return DepositsPage getDepositsPage()
     */
    public DepositsPage chooseDepositPeriod(Integer period) {

        Select selector = new Select(selectOption);
        try {
            selector.selectByValue(period.toString());
        } catch (NoSuchElementException e) {
            Assertions.fail("Указанный период не представлен банком");
        }
        return this;

    }

    /**
     * Выбор размера ежемесячного платежа
     *
     * @param amount - сумма ежемесячного платежа. Использовать L в конце числа, либо каст (long)
     *               для корректной работы метода.
     * @return DepositsPage getDepositsPage()
     */
    public DepositsPage monthlyPayment(Long amount) {

        String monthlyPayLocator = "//label[contains(text(), 'Ежемесячное пополнение')]/..//input";
        fillInputFrame(amount.toString(), monthlyPayLocator);
        return this;

    }

    /**
     * Выбор опций для начисленных процентов
     *
     * @param howManyOptions - число в промежутке 1-3, где:
     *                       1 - выбор "Ежемесячной капитализации"
     *                       2 - выбор "Частичного снятия"
     *                       3 - для выбора обеих опций
     * @return DepositsPage getDepositsPage()
     */
    public DepositsPage choosePercentage(Integer howManyOptions) {

        switch (howManyOptions) {
            case 1:
                capitalisationOptions = String.format(capitalisationOptions, "Ежемесячная капитализация");
                clickOnElement(capitalisationOptions);
                return this;
            case 2:
                capitalisationOptions = String.format(capitalisationOptions, "Частичное снятие");
                clickOnElement(capitalisationOptions);
                return this;
            case 3:
                String option1 = String.format(capitalisationOptions, "Ежемесячная капитализация");
                clickOnElement(option1);
                String option2 = String.format(capitalisationOptions, "Частичное снятие");
                clickOnElement(option2);
                return this;
        }
        Assertions.fail("Ничего не выбрано, либо элемент не найден");
        return this;

    }

    /**
     * Проверка поля "Начисленные проценты". Выводит в консоль информацию о количестве заработанных процентов
     *
     * @return DepositsPage getDepositsPage()
     */
    public DepositsPage checkPercentagesGets() {

        waitALittle((long) 1000);
        String newCalculatorResults = String.format(calculatorResults, "earned");
        for (WebElement element : calculatorResultsBlock) {
            WebElement newElement = element.findElement(By.xpath(newCalculatorResults));
            System.out.println("Начислено %: " + newElement.getText());
            return this;
        }
        Assertions.fail("Блок с начисленными процентами не найден");
        return this;

    }

    /**
     * Проверка поля "Пополнение за N месяцев". Выводит в консоль информацию об общей сумме ежемесячных платежей
     *
     * @return DepositsPage getDepositsPage()
     */
    public DepositsPage checkTotalMonthlyPayments() {

        waitALittle((long) 500);
        String newCalculatorResults = String.format(calculatorResults, "replenish");
        for (WebElement element : calculatorResultsBlock) {
            WebElement newElement = element.findElement(By.xpath(newCalculatorResults));
            System.out.println("За выбранный период вклад пополнен на: " + newElement.getText());
            return this;
        }
        Assertions.fail("Блок с информацией о пополнении не найден");
        return this;

    }

    /**
     * Проверка поля "К снятию через N месяцев". Выводит в консоль информацию об общей сумме, поступящей со вклада
     *
     * @return DepositsPage getDepositsPage()
     */
    public DepositsPage checkWhatWeGetInTheEnd() {

        waitALittle((long) 500);
        String newCalculatorResults = String.format(calculatorResults, "result");
        for (WebElement element : calculatorResultsBlock) {
            WebElement newElement = element.findElement(By.xpath(newCalculatorResults));
            System.out.println("К концу вклада будет заработано: " + newElement.getText());
            return this;
        }
        Assertions.fail("Блок с информацией о заработанной сумме со вклада не найден");
        return this;

    }

    /**
     * Метод для клика по элементу
     *
     * @param elementToBeClicked - xPath Веб-элемента для клика
     */
    private void clickOnElement(String elementToBeClicked) {

        for (WebElement element : depositsCalculator) {
            WebElement newElement = element.findElement(By.xpath(elementToBeClicked));
            scrollUntilVisible(newElement, 20);
            action.moveToElement(newElement)
                    .click()
                    .build()
                    .perform();
        }

    }

    /**
     * Перегрузка метода clickOnElement
     *
     * @param listOfWebElements  - коллекция Веб-элементов, в которой содержится необходимый Веб-элемент
     * @param elementToBeClicked - xPath Веб-элемента для клика
     */
    private void clickOnElement(List<WebElement> listOfWebElements, String elementToBeClicked) {

        for (WebElement element : listOfWebElements) {
            WebElement newElement = element.findElement(By.xpath(elementToBeClicked));
            scrollUntilVisible(newElement, 20);
            action.moveToElement(newElement)
                    .click()
                    .build()
                    .perform();
        }

    }

    /**
     * Метод для заполнения данных
     *
     * @param input             - строка, которую хотим ввести в поле
     * @param elementWhereInput - xPath поля
     */
    private void fillInputFrame(String input, String elementWhereInput) {

        for (WebElement element : depositsCalculator) {
            WebElement newElement = element.findElement(By.xpath(elementWhereInput));
            scrollUntilVisible(newElement, 20);
            action.moveToElement(newElement)
                    .click()
                    .sendKeys(input)
                    .build()
                    .perform();
        }

    }

}
