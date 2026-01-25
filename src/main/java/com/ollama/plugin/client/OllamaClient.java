package com.ollama.plugin.client;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.Service;
import okhttp3.*;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
public class OllamaClient {

    private final OkHttpClient httpClient;
    private final Gson gson;

    public OllamaClient() {
        this.httpClient = new OkHttpClient.Builder()
                .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
                .readTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
                .build();
        this.gson = new Gson();
    }

    public static OllamaClient getInstance() {
        return ApplicationManager.getApplication().getService(OllamaClient.class);
    }

    /**
     * Check if Ollama is accessible
     */
    public CompletableFuture<Boolean> isConnected(String baseUrl) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Request request = new Request.Builder()
                        .url(baseUrl + "/api/tags")
                        .get()
                        .build();
                
                try (Response response = httpClient.newCall(request).execute()) {
                    return response.isSuccessful();
                }
            } catch (Exception e) {
                return false;
            }
        });
    }

    /**
     * Fetch available models from Ollama
     */
    public CompletableFuture<List<OllamaModel>> fetchModels(String baseUrl) {
        return CompletableFuture.supplyAsync(() -> {
            List<OllamaModel> models = new ArrayList<>();
            try {
                Request request = new Request.Builder()
                        .url(baseUrl + "/api/tags")
                        .get()
                        .build();
                
                try (Response response = httpClient.newCall(request).execute()) {
                    if (response.isSuccessful() && response.body() != null) {
                        String responseBody = response.body().string();
                        JsonObject jsonResponse = gson.fromJson(responseBody, JsonObject.class);
                        
                        if (jsonResponse.has("models")) {
                            JsonArray modelsArray = jsonResponse.getAsJsonArray("models");
                            for (var modelElement : modelsArray) {
                                JsonObject modelObj = modelElement.getAsJsonObject();
                                OllamaModel model = new OllamaModel(
                                    modelObj.get("name").getAsString(),
                                    modelObj.get("size").getAsLong()
                                );
                                models.add(model);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return models;
        });
    }

    /**
     * Send prompt to Ollama and get response
     */
    public CompletableFuture<OllamaResponse> sendPrompt(String baseUrl, String model, String prompt, OllamaResponseCallback callback) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                JsonObject requestBody = new JsonObject();
                requestBody.addProperty("model", model);
                requestBody.addProperty("prompt", prompt);
                requestBody.addProperty("stream", false);

                RequestBody body = RequestBody.create(
                        requestBody.toString(),
                        MediaType.get("application/json")
                );

                Request request = new Request.Builder()
                        .url(baseUrl + "/api/generate")
                        .post(body)
                        .build();

                try (Response response = httpClient.newCall(request).execute()) {
                    if (response.isSuccessful() && response.body() != null) {
                        String responseBody = response.body().string();
                        JsonObject jsonResponse = gson.fromJson(responseBody, JsonObject.class);
                        
                        OllamaResponse ollamaResponse = new OllamaResponse(
                            jsonResponse.has("response") ? jsonResponse.get("response").getAsString() : "",
                            jsonResponse.has("eval_count") ? jsonResponse.get("eval_count").getAsLong() : 0,
                            jsonResponse.has("eval_duration") ? jsonResponse.get("eval_duration").getAsLong() : 0,
                            jsonResponse.has("total_duration") ? jsonResponse.get("total_duration").getAsLong() : 0
                        );

                        if (callback != null) {
                            callback.onSuccess(ollamaResponse);
                        }
                        return ollamaResponse;
                    } else {
                        if (callback != null) {
                            callback.onError("Failed to get response from Ollama");
                        }
                        return new OllamaResponse("Error: " + response.code(), 0, 0, 0);
                    }
                }
            } catch (Exception e) {
                if (callback != null) {
                    callback.onError(e.getMessage());
                }
                return new OllamaResponse("Error: " + e.getMessage(), 0, 0, 0);
            }
        });
    }

    /**
     * Get system metrics from Ollama
     */
    public CompletableFuture<OllamaMetrics> fetchMetrics(String baseUrl) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Request request = new Request.Builder()
                        .url(baseUrl + "/api/ps")
                        .get()
                        .build();
                
                try (Response response = httpClient.newCall(request).execute()) {
                    if (response.isSuccessful() && response.body() != null) {
                        String responseBody = response.body().string();
                        JsonObject jsonResponse = gson.fromJson(responseBody, JsonObject.class);
                        
                        // Parse memory info
                        long memory = 0;
                        if (jsonResponse.has("models") && jsonResponse.getAsJsonArray("models").size() > 0) {
                            JsonObject modelInfo = jsonResponse.getAsJsonArray("models").get(0).getAsJsonObject();
                            if (modelInfo.has("size")) {
                                memory = modelInfo.get("size").getAsLong();
                            }
                        }
                        
                        return new OllamaMetrics(memory, 0, 0);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new OllamaMetrics(0, 0, 0);
        });
    }

    public interface OllamaResponseCallback {
        void onSuccess(OllamaResponse response);
        void onError(String error);
    }
}
