package com.inoek.apps.android.games;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.games.achievement.Achievement;
import com.google.android.gms.games.achievement.AchievementBuffer;
import com.google.android.gms.games.achievement.Achievements;
import com.google.android.gms.games.achievement.Achievements.LoadAchievementsResult;
import com.google.example.games.basegameutils.GameHelper;
import com.inoek.apps.android.games.BallBounzAndroid.MyGameCallBack;

public class MainActivity extends AndroidApplication implements MyGameCallBack,
		AdsController, GameHelper.GameHelperListener {
	public static final String BANNER_AD_UNIT_ID = " ";
	public static final String INTERSTITIAL_AD_UNIT_ID = " ";
	ImageView imag;
	BroadcastReceiver mReceiver;
	View gameview;
	String rate;
	static BallBounzAndroid m;
	AdView bannerAd;
	InterstitialAd interAd;
	public GameHelper gameHelper;
	final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;

	public MainActivity() {

	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(
				WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
		DisplayMetrics d = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(d);
		m = new BallBounzAndroid(this);
		m.setMygamecallback(this);
		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
		cfg.disableAudio = false;
		cfg.useAccelerometer = true;
		cfg.useCompass = true;
		setupads();
		RelativeLayout layout = new RelativeLayout(this);
		RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, d.heightPixels
						- d.heightPixels / 10);
		p.topMargin = d.heightPixels / 10;
		gameview = initializeForView(m, cfg);
		layout.addView(gameview, p);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		layout.addView(bannerAd, params);
		setContentView(layout);
		if (gameHelper == null) {
			gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
			gameHelper.enableDebugLog(true);
		}
		gameHelper.setup(this);
		if (Build.VERSION.SDK_INT >= 23) {
			List<String> permissionsNeeded = new ArrayList<String>();
			final List<String> permissionsList = new ArrayList<String>();
			if (!addPermission(permissionsList, Manifest.permission.INTERNET))
				permissionsNeeded.add("Use Internet");
			if (!addPermission(permissionsList,
					Manifest.permission.ACCESS_NETWORK_STATE))
				permissionsNeeded.add("Access Network State");
			if (!addPermission(permissionsList, Manifest.permission.VIBRATE))
				permissionsNeeded.add("Vibration");

			if (permissionsList.size() > 0) {
				requestPermissions(
						permissionsList.toArray(new String[permissionsList
								.size()]),
						REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
				return;
			}
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode,
			String[] permissions, int[] grantResults) {
		// TODO Auto-generated method stub
		switch (requestCode) {
		case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
			Map<String, Integer> perms = new HashMap<String, Integer>();
			// Initial
			perms.put(Manifest.permission.VIBRATE,
					PackageManager.PERMISSION_GRANTED);
			perms.put(Manifest.permission.ACCESS_NETWORK_STATE,
					PackageManager.PERMISSION_GRANTED);
			perms.put(Manifest.permission.INTERNET,
					PackageManager.PERMISSION_GRANTED);
			// Fill with results
			for (int i = 0; i < permissions.length; i++)
				perms.put(permissions[i], grantResults[i]);
			// Check for ACCESS_FINE_LOCATION
			if (perms.get(Manifest.permission.VIBRATE) == PackageManager.PERMISSION_GRANTED
					&& perms.get(Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED
					&& perms.get(Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED) {
				// All Permissions Granted
				Toast.makeText(MainActivity.this, "Enjoy Playing!!",
						Toast.LENGTH_SHORT).show();
			} else {
				// Permission Denied
				finish();
				showMessageOKCancel("Required Permissions Denied");
			}
		}
			break;
		default:
			super.onRequestPermissionsResult(requestCode, permissions,
					grantResults);
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	public void setupads() {
		bannerAd = new AdView(this);
		bannerAd.setVisibility(View.INVISIBLE);
		bannerAd.setBackgroundColor(Color.BLACK);
		bannerAd.setAdUnitId(BANNER_AD_UNIT_ID);
		bannerAd.setAdSize(AdSize.SMART_BANNER);
		interAd = new InterstitialAd(this);
		interAd.setAdUnitId(INTERSTITIAL_AD_UNIT_ID);
		AdRequest.Builder builder = new AdRequest.Builder();
		AdRequest ad = builder.build();
		interAd.loadAd(ad);
	}

	@Override
	public void onStartActivityA() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, FirstActivity.class);
		startActivity(intent);
	}

	@Override
	public void onStartActivityB() {
		// TODO Auto-generated method stub

	}

	@Override
	public void showBannerAd() {
		// TODO Auto-generated method stub
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				bannerAd.setVisibility(View.VISIBLE);
				AdRequest.Builder builder = new AdRequest.Builder();
				AdRequest ad = builder.build();
				bannerAd.loadAd(ad);
			}
		});
	}

	@Override
	public void hideBannerAd() {
		// TODO Auto-generated method stub
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				bannerAd.setVisibility(View.INVISIBLE);
			}
		});
	}
	
	

	@Override
	public boolean isWifiConnected() {
		// TODO Auto-generated method stub
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return (ni != null && ni.isConnected());
	}

	@Override
	public void onStart() {
		super.onStart();
		gameHelper.onStart(this);
	}

	@Override
	public void onStop() {
		super.onStop();
		gameHelper.onStop();
	}

	@Override
	public void onActivityResult(int request, int response, Intent data) {
		super.onActivityResult(request, response, data);
		gameHelper.onActivityResult(request, response, data);
	}

	@Override
	public boolean getSignedInGPGS() {
		return gameHelper.isSignedIn();
	}

	@Override
	public void loginGPGS() {
		try {
			runOnUiThread(new Runnable() {
				public void run() {
					gameHelper.beginUserInitiatedSignIn();
				}
			});
		} catch (final Exception ex) {
		}
	}

	@Override
	public void submitScoreGPGS(long score) {
		if (getSignedInGPGS()) {
			Games.Leaderboards.submitScore(gameHelper.getApiClient(),
					"CgkIj73WhbAeEAIQGg", score);
		} else {
			loginGPGS();
		}
	}

	@Override
	public void unlockAchievementGPGS(String achievementId) {
		if (getSignedInGPGS()) {
			Games.Achievements.unlock(gameHelper.getApiClient(), achievementId);
		} else {
			loginGPGS();
		}
	}

	@Override
	public void getLeaderboardGPGS() {
		if (gameHelper.isSignedIn()) {
			startActivityForResult(
					Games.Leaderboards.getLeaderboardIntent(
							gameHelper.getApiClient(), "CgkIj73WhbAeEAIQGg"),
					100);
		} else if (!gameHelper.isConnecting()) {
			loginGPGS();
		}
	}

	@Override
	public void getAchievementsGPGS() {
		if (gameHelper.isSignedIn()) {
			startActivityForResult(
					Games.Achievements.getAchievementsIntent(gameHelper
							.getApiClient()), 101);
		} else if (!gameHelper.isConnecting()) {
			loginGPGS();
		}
	}

	@Override
	public void onSignInFailed() {
	}

	@Override
	public void onSignInSucceeded() {
	}

	@Override
	public void showInterstitialAd(final Runnable then) {
		// TODO Auto-generated method stub
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (then != null) {
					interAd.setAdListener(new AdListener() {
						@Override
						public void onAdClosed() {
							Gdx.app.postRunnable(then);
							AdRequest.Builder builder = new AdRequest.Builder();
							AdRequest ad = builder.build();
							interAd.loadAd(ad);
						}
					});
				}
				interAd.show();
			}
		});
	}

	@Override
	public boolean checkForFaceBook() {
		// TODO Auto-generated method stub
		try {
			this.getPackageManager().getPackageInfo("com.facebook.katana", 0);
			return true;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	private boolean addPermission(List<String> permissionsList,
			String permission) {
		if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
			permissionsList.add(permission);
			// Check for Rationale Option
			if (!shouldShowRequestPermissionRationale(permission))
				return false;
		}
		return true;
	}

	private void showMessageOKCancel(String message) {
		new AlertDialog.Builder(MainActivity.this).setMessage(message).create()
				.show();
	}

	@SuppressWarnings("deprecation")
	@Override
	public int loadAchievementsDecideLevel() {
		// TODO Auto-generated method stub
		boolean fullLoad = false;
		long waitTime = 60L;
		PendingResult<LoadAchievementsResult> p = Games.Achievements.load(
				gameHelper.getApiClient(), fullLoad);
		Achievements.LoadAchievementsResult r = p.await(waitTime,
				TimeUnit.SECONDS);
		int status = r.getStatus().getStatusCode();
		if (status != GamesStatusCodes.STATUS_OK) {
			r.release();
			return -1; // Error Occurred
		}
		// process the loaded achievements
		AchievementBuffer buf = r.getAchievements();
		int bufSize = buf.getCount();
		int count = 0;
		for (int i = 0; i < bufSize; i++) {
			Achievement ach = buf.get(i);
			// here you now have access to the achievement's data
			if (ach.getState() == Achievement.STATE_UNLOCKED) {// the
																// achievement
																// ID string
				count++;
			}
		}
		buf.close();
		r.release();
		if (count == 0)
			return 0;
		else
			return count;
	}

	@Override
	public void getPackageNameForFeedback() {
		// TODO Auto-generated method stub
		final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
		try {
		    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
		} catch (android.content.ActivityNotFoundException anfe) {
		    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
		}
	}

	@Override
	public boolean isBannerShowing() {
		// TODO Auto-generated method stub
		return bannerAd.isShown();
	}
}