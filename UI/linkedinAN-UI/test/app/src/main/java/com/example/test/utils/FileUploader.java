package com.example.test.utils;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

public class FileUploader {

    private static final String TAG = "FileUploader";
    private static final MediaType MEDIA_TYPE_IMAGE = MediaType.get("image/jpeg");
    private static final MediaType MEDIA_TYPE_VIDEO = MediaType.get("video/mp4");

    public static void sendImage(Context context, Uri imageUri, String serverUrl) {
        sendFile(context, imageUri, serverUrl, MEDIA_TYPE_IMAGE, "image.jpg");
    }

    public static void sendVideo(Context context, Uri videoUri, String serverUrl) {
        sendFile(context, videoUri, serverUrl, MEDIA_TYPE_VIDEO, "video.mp4");
    }

    private static void sendFile(Context context, Uri fileUri, String serverUrl, MediaType mediaType, String fileName) {
        OkHttpClient client = new OkHttpClient();

        try {
            InputStream inputStream = context.getContentResolver().openInputStream(fileUri);
            if (inputStream == null) {
                throw new IOException("Unable to open input stream from URI");
            }

            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", fileName, createRequestBody(inputStream, mediaType))
                    .build();

            Request request = new Request.Builder()
                    .url(serverUrl)
                    .post(requestBody)
                    .build();

            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                Log.i(TAG, "Server Response: " + response.body().string());
            } else {
                Log.e(TAG, "Server returned non-OK status: " + response.code());
            }

        } catch (Exception e) {
            Log.e(TAG, "Error uploading file", e);
        }
    }

    private static RequestBody createRequestBody(final InputStream inputStream, final MediaType mediaType) {
        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return mediaType;
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                Source source = null;
                try {
                    source = Okio.source(inputStream);
                    sink.writeAll(source);
                } finally {
                    if (source != null) {
                        source.close();
                    }
                }
            }
        };
    }
}
/* how to use
sendImage Method:

Calls sendFile with image parameters.
sendVideo Method:

Calls sendFile with video parameters.


Uri imageUri = Uri.parse("content://path/to/your/image");
String serverUrl = "http://yourserver.com/upload";
FileUploader.sendImage(getApplicationContext(), imageUri, serverUrl);

Uri videoUri = Uri.parse("content://path/to/your/video");
FileUploader.sendVideo(getApplicationContext(), videoUri, serverUrl);





 */
