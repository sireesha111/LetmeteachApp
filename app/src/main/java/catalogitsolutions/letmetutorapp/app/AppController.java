
/*Developed By: Catalog IT Solutions. Copyright © 2017. Catalog. All Rights Reserved.This product is protected by copyright
 laws. Unauthorized reproduction or distribution of this product,or any portion of it,may result in severe civil and
 criminal penalties, and will be prosecuted to the maximum extent possible under the law.
 File Name: AppController.java
 Created By: Sireesha A
 Created On: 02/Jan/2017
 Last Modified By: Sireesha A
 Last Modified On: 7/Feb/2017
 Purpose: To run and extend the application(Volley Library)
  */

package catalogitsolutions.letmetutorapp.app;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class AppController extends Application
{

	private int i=0;

	public int inc(){
		return ++i;
	}


	public static final String TAG = AppController.class.getSimpleName();

	private RequestQueue mRequestQueue;
	private ImageLoader mImageLoader;
	private static AppController mInstance;

	@Override
	public void onCreate()
	{
		super.onCreate();
		mInstance = this;
	}

	public static synchronized AppController getInstance()
	{
		return mInstance;
	}

	public RequestQueue getRequestQueue()
	{
		if (mRequestQueue == null)
		{
			mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		}
		return mRequestQueue;
	}

	public ImageLoader getImageLoader()
	{
		getRequestQueue();
		if (mImageLoader == null)
		{
			mImageLoader = new ImageLoader(this.mRequestQueue, new LruBitmapCache());
		}
		return this.mImageLoader;
	}

	public <T> void addToRequestQueue(Request<T> req, String tag)
	{
		// set the default tag if tag is empty
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
		getRequestQueue().add(req);
	}

	public <T> void addToRequestQueue(Request<T> req)
	{
		req.setTag(TAG);
		getRequestQueue().add(req);
	}

	public void cancelPendingRequests(Object tag)
	{
		if (mRequestQueue != null)
		{
			mRequestQueue.cancelAll(tag);
		}
	}

	public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
		ConnectivityReceiver.connectivityReceiverListener = listener;
	}
}
