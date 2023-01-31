package fr.lpprism.Main.mapManager;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PLaceHolderAPI {
    @GET("getAllToilettes")
    Call<PlaceHolderPost[]> getAllToilettes();
}