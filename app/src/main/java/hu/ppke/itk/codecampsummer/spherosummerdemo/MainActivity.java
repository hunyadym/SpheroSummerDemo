package hu.ppke.itk.codecampsummer.spherosummerdemo;

import android.os.Bundle;

public class MainActivity extends SpheroActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	protected void init() {
		sphero.setLed(0, 255, 0);
	}

}
