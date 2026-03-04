package org.example.aspect;

import io.qameta.allure.Allure;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.utils.DriverHolder;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;

@Aspect
public class ScreenshotAspect {

    @Around("@annotation(io.qameta.allure.Step) && within(org.example.pom..*)")
    public Object aroundStep(ProceedingJoinPoint pjp) throws Throwable {
        String methodName = pjp.getSignature().getName();
        attachScreenshot("Before: " + methodName);
        try {
            Object result = pjp.proceed();
            attachScreenshot("After: " + methodName);
            return result;
        } catch (Throwable t) {
            attachScreenshot("Failed: " + methodName);
            throw t;
        }
    }

    private void attachScreenshot(String name) {
        WebDriver driver = DriverHolder.get();
        if (driver instanceof TakesScreenshot) {
            byte[] bytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(name, "image/png", new ByteArrayInputStream(bytes), "png");
        }
    }
}
