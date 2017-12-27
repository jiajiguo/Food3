package com.huaye.food;

import android.content.Context;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class ImageLoaderFactory {
	public static ImageLoaderConfiguration getConfiguration(Context context) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).memoryCacheExtraOptions(480, 800).threadPoolSize(5)
				.threadPriority(Thread.NORM_PRIORITY - 1)
				.denyCacheImageMultipleSizesInMemory()
//				.memoryCache(new UsingFreqLimitedMemoryCache(3 * 1024 * 1024))
				.memoryCacheSize(5 * 1024 * 1024)
//				.diskCache(new UnlimitedDiskCache(cacheDir))
				// .discCache(new UnlimitedDiscCache(cacheDir))
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.build();
		return config;
	}

	public static void initImageLoader(Context context) {
		ImageLoader.getInstance().init(getConfiguration(context));
	}

	public static DisplayImageOptions getImageOptions(int stubImageRes,
			int imageRes) {
		return new DisplayImageOptions.Builder().showImageOnFail(stubImageRes)
				.showImageForEmptyUri(imageRes)
				.showImageOnLoading(stubImageRes)
				.cacheOnDisk(true)
				.cacheInMemory(true)
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
				.displayer(new FadeInBitmapDisplayer(300)).build();
	}

	public static DisplayImageOptions getDefaultImageOptions() {
		return getImageOptions(R.drawable.shape_empty_drawable, R.drawable.shape_empty_drawable);
	}

	public static void display(String uri, ImageView imageView) {
		ImageLoader.getInstance().displayImage(uri, imageView,
				getDefaultImageOptions());
	}

	public static void display(String uri, ImageView imageView,
			DisplayImageOptions options) {
		ImageLoader.getInstance().displayImage(uri, imageView, options);
	}
}
