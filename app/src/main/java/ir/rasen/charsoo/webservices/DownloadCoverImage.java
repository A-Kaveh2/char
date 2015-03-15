package ir.rasen.charsoo.webservices;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

import ir.rasen.charsoo.R;
import ir.rasen.charsoo.helper.Image_M;
import ir.rasen.charsoo.helper.Params;
import ir.rasen.charsoo.helper.ServerAnswer;
import ir.rasen.charsoo.helper.URLs;


/**
 * Created by android on 1/31/2015.
 */
public class DownloadCoverImage {

    //this class download images with thread pool and cache them after download.


    private String storagePath;
    private Context context;
    private int key;


    public DownloadCoverImage(Context context) {

        this.context = context;
        storagePath = Environment.getExternalStorageDirectory().getAbsolutePath() +
                context.getResources().getString(R.string.download_storage_path);
    }

    public void download(int imageID, ImageView imageView) {
        //imageSize: 1=large, 2=medium, 3= small

        key = Integer.valueOf(String.valueOf(imageID) + Image_M.COVER);

        if (isImageInStorage(key)) {
            Bitmap bitmap = BitmapFactory.decodeFile(storagePath + "/" + String.valueOf(key) + ".jpg");
            if (bitmap == null)
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
            try {
                imageView.setImageBitmap(bitmap);
                //imageView.setImageBitmap(Image_M.getRoundedCornerBitmap(bitmap,(int)context.getResources().getDimension(R.dimen.base_adapter_item_height)));
            } catch (Exception e) {
                String s = e.getMessage();
            }
            return;
        }

        new DownloadImageThread(context, imageID, key, imageView).execute();
    }

    private boolean isImageInStorage(int key) {
        File file = new File(storagePath, String.valueOf(key) + ".jpg");
        if (file.exists())
            return true;
        return false;
    }


    private class DownloadImageThread extends AsyncTask<Void, Void, String> {
        private static final String TAG = "DownloadImage";
        private ServerAnswer serverAnswer;
        private Context context;
        private int imageId;
        private int threadKey;
        private ImageView imageView;

        public DownloadImageThread(Context context, int imageId, int key, ImageView imageView) {
            this.context = context;
            this.imageId = imageId;
            this.threadKey = key;
            this.imageView = imageView;
        }

        @Override
        protected String doInBackground(Void... voids) {

            WebserviceGET webserviceGET = new WebserviceGET(URLs.DOWNLOAD_IMAGE, new ArrayList<>(
                    Arrays.asList(String.valueOf(imageId),
                            String.valueOf(Image_M.LARGE))));
            try {
                serverAnswer = webserviceGET.execute();
                if (serverAnswer.getSuccessStatus()) {
                    JSONObject jsonObject = serverAnswer.getResult();
                    if (jsonObject != null) {
                        return jsonObject.getString(Params.IMAGE);
                    }
                } else
                    return null;
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {


            if (result != null) {
                Bitmap bitmap = Image_M.getBitmapFromString(result);
                int width = context.getResources().getDisplayMetrics().widthPixels;
                //crop middle part of picture
                Bitmap croppedBitmap = Bitmap.createBitmap(bitmap, 0, width / 6, width, (width / 6) * 5);
                if (bitmap != null) {
                    Image_M.saveBitmap(storagePath, String.valueOf(threadKey) + ".jpg", croppedBitmap);
                    imageView.setImageBitmap(croppedBitmap);
                }
            }
        }
    }
}
