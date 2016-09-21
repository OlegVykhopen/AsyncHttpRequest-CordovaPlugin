package cordova-plugin-asynchttprequest;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaActivity;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

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
        if (paramString.equals("loadDataFromURL"))
            {
              String str2 = paramJSONArray.getString(0);
              this.mCallbackContext = paramCallbackContext;
              this.mRequestUrl = str2;
              this.mClient = new AsyncHttpClient();
              loadDataFromURL();
              return true;
            }
        return false;
    }

    public void loadDataFromURL()
      {
        if ((this.mRequestUrl != null) && (this.mRequestUrl.length() > 0))
        {
        	Log.e("LoadDataFromUrl",this.mRequestUrl);
          this.mClient.get(this.mRequestUrl, new AsyncHttpResponseHandler()
          {

    		@Override
    		public void onFailure(int arg0, Header[] arg1, byte[] arg2,
    				Throwable arg3) {
    			// TODO Auto-generated method stub

    		}

    		@Override
    		public void onSuccess(int arg0, Header[] arg1, byte[] paramAnonymousJSONObject) {
    			// TODO Auto-generated method stub
    			try
    	          {
    	            JSONObject localJSONObject = (JSONObject)new JSONTokener(new String(paramAnonymousJSONObject, "UTF-8")).nextValue();
    	            KfarAndroidExtentionPlugin.this.mCallbackContext.success(localJSONObject);
    	            return;
    	          }
    	          catch (JSONException localJSONException)
    	          {
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
