package ru.appline.ibs.homework.framework.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.appline.ibs.homework.framework.managers.PageManager;

import java.util.Date;

import static ru.appline.ibs.homework.framework.managers.WebDriverManager.getDriver;

/**
 * Базовый класс для всех страниц
 */
public class BasePage {

    protected PageManager page = PageManager.getPageManager();
    protected Actions action = new Actions(getDriver());
    protected JavascriptExecutor js = (JavascriptExecutor) getDriver();
    protected WebDriverWait wait = new WebDriverWait(getDriver(), 30, 1000);

    public BasePage() {

        PageFactory.initElements(getDriver(), this);

    }

    /**
     * Метод для пролистывания страницы
     *
     * @param element - Веб-элемент, до которого необходимо прокрутить страницу
     * @see BasePage#scrollUntilVisible(WebElement element, long howLongWaitInSeconds)
     * @deprecated выбрасывает исключение, если указанный элемент не найден, или находится внутри Frame.
     * рекомендуется использовать scrollUntilVisible
     */
    protected void scrollToElement(WebElement element) {

        js.executeScript("arguments[0].scrollIntoView({block: \"center\", behavior: \"smooth\"})", element);

    }

    /**
     * Метод для ожидания кликабельности Веб-элемента
     *
     * @param element - Веб-элемент, на который необходимо кликнуть
     * @return WebElement element
     */
    protected WebElement elementToBeClickable(WebElement element) {

        return wait.until(ExpectedConditions.elementToBeClickable(element));

    }

    /**
     * Метод для прокручивания страницы до тех пор, пока элемент не будет виден.
     *
     * @param element              - Веб-элемент, до которого будет происходить прокрутка
     * @param howLongWaitInSeconds - время ожидания, по истечение которого метод прекратит работать
     */
    protected void scrollUntilVisible(WebElement element, long howLongWaitInSeconds) {

        boolean isVisible = false;
        Date startWas = new Date();
        Date current;
        long timer = 0;
        while (!isVisible) {
            try {
                scrollToElement(element);
                isVisible = true;
            } catch (NoSuchElementException e) {
                js.executeScript("javascript:window.scrollBy(0,250)");
                current = new Date();
                timer = current.getTime() - startWas.getTime();
            }
            if (timer > howLongWaitInSeconds) {
                System.out.println("Время ожидания истекло.");
                isVisible = true;
            }
        }

    }

    /**
     * Метод для получения заголовка страницы
     *
     * @return String pageTitle
     */
    protected String getPageTitle() {

        return getDriver().getTitle();

    }

    /**
     * Метод для ожидания. Используется, чтобы значения успели поменяться и записаться в соотвествующие переменные
     *
     * @param mills - время ожидания в милисекундах
     */
    protected void waitALittle(Long mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
