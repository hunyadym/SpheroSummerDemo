package hu.ppke.itk.codecampsummer.spherosummerdemo;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends SpheroActivity {

	boolean mStarted;
	SensorManager sensorManager;
	SensorEventListener gravityListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	protected void initSphero() {
		final SeekBar bearingBar = (SeekBar) findViewById(R.id.bearing);
		final SeekBar speedBar = (SeekBar) findViewById(R.id.speed);
		bearingBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
										  boolean fromUser) {
				if (mStarted) {
					sphero.drive(progress, speedBar.getProgress() / 100f);
				} else {
					sphero.rotate(progress);
				}
			}
		});

		speedBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
										  boolean fromUser) {
				if (mStarted) {
					sphero.drive(bearingBar.getProgress(), progress / 100f);
				} else {

				}
			}
		});

		findViewById(R.id.buttonGreen).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						sphero.setLed(0, 255, 0);
					}
				});

		findViewById(R.id.buttonRed).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				sphero.setLed(255, 0, 0);
			}
		});

		findViewById(R.id.buttonWhite).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						sphero.setLed(255, 255, 255);
					}
				});

		findViewById(R.id.tailOn).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				sphero.setBackLedBrightness(1);
			}
		});

		findViewById(R.id.tailOff).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				sphero.setBackLedBrightness(0);
			}
		});

		findViewById(R.id.go).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				sphero.drive(bearingBar.getProgress(), speedBar.getProgress());
				mStarted = true;
			}
		});

		findViewById(R.id.stop).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				sphero.stop();
				mStarted = false;
			}
		});

		setCollisionListener(sphero, new Runnable() {

			@Override
			public void run() {
				sphero.setLed(255, 0, 0);
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						sphero.setLed(255, 255, 255);
					}
				}, 2000);
				Log.d("Ütközés", "Puff");
			}
		});
	}

	@Override
	protected void initOllie() {
		final SeekBar bearingBar = (SeekBar) findViewById(R.id.bearing);
		final SeekBar speedBar = (SeekBar) findViewById(R.id.speed);
		bearingBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
										  boolean fromUser) {
				if (mStarted) {
					ollie.drive(progress, speedBar.getProgress() / 100f);
				} else {
					ollie.rotate(progress);
				}
			}
		});

		speedBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
										  boolean fromUser) {
				if (mStarted) {
					ollie.drive(bearingBar.getProgress(), progress / 100f);
				} else {

				}
			}
		});

		findViewById(R.id.buttonGreen).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						ollie.setLed(0, 255, 0);
					}
				});

		findViewById(R.id.buttonRed).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				ollie.setLed(255, 0, 0);
			}
		});

		findViewById(R.id.buttonWhite).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						ollie.setLed(255, 255, 255);
					}
				});

		findViewById(R.id.tailOn).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				ollie.setBackLedBrightness(1);
			}
		});

		findViewById(R.id.tailOff).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				ollie.setBackLedBrightness(0);
			}
		});

		findViewById(R.id.go).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				ollie.drive(bearingBar.getProgress(), speedBar.getProgress());
				mStarted = true;
			}
		});

		findViewById(R.id.stop).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				ollie.stop();
				mStarted = false;
			}
		});

		setCollisionListener(ollie, new Runnable() {

			@Override
			public void run() {
				ollie.setLed(255, 0, 0);
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						ollie.setLed(255, 255, 255);
					}
				}, 2000);
				Log.d("Ütközés", "Puff");
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		final TextView sensorValues = (TextView) findViewById(R.id.sensor_values);
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
		gravityListener = new SensorEventListener() {
			@Override
			public void onSensorChanged(SensorEvent event) {
				double length = Math.sqrt(event.values[0] * event.values[0] +
						event.values[1] * event.values[1] +
						event.values[2] * event.values[2]);
				double alfa = Math.asin(event.values[0] / length) * 180 / Math.PI;
				double beta = Math.asin(event.values[1] / length) * 180 / Math.PI;
				sensorValues.setText(alfa + "\n" + beta);
			}

			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy) {

			}
		};
		sensorManager.registerListener(gravityListener, sensor, SensorManager.SENSOR_DELAY_GAME);
	}

	@Override
	protected void onPause() {
		super.onPause();
		sensorManager.unregisterListener(gravityListener);
	}
}
