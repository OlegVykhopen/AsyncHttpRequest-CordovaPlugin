package AsyncHttpRequest;

import android.util.Log;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaActivity;
import org.apache.cordova.CordovaPlugin;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.UnsupportedEncodingException;


/**
 * This class echoes a string called from JavaScript.
 */
public class AsyncHttpRequest extends CordovaPlugin {

    private CordovaActivity mCRDActivity;
    private CallbackContext mCallbackContext;
    private AsyncHttpClient mClient;
    private String mRequestUrl;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("loadDataFromURL")) {
            String str2 = args.getString(0);
            this.mCallbackContext = callbackContext;
            this.mRequestUrl = str2;
            this.mClient = new AsyncHttpClient();
            loadDataFromURL();
            return true;
        }
        return false;
    }

    public void loadDataFromURL() {
        if ((this.mRequestUrl != null) && (this.mRequestUrl.length() > 0)) {
            Log.e("LoadDataFromUrl", this.mRequestUrl);
            this.mClient.get(this.mRequestUrl, new AsyncHttpResponseHandler() {
                // https://github.com/loopj/android-async-http/issues/925 - fix for missed headers
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    try {
                        JSONObject localJSONObject = (JSONObject) new JSONTokener(new String(bytes, "UTF-8")).nextValue();
                        Log.d("Success", localJSONObject.toString());
                        AsyncHttpRequest.this.mCallbackContext.success(localJSONObject);
                    } catch (JSONException localJSONException) {
                        localJSONException.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {
                    try {
                        JSONObject localJSONObject = (JSONObject) new JSONTokener(new String(bytes, "UTF-8")).nextValue();
                        Log.d("Error", localJSONObject.toString());
                        AsyncHttpRequest.this.mCallbackContext.error(localJSONObject);
                    } catch (JSONException localJSONException) {
                        localJSONException.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }


            });
            return;
        }
        this.mCallbackContext.error("Expected one non-empty string argument.");
    }
}
