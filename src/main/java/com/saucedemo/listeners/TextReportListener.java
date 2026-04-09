package com.saucedemo.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TextReportListener implements ITestListener {
    private static final String REPORT_DIRECTORY = "test-output";
    private static final String REPORT_PATH = REPORT_DIRECTORY + "/test-report.txt";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private BufferedWriter writer;
    private long suiteStartTime;

    @Override
    public void onStart(ITestContext context) {
        suiteStartTime = System.currentTimeMillis();
        try {
            File directory = new File(REPORT_DIRECTORY);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            writer = new BufferedWriter(new FileWriter(REPORT_PATH, false));
            writeLine("TEST REPORT");
            writeLine("Suite: " + context.getName());
            writeLine("Started: " + LocalDateTime.now().format(FORMATTER));
            writeLine("");
        } catch (IOException exception) {
            throw new RuntimeException("Khong the tao file report text.", exception);
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        writeLine("START: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        long duration = result.getEndMillis() - result.getStartMillis();
        writeLine("PASS: " + result.getMethod().getMethodName() + " - " + duration + " ms");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String errorMessage = result.getThrowable() != null ? result.getThrowable().getMessage() : "Khong co noi dung loi.";
        writeLine("FAIL: " + result.getMethod().getMethodName() + " - " + sanitize(errorMessage));
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        writeLine("SKIP: " + result.getMethod().getMethodName());
    }

    @Override
    public void onFinish(ITestContext context) {
        long totalDuration = System.currentTimeMillis() - suiteStartTime;
        writeLine("");
        writeLine("SUMMARY");
        writeLine("PASS: " + context.getPassedTests().size());
        writeLine("FAIL: " + context.getFailedTests().size());
        writeLine("SKIP: " + context.getSkippedTests().size());
        writeLine("TOTAL TIME: " + Duration.ofMillis(totalDuration).toSeconds() + " s");
        writeLine("Finished: " + LocalDateTime.now().format(FORMATTER));
        closeWriter();
    }

    private synchronized void writeLine(String text) {
        try {
            if (writer != null) {
                writer.write(text);
                writer.newLine();
                writer.flush();
            }
        } catch (IOException exception) {
            throw new RuntimeException("Khong the ghi file report text.", exception);
        }
    }

    private String sanitize(String text) {
        return text.replace(System.lineSeparator(), " ").trim();
    }

    private void closeWriter() {
        if (writer == null) {
            return;
        }

        try {
            writer.close();
        } catch (IOException exception) {
            throw new RuntimeException("Khong the dong file report text.", exception);
        }
    }
}
