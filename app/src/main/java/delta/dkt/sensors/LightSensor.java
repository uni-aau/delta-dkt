package delta.dkt.sensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

public class LightSensor implements SensorEventListener {
    public static float value;
    private static final float COVER_THRESHOLD = 300;

    public static boolean isCovered() {
        return value < COVER_THRESHOLD;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        LightSensor.value = sensorEvent.values[0];
        Log.d("Movement-LightSensor", "Value has changed to: "+sensorEvent.values[0]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
