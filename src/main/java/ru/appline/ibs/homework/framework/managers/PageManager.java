package ru.appline.ibs.homework.framework.managers;

import ru.appline.ibs.homework.framework.pages.DepositsPage;
import ru.appline.ibs.homework.framework.pages.StartPage;

/**
 * Менеджер страниц
 */
public class PageManager {

    private static PageManager pageManager;

    StartPage startPage;
    DepositsPage depositsPage;

    /**
     * Конструктор на базе паттерна Singleton
     */
    private PageManager() {

    }

    /**
     * Геттер для PageManager
     * @return PageManager pageManager
     */
    public static PageManager getPageManager() {

        if (pageManager == null) {
            pageManager = new PageManager();
        }
        return pageManager;

    }

    /**
     * Геттер для StartPage
     * @return StartPage startPage
     */
    public  StartPage getStartPage() {

        if (startPage == null) {
            startPage = new StartPage();
        }
        return startPage;

    }

    /**
     * Геттер для DepositsPage
     * @return DepositsPage depositsPage
     */
    public DepositsPage getDepositsPage() {

        if (depositsPage == null) {
            depositsPage = new DepositsPage();
        }
        return depositsPage;

    }

}
