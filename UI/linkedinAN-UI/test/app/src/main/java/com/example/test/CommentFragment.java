package com.example.test;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommentFragment extends Fragment {

    private ListView listView;
    private CommentAdapter commentAdapter;
    private List<Comment> commentList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_item_comment, container, false);

        listView = view.findViewById(R.id.list_view);

        // Initialize comment list and adapter
        commentList = new ArrayList<>();
        // Add some sample comments to the list
        commentList.add(new Comment("User1", "This is a comment", null, null, new Date()));
        commentList.add(new Comment("User2", "This is another comment", "https://example.com/image.jpg", null, new Date()));
        commentList.add(new Comment("User3", "This comment has a video", null, "https://example.com/video.mp4", new Date()));

        commentAdapter = new CommentAdapter(getContext(), commentList);
        listView.setAdapter(commentAdapter);

        return view;
    }
}
