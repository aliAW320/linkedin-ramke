package com.example.test;

import static android.content.ContentValues.TAG;
import static com.example.test.mainScreenActivity2.changeFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ThemedSpinnerAdapter;

import com.example.test.Models.ResponseSpecial;
import com.example.test.Models.User;
import com.example.test.utils.GetIp;
import com.example.test.utils.GetToken;
import com.example.test.utils.GsonBuilderRun;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HomeFragment extends Fragment {
    Button addPostButton;
    ListView listView;
    PostAdapter postAdapter;
    List<Post> postList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        addPostButton = view.findViewById(R.id.add_post);
        listView = view.findViewById(R.id.list_view);

        postList = new ArrayList<>();

        new Thread(new Runnable() {
            @Override
            public void run() {
                    OkHttpClient client = new OkHttpClient();
                    JsonObject jsonBody = new JsonObject();
                    RequestBody requestBody = RequestBody.create(
                            jsonBody.toString(), MediaType.parse("application/json"));
                    Request request = new Request.Builder()
                            .url("http://" + GetIp.get() + ":8000/post")
                            .header("Content-Type", "application/json")
                            .header("User-Agent", "insomnia/9.2.0")
                            .header("Authorization", "Bearer " + GetToken.get(getContext()))
                            .get()
                            .build();
                    try (Response response = client.newCall(request).execute()) {
                        int statusCode = response.code();
                        Log.d(TAG, "Status Code: " + statusCode);
                        if (statusCode == 200) {
                            String responseBody = response.body().string();
                            Log.d(TAG, "Response Body: " + responseBody);
                            ResponseSpecial responseSpecial = GsonBuilderRun.getGson().fromJson(responseBody, ResponseSpecial.class);
                            List<Post> posts = responseSpecial.getListPost();
                            if (responseSpecial.isResult()) {
                                User user = responseSpecial.getUser();
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        postList.addAll(posts);
                                        postAdapter = new PostAdapter(getContext(), postList);
                                        listView.setAdapter(postAdapter);
                                    }
                                });
                            } else {
                                Log.e(TAG, "Response special indicates an error: " + responseSpecial.getMessage());
                            }
                        } else {
                            Log.e(TAG, "Network request failed with status code: " + statusCode);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e(TAG, "Network request failed", e);
                    }
                }
        }).start();


        addPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new AddPostFragment());
            }
        });

        return view;
    }
}
