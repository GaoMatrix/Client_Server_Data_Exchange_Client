
package com.gao.client_server_data_exchange;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.gao.client_server_data_exchange.service.ServiceRulesException;
import com.gao.client_server_data_exchange.service.UserService;
import com.gao.client_server_data_exchange.service.UserServiceImpl;

public class ImageViewActivity extends Activity {
    private ImageView mImageView;
    UserService mUserService = new UserServiceImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_view_activity);
        mImageView = (ImageView) findViewById(R.id.iv_image);
//        mImageView.setImageResource(R.drawable.sample);
//        mImageView.setImageDrawable(this.getResources().getDrawable(R.drawable.sample));
        
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    final Bitmap bitmap = mUserService.getImage();
                    if (null != bitmap) {
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                mImageView.setImageBitmap(bitmap);
                            }
                        });
                    }
                } catch (final ServiceRulesException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Toast.makeText(ImageViewActivity.this, e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
                catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Toast.makeText(ImageViewActivity.this, "载入图片失败", Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            }
        }).start();
    }
}
