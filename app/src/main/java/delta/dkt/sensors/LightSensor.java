package delta.dkt.sensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

import static ClientUIHandling.Constants.LOG_LIGHT_SENSOR;

public class LightSensor implements SensorEventListener {
    public static float value;
    private static final float COVER_THRESHOLD = 300;

    public static boolean isCovered() {
        return value < COVER_THRESHOLD;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(Math.abs(LightSensor.value - sensorEvent.values[0]) >= COVER_THRESHOLD / 2){
            //* Only notify about value update when there is a significant change.
            Log.v(LOG_LIGHT_SENSOR, "Value has significantly changed to: "+sensorEvent.values[0]);
        }

        LightSensor.value = sensorEvent.values[0];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
