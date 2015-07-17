package hu.ppke.itk.codecampsummer.spherosummerdemo;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.orbotix.Sphero;
import com.orbotix.async.CollisionDetectedAsyncData;
import com.orbotix.classic.DiscoveryAgentClassic;
import com.orbotix.common.DiscoveryAgentEventListener;
import com.orbotix.common.DiscoveryException;
import com.orbotix.common.ResponseListener;
import com.orbotix.common.Robot;
import com.orbotix.common.RobotChangedStateListener;
import com.orbotix.common.internal.AsyncMessage;
import com.orbotix.common.internal.DeviceResponse;

import java.util.List;

public class SpheroActivity extends AppCompatActivity {
	protected Sphero sphero;

	protected void init() {}

	protected void onStart() {
		super.onStart();
		try {
			final DiscoveryAgentClassic discoveryAgent = DiscoveryAgentClassic.getInstance();
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
				public void changedState(Robot robot, RobotChangedStateNotificationType robotChangedStateNotificationType) {
					switch (robotChangedStateNotificationType) {
						case Online:
							SpheroActivity.this.sphero = new Sphero(robot);
							Toast.makeText(SpheroActivity.this, "Kapcsolódva", Toast.LENGTH_SHORT).show();
							sphero.setBackLedBrightness(0f);
							sphero.setLed(0, 0, 255);
							init();
							break;
						case Offline:
							Toast.makeText(SpheroActivity.this, "Lekapcsolódva", Toast.LENGTH_SHORT).show();
							SpheroActivity.this.sphero = null;
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

	protected void setCollisionListener(Sphero sphero, final Runnable runnable) {
		sphero.enableCollisions(true);
		sphero.addResponseListener(new ResponseListener() {
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
