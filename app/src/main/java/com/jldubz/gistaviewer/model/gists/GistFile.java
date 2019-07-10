package com.jldubz.gistaviewer.model.gists;

public class GistFile {
    /*
    Modified sample data from: https://developer.github.com/v3/gists/#list-a-users-gists
    {
        "filename": "hello_world.rb"
    }
    */
    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
