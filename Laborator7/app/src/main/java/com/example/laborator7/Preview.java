package com.example.laborator7;
import java.io.IOException;
import android.view.SurfaceView;
import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;


public class Preview extends SurfaceView implements SurfaceHolder.Callback {
    private Camera camera;
    private SurfaceHolder surfaceHolder;


    public Preview(Context context, Camera _camera) {
        super(context);
        this.camera = _camera;
        
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    
    
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            if (camera == null) {
                camera.setPreviewDisplay(holder);
                camera.startPreview();
            }
        } catch (IOException e) {
            System.out.println( "Error==> setting camera preview: " + e.getMessage());
        }
    }

    public void refreshCamera(Camera camera) {
        if (surfaceHolder.getSurface() == null) {
            return;
        }
        try {
            camera.stopPreview();
        } catch (Exception e) {
           
        }
        setCamera(camera);
        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        } catch (Exception e) {
            System.out.println("Error=> starting camera preview: " + e.getMessage());
        }
    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) { }
    public void setCamera(Camera _camera) {
        camera = _camera;
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        refreshCamera(camera);
    }
}