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
import java.util.ArrayList;
import java.util.List;

/**
 * HTML Report Listener - Generates beautiful HTML test report
 */
public class HtmlReportListener implements ITestListener {
    private static final String REPORT_DIRECTORY = "test-output";
    private static final String REPORT_PATH = REPORT_DIRECTORY + "/test-report.html";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private BufferedWriter writer;
    private long suiteStartTime;
    private List<TestData> testDataList = new ArrayList<>();

    private static class TestData {
        int number;
        String testName;
        String status;
        long duration;

        TestData(int number, String testName, String status, long duration) {
            this.number = number;
            this.testName = testName;
            this.status = status;
            this.duration = duration;
        }
    }

    @Override
    public void onStart(ITestContext context) {
        suiteStartTime = System.currentTimeMillis();
        testDataList.clear();
        try {
            File directory = new File(REPORT_DIRECTORY);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            writer = new BufferedWriter(new FileWriter(REPORT_PATH, false));
            writeHtmlHeader(context);
        } catch (IOException exception) {
            throw new RuntimeException("Khong the tao file HTML report.", exception);
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        // Nothing needed
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        long duration = result.getEndMillis() - result.getStartMillis();
        testDataList.add(new TestData(
            testDataList.size() + 1,
            result.getMethod().getMethodName(),
            "PASS",
            duration
        ));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        long duration = result.getEndMillis() - result.getStartMillis();
        testDataList.add(new TestData(
            testDataList.size() + 1,
            result.getMethod().getMethodName(),
            "FAIL",
            duration
        ));
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        testDataList.add(new TestData(
            testDataList.size() + 1,
            result.getMethod().getMethodName(),
            "SKIP",
            0
        ));
    }

    @Override
    public void onFinish(ITestContext context) {
        long totalDuration = System.currentTimeMillis() - suiteStartTime;
        int passed = context.getPassedTests().size();
        int failed = context.getFailedTests().size();
        int skipped = context.getSkippedTests().size();
        int total = passed + failed + skipped;

        writeTestTable();
        writeSummary(passed, failed, skipped, total, totalDuration);
        writeHtmlFooter();
        closeWriter();
    }

    private void writeHtmlHeader(ITestContext context) throws IOException {
        String html = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Test Report - " + context.getName() + "</title>\n" +
                "    <style>\n" +
                "        * { margin: 0; padding: 0; box-sizing: border-box; }\n" +
                "        body {\n" +
                "            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;\n" +
                "            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);\n" +
                "            min-height: 100vh;\n" +
                "            padding: 20px;\n" +
                "        }\n" +
                "        .container {\n" +
                "            max-width: 1200px;\n" +
                "            margin: 0 auto;\n" +
                "            background: white;\n" +
                "            border-radius: 10px;\n" +
                "            box-shadow: 0 20px 60px rgba(0,0,0,0.3);\n" +
                "            overflow: hidden;\n" +
                "        }\n" +
                "        .header {\n" +
                "            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);\n" +
                "            color: white;\n" +
                "            padding: 40px 30px;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "        .header h1 {\n" +
                "            font-size: 2.5em;\n" +
                "            margin-bottom: 10px;\n" +
                "            text-shadow: 2px 2px 4px rgba(0,0,0,0.2);\n" +
                "        }\n" +
                "        .header p {\n" +
                "            font-size: 1.1em;\n" +
                "            opacity: 0.9;\n" +
                "        }\n" +
                "        .content {\n" +
                "            padding: 30px;\n" +
                "        }\n" +
                "        .info-box {\n" +
                "            display: grid;\n" +
                "            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));\n" +
                "            gap: 20px;\n" +
                "            margin-bottom: 30px;\n" +
                "        }\n" +
                "        .info-item {\n" +
                "            background: #f8f9fa;\n" +
                "            padding: 20px;\n" +
                "            border-radius: 8px;\n" +
                "            border-left: 4px solid #667eea;\n" +
                "        }\n" +
                "        .info-label {\n" +
                "            font-weight: 600;\n" +
                "            color: #333;\n" +
                "            margin-bottom: 5px;\n" +
                "        }\n" +
                "        .info-value {\n" +
                "            font-size: 1.2em;\n" +
                "            color: #667eea;\n" +
                "            font-weight: 700;\n" +
                "        }\n" +
                "        h2 {\n" +
                "            color: #333;\n" +
                "            margin-bottom: 20px;\n" +
                "            border-bottom: 3px solid #667eea;\n" +
                "            padding-bottom: 10px;\n" +
                "            font-size: 1.5em;\n" +
                "        }\n" +
                "        table {\n" +
                "            width: 100%;\n" +
                "            border-collapse: collapse;\n" +
                "            margin-bottom: 30px;\n" +
                "            box-shadow: 0 2px 8px rgba(0,0,0,0.1);\n" +
                "            border-radius: 8px;\n" +
                "            overflow: hidden;\n" +
                "        }\n" +
                "        th {\n" +
                "            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);\n" +
                "            color: white;\n" +
                "            padding: 15px;\n" +
                "            text-align: left;\n" +
                "            font-weight: 600;\n" +
                "        }\n" +
                "        td {\n" +
                "            padding: 12px 15px;\n" +
                "            border-bottom: 1px solid #eee;\n" +
                "        }\n" +
                "        tr:hover {\n" +
                "            background-color: #f8f9fa;\n" +
                "        }\n" +
                "        .status-pass {\n" +
                "            color: #28a745;\n" +
                "            font-weight: 600;\n" +
                "        }\n" +
                "        .status-pass::before {\n" +
                "            content: '✅ ';\n" +
                "        }\n" +
                "        .status-fail {\n" +
                "            color: #dc3545;\n" +
                "            font-weight: 600;\n" +
                "        }\n" +
                "        .status-fail::before {\n" +
                "            content: '❌ ';\n" +
                "        }\n" +
                "        .status-skip {\n" +
                "            color: #ffc107;\n" +
                "            font-weight: 600;\n" +
                "        }\n" +
                "        .status-skip::before {\n" +
                "            content: '⊘ ';\n" +
                "        }\n" +
                "        .summary {\n" +
                "            display: grid;\n" +
                "            grid-template-columns: repeat(4, 1fr);\n" +
                "            gap: 15px;\n" +
                "            margin-bottom: 30px;\n" +
                "        }\n" +
                "        .summary-item {\n" +
                "            padding: 20px;\n" +
                "            border-radius: 8px;\n" +
                "            text-align: center;\n" +
                "            color: white;\n" +
                "            font-weight: 600;\n" +
                "        }\n" +
                "        .summary-item.pass {\n" +
                "            background: linear-gradient(135deg, #28a745 0%, #20c997 100%);\n" +
                "        }\n" +
                "        .summary-item.fail {\n" +
                "            background: linear-gradient(135deg, #dc3545 0%, #fd7e14 100%);\n" +
                "        }\n" +
                "        .summary-item.skip {\n" +
                "            background: linear-gradient(135deg, #ffc107 0%, #ff9800 100%);\n" +
                "        }\n" +
                "        .summary-item.total {\n" +
                "            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);\n" +
                "        }\n" +
                "        .summary-number {\n" +
                "            font-size: 2em;\n" +
                "            margin-bottom: 5px;\n" +
                "        }\n" +
                "        .summary-label {\n" +
                "            font-size: 0.9em;\n" +
                "            opacity: 0.9;\n" +
                "        }\n" +
                "        .footer {\n" +
                "            background: #f8f9fa;\n" +
                "            padding: 20px 30px;\n" +
                "            text-align: center;\n" +
                "            border-top: 1px solid #eee;\n" +
                "            color: #666;\n" +
                "            font-size: 0.9em;\n" +
                "        }\n" +
                "        .status-message {\n" +
                "            padding: 20px;\n" +
                "            margin-bottom: 20px;\n" +
                "            border-radius: 8px;\n" +
                "            text-align: center;\n" +
                "            font-weight: 600;\n" +
                "            font-size: 1.1em;\n" +
                "        }\n" +
                "        .status-message.success {\n" +
                "            background: #d4edda;\n" +
                "            color: #155724;\n" +
                "            border: 1px solid #c3e6cb;\n" +
                "        }\n" +
                "        .status-message.failed {\n" +
                "            background: #f8d7da;\n" +
                "            color: #721c24;\n" +
                "            border: 1px solid #f5c6cb;\n" +
                "        }\n" +
                "        @media (max-width: 768px) {\n" +
                "            .summary {\n" +
                "                grid-template-columns: repeat(2, 1fr);\n" +
                "            }\n" +
                "            .header h1 {\n" +
                "                font-size: 1.8em;\n" +
                "            }\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div class=\"container\">\n" +
                "    <div class=\"header\">\n" +
                "        <h1>🧪 Test Execution Report</h1>\n" +
                "        <p>" + context.getName() + "</p>\n" +
                "        <p style=\"margin-top: 10px; font-size: 0.95em; opacity: 0.8;\">Started at: " + LocalDateTime.now().format(FORMATTER) + "</p>\n" +
                "    </div>\n" +
                "    <div class=\"content\">\n";

        writer.write(html);
        writer.flush();
    }

    private void writeTestTable() throws IOException {
        writer.write("        <h2>📋 Test Results</h2>\n");
        writer.write("        <table>\n");
        writer.write("            <thead>\n");
        writer.write("                <tr>\n");
        writer.write("                    <th style=\"width: 5%;\">No.</th>\n");
        writer.write("                    <th style=\"width: 60%;\">Test Name</th>\n");
        writer.write("                    <th style=\"width: 20%;\">Status</th>\n");
        writer.write("                    <th style=\"width: 15%;\">Duration (ms)</th>\n");
        writer.write("                </tr>\n");
        writer.write("            </thead>\n");
        writer.write("            <tbody>\n");

        for (TestData test : testDataList) {
            String statusClass = "status-" + test.status.toLowerCase();
            writer.write("                <tr>\n");
            writer.write("                    <td>" + test.number + "</td>\n");
            writer.write("                    <td>" + test.testName + "</td>\n");
            writer.write("                    <td><span class=\"" + statusClass + "\">" + test.status + "</span></td>\n");
            writer.write("                    <td>" + test.duration + "</td>\n");
            writer.write("                </tr>\n");
        }

        writer.write("            </tbody>\n");
        writer.write("        </table>\n");
        writer.flush();
    }

    private void writeSummary(int passed, int failed, int skipped, int total, long totalDuration) throws IOException {
        double passRate = total > 0 ? (passed * 100.0 / total) : 0;
        String statusMessage = failed == 0 ?
            "<div class=\"status-message success\">✅ All Tests Passed! Build Successful</div>" :
            "<div class=\"status-message failed\">⚠️ Some Tests Failed. Please review the details above.</div>";

        String html = "        " + statusMessage + "\n" +
                "        <h2>📊 Summary</h2>\n" +
                "        <div class=\"summary\">\n" +
                "            <div class=\"summary-item pass\">\n" +
                "                <div class=\"summary-number\">" + passed + "</div>\n" +
                "                <div class=\"summary-label\">Passed</div>\n" +
                "            </div>\n" +
                "            <div class=\"summary-item fail\">\n" +
                "                <div class=\"summary-number\">" + failed + "</div>\n" +
                "                <div class=\"summary-label\">Failed</div>\n" +
                "            </div>\n" +
                "            <div class=\"summary-item skip\">\n" +
                "                <div class=\"summary-number\">" + skipped + "</div>\n" +
                "                <div class=\"summary-label\">Skipped</div>\n" +
                "            </div>\n" +
                "            <div class=\"summary-item total\">\n" +
                "                <div class=\"summary-number\">" + total + "</div>\n" +
                "                <div class=\"summary-label\">Total</div>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "        <div class=\"info-box\">\n" +
                "            <div class=\"info-item\">\n" +
                "                <div class=\"info-label\">Total Execution Time</div>\n" +
                "                <div class=\"info-value\">" + Duration.ofMillis(totalDuration).toSeconds() + " s</div>\n" +
                "            </div>\n" +
                "            <div class=\"info-item\">\n" +
                "                <div class=\"info-label\">Pass Rate</div>\n" +
                "                <div class=\"info-value\">" + String.format("%.1f%%", passRate) + "</div>\n" +
                "            </div>\n" +
                "            <div class=\"info-item\">\n" +
                "                <div class=\"info-label\">Completed At</div>\n" +
                "                <div class=\"info-value\" style=\"font-size: 0.9em;\">" + LocalDateTime.now().format(FORMATTER) + "</div>\n" +
                "            </div>\n" +
                "        </div>\n";

        writer.write(html);
        writer.flush();
    }

    private void writeHtmlFooter() throws IOException {
        String footer = "    </div>\n" +
                "    <div class=\"footer\">\n" +
                "        <p>Generated by TestingAutomation Framework | " + LocalDateTime.now().format(FORMATTER) + "</p>\n" +
                "    </div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";

        writer.write(footer);
        writer.flush();
    }

    private synchronized void closeWriter() {
        if (writer == null) {
            return;
        }

        try {
            writer.close();
        } catch (IOException exception) {
            throw new RuntimeException("Khong the dong file HTML report.", exception);
        }
    }
}

