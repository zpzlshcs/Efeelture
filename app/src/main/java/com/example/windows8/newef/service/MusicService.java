package com.example.windows8.newef.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Handler;
import android.os.IBinder;

import com.example.windows8.newef.bean.LrcModel;
import com.example.windows8.newef.bean.Model;
import com.example.windows8.newef.util.Saomiao;

import java.io.IOException;
import java.util.ArrayList;

public class MusicService extends Service {

	MediaPlayer media;
	ArrayList<Model> alist;
	Saomiao scan;
	Mybroadmusic myBM;
	int num = 0;
	int n = 0;
	int check = 1;
	Handler handler;
	Handler lrchandler;
	ArrayList<LrcModel> lrclist;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

		alist = new ArrayList<Model>();
		lrclist = new ArrayList<LrcModel>();

		media = new MediaPlayer();
		scan = new Saomiao();
		alist = scan.query(this);
		myBM = new Mybroadmusic();
		handler = new Handler();
		lrchandler = new Handler();
		zhuce();

	}

	public class Mybroadmusic extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if (intent.getAction().equals("ACTION_INDEX")) {
				num = intent.getIntExtra("index", 0);

				String path = alist.get(num).getPath();
				setdata(path);
				panduan();
				play();
			} else if (intent.getAction().equals("ACTION_ISPLAY")) {
				// System.out.println(">>>>>>>>>>>>>>>");
				boolean isplay = intent.getBooleanExtra("isplay", false);
				if (isplay == false) {
					media.pause();
					handler.removeCallbacks(mRunnable);
					panduan();
				} else {
					media.start();
					handler.postDelayed(mRunnable, 1000);
					if(lrclist.size()!=0){
					lrchandler.postDelayed(lrcRunnable,100);
					}
				}
			} else if (intent.getAction().equals("ACTION_NEXT")) {
				int next = intent.getIntExtra("next", 0);
				if (next == 0) {
					if (check == 3) {
						n = (int) (Math.random() * alist.size());
						if (n == num) {
							n = (int) (Math.random() * alist.size());
						}
						String path = alist.get(n).getPath();
						setdata(path);
						panduan();
						play();
					} else {
						up();
					}
				} else {
					if (check == 3) {
						n = (int) (Math.random() * alist.size());
						if (n == num) {
							n = (int) (Math.random() * alist.size());
						}
						String path = alist.get(n).getPath();
						setdata(path);
						panduan();
						play();
					} else {
						down();
					}
				}
			} else if (intent.getAction().equals("ACTION_SEEKBAR")) {
				int progress = intent.getIntExtra("seekbar", 0);
				media.seekTo(progress);
			} else if (intent.getAction().equals("ACTION_STYLE")) {
				check = intent.getIntExtra("check", 0);

				if (check == 1) {
					nextmusic();
				}
				if (check == 2) {

					danqu();
				}
				if (check == 3) {
					suiji();
				}
			}

		}
	}
	public void panduan(){
		if(lrclist.size()==0){
			lrchandler.removeCallbacks(lrcRunnable);
		}
	}

	public void zhuce() {
		IntentFilter mFilter = new IntentFilter();
		mFilter.addAction("ACTION_INDEX");
		mFilter.addAction("ACTION_ISPLAY");
		mFilter.addAction("ACTION_NEXT");

		mFilter.addAction("ACTION_SEEKBAR");

		mFilter.addAction("ACTION_STYLE");

		registerReceiver(myBM, mFilter);
	}

	public void setdata(String path) {

		media.reset();

		try {
			media.setDataSource(path);
			media.prepare();

			lrclist = scan.redlrc(path);

			maxtime();
			nowmusic();

		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void play() {

		media.start();
		//System.out.println("=================" + lrclist.size());
		if (lrclist.size() != 0) {
			lrchandler.postDelayed(lrcRunnable, 100);
		}
		handler.postDelayed(mRunnable, 1000);
	}

	public void nextmusic() {
		media.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				num++;
				if (num > alist.size() - 1) {
					num = 0;
				}
				String path = alist.get(num).getPath();
				setdata(path);
				panduan();
				play();
			}
		});

	}

	public void suiji() {
		n = (int) (Math.random() * alist.size());
		if (n == num) {
			n = (int) (Math.random() * alist.size());
		}
		media.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				String path = alist.get(n).getPath();
				setdata(path);
				panduan();
				play();
			}
		});
	}

	public void danqu() {
		media.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				String path = alist.get(num).getPath();
				setdata(path);
				panduan();
				play();
			}
		});
	}

	public void up() {
		num--;
		if (num < 0) {
			num = alist.size() - 1;
		}
		String path = alist.get(num).getPath();
		setdata(path);
		panduan();
		play();
	}

	public void down() {
		num++;
		if (num > alist.size() - 1) {
			num = 0;
		}
		String path = alist.get(num).getPath();
		setdata(path);
		panduan();
		play();
	}

	public void maxtime() {
		Intent intent = new Intent();
		intent.setAction("ACTION_MAXTIME");
		intent.putExtra("maxtime", media.getDuration());
		sendBroadcast(intent);
	}

	public void nowtime() {
		Intent intent = new Intent();
		intent.setAction("ACTION_NOWTIME");
		intent.putExtra("nowtime", media.getCurrentPosition());
		sendBroadcast(intent);
	}
	public void nowmusic(){
		Intent intent = new Intent();
		intent.setAction("ACTION_NOWMUSIC");
		intent.putExtra("nowmusic",alist.get(num).getMusic_name());
		sendBroadcast(intent);
		
	}

	public int lrcindex() {
		int nowtime = 0, alltime = 0, index = 0;
		if (media.isPlaying()) {
			nowtime = media.getCurrentPosition();
			alltime = media.getDuration();
		}
		if (nowtime < alltime) {
			for (int i = 0; i < lrclist.size(); i++) {
				if (i < lrclist.size() - 1) {
					if (nowtime < lrclist.get(i).getTime() && i == 0) {
						index = i;
					}
					if (nowtime > lrclist.get(i).getTime() && nowtime < lrclist.get(i + 1).getTime()) {
						index = i;
					}

				}
				if (i == lrclist.size() - 1 && nowtime > lrclist.get(i).getTime()) {
					index = i;
				}
			}
		}

		return index;
	}

	Runnable lrcRunnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			String lrc = lrclist.get(lrcindex()).getIrc();

			System.out.println("[[[[[[[[[[[[[[[[[[[" + lrc);
			Intent intent = new Intent();
			intent.setAction("ACTION_LRC");
			intent.putExtra("geci", lrc);
			sendBroadcast(intent);

			lrchandler.postDelayed(lrcRunnable, 100);
		}
	};

	Runnable mRunnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub

			nowtime();

			handler.postDelayed(mRunnable, 1000);
		}
	};

}
