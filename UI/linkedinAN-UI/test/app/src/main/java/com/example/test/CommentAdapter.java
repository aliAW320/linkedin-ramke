package com.example.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.List;

public class CommentAdapter extends BaseAdapter {

    private Context context;
    private List<Comment> commentList;

    public CommentAdapter(Context context, List<Comment> commentList) {
        this.context = context;
        this.commentList = commentList;
    }

    @Override
    public int getCount() {
        return commentList.size();
    }

    @Override
    public Object getItem(int position) {
        return commentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommentViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.fragment_comment, parent, false);
            holder = new CommentViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (CommentViewHolder) convertView.getTag();
        }

        Comment comment = commentList.get(position);
        holder.nameText.setText(comment.getUser());
        holder.commentText.setText(comment.getText());

        if (comment.getImageUrl() != null && !comment.getImageUrl().isEmpty()) {
            holder.imageComment.setVisibility(View.VISIBLE);
            // Load the image using a library like Glide or Picasso
            // Glide.with(context).load(comment.getImageUrl()).into(holder.imageComment);
        } else {
            holder.imageComment.setVisibility(View.GONE);
        }

        if (comment.getVideoUrl() != null && !comment.getVideoUrl().isEmpty()) {
            holder.videoComment.setVisibility(View.VISIBLE);
            // Load the video using a method appropriate for your app
            // holder.videoComment.setVideoPath(comment.getVideoUrl());
            // holder.videoComment.start();
        } else {
            holder.videoComment.setVisibility(View.GONE);
        }

        return convertView;
    }

    private static class CommentViewHolder {
        public ImageView profileImage;
        public TextView nameText;
        public ImageView imageComment;
        public VideoView videoComment;
        public TextView commentText;

        public CommentViewHolder(View itemView) {
            profileImage = itemView.findViewById(R.id.profile_image);
            nameText = itemView.findViewById(R.id.nameText);
            imageComment = itemView.findViewById(R.id.image_comment);
            videoComment = itemView.findViewById(R.id.video_comment);
            commentText = itemView.findViewById(R.id.comment_text);
        }
    }
}
