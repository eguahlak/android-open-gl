package dk.cphbusiness.exampleopengl;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends Activity {
    private CanvasView canvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        canvas = new CanvasView(this);
        setContentView(canvas);
        }

    }
