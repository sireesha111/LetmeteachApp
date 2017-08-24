
/*Developed By: Catalog IT Solutions. Copyright © 2017. Catalog. All Rights Reserved.This product is protected by copyright
 laws. Unauthorized reproduction or distribution of this product,or any portion of it,may result in severe civil and
 criminal penalties, and will be prosecuted to the maximum extent possible under the law.
 File Name: LruBitmapCache.java
 Created By: Sireesha A
 Created On: 02/Jan/2017
 Last Modified By: Sireesha A
 Last Modified On: 24/Apr/2017
 Purpose: To display Images
  */

package catalogitsolutions.letmetutorapp.app;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;

public class LruBitmapCache extends LruCache<String, Bitmap> implements	ImageCache
{
	public static int getDefaultLruCacheSize()
	{
		final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
		final int cacheSize = maxMemory / 8;

		return cacheSize;
	}

	public LruBitmapCache()
	{
		this(getDefaultLruCacheSize());
	}

	public LruBitmapCache(int sizeInKiloBytes)
	{
		super(sizeInKiloBytes);
	}

	@Override
	protected int sizeOf(String key, Bitmap value)
	{
		return value.getRowBytes() * value.getHeight() / 1024;
	}

	@Override
	public Bitmap getBitmap(String url)
	{
		return get(url);
	}

	@Override
	public void putBitmap(String url, Bitmap bitmap)
	{
		put(url, bitmap);
	}
}