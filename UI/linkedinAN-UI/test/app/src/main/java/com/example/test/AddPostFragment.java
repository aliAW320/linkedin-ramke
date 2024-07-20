package com.example.test;

import static android.content.ContentValues.TAG;
import static com.example.test.mainScreenActivity2.changeFragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.test.Models.ResponseSpecial;
import com.example.test.utils.GetIp;
import com.example.test.utils.GetToken;
import com.example.test.utils.GsonBuilderRun;
import com.google.gson.JsonObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddPostFragment extends Fragment {
    private ImageView postImage;
    private VideoView postVideo;
    private Uri selectedMediaUri;
    private Button addPostButton;
    private ActivityResultLauncher<Intent> mediaPickerLauncher;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_post, container, false);
        postImage = view.findViewById(R.id.postImage);
        postVideo = view.findViewById(R.id.postVideo);
        Button selectMediaButton = view.findViewById(R.id.select_media);
        Button addPostButton = view.findViewById(R.id.add_post);
        EditText editText = view.findViewById(R.id.postDescription);
        registerResult();
        selectMediaButton.setOnClickListener(view1 -> pickMedia());
        addPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpClient client = new OkHttpClient();
                        JsonObject jsonBody = new JsonObject();
                        jsonBody.addProperty("text", editText.getText().toString());

                        RequestBody requestBody = RequestBody.create(
                                jsonBody.toString(), MediaType.parse("application/json"));

                        Request request = new Request.Builder()
                                .url("http://" + GetIp.get() + ":8000/post")
                                .header("Content-Type", "application/json")
                                .header("User-Agent", "insomnia/9.2.0")
                                .header("Authorization", "Bearer " + GetToken.get(getContext()))
                                .post(requestBody)
                                .build();
                        try (Response response = client.newCall(request).execute()) {
                            int statusCode = response.code();
                            Log.d(TAG, "Status Code: " + statusCode);
                            if (statusCode == 200) {
                                String responseBody = response.body().string();
                                Log.d(TAG, "Response Body: " + responseBody);
                                ResponseSpecial responseSpecial = GsonBuilderRun.getGson().fromJson(responseBody, ResponseSpecial.class);
                                if (responseSpecial.isResult()) {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent = new Intent(getContext(), mainScreenActivity2.class);
                                            startActivity(intent);
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
            }
        });
        return view;
    }

    private void pickMedia() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/* video/*");
        mediaPickerLauncher.launch(intent);
    }

    private void registerResult() {
        mediaPickerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == getActivity().RESULT_OK && result.getData() != null) {
                    Uri mediaUri = result.getData().getData();
                    String mimeType = getActivity().getContentResolver().getType(mediaUri);

                    if (mimeType.startsWith("image")) {
                        postImage.setImageURI(mediaUri);
                        postImage.setVisibility(View.VISIBLE);
                        postVideo.setVisibility(View.GONE);
                    } else if (mimeType.startsWith("video")) {
                        postVideo.setVideoURI(mediaUri);
                        postVideo.setVisibility(View.VISIBLE);
                        postImage.setVisibility(View.GONE);
                        postVideo.start();
                    }
                } else {
                    Toast.makeText(getActivity(), "Media selection canceled", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
