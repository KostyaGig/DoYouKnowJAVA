package com.kostya.doyouknow.listeners;

import com.kostya.doyouknow.model.Post;

public interface OnItemClick {
    void itemClick(int position, Post currentPost);
}
