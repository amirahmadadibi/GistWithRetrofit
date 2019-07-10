package com.jldubz.gistaviewer.viewmodel;

import android.view.View;

import com.jldubz.gistaviewer.model.Constants;
import com.jldubz.gistaviewer.model.gists.Gist;
import com.jldubz.gistaviewer.model.gists.GistComment;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * ViewModel that handles business logic for a GistActivity
 *
 * @author Jon-Luke West
 * @see com.jldubz.gistaviewer.ui.gists.GistActivity
 */
public class GistViewModel extends ViewModel {

    private MutableLiveData<Gist> mGist;
    private MutableLiveData<List<GistComment>> mComments = new MutableLiveData<>();
    private MutableLiveData<Integer> mProgressBarVisibility = new MutableLiveData<>();
    private MutableLiveData<Integer> mCommentsProgressBarVisibility = new MutableLiveData<>();
    private MutableLiveData<String> mErrorMessage = new MutableLiveData<>();
    private MutableLiveData<Boolean> mIsGistStarred = new MutableLiveData<>();

    private String mUsername;
    private String mToken;
    private String mGistId;
    private int mGistCommentPrevPage;

    public GistViewModel() {
        super();
        init();
        initAnonService();
    }

    /***
     * Clean and initialize the state of the view and all related counters and flags
     */
    private void init() {

        mProgressBarVisibility.setValue(View.GONE);
        mCommentsProgressBarVisibility.setValue(View.GONE);
        mErrorMessage.setValue(null);
        mIsGistStarred.setValue(false);
        mComments = new MutableLiveData<>();
        mGist = null;
        mUsername = null;
        mToken = null;
    }

    /***
     * Configure a new Retrofit instance for future API calls with no authorization
     */
    private void initAnonService() {


    }

    /**
     * Set the credentials to use for authorization when communicating with the GitHub API for
     *  this Gist.  The Retrofit service instance will be rebuild using these credentials.
     * @param username the GitHub username used for authorization
     * @param token the private access token associated with the GitHub user
     */
    public void setCredentials(String username, String token) {

        //Check if the provided credentials are empty
        if (username.isEmpty() || token.isEmpty()) {
            return;
        }

        //Store the username and token for later
        mUsername = username;
        mToken = token;


    }

    public LiveData<Integer> getProgressBarVisibility() {
        return mProgressBarVisibility;
    }

    public LiveData<String> getErrorMessage() {
        return mErrorMessage;
    }

    //region Gist

    /**
     * Set the ID of the Gist to use
     * @param mGistId the ID of the Gist
     */
    public void setGistId(String mGistId) {
        this.mGistId = mGistId;
    }

    /**
     * Get an observable instance of the Gist.  This will also load the Gist from the API
     *  if one has not been loaded yet.
     * @return an observable Gist
     * @see LiveData
     */
    public LiveData<Gist> getGist() {
        if (mGist == null) {
            mGist = new MutableLiveData<>();
            loadGist();
        }

        return mGist;
    }

    /**
     * Download a Gist from the GitHub API
     */
    private void loadGist() {

        //Make sure that a Gist ID was stored for use
        if (mGistId.isEmpty()) {
            showError(Constants.INVALID_GIST_ID_ERROR);
            return;
        }

        //Show the progress bar
        mProgressBarVisibility.postValue(View.VISIBLE);


    }

    //endregion

    //region Star

    public LiveData<Boolean> getStarredState() {
        return mIsGistStarred;
    }

    /**
     * Called to indicate that the user has clicked the star on the Gist in an attempt to either
     *  add or remove it from their list of starred Gists.
     */
    public void starItemClicked() {
        //Make sure that the state of the star has been initialized
        if (mIsGistStarred == null) {
            showError("There was a problem reading the current state of this Gist's star.  Please try again.");
            return;
        }
        if (mIsGistStarred.getValue() == null) {
            mIsGistStarred.postValue(false);
        }

        //Perform the appropriate action based on the state of the star
        if (mIsGistStarred.getValue()) {
            unStarGist();
        }
        else {
            starGist();
        }
    }

    /**
     * Get the current state of the Gist's star from the GitHub API
     */
    private void getGistStar() {

        //Make sure there is a Gist ID stored for use
        if (mGistId.isEmpty()) {
            return;
        }


    }

    /**
     * Add a star to the Gist so that it shows up in the authorized user's list
     *  of starred Gists.
     */
    private void starGist() {

        //Make sure there is a Gist ID stored for use
        if (mGistId.isEmpty()) {
            return;
        }


    }

    /**
     * Remove a star from the Gist so that it no longer shows up in the authorized user's list
     *  of starred Gists.
     */
    private void unStarGist() {

        //Make sure there is a Gist ID stored for use
        if (mGistId.isEmpty()) {
            return;
        }


    }

    //endregion

    //region Comments

    public LiveData<List<GistComment>> getComments() { return mComments; }

    /**
     * Download comments for the gist from the GitHub API
     */
    public void loadMoreComments() {

        //Make sure there is a Gist ID stored for use
        if (mGistId.isEmpty()) {
            return;
        }

        //Make sure that there are more comments to load (last -> first)
        if (mGistCommentPrevPage == 0){
            return;
        }

        //Show the progress bar in the comments section
        mCommentsProgressBarVisibility.postValue(View.VISIBLE);

    }

    /**
     * Determine if there are more pages that can be loaded for comments
     * @return TRUE if more pages can be loaded, FALSE if not
     */
    public boolean isMoreCommentsAvailable() {
        return mGistCommentPrevPage != 0;
    }

    /**
     * Add a comment to the Gist as the authorized user.  The API will error if there is no
     *  authorized user
     * @param comment the comment to post
     */
    public void createComment(String comment) {

        //Make sure there is a Gist ID stored for use
        if (mGistId.isEmpty()) {
            return;
        }

        //Make sure the submitted comment isn't empty
        if (comment.trim().isEmpty()) {
            showError("You cannot create a blank comment");
            return;
        }



    }

    /**
     * Download the headers for the comment API call to determine how many pages there are to load.
     *  Comments cannot be loaded until this is executed first.
     */
    private void loadCommentPageCount() {
        //Make sure there is a Gist ID stored for use
        if (mGistId.isEmpty()) {
            return;
        }

        //Show the progress bar in the comments section
        mCommentsProgressBarVisibility.postValue(View.VISIBLE);

    }

    //endregion

    /**
     * Check a link header returned in a call to the GitHub API to see if there is a URL pointing to the last page of content
     *  and then get the number of pages from it
     *
     * @param linkHeader the Link header returned by the call to the GitHub API
     * @return the number of pages in the list of data
     */
    private int getLastPageNum(String linkHeader) {
        //check to see if there is a "last" page
        int lastLinkIndex = linkHeader.indexOf("; rel=\"last\"");
        if (lastLinkIndex >= 0) {
            //grab the page number for the "last" link
            int lastPageNumberIndex = linkHeader.lastIndexOf("page=");
            String lastPageNum = linkHeader.substring(lastPageNumberIndex+5, linkHeader.indexOf(">", lastPageNumberIndex));
            try {
                return Integer.parseInt(lastPageNum);
            }
            catch (NumberFormatException exception) {
                showError("Couldn't load comments.  Please try again.");
                return 0;
            }

        }
        else {
            return 0;
        }
    }

    /**
     * Convenience method for showing an error to the user
     *
     * @param message the message to show to the user
     */
    private void showError(String message) {
        mProgressBarVisibility.postValue(View.GONE);
        mCommentsProgressBarVisibility.postValue(View.GONE);
        mErrorMessage.postValue(message);
    }
}
