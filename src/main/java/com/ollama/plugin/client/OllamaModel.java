package com.ollama.plugin.client;

public class OllamaModel {
    private String name;
    private long size;

    public OllamaModel(String name, long size) {
        this.name = name;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public long getSize() {
        return size;
    }

    public String getSizeFormatted() {
        if (size == 0) return "0 B";
        long bytes = Math.abs(size);
        if (bytes < 1024) return bytes + " B";
        int z = (63 - Long.numberOfLeadingZeros(bytes)) / 10;
        return String.format("%.1f %sB", (double) bytes / (1L << (z * 10)), " KMGTPE".charAt(z));
    }

    @Override
    public String toString() {
        return name + " (" + getSizeFormatted() + ")";
    }
}
