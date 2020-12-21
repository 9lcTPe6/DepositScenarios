package ru.appline.ibs.homework.framework.utils;

import ru.appline.ibs.homework.framework.pages.BasePage;

/**
 * Интерфейс, отвечающий за возможность страницы возвращать ссылку на другую страницу
 */
public interface NextPageReturnable {

    BasePage getNextPage(String pageName);

}
