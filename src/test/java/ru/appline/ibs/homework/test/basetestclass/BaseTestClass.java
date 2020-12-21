package ru.appline.ibs.homework.test.basetestclass;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import ru.appline.ibs.homework.framework.managers.PageManager;

import static ru.appline.ibs.homework.framework.managers.FrameworkLaunchManager.launchFramework;
import static ru.appline.ibs.homework.framework.managers.FrameworkLaunchManager.stopFramework;
import static ru.appline.ibs.homework.framework.utils.PropertyVars.CHROME_DRIVER;

public class BaseTestClass {

    protected PageManager page = PageManager.getPageManager();

    @BeforeAll
    public static void runTest() {

        launchFramework(CHROME_DRIVER);

    }

    @AfterAll
    public static void stopTest() {

        stopFramework();

    }

}
