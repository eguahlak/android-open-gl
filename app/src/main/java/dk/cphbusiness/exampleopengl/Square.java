package dk.cphbusiness.exampleopengl;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class Square {
    private FloatBuffer vertexBuffer;
    private ShortBuffer drawListBuffer;

    static final int COORDINATES_PER_VERTEX = 3;
    static float[] coordinates = {
        -0.5f,  0.5f, -0.5f,
        -0.5f, -0.5f, -0.5f,
         0.5f, -0.5f, -0.5f,
         0.5f,  0.5f, -0.5f,
        -0.5f,  0.5f,  0.5f,
        -0.5f, -0.5f,  0.5f,
         0.5f, -0.5f,  0.5f,
         0.5f,  0.5f,  0.5f
        };
    private short[] drawOrder = {
        0, 1, 2,
        0, 2, 3,
        3, 2, 6,
        7, 6, 3
        };

    float[] color = { 1f, 1f, 0f, 1f };

    private final int programHandle;

    public Square() {
        ByteBuffer coordinateBytes = ByteBuffer.allocateDirect(4*coordinates.length);
        coordinateBytes.order(ByteOrder.nativeOrder());
        vertexBuffer = coordinateBytes.asFloatBuffer();
        vertexBuffer.put(coordinates).position(0);

        ByteBuffer drawBytes = ByteBuffer.allocateDirect(2*drawOrder.length);
        drawBytes.order(ByteOrder.nativeOrder());
        drawListBuffer = drawBytes.asShortBuffer();
        drawListBuffer.put(drawOrder).position(0);

        int vertexShaderHandle = CanvasView.loadVertexShader(vertexShaderCode);
        int fragmentShaderHandle = CanvasView.loadFragmentShader(fragmentShaderCode);

        programHandle = GLES20.glCreateProgram();
        GLES20.glAttachShader(programHandle, vertexShaderHandle);
        GLES20.glAttachShader(programHandle, fragmentShaderHandle);
        GLES20.glLinkProgram(programHandle);
        }

    private int positionHandle;
    private int colorHandle;
    private int matrixHandle;

    //private final int vertexCount = coordinates.length/COORDINATES_PER_VERTEX;
    private final int vertexStride = 4*COORDINATES_PER_VERTEX;

    public void draw(float[] modelViewProjectionMatrix) {
        GLES20.glUseProgram(programHandle);
        positionHandle = GLES20.glGetAttribLocation(programHandle, "vPosition");
        GLES20.glEnableVertexAttribArray(positionHandle);
        GLES20.glVertexAttribPointer(positionHandle, COORDINATES_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);

        colorHandle = GLES20.glGetUniformLocation(programHandle, "vColor");
        GLES20.glUniform4fv(colorHandle, 1, color, 0);

        matrixHandle = GLES20.glGetUniformLocation(programHandle, "uMVPMatrix");
        GLES20.glUniformMatrix4fv(matrixHandle, 1, false, modelViewProjectionMatrix, 0);

        //GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, drawOrder.length, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

        GLES20.glDisableVertexAttribArray(positionHandle);
        }

    private final String vertexShaderCode =
            "uniform mat4 uMVPMatrix;"+
            "attribute vec4 vPosition;"+
            "void main() {"+
            "  gl_Position = uMVPMatrix * vPosition;"+
            "  }";

    private final String fragmentShaderCode =
            "precision mediump float;"+
            "uniform vec4 vColor;"+
            "void main() {"+
            "  gl_FragColor = vColor;"+
            "  }";

    }
