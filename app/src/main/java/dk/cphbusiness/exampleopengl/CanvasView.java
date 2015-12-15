package dk.cphbusiness.exampleopengl;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class CanvasView extends GLSurfaceView implements GLSurfaceView.Renderer {
    private Square square;

    public CanvasView(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        setRenderer(this);
        setRenderMode(RENDERMODE_WHEN_DIRTY);
        }

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        GLES20.glClearColor(0f, 0f, 0.5f, 1f);
        square = new Square();
        }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        }

    @Override
    public void onDrawFrame(GL10 unused) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        square.draw();
        }

    private static int loadShader(int type, String code) {
        int shaderHandle = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shaderHandle, code);
        GLES20.glCompileShader(shaderHandle);
        return  shaderHandle;
        }

    public static int loadVertexShader(String code) {
        return loadShader(GLES20.GL_VERTEX_SHADER, code);
        }

    public static int loadFragmentShader(String code) {
        return loadShader(GLES20.GL_FRAGMENT_SHADER, code);
        }

    }
