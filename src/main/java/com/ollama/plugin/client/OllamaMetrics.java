package com.ollama.plugin.client;

public class OllamaMetrics {
    private long memoryUsage;
    private double cpuUsage;
    private double tokensPerSecond;

    public OllamaMetrics(long memoryUsage, double cpuUsage, double tokensPerSecond) {
        this.memoryUsage = memoryUsage;
        this.cpuUsage = cpuUsage;
        this.tokensPerSecond = tokensPerSecond;
    }

    public long getMemoryUsage() {
        return memoryUsage;
    }

    public String getMemoryUsageFormatted() {
        if (memoryUsage == 0) return "0 B";
        long bytes = Math.abs(memoryUsage);
        if (bytes < 1024) return bytes + " B";
        int z = (63 - Long.numberOfLeadingZeros(bytes)) / 10;
        return String.format("%.2f %sB", (double) bytes / (1L << (z * 10)), " KMGTPE".charAt(z));
    }

    public double getCpuUsage() {
        return cpuUsage;
    }

    public double getTokensPerSecond() {
        return tokensPerSecond;
    }
}
