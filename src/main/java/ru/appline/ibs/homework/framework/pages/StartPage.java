package ru.appline.ibs.homework.framework.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.appline.ibs.homework.framework.utils.NextPageReturnable;

import java.util.List;

/**
 * Стартовая страницы
 */
public class StartPage extends BasePage implements NextPageReturnable {

    /**
     * Меню с продуктами банка
     */
    @FindBy(xpath = "//div[@class='services services_main']")
    List<WebElement> startPageProductMenu;

    /**
     * Переменная для xPath до нужного меню
     */
    private String menuNav = "//div[contains(text(), '%s')]";

    /**
     * Проверка, что данная страница является стартовой
     * @return StartPage startPage
     */
    public StartPage checkStartPage() {

        Assertions.assertEquals("Банк «Ренессанс Кредит»", getPageTitle(), "Заголовок страницы не соответсвует ожидаемому");
        return this;

    }

    /**
     * Переопредение метода интерфейса NextPageReturnable
     * @param pageName - название продукта, к странице которого хотим перейти
     * @return DepositPage getDepositPage(), поскольку на текущем этапе других страниц, кроме Вкладов, не реализовано.
     */
    @Override
    public DepositsPage getNextPage(String pageName) {

        switch (pageName) {
            case "Карты":
            case "Кредиты":
            case "Инвестиции":
                System.out.println("Данная страница не реализована в текущей версии Фреймворка");
                break;
            case "Вклады":
                menuNav = String.format(menuNav, pageName);
                for (WebElement element : startPageProductMenu) {
                    WebElement deposits = element.findElement(By.xpath(menuNav + "/../a[@href='/contributions/']"));
                    scrollUntilVisible(deposits, 20);
                    action.moveToElement(deposits)
                            .click()
                            .build()
                            .perform();
                    return page.getDepositsPage();
                }
        }
        Assertions.fail("Такой пункт в меню не найден");
        return null;

    }

}
