package dk.cphbusiness.exampleopengl;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class CanvasView extends GLSurfaceView implements GLSurfaceView.Renderer {
    private final float[] modelViewProjectionMatrix = new float[16];
    private final float[] projectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];
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
        float ratio = (float)width/height;
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
        }

    @Override
    public void onDrawFrame(GL10 unused) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        Matrix.setLookAtM(viewMatrix, 0,
                0f, 0f, 3f,
                0f, 0f, 0f,
                0f, 1f, 0f);
        Matrix.multiplyMM(modelViewProjectionMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
        square.draw(modelViewProjectionMatrix);
        }

    private static int loadShader(int type, String code) {
        int shaderHandle = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shaderHandle, code);
        GLES20.glCompileShader(shaderHandle);
        return shaderHandle;
        }

    public static int loadVertexShader(String code) {
        return loadShader(GLES20.GL_VERTEX_SHADER, code);
        }

    public static int loadFragmentShader(String code) {
        return loadShader(GLES20.GL_FRAGMENT_SHADER, code);
        }

    }
