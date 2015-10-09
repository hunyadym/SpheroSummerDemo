package hu.ppke.itk.codecampsummer.spherosummerdemo;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.orbotix.ConvenienceRobot;
import com.orbotix.DualStackDiscoveryAgent;
import com.orbotix.Ollie;
import com.orbotix.Sphero;
import com.orbotix.async.CollisionDetectedAsyncData;
import com.orbotix.classic.RobotClassic;
import com.orbotix.common.DiscoveryAgentEventListener;
import com.orbotix.common.DiscoveryException;
import com.orbotix.common.ResponseListener;
import com.orbotix.common.Robot;
import com.orbotix.common.RobotChangedStateListener;
import com.orbotix.common.internal.AsyncMessage;
import com.orbotix.common.internal.DeviceResponse;
import com.orbotix.le.RobotLE;

import java.util.List;

public class SpheroActivity extends AppCompatActivity {
	protected Sphero sphero;
	protected Ollie ollie;

	protected void initSphero() {
	}

	protected void initOllie() {
	}

	protected void onStart() {
		super.onStart();
		try {
			final DualStackDiscoveryAgent discoveryAgent = DualStackDiscoveryAgent.getInstance();
			discoveryAgent.addDiscoveryListener(new DiscoveryAgentEventListener() {
				@Override
				public void handleRobotsAvailable(List<Robot> list) {
					if (list != null && list.size() > 0) {
						Toast.makeText(SpheroActivity.this, "Kapcsolódás a golyóhoz", Toast.LENGTH_SHORT).show();
						discoveryAgent.connect(list.get(0));
					}
				}
			});
			discoveryAgent.addRobotStateListener(new RobotChangedStateListener() {
				@Override
				public void handleRobotChangedState(Robot robot, RobotChangedStateNotificationType robotChangedStateNotificationType) {
					switch (robotChangedStateNotificationType) {
						case Online:
							if (robot instanceof RobotLE) {
								SpheroActivity.this.ollie = new Ollie(robot);
								Toast.makeText(SpheroActivity.this, "Kapcsolódva", Toast.LENGTH_SHORT).show();
								ollie.setBackLedBrightness(0f);
								ollie.setLed(0, 0, 255);
								initOllie();
							} else if (robot instanceof RobotClassic) {
								SpheroActivity.this.sphero = new Sphero(robot);
								Toast.makeText(SpheroActivity.this, "Kapcsolódva", Toast.LENGTH_SHORT).show();
								sphero.setBackLedBrightness(0f);
								sphero.setLed(0, 0, 255);
								initSphero();
							}
							break;
						case Offline:
							Toast.makeText(SpheroActivity.this, "Lekapcsolódva", Toast.LENGTH_SHORT).show();
							SpheroActivity.this.sphero = null;
							SpheroActivity.this.ollie = null;
							break;
					}
				}
			});
			discoveryAgent.startDiscovery(this);
		} catch (DiscoveryException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (sphero != null) {
			sphero.disconnect();
		}
	}

	protected void setCollisionListener(ConvenienceRobot convenienceRobot, final Runnable runnable) {
		convenienceRobot.enableCollisions(true);
		convenienceRobot.addResponseListener(new ResponseListener() {
			@Override
			public void handleResponse(DeviceResponse deviceResponse, Robot robot) {
			}

			@Override
			public void handleStringResponse(String s, Robot robot) {
			}

			@Override
			public void handleAsyncMessage(AsyncMessage asyncMessage, Robot robot) {
				if (asyncMessage instanceof CollisionDetectedAsyncData) {
					runnable.run();
				}
			}
		});
	}
}
