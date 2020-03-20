package com.jiashuwu.androidbarcodegenerator;

import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private String message = "";
    private String message2 = "";
    private String type = "";
    private ImageView imageView;
    private int size = 660;
    private int size_width = 660;
    private int size_height = 264;




    private Bitmap myBitmap;

    public static String getSerialNumber() {
        //String serialNumber;
        //serialNumber = Build.SERIAL;
        return Build.SERIAL;
    };


    public String getUSB(){
    UsbManager manager = (UsbManager) getSystemService(Context.USB_SERVICE);
    HashMap<String, UsbDevice> deviceList = manager.getDeviceList();
    UsbDevice device = deviceList.get("deviceName");
    return device.getDeviceName();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.justbarcode);

       

        type = "Barcode";
        //button_generate = (Button) findViewById(R.id.generate_button);
        imageView = (ImageView) findViewById(R.id.image_imageview);
        TextView textView = findViewById(R.id.textView);
        message = getSerialNumber();
        message2 = getUSB();
        textView.setText(message);
        TextView textView2 = findViewById(R.id.textView2);
        textView2.setText(message2);




        //button_generate.setOnClickListener(new View.OnClickListener() {
          //@Override
            //public void onClick(View v) {




              //  {
                  Bitmap bitmap = null;
                   try
                    {
                        bitmap = CreateImage(message, type);
                        myBitmap = bitmap;
                    }
                    catch (WriterException we)
                    {
                        we.printStackTrace();
                    }
                    if (bitmap != null)
                    {
                        imageView.setImageBitmap(bitmap);
                    }
    }



    public Bitmap CreateImage(String message, String type) throws WriterException
    {
        BitMatrix bitMatrix = null;
        // BitMatrix bitMatrix = new MultiFormatWriter().encode(message, BarcodeFormat.QR_CODE, size, size);
        switch (type)
        {
            case "Barcode": bitMatrix = new MultiFormatWriter().encode(message, BarcodeFormat.CODE_128, size_width, size_height);break;
        }
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        int [] pixels = new int [width * height];
        for (int i = 0 ; i < height ; i++)
        {
            for (int j = 0 ; j < width ; j++)
            {
                if (bitMatrix.get(j, i))
                {
                    pixels[i * width + j] = 0xff000000;
                }
                else
                {
                    pixels[i * width + j] = 0xffffffff;
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }




}
