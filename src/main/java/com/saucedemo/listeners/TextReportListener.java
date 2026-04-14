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
    private int testCount = 0;
    private java.util.List<TestResult> testResults = new java.util.ArrayList<>();

    private static class TestResult {
        String name;
        String status;
        long duration;
        String description;

        TestResult(String name, String status, long duration, String description) {
            this.name = name;
            this.status = status;
            this.duration = duration;
            this.description = description;
        }
    }

    @Override
    public void onStart(ITestContext context) {
        suiteStartTime = System.currentTimeMillis();
        testResults.clear();
        try {
            File directory = new File(REPORT_DIRECTORY);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            writer = new BufferedWriter(new FileWriter(REPORT_PATH, false));
            writeLine("╔════════════════════════════════════════════════════════════════╗");
            writeLine("║                      TEST EXECUTION REPORT                     ║");
            writeLine("╚════════════════════════════════════════════════════════════════╝");
            writeLine("");
            writeLine("📊 SUITE INFORMATION");
            writeLine("├─ Suite Name: " + context.getName());
            writeLine("├─ Started: " + LocalDateTime.now().format(FORMATTER));
            writeLine("└─ Date: " + java.time.LocalDate.now());
            writeLine("");
            writeLine("📋 TEST DETAILS");
            writeLine("");
        } catch (IOException exception) {
            throw new RuntimeException("Khong the tao file report text.", exception);
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        // Không cần in ở đây, sẽ gom lại sau
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        long duration = result.getEndMillis() - result.getStartMillis();
        String methodName = result.getMethod().getMethodName();
        String description = result.getMethod().getDescription();
        testResults.add(new TestResult(methodName, "✅ PASS", duration, description));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        long duration = result.getEndMillis() - result.getStartMillis();
        String methodName = result.getMethod().getMethodName();
        String description = result.getMethod().getDescription();
        testResults.add(new TestResult(methodName, "❌ FAIL", duration, description));
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        long duration = 0;
        String methodName = result.getMethod().getMethodName();
        String description = result.getMethod().getDescription();
        testResults.add(new TestResult(methodName, "⊘ SKIP", duration, description));
    }

    @Override
    public void onFinish(ITestContext context) {
        long totalDuration = System.currentTimeMillis() - suiteStartTime;

        // Print table header
        writeLine("┌─────┬──────────────────────────────────────────────┬──────────┬────────────┐");
        writeLine("│ No. │ Test Name                                    │ Status   │ Duration   │");
        writeLine("├─────┼──────────────────────────────────────────────┼──────────┼────────────┤");

        // Print each test result
        int no = 1;
        for (TestResult tr : testResults) {
            String name = truncate(tr.name, 42);
            String line = String.format("│ %-3d │ %-44s │ %-8s │ %6d ms   │",
                no, name, tr.status, tr.duration);
            writeLine(line);
            no++;
        }

        // Print table footer
        writeLine("└─────┴──────────────────────────────────────────────┴──────────┴────────────┘");
        writeLine("");

        // Print summary
        int passed = context.getPassedTests().size();
        int failed = context.getFailedTests().size();
        int skipped = context.getSkippedTests().size();
        int total = passed + failed + skipped;

        writeLine("📈 SUMMARY");
        writeLine("┌──────────────────────────────────────────────────────────┐");
        writeLine(String.format("│ ✅ Passed:       %-42d │", passed));
        writeLine(String.format("│ ❌ Failed:       %-42d │", failed));
        writeLine(String.format("│ ⊘ Skipped:      %-42d │", skipped));
        writeLine(String.format("│ 📊 Total:       %-42d │", total));
        writeLine("├──────────────────────────────────────────────────────────┤");
        writeLine(String.format("│ ⏱️  Total Time:  %-39d s │", Duration.ofMillis(totalDuration).toSeconds()));
        writeLine(String.format("│ ✨ Pass Rate:   %-41.1f%% │", (passed * 100.0 / total)));
        writeLine("└──────────────────────────────────────────────────────────┘");
        writeLine("");
        writeLine("⏲️  Finished: " + LocalDateTime.now().format(FORMATTER));
        writeLine("");

        // Print status
        if (failed == 0) {
            writeLine("╔════════════════════════════════════════════════════════════════╗");
            writeLine("║  🎉 ALL TESTS PASSED! BUILD SUCCESSFUL                       ║");
            writeLine("╚════════════════════════════════════════════════════════════════╝");
        } else {
            writeLine("╔════════════════════════════════════════════════════════════════╗");
            writeLine("║  ⚠️  SOME TESTS FAILED. PLEASE CHECK DETAILS.               ║");
            writeLine("╚════════════════════════════════════════════════════════════════╝");
        }

        closeWriter();
    }

    private String truncate(String str, int length) {
        if (str.length() <= length) {
            return str;
        }
        return str.substring(0, length - 3) + "...";
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
