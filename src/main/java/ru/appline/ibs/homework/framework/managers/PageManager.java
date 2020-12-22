package ru.appline.ibs.homework.framework.managers;

import ru.appline.ibs.homework.framework.pages.BasePage;
import ru.appline.ibs.homework.framework.pages.DepositsPage;
import ru.appline.ibs.homework.framework.pages.StartPage;

import java.util.ArrayList;
import java.util.List;

/**
 * Менеджер страниц
 */
public class PageManager {

    private static PageManager pageManager;

    private static List<BasePage> pageList = new ArrayList<>();

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
            pageList.add(startPage);
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
            pageList.add(depositsPage);
        }
        return depositsPage;

    }

    static void cleanPages() {

        for (BasePage pages: pageList) {
            pages = null;
        }
        pageList.clear();

    }

}
