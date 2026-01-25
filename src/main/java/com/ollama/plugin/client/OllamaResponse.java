package com.ollama.plugin.client;

public class OllamaResponse {
    private String response;
    private long tokenCount;
    private long evalDuration;
    private long totalDuration;

    public OllamaResponse(String response, long tokenCount, long evalDuration, long totalDuration) {
        this.response = response;
        this.tokenCount = tokenCount;
        this.evalDuration = evalDuration;
        this.totalDuration = totalDuration;
    }

    public String getResponse() {
        return response;
    }

    public long getTokenCount() {
        return tokenCount;
    }

    public long getEvalDuration() {
        return evalDuration;
    }

    public long getTotalDuration() {
        return totalDuration;
    }

    public double getTokensPerSecond() {
        if (evalDuration == 0) return 0;
        return (tokenCount * 1_000_000_000.0) / evalDuration;
    }
}
